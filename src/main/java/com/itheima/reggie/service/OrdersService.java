package com.itheima.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.reggie.domain.Orders;

/**
 * @author 小空
 * @create 2022-05-20 16:46
 * @description 订单业务层接口
 */
public interface OrdersService extends IService<Orders> {
    /**
     * 订单支付
     *
     * @param orders 订单对象,包含订单信息
     */
    void pay(Orders orders);
}
