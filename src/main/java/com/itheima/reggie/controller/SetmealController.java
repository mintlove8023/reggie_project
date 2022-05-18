package com.itheima.reggie.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.itheima.reggie.domain.R;
import com.itheima.reggie.domain.Setmeal;
import com.itheima.reggie.domain.SetmealDto;
import com.itheima.reggie.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

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
}
