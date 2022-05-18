package com.itheima.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itheima.reggie.domain.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 小空
 * @create 2022-05-18 16:52
 * @description 用户相关操作持久层
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
