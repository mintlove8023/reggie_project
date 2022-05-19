package com.itheima.reggie.controller;

import com.itheima.reggie.domain.AddressBook;
import com.itheima.reggie.domain.R;
import com.itheima.reggie.service.AddressBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

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

    /**
     * 查询当前用户的所有收获地址,并展示
     *
     * @param httpSession Session对象,主要用来获取当前已登录的用户id
     * @return R, 包含了该对象的所有收货地址, 以集合进行展示
     */
    @GetMapping("/list")
    public R selectAddressBookByUserId(HttpSession httpSession) {
        List<AddressBook> addressList = addressBookService.selectAddressBookByUserId(httpSession);
        if (addressList == null) {
            return R.error("获取收获地址失败!");
        }
        return R.success(addressList);
    }

    /**
     * 设置默认地址
     *
     * @param addressBook AddressBook对象
     * @return R
     */
    @PutMapping("/default")
    public R updateDefaultShippingAddress(HttpSession httpSession, @RequestBody AddressBook addressBook) {
        addressBookService.updateDefaultShippingAddress(httpSession, addressBook);
        return R.success("已将当前地址设为默认地址!");
    }

    /**
     * 修改收货地址数据回显
     *
     * @param id 收货地址id
     * @return AddressBook对象, 包含收货地址的信息
     */
    @GetMapping("{id}")
    public R echoAddressBookById(@PathVariable Long id) {
        AddressBook addressBook = addressBookService.echoAddressBookById(id);
        return R.success(addressBook);
    }

    /**
     * 修改收货地址,如果当前地址已被设置为默认收货地址
     * 需要将默认收货地址的标识给设置回去
     *
     * @param addressBook AddressBook
     * @return R
     */
    @PutMapping
    public R updateAddressBook(@RequestBody AddressBook addressBook) {
        addressBookService.updateAddressBook(addressBook);
        return R.success("修改成功!");
    }

    @DeleteMapping
    public R deleteAddressBook(Long ids) {
        addressBookService.removeById(ids);
        return R.success("删除成功!");
    }
}
