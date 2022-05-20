package com.itheima.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.reggie.domain.ShoppingCart;

import java.util.List;

/**
 * @author 小空
 * @create 2022-05-19 18:46
 * @description 购物车业务层接口
 * @see com.itheima.reggie.service.impl.ShoppingCartServiceImpl
 */
public interface ShoppingCartService extends IService<ShoppingCart> {
    /**
     * @param shoppingCart ShoppingCart对象,包含了购物车中点餐的信息
     */
    ShoppingCart joinTheShoppingCart(ShoppingCart shoppingCart);

    /**
     * 根据当前登录用户ID查询购物车列表，并对查询的
     * 结果进行创建时间的倒序排序
     *
     * @return List集合, 包含购物车点餐信息
     */
    List<ShoppingCart> selectShoppingCartByType();
}
