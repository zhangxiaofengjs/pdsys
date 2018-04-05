package com.zworks.pdsys.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zworks.pdsys.mappers.WareHouseMachinePartMapper;
import com.zworks.pdsys.models.WareHouseMachinePartModel;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2018/04/05
 */
@Service
public class WareHouseMachinePartService {
	@Autowired
    private WareHouseMachinePartMapper wareHouseMapper;
	
	public List<WareHouseMachinePartModel> queryList(WareHouseMachinePartModel filterObj) {
		List<WareHouseMachinePartModel> list = wareHouseMapper.queryList(filterObj);
		
		return list;
	}
}
