package com.itheima.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.itheima.reggie.common.BaseContext;
import com.itheima.reggie.domain.*;
import com.itheima.reggie.mapper.OrdersMapper;
import com.itheima.reggie.service.*;
import com.itheima.reggie.utils.MyTimeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @author 小空
 * @create 2022-05-20 16:47
 * @description 订单业务层实现类
 */
@Service
@Slf4j
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders> implements OrdersService {
    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private UserService userService;

    @Autowired
    private AddressBookService addressBookService;

    @Autowired
    private OrderDetailService orderDetailService;


    /**
     * 用户下单付钱
     * 获取当前登录的用户id
     * A. 根据当前登录用户id, 查询当前用户的购物车数据
     * B. 根据当前登录用户id, 查询用户数据
     * C. 根据地址ID, 查询地址数据
     * D. 组装订单明细数据, 批量保存订单明细
     * E. 组装订单数据, 批量保存订单数据
     * F. 删除当前用户的购物车列表数据
     */
    @Override
    public void pay(Orders orders) {
        //获得当前用户id, 查询当前用户的购物车数据
        Long userId = BaseContext.getId();
        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId, userId);
        List<ShoppingCart> shoppingCartList = shoppingCartService.list(queryWrapper);

        //根据当前登录用户id, 查询用户数据
        User currentLoginUser = userService.getById(userId);

        //根据地址ID, 查询地址数据
        Long addressBookId = orders.getAddressBookId();
        AddressBook addressBook = addressBookService.getById(addressBookId);

        //组装订单明细数据, 批量保存订单明细
        long orderId = IdWorker.getId();
        AtomicInteger amount = new AtomicInteger(0);
        List<OrderDetail> orderDetailList = shoppingCartList.stream().map((item) -> {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrderId(orderId);
            orderDetail.setNumber(item.getNumber());
            orderDetail.setDishFlavor(item.getDishFlavor());
            orderDetail.setDishId(item.getDishId());
            orderDetail.setSetmealId(item.getSetmealId());
            orderDetail.setName(item.getName());
            orderDetail.setImage(item.getImage());
            orderDetail.setAmount(item.getAmount());
            amount.addAndGet(item.getAmount().multiply(new BigDecimal(item.getNumber())).intValue());
            return orderDetail;
        }).collect(Collectors.toList());

        //组装订单数据, 批量保存订单数据
        orders.setId(orderId);
        orders.setOrderTime(LocalDateTime.now());
        orders.setCheckoutTime(LocalDateTime.now());
        orders.setStatus(2);
        orders.setAmount(new BigDecimal(amount.get()));//总金额
        orders.setUserId(userId);
        orders.setNumber(String.valueOf(orderId));
        orders.setUserName(currentLoginUser.getName());
        orders.setConsignee(addressBook.getConsignee());
        orders.setPhone(addressBook.getPhone());
        orders.setAddress((addressBook.getProvinceName() == null ? "" :
                addressBook.getProvinceName())
                + (addressBook.getCityName() == null ? "" : addressBook.getCityName())
                + (addressBook.getDistrictName() == null ? "" :
                addressBook.getDistrictName())
                + (addressBook.getDetail() == null ? "" : addressBook.getDetail()));

        //存储订单数据到数据库中
        save(orders);

        //存储订单详情数据到数据库中
        orderDetailService.saveBatch(orderDetailList);

        //删除当前用户的购物车列表数据
        shoppingCartService.clearShoppingCart();
    }

    @Override
    public IPage<Orders> selectOrderPages(int page, int pageSize) {
        IPage<Orders> p = new Page<>(page, pageSize);
        //根据当前登录的用户id,获取当前用户订单
        Long userId = BaseContext.getId();
        LambdaQueryWrapper<Orders> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Orders::getUserId, userId);

        //分页查询当前用户的订单数据
        page(p, queryWrapper);

        List<Orders> ordersList = p.getRecords();
        ordersList.stream().map(item -> {
            //根据订单id去查询订单详情数据
            LambdaQueryWrapper<OrderDetail> qw = new LambdaQueryWrapper<>();
            qw.eq(OrderDetail::getOrderId, item.getId());
            List<OrderDetail> orderDetailList = orderDetailService.list(qw);
            item.setOrderDetails(orderDetailList);
            return item;
        }).collect(Collectors.toList());
        return p;
    }

    @Override
    public IPage<Orders> selectOrdersPage(int page, int pageSize, String number, String beginTime, String endTime) {
        //根据条件查询订单数据
        IPage<Orders> p = new Page<>(page, pageSize);
        LambdaQueryWrapper<Orders> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(number != null, Orders::getNumber, number)
                .ge(beginTime != null, Orders::getOrderTime, beginTime)
                .le(endTime != null, Orders::getOrderTime, endTime);
        page(p, queryWrapper);

        //返回分页查询条件
        return p;
    }

    @Override
    public void updateDeliveryStatus(Orders orders) {
        LambdaUpdateWrapper<Orders> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(Orders::getStatus, orders.getStatus())
                .eq(Orders::getId, orders.getId());
        update(updateWrapper);
    }

    @Override
    public void oneMoreOrder(Orders orders) {
        //根据当前登录的用户id去查询购物车,如果购物车有数据则清除
        Long userId = BaseContext.getId();
        LambdaQueryWrapper<ShoppingCart> qw = new LambdaQueryWrapper<>();
        qw.eq(ShoppingCart::getUserId, userId);
        int shoppingCarServiceCount = shoppingCartService.count(qw);
        if (shoppingCarServiceCount > 0) {
            shoppingCartService.clearShoppingCart();
        }

        //获取订单id
        Long orderId = orders.getId();

        //查询订单详情,获取到订单中点的菜品数据
        LambdaQueryWrapper<OrderDetail> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OrderDetail::getOrderId, orderId);
        List<OrderDetail> orderDetailList = orderDetailService.list(queryWrapper);

        //再来一单,将订单中的数据设置到购物车中
        List<ShoppingCart> shoppingCartList = orderDetailList.stream().map(item ->
                ShoppingCart.builder()
                        .name(item.getName())
                        .image(item.getImage())
                        .userId(BaseContext.getId())
                        .dishId(item.getDishId())
                        .setmealId(item.getSetmealId())
                        .dishFlavor(item.getDishFlavor())
                        .number(item.getNumber())
                        .amount(item.getAmount())
                        .build()).collect(Collectors.toList());

        //存储数据到购物车
        shoppingCartService.saveBatch(shoppingCartList);
    }
}
