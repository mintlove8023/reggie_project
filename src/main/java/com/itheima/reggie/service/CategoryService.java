package com.itheima.reggie.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.reggie.domain.Category;
import com.itheima.reggie.domain.R;

/**
 * @author 小空
 * @create 2022-05-14 11:37
 * @description 菜品分类业务层
 */
public interface CategoryService extends IService<Category> {
    /**
     * 菜品分页查询展示
     *
     * @param page     当前页数
     * @param pageSize 当前页数据个数
     * @return IPage对象, 包含查询到的分页数据
     */
    IPage<Category> pagingQuery(int page, int pageSize);

    /**
     * 根据id删除菜品类别
     * 菜品类别分为单菜品和套餐菜品
     * 如果这两个菜品类别下没有菜品数据,那么才可以进行菜品类别删除
     * 否则不能删除,直接抛异常
     *
     * @param id 菜品类别id
     * @see com.itheima.reggie.domain.Dish [单菜品]
     * @see com.itheima.reggie.domain.Setmeal [套餐菜品]
     */
    void deleteCategory(Long id);

    /**
     * 查找菜品分类
     *
     * @param type 菜品分类类型
     * @return R, 包含菜品分类信息
     */
    R findDishCategoryByType(int type);
}
