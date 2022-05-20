package com.itheima.reggie.controller;

import com.itheima.reggie.domain.R;
import com.itheima.reggie.domain.ShoppingCart;
import com.itheima.reggie.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

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

    @GetMapping("/list")
    public R selectShoppingCartByType() {
        ArrayList<ShoppingCart> arrayList = new ArrayList<>();
        return R.success(arrayList);
    }


    @PostMapping("/add")
    public R joinTheShoppingCart(@RequestBody ShoppingCart shoppingCart) {
        ShoppingCart shopCar = shoppingCartService.joinTheShoppingCart(shoppingCart);
        return R.success(shopCar);
    }
}
