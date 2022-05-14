package com.itheima.reggie.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.itheima.reggie.domain.Category;
import com.itheima.reggie.domain.R;
import com.itheima.reggie.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author 小空
 * @create 2022-05-14 11:36
 * @description 菜品分类控制层
 */
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public R addDishCategory(@RequestBody Category category) {
        categoryService.save(category);
        return R.success("菜品分类添加成功!");
    }

    @GetMapping("/page")
    public R pagingQuery(int page, int pageSize) {
        IPage<Category> p = categoryService.pagingQuery(page, pageSize);
        return R.success(p);
    }

    @PutMapping
    public R updateCategory(@RequestBody Category category) {
        //根据菜品类别id修改
        categoryService.updateById(category);
        //返回数据
        return R.success("修改成功!");
    }

    @DeleteMapping
    public R deleteCategory(Long id) {
        categoryService.deleteCategory(id);
        return R.success("删除成功!");
    }
}
