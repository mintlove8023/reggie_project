package com.itheima.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.reggie.domain.Employee;
import com.itheima.reggie.domain.R;
import com.itheima.reggie.mapper.EmployeeMapper;
import com.itheima.reggie.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.time.LocalDateTime;

/**
 * @author 小空
 * @create 2022-05-11 11:55
 * @description 员工业务实现类
 * @see EmployeeService
 */
@Service
@Slf4j
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {
    @Autowired
    private EmployeeMapper employeeMapper;

    @Override
    public R login(Employee employee) {
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getUsername, employee.getUsername());
        //该Employee对象是从数据库中查询出指定的员工来返回的
        Employee emp = employeeMapper.selectOne(queryWrapper);
        if (emp == null) {
            return R.error("错误:用户不存在!");
        }

        //对密码进行md5加密并进行密码校验
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        if (!password.equals(emp.getPassword())) {
            return R.error("错误:密码输入错误!");
        }

        //对员工状态进行判断,判断员工是否处于启用状态
        if (emp.getStatus() == 0) {
            return R.error("错误:该员工账号状态异常!");
        }

        //登录成功,进行返回
        return R.success(emp);
    }

    @Override
    public void addEmployee(Employee employee) {
        //设置新增员工的默认登录密码
        employee.setPassword(DigestUtils.md5DigestAsHex(EMPLOYEE_DEFAULT_PASSWORD.getBytes()));

        //设置新增员工默认为启用状态
        employee.setStatus(EMPLOYEE_DEFAULT_STATUS);

        //设置员工创建时间与修改时间(默认为:当前系统时间)
        employee.setCreateTime(LocalDateTime.now());
        employee.setUpdateTime(LocalDateTime.now());

        //保存员工
        save(employee);
    }
}
