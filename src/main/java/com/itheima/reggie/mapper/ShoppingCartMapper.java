package com.itheima.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itheima.reggie.domain.ShoppingCart;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 小空
 * @create 2022-05-19 18:45
 * @description 购物车持久层
 */
@Mapper
public interface ShoppingCartMapper extends BaseMapper<ShoppingCart> {
}
