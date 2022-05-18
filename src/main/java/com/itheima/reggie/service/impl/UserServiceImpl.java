package com.itheima.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.reggie.domain.R;
import com.itheima.reggie.domain.User;
import com.itheima.reggie.mapper.UserMapper;
import com.itheima.reggie.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

/**
 * @author 小空
 * @create 2022-05-18 16:53
 * @description 用户相关操作业务层实现类
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Override
    public R login(HttpSession httpSession, User user) {
        //获取验证码
        Object verifyCode = httpSession.getAttribute(user.getPhone());

        //验证码校验
        Object code = httpSession.getAttribute(user.getPhone());
        if (verifyCode != null && verifyCode.equals(code)) {
            LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(User::getPhone, user.getPhone());
            User u = getOne(queryWrapper);
            //从数据库查询该用户是否存在,不存在则自动注册
            if (u == null) {
                u = new User();
                u.setPhone(user.getPhone());
                u.setStatus(1);
                save(u);
            }
            httpSession.setAttribute("user", u.getId());
            return R.success(u);
        }
        return R.error("登录失败!");
    }
}
