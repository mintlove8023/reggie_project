package com.itheima.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itheima.reggie.common.PageBean;
import com.itheima.reggie.domain.Dish;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 小空
 * @create 2022-05-14 14:45
 * @description 单菜品持久层
 */
@Mapper
public interface DishMapper extends BaseMapper<Dish> {
    /**
     * 基于mybatis的分页条件查询
     *
     * @param page     当前页数
     * @param pageSize 当前页的数据条数
     * @param name     分页条件
     * @return List集合, 包含Dish对象信息
     */
    List<Dish> mybatisPagingFunction(@Param("page") int page, @Param("pageSize") int pageSize, @Param("name") String name);

    /**
     * 获取查询出来的数据总数
     *
     * @param name 分页条件
     * @return 数据总数
     */
    int mybatisGetPagingTotal(String name);
}
