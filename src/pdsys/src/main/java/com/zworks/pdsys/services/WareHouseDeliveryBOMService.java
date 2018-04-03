package com.zworks.pdsys.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zworks.pdsys.mappers.WareHouseDeliveryBOMMapper;
import com.zworks.pdsys.models.WareHouseDeliveryBOMModel;
import com.zworks.pdsys.models.WareHouseDeliveryModel;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2018/04/01
 */
@Service
public class WareHouseDeliveryBOMService {
	@Autowired
    private WareHouseDeliveryBOMMapper wareHouseDeliveryBOMMapper;
	
	@Autowired
	WareHouseDeliveryService wareHouseDeliveryService;
	
	public List<WareHouseDeliveryBOMModel> queryList(WareHouseDeliveryBOMModel obj) {
		return wareHouseDeliveryBOMMapper.queryList(obj);
	}

	public void add(WareHouseDeliveryBOMModel wareHouseDeliveryBOM) {
		WareHouseDeliveryModel wareHouseDelivery =  wareHouseDeliveryBOM.getWareHouseDelivery();
		
		List<WareHouseDeliveryModel> deliverys = wareHouseDeliveryService.queryList(wareHouseDelivery);
		if(deliverys == null || deliverys.size() == 0) {
			//该领收人还未创建输出单的话，先创建
			int deliveryId = wareHouseDeliveryService.add(wareHouseDelivery);
			wareHouseDelivery.setId(deliveryId);
		}
		
		wareHouseDeliveryBOMMapper.add(wareHouseDeliveryBOM);
	}
}
