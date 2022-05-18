package com.itheima.reggie.exception;

/**
 * @author 小空
 * @create 2022-05-18 16:37
 * @description 发送短信网络异常
 */
public class SendMessageInternetException extends RuntimeException {
    public SendMessageInternetException() {
        super();
    }

    public SendMessageInternetException(String message) {
        super(message);
    }
}
