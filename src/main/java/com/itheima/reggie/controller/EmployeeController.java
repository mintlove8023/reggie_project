package com.itheima.reggie.controller;

import com.itheima.reggie.domain.Employee;
import com.itheima.reggie.domain.R;
import com.itheima.reggie.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

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
}
