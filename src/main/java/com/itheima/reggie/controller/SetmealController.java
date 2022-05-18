package com.itheima.reggie.controller;

import com.itheima.reggie.domain.R;
import com.itheima.reggie.domain.Setmeal;
import com.itheima.reggie.domain.SetmealDto;
import com.itheima.reggie.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
