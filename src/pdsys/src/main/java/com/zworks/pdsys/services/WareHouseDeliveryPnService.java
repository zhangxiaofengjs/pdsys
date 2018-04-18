package com.zworks.pdsys.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zworks.pdsys.mappers.WareHouseDeliveryPnMapper;
import com.zworks.pdsys.models.WareHouseDeliveryPnModel;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2018/04/04
 */
@Service
public class WareHouseDeliveryPnService {
	@Autowired
    private WareHouseDeliveryPnMapper wareHouseDeliveryPnMapper;
	
	@Autowired
	WareHouseDeliveryService wareHouseDeliveryService;
	
	public List<WareHouseDeliveryPnModel> queryList(WareHouseDeliveryPnModel obj) {
		return wareHouseDeliveryPnMapper.queryList(obj);
	}

	public void add(WareHouseDeliveryPnModel deliveryPn) {
		wareHouseDeliveryPnMapper.add(deliveryPn);
	}

	@Transactional
	public void delete(List<WareHouseDeliveryPnModel> deliveryPns) {
		for(WareHouseDeliveryPnModel deliveryPn : deliveryPns) {
			wareHouseDeliveryPnMapper.delete(deliveryPn);
		}
	}

	public boolean exists(WareHouseDeliveryPnModel deliveryPn) {
		List<WareHouseDeliveryPnModel> list = queryList(deliveryPn);
		return list.size() != 0;
	}

	public void update(WareHouseDeliveryPnModel deliveryPn) {
		wareHouseDeliveryPnMapper.update(deliveryPn);
	}
}
