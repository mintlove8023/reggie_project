package com.itheima.reggie.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Data
public class R implements Serializable {

    private Integer code; //编码：1成功，0和其它数字为失败

    private String msg; //错误信息

    private Object data; //数据

    public static R success(Object object) {
        R r = new R();
        r.data = object;
        r.code = 1;
        return r;
    }

    public static R error(String msg) {
        R r = new R();
        r.msg = msg;
        r.code = 0;
        return r;
    }
}
