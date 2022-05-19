package com.itheima.reggie.controller;

import com.itheima.reggie.domain.AddressBook;
import com.itheima.reggie.domain.R;
import com.itheima.reggie.mapper.AddressBookMapper;
import com.itheima.reggie.service.AddressBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * @author 小空
 * @create 2022-05-19 10:37
 * @description 地址管理控制层
 */
@RestController
@RequestMapping("/addressBook")
public class AddressBookController {
    @Autowired
    private AddressBookService addressBookService;

    /**
     * 添加收货地址
     *
     * @param httpSession Session对象,主要用来获取当前已登录的用户id
     * @param addressBook AddressBook对象,包含了一些地址,用户手机号,id等一些信息
     * @return R
     */
    @PostMapping
    public R addShippingAddress(HttpSession httpSession, @RequestBody AddressBook addressBook) {
        Long userId = (Long) httpSession.getAttribute("user");
        if (userId != null) {
            addressBook.setUserId(userId);
            addressBookService.save(addressBook);
            return R.success("收获地址添加成功!");
        }
        return R.error("收获地址添加失败!");
    }
}
