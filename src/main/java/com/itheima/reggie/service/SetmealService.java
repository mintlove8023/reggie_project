package com.itheima.reggie.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.reggie.domain.*;

import java.util.List;

/**
 * @author 小空
 * @create 2022-05-14 14:48
 * @description 菜品套餐业务层接口
 * @see com.itheima.reggie.service.impl.SetmealServiceImpl
 */
public interface SetmealService extends IService<Setmeal> {
    /**
     * 套餐启用状态
     */
    int SETMEAL_STATUS_ENABLE = 1;
    /**
     * 套餐禁用状态
     */
    int SETMEAL_STATUS_DISABLE = 0;

    /**
     * 添加套餐
     *
     * @param setmealDto
     */
    void addSetmeal(SetmealDto setmealDto);

    /**
     * 分页条件查询套餐
     *
     * @param page     当前页
     * @param pageSize 当前页数据条数
     * @param name     模糊查询关键字
     * @return Page对象, 展示出套餐分页数据
     */
    IPage<Setmeal> setmealPagingByCondition(int page, int pageSize, String name);

    /**
     * 批量删除套餐
     * 1:必须先判断套餐是否为启用状态,如果为启用状态,则抛出错误提示,告诉不能删除
     * 2:停售后,还需要判断当前套餐下是否有菜品数据,有,则删除,没有则删除套餐
     *
     * @param ids 套餐id数据
     */
    void deleteSetmeal(Long[] ids);

    /**
     * 批量起售与停售套餐
     *
     * @param status 起售停售状态
     * @param ids    套餐id
     */
    void updateSetmealStatus(Integer status, Long[] ids);

    /**
     * 根据CategoryId(分类id)查询所有套餐
     *
     * @param categoryId 分类id
     * @return List集合, 包含所有套餐
     */
    List<Setmeal> selectSetmealByCategoryId(Long categoryId);

    /**
     * 根据套餐id展示套餐图片
     *
     * @param id 套餐id
     * @return ShoppingCart, 包含购物车套餐信息
     */
    List<Dish> selectSetmealImageById(Long id);

    /**
     * 根据id回显[修改套餐]中的数据
     *
     * @param id 套餐id
     * @return SetmealDto, 包含套餐基本信息,和套餐下的菜品数据
     */
    SetmealDto echoSetmealById(Long id);
}
