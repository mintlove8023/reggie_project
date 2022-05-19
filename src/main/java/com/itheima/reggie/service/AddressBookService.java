package com.itheima.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.reggie.domain.AddressBook;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author 小空
 * @create 2022-05-19 10:33
 * @description 地址管理业务层接口
 */
public interface AddressBookService extends IService<AddressBook> {
    /**
     * 默认地址
     */
    Integer IS_DEFAUTLT_ADDRESS = 1;

    /**
     * 不是默认地址
     */
    Integer NOT_DEFAUTLT_ADDRESS = 0;

    /**
     * 询当前用户的所有收获地址,并展示
     *
     * @param httpSession Session对象,主要用来获取当前已登录的用户id
     * @return 包含了该对象的所有收货地址
     */
    List<AddressBook> selectAddressBookByUserId(HttpSession httpSession);

    /**
     * 设置默认地址
     *
     * @param addressBook AddressBook对象
     */
    void updateDefaultShippingAddress(HttpSession httpSession, AddressBook addressBook);

    /**
     * 修改收货地址
     *
     * @param id 收货地址id
     */
    AddressBook echoAddressBookById(Long id);

    /**
     * 修改收货地址,如果当前地址已被设置为默认收货地址
     * 需要将默认收货地址的标识给设置回去
     *
     * @param addressBook AddressBook对象,里面包含了JSON数据
     */
    void updateAddressBook(AddressBook addressBook);
}
