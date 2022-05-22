package com.itheima.reggie.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.itheima.reggie.domain.*;
import com.itheima.reggie.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 小空
 * @create 2022-05-18 10:11
 * @description 套餐管理控制层
 */
@RestController
@RequestMapping("/setmeal")
public class SetmealController {
    @Autowired
    private SetmealService setmealService;

    @PostMapping
    public R addSetmeal(@RequestBody SetmealDto setmealDto) {
        setmealService.addSetmeal(setmealDto);
        return R.success("添加成功!");
    }

    @GetMapping("/page")
    public R setmealPagingByCondition(int page, int pageSize, String name) {
        IPage<Setmeal> p = setmealService.setmealPagingByCondition(page, pageSize, name);
        return R.success(p);
    }

    @DeleteMapping
    public R deleteSetmeal(Long[] ids) {
        setmealService.deleteSetmeal(ids);
        return R.success("删除成功!");
    }

    @PostMapping("/status/{status}")
    public R updateSetmealStatus(@PathVariable Integer status, Long[] ids) {
        setmealService.updateSetmealStatus(status, ids);
        return R.success("状态修改成功!");
    }

    @GetMapping("/list")
    public R selectSetmealByCategoryId(Long categoryId) {
        List<Setmeal> setmealList = setmealService.selectSetmealByCategoryId(categoryId);
        return R.success(setmealList);
    }

    @GetMapping("/dish/{id}")
    public R selectSetmealImageById(@PathVariable Long id) {
        List<Dish> setmealOnDishList = setmealService.selectSetmealImageById(id);
        return R.success(setmealOnDishList);
    }
}
