package com.zworks.pdsys.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zworks.pdsys.common.utils.StringUtils;
import com.zworks.pdsys.mappers.WareHouseDeliveryPnMapper;
import com.zworks.pdsys.models.WareHousePnModel;
import com.zworks.pdsys.models.WareHouseDeliveryPnModel;
import com.zworks.pdsys.models.WareHouseDeliveryModel;

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

	@Transactional
	public void addOrUpdate(String strIds, WareHouseDeliveryPnModel wareHouseDeliveryPn) {
//		List<Integer> whPnIds = StringUtils.split(strIds);
//		
//		for(int whPnId : whPnIds) {
//			WareHousePnModel wareHousePn = wareHouseDeliveryPn.getWareHousePn();
//			wareHousePn.setId(whPnId);
//			
//			WareHouseDeliveryModel wareHouseDelivery =  wareHouseDeliveryPn.getWareHouseDelivery();
//			
//			List<WareHouseDeliveryModel> deliverys = wareHouseDeliveryService.queryList(wareHouseDelivery);
//			if(deliverys == null || deliverys.size() == 0) {
//				//该领收人还未创建输出单的话，先创建
//				wareHouseDeliveryService.add(wareHouseDelivery);
//			} else {
//				WareHouseDeliveryModel wareHouseDeliveryExist = deliverys.get(0);
//				wareHouseDelivery.setId(wareHouseDeliveryExist.getId());
//			}
//			
//			List<WareHouseDeliveryPnModel> whDeliveryPns = wareHouseDeliveryPnMapper.queryList(wareHouseDeliveryPn);
//			if(whDeliveryPns == null || whDeliveryPns.size() == 0) {
//				//该领收人还未创建输出单的话，先创建
//				wareHouseDeliveryPnMapper.add(wareHouseDeliveryPn);
//			} else {
//				WareHouseDeliveryPnModel wareHouseDeliveryPnExist = whDeliveryPns.get(0);
//				wareHouseDeliveryPn.setId(wareHouseDeliveryPnExist.getId());
//				wareHouseDeliveryPnMapper.update(wareHouseDeliveryPn);
//			}
//		}
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
}
