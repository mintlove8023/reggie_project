package com.itheima.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.reggie.common.BaseContext;
import com.itheima.reggie.domain.R;
import com.itheima.reggie.domain.ShoppingCart;
import com.itheima.reggie.domain.User;
import com.itheima.reggie.mapper.ShoppingCartMapper;
import com.itheima.reggie.service.ShoppingCartService;
import com.itheima.reggie.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author 小空
 * @create 2022-05-19 18:47
 * @description 购物车业务层实现类
 * 添加购物车
 * 查询购物车
 * 清空购物车
 */
@Service
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart> implements ShoppingCartService {
    @Autowired
    private UserService userService;


    /**
     * 获取当前登录用户，为购物车对象赋值
     * 根据当前登录用户ID 及 本次添加的菜品ID/套餐ID，查询购物车数据是否存在
     * 如果已经存在，就在原来数量基础上加1
     * 如果不存在，则添加到购物车，数量默认就是1
     *
     * @param shoppingCart ShoppingCart对象,包含了购物车中点餐的信息
     */
    @Override
    public ShoppingCart joinTheShoppingCart(ShoppingCart shoppingCart) {
        //获取当前登录用户，为购物车对象赋值
        Long userId = BaseContext.getId();
        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        if (userId != null) {
            shoppingCart.setUserId(userId);
            queryWrapper.eq(ShoppingCart::getUserId, userId);
        }

        //根据当前登录用户ID 及 本次添加的菜品ID/套餐ID，查询购物车数据是否存在
        Long dishId = shoppingCart.getDishId();
        if (dishId != null) {
            queryWrapper.eq(ShoppingCart::getDishId, dishId);
        } else {
            queryWrapper.eq(ShoppingCart::getSetmealId, shoppingCart.getSetmealId());
        }
        ShoppingCart shopCar = getOne(queryWrapper);
        if (shopCar != null) {
            //如果已经存在，就在原来数量基础上加1
            Integer afterNum = shopCar.getNumber();
            shopCar.setNumber(afterNum + 1);
            updateById(shopCar);
        } else {
            //如果不存在，则添加到购物车，数量默认就是1
            shoppingCart.setNumber(1);
            shoppingCart.setCreateTime(LocalDateTime.now());
            save(shoppingCart);
            shopCar = shoppingCart;
        }
        return shopCar;
    }

    @Override
    public List<ShoppingCart> selectShoppingCartByType() {
        Long userId = BaseContext.getId();
        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId, userId).orderByAsc(ShoppingCart::getCreateTime);
        return list(queryWrapper);
    }
}
