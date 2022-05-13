package com.itheima.reggie.exception;


/**
 * @author 小空
 * @create 2022-05-13 14:55
 * @description 用户已存在异常(自定义异常)
 */
public class UserExistsException extends RuntimeException {
    public UserExistsException() {
        super();
    }

    public UserExistsException(String message) {
        super(message);
    }
}
