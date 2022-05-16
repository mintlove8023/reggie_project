package com.itheima.reggie.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.reggie.common.PageBean;
import com.itheima.reggie.domain.Dish;
import com.itheima.reggie.domain.DishDto;

/**
 * @author 小空
 * @create 2022-05-14 14:47
 * @description 单菜品业务层接口
 * @see com.itheima.reggie.service.impl.DishServiceImpl
 */
public interface DishService extends IService<Dish> {
    /**
     * 添加菜品
     *
     * @param dishDto Dish菜品对象
     */
    void addDish(DishDto dishDto);

    /**
     * 菜品管理分页条件查询(Mybatis-Plus)
     *
     * @param page     页数
     * @param pageSize 当前页数展示的数据条数
     * @param name     搜索条件
     * @return Page对象, 包含了所以菜品信息
     */
    IPage<Dish> selectPagingCondition(int page, int pageSize, String name);

    /**
     * 菜品管理分页条件查询(Mybatis)
     *
     * @param page     页数
     * @param pageSize 当前页数展示的数据条数
     * @param name     搜索条件
     * @return PageBean对象, 包含了所以菜品信息
     */
    PageBean mybatisPagingFunction(int page, int pageSize, String name);

    /**
     * 回显菜品数据
     *
     * @param id 菜品id
     * @return DishDto对象, 包含菜品基本信息和口味信息
     */
    DishDto echoDishData(Long id);

    /**
     * 修改Dish菜品
     *
     * @param dishDto DishDto对象, 包含菜品基本信息和口味信息
     */
    void updateDish(DishDto dishDto);

    /**
     * 删除Dish菜品,包括了批量删除
     *
     * @param ids 存放菜品的id
     */
    void deleteDish(Long[] ids);

    /**
     * 修改Dish菜品售卖状态
     *
     * @param status 售卖状态
     * @param ids    菜品id
     */
    void dishSaleStatus(Integer status, Long[] ids);
}
