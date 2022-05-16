package com.itheima.reggie.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.reggie.common.PageBean;
import com.itheima.reggie.domain.Category;
import com.itheima.reggie.domain.Dish;
import com.itheima.reggie.domain.DishDto;
import com.itheima.reggie.domain.DishFlavor;
import com.itheima.reggie.mapper.DishMapper;
import com.itheima.reggie.service.CategoryService;
import com.itheima.reggie.service.DishFlavorService;
import com.itheima.reggie.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 小空
 * @create 2022-05-14 14:47
 * @description 单菜品业务层实现类
 */
@Service
@Slf4j
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {
    @Autowired
    private DishFlavorService dishFlavorService;

    @Autowired
    private DishMapper dishMapper;

    @Autowired
    private CategoryService categoryService;

    @Override
    public void addDish(DishDto dishDto) {
        //将菜品信息插入数据库
        save(dishDto);

        //遍历获取口味信息配置,并为口味设置菜品id
        List<DishFlavor> flavors = dishDto.getFlavors();
        for (DishFlavor dishFlavor : flavors) {
            dishFlavor.setDishId(dishDto.getId());
        }
        //将菜品口味配置信息插入数据库
        dishFlavorService.saveBatch(flavors);
    }

    @Override
    public IPage<Dish> selectPagingCondition(int page, int pageSize, String name) {
        IPage<Dish> p = new Page<>(page, pageSize);
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(name), Dish::getName, name);
        dishMapper.selectPage(p, queryWrapper);

        List<Dish> pageRecords = p.getRecords();
        for (Dish dish : pageRecords) {
            Category category = categoryService.getById(dish.getCategoryId());
            dish.setCategoryName(category.getName());
        }
        return p;
    }

    @Override
    public PageBean mybatisPagingFunction(int page, int pageSize, String name) {
        int start = (page - 1) * pageSize;
        List<Dish> dishList = dishMapper.mybatisPagingFunction(start, pageSize, name);
        int total = dishMapper.mybatisGetPagingTotal(name);
        //封装分页条件查询数据与数据总条数
        PageBean pb = new PageBean();
        pb.setRecords(dishList);
        pb.setTotal(total);
        return pb;
    }
}
