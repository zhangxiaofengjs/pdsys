package com.hyron.modules.api.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.hyron.modules.api.entity.HouseBaseInfoEntity;
import com.hyron.modules.api.entity.HouseTypeEntity;
import com.hyron.modules.sys.dao.BaseDao;

/**
 * 房屋基础信息
 * 
 * @author ZHAI
 * 
 * @date 2018-02-05 16:06:10
 */
@Mapper
public interface ApiHouseBaseInfoDao extends BaseDao<HouseBaseInfoEntity> {

	List<HouseTypeEntity> queryAllTypeList();
}
