package com.itheima.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.reggie.domain.R;
import com.itheima.reggie.domain.User;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpSession;

/**
 * @author 小空
 * @create 2022-05-18 16:53
 * @description 用户相关操作业务层接口
 */
public interface UserService extends IService<User> {
    /**
     * 移动端用户登录处理
     *
     * @param user        User对象
     * @param httpSession Session对象
     */
    R login(HttpSession httpSession, User user);
}
