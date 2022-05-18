package com.itheima.reggie.service;

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
}
