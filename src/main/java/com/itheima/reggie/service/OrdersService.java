package com.itheima.reggie.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.reggie.domain.Orders;

/**
 * @author 小空
 * @create 2022-05-20 16:46
 * @description 订单业务层接口
 * @see com.itheima.reggie.service.impl.OrdersServiceImpl
 */
public interface OrdersService extends IService<Orders> {
    String DATE_FORMAT_PATTNER = "yyyy-MM-dd HH:mm:ss";

    /**
     * 订单支付
     *
     * @param orders 订单对象,包含订单信息
     */
    void pay(Orders orders);

    /**
     * 分页查询订单(历史订单和最新订单)
     *
     * @param page     当前页
     * @param pageSize 当前页显示的数据个数
     * @return 订单数据
     */
    IPage<Orders> selectOrderPages(int page, int pageSize);

    /**
     * 分页条件查询订单
     *
     * @param page      当前页
     * @param pageSize  当前页显示的数据条数
     * @param number    订单号
     * @param beginTime 订单下单的开始时间
     * @param endTime   订单下单的结束时间
     * @return Page对象, 包含订单数据
     */
    IPage<Orders> selectOrdersPage(int page, int pageSize, Long number, String beginTime, String endTime);

    /**
     * @param orders Orders订单对象
     */
    void updateDeliveryStatus(Orders orders);

    /**
     * 再来一单操作
     *
     * @param orders 订单对象
     */
    void oneMoreOrder(Orders orders);
}
