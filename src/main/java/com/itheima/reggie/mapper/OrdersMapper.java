package com.itheima.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itheima.reggie.domain.Orders;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 小空
 * @create 2022-05-20 16:45
 * @description 订单持久层
 */
@Mapper
public interface OrdersMapper extends BaseMapper<Orders> {
}
