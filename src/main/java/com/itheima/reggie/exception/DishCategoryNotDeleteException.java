package com.itheima.reggie.exception;

/**
 * @author 小空
 * @create 2022-05-14 14:42
 * @description 菜品类别不能删除异常(自定义异常)
 */
public class DishCategoryNotDeleteException extends RuntimeException {
    public DishCategoryNotDeleteException() {
    }

    public DishCategoryNotDeleteException(String message) {
        super(message);
    }
}
