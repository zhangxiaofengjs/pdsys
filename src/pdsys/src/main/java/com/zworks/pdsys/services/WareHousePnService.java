package com.zworks.pdsys.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zworks.pdsys.mappers.WareHousePnMapper;
import com.zworks.pdsys.models.WareHouseDeliveryPnModel;
import com.zworks.pdsys.models.WareHousePnModel;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2018/03/30
 */
@Service
public class WareHousePnService {
	@Autowired
    private WareHousePnMapper wareHousePnMapper;
	
	public List<WareHousePnModel> queryList(WareHousePnModel filterObj) {
		List<WareHousePnModel> list = wareHousePnMapper.queryList(filterObj);
		
		return list;
	}
	
	public WareHousePnModel queryOne(WareHousePnModel filterObj) {
		List<WareHousePnModel> list = queryList(filterObj);
		if(list.size() == 1) {
			return list.get(0);
		}
		return null;
	}

	public void update(WareHousePnModel wareHousePn) {
		wareHousePnMapper.update(wareHousePn);
	}

	public void delete(WareHouseDeliveryPnModel deliveryPn) {
		wareHousePnMapper.delete(deliveryPn);
	}

	public void add(WareHousePnModel wareHousePn) {
		wareHousePnMapper.add(wareHousePn);
	}
}
