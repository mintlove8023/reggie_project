package com.itheima.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.reggie.domain.AddressBook;
import com.itheima.reggie.mapper.AddressBookMapper;
import com.itheima.reggie.service.AddressBookService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Override
    @Transactional
    public void updateDefaultShippingAddress(HttpSession httpSession, AddressBook addressBook) {
        Long userId = (Long) httpSession.getAttribute("user");

        //1:先把所有收获地址默认设置为0
        LambdaUpdateWrapper<AddressBook> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(AddressBook::getIsDefault, NOT_DEFAUTLT_ADDRESS).in(AddressBook::getUserId, userId);
        update(updateWrapper);

        //2:然后再设置选中的默认地址
        LambdaUpdateWrapper<AddressBook> uw = new LambdaUpdateWrapper<>();
        uw.set(AddressBook::getIsDefault, IS_DEFAUTLT_ADDRESS).in(AddressBook::getId, addressBook.getId());
        update(uw);
    }

    @Override
    public AddressBook echoAddressBookById(Long id) {
        LambdaQueryWrapper<AddressBook> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AddressBook::getId, id);
        return getOne(queryWrapper);
    }

    @Override
    public void updateAddressBook(AddressBook addressBook) {
        //判断当前的地址是否为默认地址,如果是默认地址,则设置默认地址后再进行修改
        if (addressBook.getIsDefault() == 1) {
            addressBook.setIsDefault(IS_DEFAUTLT_ADDRESS);
        }
        //修改当前地址
        updateById(addressBook);
    }
}
