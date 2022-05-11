package com.itheima.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.reggie.domain.Employee;
import com.itheima.reggie.domain.R;

/**
 * @author 小空
 * @create 2022-05-11 11:55
 * @description Employee业务层接口
 * 实现类
 * @see com.itheima.reggie.service.impl.EmployeeServiceImpl
 */
public interface EmployeeService extends IService<Employee> {
    /**
     * 1:处理员工登录的业务逻辑
     * #1.1:查询数据库,判断用户名是否存在,如果存在进入下一步,否则返回提示
     * #1.2:如果用户存在,那么再进行密码校验,在校验密码之前将密码进行md5加密,密码错误,则直接返回提示
     * #1.3:如果用户名存在且密码都正确(即通过了1.1、1.2的判断),那么再进行员工状态判断[0:禁用员工, 1:启用员工]
     * 员工启用：通过了1.1、1.2、1.3,最后返回【登录成功】
     * 员工禁用：返回提示
     *
     * @param employee Employee对象,该对象是存在于请求体之中的
     * @return R
     */
    R login(Employee employee);
}
