package com.itheima.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itheima.reggie.domain.Dish;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 小空
 * @create 2022-05-14 14:45
 * @description 单菜品持久层
 */
@Mapper
public interface DishMapper extends BaseMapper<Dish> {
}
