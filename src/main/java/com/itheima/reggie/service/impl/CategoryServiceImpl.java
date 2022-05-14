package com.itheima.reggie.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.reggie.domain.Category;
import com.itheima.reggie.mapper.CategoryMapper;
import com.itheima.reggie.service.CategoryService;
import org.springframework.stereotype.Service;

/**
 * @author 小空
 * @create 2022-05-14 11:40
 * @description 菜品分类业务层实现类
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
    @Override
    public IPage<Category> pagingQuery(int page, int pageSize) {
        IPage<Category> p = new Page<>(page, pageSize);
        page(p);
        return p;
    }
}
