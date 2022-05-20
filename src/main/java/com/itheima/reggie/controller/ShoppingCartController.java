package com.itheima.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.itheima.reggie.common.BaseContext;
import com.itheima.reggie.domain.R;
import com.itheima.reggie.domain.ShoppingCart;
import com.itheima.reggie.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 小空
 * @create 2022-05-19 18:49
 * @description 购物车控制层
 */
@RestController
@RequestMapping("/shoppingCart")
public class ShoppingCartController {
    @Autowired
    private ShoppingCartService shoppingCartService;

    /**
     * 根据当前登录用户ID查询购物车列表，并对查询的
     * 结果进行创建时间的倒序排序
     *
     * @return List集合, 包含已点的菜品
     */
    @GetMapping("/list")
    public R selectShoppingCartByType() {
        List<ShoppingCart> shoppingCartList = shoppingCartService.selectShoppingCartByType();
        return R.success(shoppingCartList);
    }

    @PostMapping("/add")
    public R joinTheShoppingCart(@RequestBody ShoppingCart shoppingCart) {
        ShoppingCart shopCar = shoppingCartService.joinTheShoppingCart(shoppingCart);
        return R.success(shopCar);
    }

    @PostMapping("/sub")
    public R subtractionShoppingCart(@RequestBody ShoppingCart shoppingCart) {
        ShoppingCart shopCar = shoppingCartService.subtractionShoppingCart(shoppingCart);
        return R.success(shopCar);
    }

    @DeleteMapping("/clean")
    public R clearShoppingCart() {
        return shoppingCartService.clearShoppingCart();
    }
}
