package com.itheima.reggie.controller;

import com.itheima.reggie.common.PageBean;
import com.itheima.reggie.domain.*;
import com.itheima.reggie.service.DishService;
import com.sun.org.apache.regexp.internal.RE;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 小空
 * @create 2022-05-16 11:45
 * @description 菜品管理控制层
 */
@RestController
@RequestMapping("/dish")
@Slf4j
public class DishController {
    @Autowired
    private DishService dishService;

    @PostMapping
    public R addDish(@RequestBody DishDto dishDto) {
        dishService.addDish(dishDto);
        return R.success("添加菜品成功!");
    }

    @GetMapping("/page")
    public R selectPagingCondition(int page, int pageSize, String name) {
        //mybatis-plus实现分页条件查询
        //IPage<Dish> p = dishService.selectPagingCondition(page, pageSize, name);

        //mybatis实现分页条件查询
        PageBean p = dishService.mybatisPagingFunction(page, pageSize, name);
        return R.success(p);
    }

    @GetMapping("{id}")
    public R echoDishData(@PathVariable Long id) {
        DishDto dishDto = dishService.echoDishData(id);
        return R.success(dishDto);
    }

    @PutMapping
    public R updateDish(@RequestBody DishDto dishDto) {
        dishService.updateDish(dishDto);
        return R.success("修改成功!");
    }

    @DeleteMapping
    public R deleteDish(Long[] ids) {
        dishService.deleteDish(ids);
        return R.success("删除成功!");
    }

    @PostMapping("/status/{status}")
    public R dishSaleStatus(@PathVariable Integer status, Long[] ids) {
        dishService.dishSaleStatus(status, ids);
        return R.success("起售成功!");
    }

    @GetMapping("/list")
    public R selectDishByCategoryId(Long categoryId) {
        List<Dish> setmealList = dishService.selectDishByCategoryId(categoryId);
        return R.success(setmealList);
    }
}
