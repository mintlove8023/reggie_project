package com.itheima.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.reggie.domain.Employee;
import com.itheima.reggie.domain.R;
import com.itheima.reggie.mapper.EmployeeMapper;
import com.itheima.reggie.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

/**
 * @author 小空
 * @create 2022-05-11 11:55
 * @description 员工业务实现类
 * @see EmployeeService
 */
@Service
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
}
