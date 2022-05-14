package com.itheima.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.reggie.domain.Category;
import com.itheima.reggie.domain.Dish;
import com.itheima.reggie.domain.Setmeal;
import com.itheima.reggie.exception.DishCategoryNotDeleteException;
import com.itheima.reggie.mapper.CategoryMapper;
import com.itheima.reggie.service.CategoryService;
import com.itheima.reggie.service.DishService;
import com.itheima.reggie.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 小空
 * @create 2022-05-14 11:40
 * @description 菜品分类业务层实现类
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
    @Autowired
    private DishService dishService;

    @Autowired
    private SetmealService setmealService;

    @Override
    public IPage<Category> pagingQuery(int page, int pageSize) {
        IPage<Category> p = new Page<>(page, pageSize);
        page(p);
        return p;
    }

    @Override
    public void deleteCategory(Long id) {
        //获取指定id菜品类别下的单菜品数量
        LambdaQueryWrapper<Dish> dishQueryWrapper = new LambdaQueryWrapper<>();
        dishQueryWrapper.eq(Dish::getCategoryId, id);
        int dishCount = dishService.count(dishQueryWrapper);

        //获取指定id菜品类别下的套餐菜品数量
        LambdaQueryWrapper<Setmeal> setmealQueryWrapper = new LambdaQueryWrapper<>();
        setmealQueryWrapper.eq(Setmeal::getCategoryId, id);
        int setmealCount = setmealService.count(setmealQueryWrapper);

        //如果指定id的菜品类别下没有任何菜品和套餐才能删除
        if (dishCount == 0 && setmealCount == 0) {
            removeById(id);
            return;
        }

        //否则抛出异常,表示删除菜品类别失败!
        throw new DishCategoryNotDeleteException("菜品删除失败!该菜品类别下有已保存的菜品或套餐");
    }
}
