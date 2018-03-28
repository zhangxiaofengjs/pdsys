package com.hyron.modules.api.dao;

import org.apache.ibatis.annotations.Mapper;

import com.hyron.modules.api.entity.UserEntity;
import com.hyron.modules.sys.dao.BaseDao;

/**
 * 用户
 * 
 * @author ZHAI
 * 
 * @date 2017-03-23 15:22:06
 */
@Mapper
public interface UserDao extends BaseDao<UserEntity> {

    UserEntity queryByMobile(String mobile);
}
