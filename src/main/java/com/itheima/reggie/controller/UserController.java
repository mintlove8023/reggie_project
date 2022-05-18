package com.itheima.reggie.controller;

import com.itheima.reggie.domain.R;
import com.itheima.reggie.domain.User;
import com.itheima.reggie.service.UserService;
import com.itheima.reggie.utils.ValidateCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * @author 小空
 * @create 2022-05-18 16:47
 * @description 用户相关操作控制层
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/sendMsg")
    public R sendVerifyCode(HttpSession httpSession, @RequestBody User user) {
        String phone = user.getPhone();
        if (StringUtils.isNotBlank(phone)) {
            Integer verifyCode = ValidateCodeUtils.generateValidateCode(6);
            log.info("登录验证码 -> [" + String.valueOf(verifyCode) + "]");
            httpSession.setAttribute(phone, verifyCode);
            return R.success("验证码发送成功!");
        }
        return R.error("验证码发送失败!");
    }

    @PostMapping("/login")
    public R login(HttpSession httpSession, @RequestBody User user) {
        //获取验证码
        R login = userService.login(httpSession, user);
        return R.success(login);
    }
}
