package com.itheima.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itheima.reggie.domain.Category;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 小空
 * @create 2022-05-14 11:39
 * @description 菜品持久层
 */
@Mapper
public interface CategoryMapper extends BaseMapper<Category> {
}
