package com.itheima.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itheima.reggie.domain.AddressBook;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 小空
 * @create 2022-05-19 10:31
 * @description 地址管理持久层接口
 */
@Mapper
public interface AddressBookMapper extends BaseMapper<AddressBook> {
}
