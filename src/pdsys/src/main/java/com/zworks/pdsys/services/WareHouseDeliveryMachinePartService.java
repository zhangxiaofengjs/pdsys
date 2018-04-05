package com.zworks.pdsys.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zworks.pdsys.common.utils.StringUtils;
import com.zworks.pdsys.mappers.WareHouseDeliveryMachinePartMapper;
import com.zworks.pdsys.models.WareHouseMachinePartModel;
import com.zworks.pdsys.models.WareHouseDeliveryMachinePartModel;
import com.zworks.pdsys.models.WareHouseDeliveryModel;

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

	@Transactional
	public void addOrUpdate(String strIds, WareHouseDeliveryMachinePartModel wareHouseDeliveryMachinePart) {
		List<Integer> whMachinePartIds = StringUtils.split(strIds);
		
		for(int whMachinePartId : whMachinePartIds) {
			WareHouseMachinePartModel wareHouseMachinePart = wareHouseDeliveryMachinePart.getWareHouseMachinePart();
			wareHouseMachinePart.setId(whMachinePartId);
			
			WareHouseDeliveryModel wareHouseDelivery =  wareHouseDeliveryMachinePart.getWareHouseDelivery();
			
			List<WareHouseDeliveryModel> deliverys = wareHouseDeliveryService.queryList(wareHouseDelivery);
			if(deliverys == null || deliverys.size() == 0) {
				//该领收人还未创建输出单的话，先创建
				wareHouseDeliveryService.add(wareHouseDelivery);
			} else {
				WareHouseDeliveryModel wareHouseDeliveryExist = deliverys.get(0);
				wareHouseDelivery.setId(wareHouseDeliveryExist.getId());
			}
			
			List<WareHouseDeliveryMachinePartModel> whDeliveryMachineParts = wareHouseDeliveryMachinePartMapper.queryList(wareHouseDeliveryMachinePart);
			if(whDeliveryMachineParts == null || whDeliveryMachineParts.size() == 0) {
				//该领收人还未创建输出单的话，先创建
				wareHouseDeliveryMachinePartMapper.add(wareHouseDeliveryMachinePart);
			} else {
				WareHouseDeliveryMachinePartModel wareHouseDeliveryMachinePartExist = whDeliveryMachineParts.get(0);
				wareHouseDeliveryMachinePart.setId(wareHouseDeliveryMachinePartExist.getId());
				wareHouseDeliveryMachinePartMapper.update(wareHouseDeliveryMachinePart);
			}
		}
	}
}
