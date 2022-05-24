package com.itheima.reggie.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.itheima.reggie.domain.Orders;
import com.itheima.reggie.domain.OrdersDto;
import com.itheima.reggie.domain.R;
import com.itheima.reggie.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author 小空
 * @create 2022-05-20 16:41
 * @description
 */
@RestController
@RequestMapping("/order")
public class OrdersController {
    @Autowired
    private OrdersService ordersService;

    /**
     * 用户下单付钱
     * A. 获得当前用户id, 查询当前用户的购物车数据
     * B. 根据当前登录用户id, 查询用户数据
     * C. 根据地址ID, 查询地址数据
     * D. 组装订单明细数据, 批量保存订单明细
     * E. 组装订单数据, 批量保存订单数据
     * F. 删除当前用户的购物车列表数据
     *
     * @return R
     */
    @PostMapping("/submit")
    public R pay(@RequestBody Orders orders) {
        ordersService.pay(orders);
        return R.success("支付成功!");
    }

    /**
     * 分页查询订单(历史订单和最新订单)
     *
     * @param page     当前页
     * @param pageSize 当前页显示的数据个数
     * @return 订单数据
     */
    @GetMapping("/userPage")
    public R selectOrderPages(int page, int pageSize) {
        IPage<Orders> p = ordersService.selectOrderPages(page, pageSize);
        return R.success(p);
    }

    @GetMapping("/page")
    public R selectOrdersPage(int page, int pageSize, Long number, String beginTime, String endTime) {
        IPage<Orders> p = ordersService.selectOrdersPage(page, pageSize, number, beginTime, endTime);
        return R.success(p);
    }

    @PutMapping
    public R updateDeliveryStatus(@RequestBody Orders orders) {
        ordersService.updateDeliveryStatus(orders);
        return R.success("派送状态修改成功!");
    }

    @PostMapping("/again")
    public R oneMoreOrder(@RequestBody Orders orders) {
        ordersService.oneMoreOrder(orders);
        return R.success(null);
    }
}
