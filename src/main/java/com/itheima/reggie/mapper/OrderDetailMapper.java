package com.itheima.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itheima.reggie.domain.OrderDetail;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 小空
 * @create 2022-05-22 09:31
 * @description 订单详情持久层
 */
@Mapper
public interface OrderDetailMapper extends BaseMapper<OrderDetail> {
}
