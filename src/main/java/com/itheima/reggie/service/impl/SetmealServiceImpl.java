package com.itheima.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.reggie.domain.*;
import com.itheima.reggie.exception.SetmealEnableStatusException;
import com.itheima.reggie.mapper.SetmealMapper;
import com.itheima.reggie.service.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

    @Autowired
    private DishService dishService;

    @Override
    @CacheEvict(value = "setmealCache", allEntries = true)
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

    @Override
    @CacheEvict(value = "setmealCache", allEntries = true)
    public void deleteSetmeal(Long[] ids) {
        //1:必须先判断套餐是否为启用状态,如果为启用状态,则抛出错误提示,告诉不能删除
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Setmeal::getStatus, SETMEAL_STATUS_ENABLE).in(Setmeal::getId, Arrays.asList(ids));
        int count = count(queryWrapper);

        if (count > 0) {
            throw new SetmealEnableStatusException("该套餐已起售,请停售后再进行操作!");
        }

        //2:停售后,还需要判断当前套餐下是否有菜品数据,有,则删除,没有则删除套餐
        LambdaQueryWrapper<SetmealDish> qw = new LambdaQueryWrapper<>();
        qw.in(SetmealDish::getSetmealId, Arrays.asList(ids));
        setmealDishService.remove(qw);

        //3:删除套餐
        removeByIds(Arrays.asList(ids));
    }

    @Override
    @CacheEvict(value = "setmealCache", allEntries = true)
    public void updateSetmealStatus(Integer status, Long[] ids) {
        LambdaUpdateWrapper<Setmeal> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(Setmeal::getStatus, status).in(Setmeal::getId, Arrays.asList(ids));
        update(updateWrapper);
    }

    @Override
    @Cacheable(value = "setmealCache", key = "#categoryId")
    public List<Setmeal> selectSetmealByCategoryId(Long categoryId) {
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Setmeal::getCategoryId, categoryId)
                .eq(Setmeal::getStatus, SETMEAL_STATUS_ENABLE);
        return list(queryWrapper);
    }

    @Override
    public List<Dish> selectSetmealImageById(Long id) {
        //查询套餐下的菜品
        LambdaQueryWrapper<SetmealDish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SetmealDish::getSetmealId, id);
        List<SetmealDish> setmealDishes = setmealDishService.list(queryWrapper);

        //获取套餐下的菜品数据
        List<Dish> dishes = setmealDishes.stream().map(item -> {
            Long dishId = item.getDishId();
            Dish dish = dishService.getById(dishId);
            dish.setCopies(item.getCopies());
            return dish;
        }).collect(Collectors.toList());

        return dishes;
    }

    @Override
    public SetmealDto echoSetmealById(Long id) {
        //查询根据id查询出套餐
        Setmeal setmeal = getById(id);

        //根据套餐id查询所有的菜品
        LambdaQueryWrapper<SetmealDish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SetmealDish::getSetmealId, setmeal.getId());
        List<SetmealDish> setmealDishList = setmealDishService.list(queryWrapper);

        //将套餐下的菜品数据设置给Dto
        SetmealDto setmealDto = new SetmealDto();
        setmealDto.setSetmealDishes(setmealDishList);
        BeanUtils.copyProperties(setmeal, setmealDto);
        return setmealDto;
    }

    @Override
    @CacheEvict(value = "setmealCache", allEntries = true)
    public void updateSetmeal(SetmealDto setmealDto) {
        updateById(setmealDto);

        Long setmealId = setmealDto.getId();
        //先移除更新前的菜品数据
        LambdaQueryWrapper<SetmealDish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SetmealDish::getSetmealId, setmealId);
        setmealDishService.remove(queryWrapper);

        //获取新修改的菜品数据,将套餐下的菜品id进行绑定
        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
        for (SetmealDish setmealDish : setmealDishes) {
            setmealDish.setSetmealId(setmealDto.getId());
        }

        //更新套餐下的菜品数据
        setmealDishService.saveBatch(setmealDishes);
    }
}
