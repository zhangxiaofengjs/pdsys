package com.hyron.modules.api.service;

import java.util.List;
import java.util.Map;

import com.hyron.modules.api.entity.HouseBaseInfoEntity;
import com.hyron.modules.api.entity.HouseTypeEntity;

/**
 * 房屋信息
 * 
 * @author ZHAI
 * 
 * @date 2018-02-05 15:47
 */
public interface HouseService {

	HouseBaseInfoEntity queryObject(Long houseId);
	
	List<HouseTypeEntity> queryAllTypeList();
	
	List<HouseBaseInfoEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(HouseBaseInfoEntity houseBaseInfo);
	
	void update(HouseBaseInfoEntity houseBaseInfo);
	
	void delete(Long houseId);
	
	void deleteBatch(Long[] houseIds);
}
