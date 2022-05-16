package com.itheima.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itheima.reggie.domain.DishFlavor;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 小空
 * @create 2022-05-16 11:52
 * @description 菜品口味持久层
 */
@Mapper
public interface DishFlavorMapper extends BaseMapper<DishFlavor> {
}
