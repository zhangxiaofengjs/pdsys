package com.zworks.pdsys.services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.DateUtils;

import com.zworks.pdsys.common.enumClass.DeliveryState;
import com.zworks.pdsys.common.exception.PdsysException;
import com.zworks.pdsys.common.exception.PdsysExceptionCode;
import com.zworks.pdsys.mappers.WareHouseDeliveryBOMMapper;
import com.zworks.pdsys.mappers.WareHouseDeliveryMapper;
import com.zworks.pdsys.models.WareHouseDeliveryBOMModel;
import com.zworks.pdsys.models.WareHouseDeliveryModel;
import com.zworks.pdsys.models.WareHouseDeliveryPnModel;
import com.zworks.pdsys.models.WareHousePnModel;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2018/04/03
 */
@Service
public class WareHouseDeliveryService {
	@Autowired
    private WareHouseDeliveryMapper wareHouseDeliveryMapper;
	@Autowired
	private WareHousePnService wareHousePnService;
	@Autowired
	private WareHouseDeliveryPnService wareHouseDeliveryPnService;
	
	public List<WareHouseDeliveryModel> queryList(WareHouseDeliveryModel obj) {
		return wareHouseDeliveryMapper.queryList(obj);
	}
	
	public void add(WareHouseDeliveryModel obj) {
		wareHouseDeliveryMapper.add(obj);
	}
	public void delete(WareHouseDeliveryModel delivery) {
		delivery.setState(DeliveryState.DELETE.ordinal());
		wareHouseDeliveryMapper.update(delivery);
	}
	
	@Transactional
	public void deleteForever(WareHouseDeliveryModel delivery) {
		wareHouseDeliveryPnService.delete(delivery.getWareHouseDeliveryPns());
		wareHouseDeliveryMapper.delete(delivery);
	}
	
	public WareHouseDeliveryModel queryOne(Integer id) {
		WareHouseDeliveryModel obj = new WareHouseDeliveryModel();
		obj.setId(id);
		List<WareHouseDeliveryModel> list = wareHouseDeliveryMapper.queryList(obj);
		
		if(list.size() != 0) {
			return list.get(0);
		}
		return null;
	}

	@Transactional
	public boolean delivery(WareHouseDeliveryModel delivery) {
		for(WareHouseDeliveryPnModel deliveryPn : delivery.getWareHouseDeliveryPns()) {
			WareHousePnModel wareHousePn = deliveryPn.getWareHousePn();
			float semiNum = wareHousePn.getSemiProducedNum() - deliveryPn.getSemiProducedNum();
			float num = wareHousePn.getProducedNum() - deliveryPn.getProducedNum();
			if(num < 0 || semiNum < 0) {
				//库存不足
				return false;
			}
			wareHousePn.setProducedNum(num);
			wareHousePn.setSemiProducedNum(num);
			
			wareHousePnService.update(wareHousePn);
		}
		
		delivery.setDeliveryTime(new Date());
		delivery.setState(DeliveryState.DELIVERIED.ordinal());
		
		wareHouseDeliveryMapper.update(delivery);
		return true;
	}
}
