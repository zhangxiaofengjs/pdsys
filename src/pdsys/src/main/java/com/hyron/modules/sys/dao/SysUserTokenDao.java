package com.hyron.modules.sys.dao;

import org.apache.ibatis.annotations.Mapper;

import com.hyron.modules.sys.entity.SysUserTokenEntity;

/**
 * 系统用户Token
 * 
 * @author Allen
 * @webSite https://www.allen-software.cn
 * @date 2017-03-23 15:22:07
 */
@Mapper
public interface SysUserTokenDao extends BaseDao<SysUserTokenEntity> {
    
    SysUserTokenEntity queryByUserId(Long userId);

    SysUserTokenEntity queryByToken(String token);
}
