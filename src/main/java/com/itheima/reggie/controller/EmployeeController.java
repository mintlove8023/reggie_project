package com.itheima.reggie.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.reggie.domain.Employee;
import com.itheima.reggie.domain.R;
import com.itheima.reggie.exception.UserExistsException;
import com.itheima.reggie.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;

/**
 * @author 小空
 * @create 2022-05-11 11:54
 * @description Employee表现层
 */
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/login")
    public R login(HttpSession httpSession, @RequestBody Employee employee) {
        R result = employeeService.login(employee);
        if (result.getCode() == 1) {
            Employee empData = (Employee) result.getData();
            httpSession.setAttribute("employee", empData.getId());
        }
        return result;
    }

    @PostMapping("/logout")
    public R logout(HttpSession httpSession) {
        httpSession.removeAttribute("employee");
        return R.success("你已退出登录!");
    }

    @PostMapping
    public R addEmployee(HttpSession httpSession, @RequestBody Employee employee) {
        Long employeeID = (Long) httpSession.getAttribute("employee");
        employee.setCreateUser(employeeID);
        employee.setUpdateUser(employeeID);
        employeeService.addEmployee(employee);
        return R.success("添加成功!");
    }

    @GetMapping("/page")
    public R pageConditionQuery(int page, int pageSize, String name) {
        Page<Employee> p = employeeService.pageConditionQuery(page, pageSize, name);
        return R.success(p);
    }

    @PutMapping
    public R updateEmployee(HttpSession httpSession, @RequestBody Employee employee) {
        employeeService.updateEmployee(httpSession, employee);
        //返回修改结果
        return R.success("修改成功!");
    }

    @GetMapping("{id}")
    public R echoUpdateEmployeeData(@PathVariable Long id) {
        return R.success(employeeService.echoUpdateEmployeeData(id));
    }
}
