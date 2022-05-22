package com.itheima.reggie.controller;

import com.itheima.reggie.domain.Orders;
import com.itheima.reggie.domain.R;
import com.itheima.reggie.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
}
