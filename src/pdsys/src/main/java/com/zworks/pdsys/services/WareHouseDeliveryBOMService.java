package com.zworks.pdsys.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zworks.pdsys.mappers.WareHouseDeliveryBOMMapper;
import com.zworks.pdsys.models.WareHouseDeliveryBOMModel;

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

	public void add(WareHouseDeliveryBOMModel deliveryBOM) {
		wareHouseDeliveryBOMMapper.add(deliveryBOM);
	}

	@Transactional
	public void delete(List<WareHouseDeliveryBOMModel> deliveryBOMs) {
		for(WareHouseDeliveryBOMModel deliveryBOM : deliveryBOMs) {
			wareHouseDeliveryBOMMapper.delete(deliveryBOM);
		}
	}

	public boolean exists(WareHouseDeliveryBOMModel deliveryBOM) {
		List<WareHouseDeliveryBOMModel> list = queryList(deliveryBOM);
		return list.size() != 0;
	}

	public void update(WareHouseDeliveryBOMModel deliveryBOM) {
		wareHouseDeliveryBOMMapper.update(deliveryBOM);
	}
}
