package com.zworks.pdsys.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zworks.pdsys.mappers.WareHouseDeliveryMachinePartMapper;
import com.zworks.pdsys.models.WareHouseDeliveryMachinePartModel;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2018/04/05
 */
@Service
public class WareHouseDeliveryMachinePartService {
	@Autowired
    private WareHouseDeliveryMachinePartMapper wareHouseDeliveryMachinePartMapper;
	
	@Autowired
	WareHouseDeliveryService wareHouseDeliveryService;
	
	public List<WareHouseDeliveryMachinePartModel> queryList(WareHouseDeliveryMachinePartModel obj) {
		return wareHouseDeliveryMachinePartMapper.queryList(obj);
	}
	
	public void add(WareHouseDeliveryMachinePartModel deliveryMachinePart) {
		wareHouseDeliveryMachinePartMapper.add(deliveryMachinePart);
	}

	@Transactional
	public void delete(List<WareHouseDeliveryMachinePartModel> deliveryMachineParts) {
		for(WareHouseDeliveryMachinePartModel deliveryMachinePart : deliveryMachineParts) {
			wareHouseDeliveryMachinePartMapper.delete(deliveryMachinePart);
		}
	}

	public boolean exists(WareHouseDeliveryMachinePartModel deliveryMachinePart) {
		List<WareHouseDeliveryMachinePartModel> list = queryList(deliveryMachinePart);
		return list.size() != 0;
	}

	public void update(WareHouseDeliveryMachinePartModel deliveryMachinePart) {
		wareHouseDeliveryMachinePartMapper.update(deliveryMachinePart);
	}
}
