package com.zworks.pdsys.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	
	public boolean queryList(WareHouseDeliveryBOMModel obj) {
		int ret = wareHouseDeliveryBOMMapper.add(obj);
		
		return true;
	}
}
