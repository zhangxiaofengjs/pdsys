package com.zworks.pdsys.modules.api.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zworks.pdsys.modules.api.dao.ApiHouseBaseInfoDao;
import com.zworks.pdsys.modules.api.entity.HouseBaseInfoEntity;
import com.zworks.pdsys.modules.api.entity.HouseTypeEntity;
import com.zworks.pdsys.modules.api.service.HouseService;

@Service("houseService")
public class HouseServiceImpl implements HouseService {

	@Autowired
	private ApiHouseBaseInfoDao houseBaseInfoDao;

	@Override
	public HouseBaseInfoEntity queryObject(Long houseId) {
		return houseBaseInfoDao.queryObject(houseId);
	}

	@Override
	public List<HouseTypeEntity> queryAllTypeList() {
		return houseBaseInfoDao.queryAllTypeList();
	}

	@Override
	public List<HouseBaseInfoEntity> queryList(Map<String, Object> map) {
		return houseBaseInfoDao.queryList(map);
	}

	@Override
	public int queryTotal(Map<String, Object> map) {
		return houseBaseInfoDao.queryTotal(map);
	}

	@Override
	public void save(HouseBaseInfoEntity houseBaseInfo) {
		houseBaseInfoDao.save(houseBaseInfo);
	}

	@Override
	public void update(HouseBaseInfoEntity houseBaseInfo) {
		houseBaseInfoDao.update(houseBaseInfo);
	}

	@Override
	public void delete(Long houseId) {
		houseBaseInfoDao.delete(houseId);
	}

	@Override
	public void deleteBatch(Long[] houseIds) {
		houseBaseInfoDao.deleteBatch(houseIds);
	}
}
