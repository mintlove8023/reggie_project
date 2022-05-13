package com.itheima.reggie.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.reggie.domain.Employee;
import com.itheima.reggie.domain.R;

import javax.servlet.http.HttpSession;

/**
 * @author 小空
 * @create 2022-05-11 11:55
 * @description Employee业务层接口
 * 实现类
 * @see com.itheima.reggie.service.impl.EmployeeServiceImpl
 */
public interface EmployeeService extends IService<Employee> {
    //员工默认密码
    public final static String EMPLOYEE_DEFAULT_PASSWORD = "123456";
    //员工默认状态
    public final static Integer EMPLOYEE_DEFAULT_STATUS = 1;

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

    /**
     * 2:添加员工业务
     *
     * @param employee 员工对象
     */
    void addEmployee(Employee employee);

    /**
     * 3:分页条件查询所有员工
     *
     * @param page     当前页数
     * @param pageSize 当前页数所展示的数据条数
     * @param name     根据搜索,来展示数据
     * @return Page对象, 包含所有员工数据
     */
    Page<Employee> pageConditionQuery(int page, int pageSize, String name);

    /**
     * 修改员工数据
     *
     * @param employee    员工对象
     * @param httpSession HttpSession对象,主要用来获取当前登录用户信息
     * @return R
     */
    void updateEmployee(HttpSession httpSession, Employee employee);

    /**
     * 查询对应员工的数据,这里用于修改员工数据回显
     *
     * @param id 员工id
     * @return 员工对象
     */
    Employee echoUpdateEmployeeData(Long id);
}
