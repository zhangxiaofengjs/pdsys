package com.zworks.pdsys.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zworks.pdsys.common.exception.PdsysException;
import com.zworks.pdsys.common.utils.ListUtils;
import com.zworks.pdsys.mappers.WareHouseDeliveryPnMapper;
import com.zworks.pdsys.models.PnModel;
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

	public WareHouseDeliveryPnModel queryOne(WareHouseDeliveryPnModel deliveryPn) {
		List<WareHouseDeliveryPnModel> queryList = queryList(deliveryPn);
		if(ListUtils.isNullOrEmpty(queryList)) {
			return null;
		} else if(queryList.size() == 1) {
			return queryList.get(0);
		}
		
		throw new PdsysException("未想定数目：WareHouseDeliveryPnService-queryOne");
	}
	
	public void add(WareHouseDeliveryPnModel deliveryPn) {
		wareHouseDeliveryPnMapper.add(deliveryPn);
	}

	@Transactional
	public void delete(List<WareHouseDeliveryPnModel> deliveryPns) {
		for(WareHouseDeliveryPnModel deliveryPn : deliveryPns) {
			delete(deliveryPn);
		}
	}
	

	public void delete(WareHouseDeliveryPnModel deliveryPn) {
		wareHouseDeliveryPnMapper.delete(deliveryPn);
	}

	public boolean exists(WareHouseDeliveryPnModel deliveryPn) {
		List<WareHouseDeliveryPnModel> list = queryList(deliveryPn);
		return list.size() != 0;
	}

	public void update(WareHouseDeliveryPnModel deliveryPn) {
		wareHouseDeliveryPnMapper.update(deliveryPn);
	}

	public void checkUsedPn(PnModel pn) {
		WareHouseDeliveryPnModel whPn = new WareHouseDeliveryPnModel();
		whPn.setPn(pn);
	
		List<WareHouseDeliveryPnModel> list = queryList(whPn);
		if(!ListUtils.isNullOrEmpty(list)) {
			throw new PdsysException("出库单使用中。出库单号:" + list.get(0).getWareHouseDelivery().getNo());
		}
	}
}
