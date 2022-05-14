package com.itheima.reggie.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.reggie.domain.Category;

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
}
