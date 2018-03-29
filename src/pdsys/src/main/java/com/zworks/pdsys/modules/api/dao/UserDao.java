package com.zworks.pdsys.modules.api.dao;

import org.apache.ibatis.annotations.Mapper;

import com.zworks.pdsys.modules.api.entity.UserEntity;
import com.zworks.pdsys.modules.sys.dao.BaseDao;

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
