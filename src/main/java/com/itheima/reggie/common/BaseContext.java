package com.itheima.reggie.common;

import org.springframework.stereotype.Component;

/**
 * @author 小空
 * @create 2022-05-14 10:35
 * @description 主要用来维护数据在线程之间传递
 */
public class BaseContext {
    private static ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    /**
     * 通过ThreadLocal设置id
     *
     * @param id 员工id
     */
    public static void setId(Long id) {
        threadLocal.set(id);
    }

    /**
     * 获取id值
     * @return 员工id
     */
    public static Long getId() {
        return threadLocal.get();
    }
}
