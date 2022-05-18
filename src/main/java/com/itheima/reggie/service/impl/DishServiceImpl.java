package com.itheima.reggie.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.reggie.common.PageBean;
import com.itheima.reggie.domain.*;
import com.itheima.reggie.mapper.DishMapper;
import com.itheima.reggie.service.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
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

    @Autowired
    private SetmealDishService setmealDishService;

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

    @Override
    public DishDto echoDishData(Long id) {
        //查询菜品id
        Dish dish = dishMapper.selectById(id);
        //根据菜品id查询出口味信息(口味信息有多个)
        LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DishFlavor::getDishId, dish.getId());
        List<DishFlavor> flavorList = dishFlavorService.list(queryWrapper);
        //设置口味信息到DishDto
        DishDto dishDto = new DishDto();
        dishDto.setFlavors(flavorList);
        //将Dish菜品数据封装数据到DishDto
        BeanUtils.copyProperties(dish, dishDto);
        return dishDto;
    }

    @Override
    @Transactional
    public void updateDish(DishDto dishDto) {
        //1:修改存储Dish菜品基本信息到数据库
        updateById(dishDto);

        //2:清除口味信息
        LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DishFlavor::getDishId, dishDto.getId());
        dishFlavorService.remove(queryWrapper);

        //2.1:遍历获取口味信息配置,并为口味设置菜品id
        List<DishFlavor> flavors = dishDto.getFlavors();
        for (DishFlavor dishFlavor : flavors) {
            dishFlavor.setDishId(dishDto.getId());
        }

        //2.2:存储口味信息到数据库
        dishFlavorService.saveBatch(flavors);
    }

    @Override
    public void deleteDish(Long[] ids) {
        LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(DishFlavor::getDishId, Arrays.asList(ids));
        dishFlavorService.remove(queryWrapper);
        removeByIds(Arrays.asList(ids));
    }

    @Override
    public void dishSaleStatus(Integer status, Long[] ids) {
        LambdaUpdateWrapper<Dish> queryWrapper = new LambdaUpdateWrapper<>();
        queryWrapper.set(Dish::getStatus, status)
                .in(Dish::getId, Arrays.asList(ids));
        update(queryWrapper);
    }

    @Override
    public List<Dish> selectDishByCategoryId(Long categoryId) {
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Dish::getCategoryId, categoryId);
        return list(queryWrapper);
    }
}