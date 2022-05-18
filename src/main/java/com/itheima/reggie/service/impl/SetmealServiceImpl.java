package com.itheima.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.reggie.domain.Category;
import com.itheima.reggie.domain.Setmeal;
import com.itheima.reggie.domain.SetmealDish;
import com.itheima.reggie.domain.SetmealDto;
import com.itheima.reggie.mapper.SetmealMapper;
import com.itheima.reggie.service.CategoryService;
import com.itheima.reggie.service.SetmealDishService;
import com.itheima.reggie.service.SetmealService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 小空
 * @create 2022-05-14 14:50
 * @description 菜品套餐业务层实现类
 */
@Service
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService {
    @Autowired
    private SetmealDishService setmealDishService;

    @Autowired
    private CategoryService categoryService;

    @Override
    public void addSetmeal(SetmealDto setmealDto) {
        //将套餐基本信息存储到数据库中
        save(setmealDto);

        //查询出套餐中的菜品数据并存储到数据库中
        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
        for (SetmealDish setmealDish : setmealDishes) {
            setmealDish.setSetmealId(setmealDto.getId());
        }
        setmealDishService.saveBatch(setmealDishes);
    }

    @Override
    public IPage<Setmeal> setmealPagingByCondition(int page, int pageSize, String name) {
        //分页条件查询
        IPage<Setmeal> p = new Page<>(page, pageSize);
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.isNotBlank(name), Setmeal::getName, name);
        //分页条件查询
        page(p, queryWrapper);

        //套餐分类设置
        List<Setmeal> records = p.getRecords();

        for (Setmeal setmeal : records) {
            Category category = categoryService.getById(setmeal.getCategoryId());
            setmeal.setCategoryName(category.getName());
        }
        return p;
    }

    
}
