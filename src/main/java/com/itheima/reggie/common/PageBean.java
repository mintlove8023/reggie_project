package com.itheima.reggie.common;

import lombok.Data;

import java.util.List;

/**
 * @author 小空
 * @create 2022-05-16 15:20
 * @description mybatis分页
 */
@Data
public class PageBean {
    private List<?> records;
    private Integer total;
}
