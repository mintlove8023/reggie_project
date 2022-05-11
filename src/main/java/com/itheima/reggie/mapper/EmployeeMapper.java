package com.itheima.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itheima.reggie.domain.Employee;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 小空
 * @create 2022-05-11 11:57
 * @description 员工持久层
 * 该层主要通过Mybatis-plus用来操作数据库
 */
@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {
}
