package com.itheima.reggie.exception;

import com.itheima.reggie.domain.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author 小空
 * @create 2022-05-13 14:52
 * @description 全局异常处理
 */
@RestControllerAdvice
@Slf4j
public class HandlerGlobalException {

    @ExceptionHandler({UserExistsException.class})
    public R handlerUserExistsException(UserExistsException e) {
        return R.error(e.getMessage());
    }

    @ExceptionHandler(DishCategoryNotDeleteException.class)
    public R handlerDishCategoryDeleteException(DishCategoryNotDeleteException e) {
        return R.error(e.getMessage());
    }

    @ExceptionHandler(SetmealEnableStatusException.class)
    public R handlerSetmealEnableStatusException(SetmealEnableStatusException e) {
        return R.error(e.getMessage());
    }

    @ExceptionHandler(SendMessageInternetException.class)
    public R handlerSendMessageInternetException(SendMessageInternetException e) {
        return R.error(e.getMessage());
    }

    @ExceptionHandler(AddressBookNotFoundException.class)
    public R handlerSendMessageInternetException(AddressBookNotFoundException e) {
        return R.error(e.getMessage());
    }
}
