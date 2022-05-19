package com.itheima.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.reggie.domain.AddressBook;
import com.itheima.reggie.mapper.AddressBookMapper;
import com.itheima.reggie.service.AddressBookService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author 小空
 * @create 2022-05-19 10:33
 * @description 地址管理业务层实现类
 * @see com.itheima.reggie.service.AddressBookService
 */
@Service
public class AddressBookServiceImpl extends ServiceImpl<AddressBookMapper, AddressBook> implements AddressBookService {

    @Override
    public List<AddressBook> selectAddressBookByUserId(HttpSession httpSession) {
        Long userId = (Long) httpSession.getAttribute("user");
        if (userId != null) {
            //按条件查询
            LambdaQueryWrapper<AddressBook> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(AddressBook::getUserId, userId);
            return list(queryWrapper);
        }
        return null;
    }
}
