package com.itheima.reggie.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.reggie.domain.Setmeal;
import com.itheima.reggie.domain.SetmealDto;

/**
 * @author 小空
 * @create 2022-05-14 14:48
 * @description 菜品套餐业务层接口
 * @see com.itheima.reggie.service.impl.SetmealServiceImpl
 */
public interface SetmealService extends IService<Setmeal> {
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
}
