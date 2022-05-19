package com.itheima.reggie.controller;

import com.itheima.reggie.domain.R;
import com.itheima.reggie.domain.ShoppingCart;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

/**
 * @author 小空
 * @create 2022-05-19 18:49
 * @description 购物车控制层
 */
@RestController
@RequestMapping("/shoppingCart")
public class ShoppingCartController {
    @GetMapping("/list")
    public R selectShoppingCartByType() {
        ArrayList<ShoppingCart> arrayList = new ArrayList<>();
        return R.success(arrayList);
    }
}
