package com.itheima.reggie.utils;

import org.apache.commons.lang.StringUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author 小空
 * @create 2022-05-23 20:10
 * @description 日期工具类
 */
public class MyTimeUtils {
    private MyTimeUtils() {
    }

    /**
     * 按照指定日期格式,字符串转成LocalDateTime日期对象
     *
     * @param date   字符串日期
     * @param format 格式化规则
     * @return LocalDateTime对象
     */
    public static LocalDateTime string2LocalDateTime(String date, String format) {
        if (date == null) {
            return null;
        }
        return LocalDateTime.parse(date, DateTimeFormatter.ofPattern(format));
    }
}
