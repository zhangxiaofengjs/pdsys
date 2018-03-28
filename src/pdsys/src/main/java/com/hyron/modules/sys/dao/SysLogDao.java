package com.hyron.modules.sys.dao;

import org.apache.ibatis.annotations.Mapper;

import com.hyron.modules.sys.entity.SysLogEntity;

/**
 * 系统日志
 * 
 * @author ZHAI
 * 
 * @date 2017-03-08 10:40:56
 */
@Mapper
public interface SysLogDao extends BaseDao<SysLogEntity> {
	
}
