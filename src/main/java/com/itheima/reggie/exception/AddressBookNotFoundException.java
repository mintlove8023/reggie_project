package com.itheima.reggie.exception;

/**
 * @author 小空
 * @create 2022-05-22 09:49
 * @description 收货地址未找到异常(自定义异常)
 */
public class AddressBookNotFoundException extends RuntimeException {
    public AddressBookNotFoundException() {
        super();
    }

    public AddressBookNotFoundException(String message) {
        super(message);
    }
}
