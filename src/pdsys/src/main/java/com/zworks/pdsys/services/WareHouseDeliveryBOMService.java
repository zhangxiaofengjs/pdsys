package com.zworks.pdsys.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zworks.pdsys.mappers.OrderMapper;
import com.zworks.pdsys.mappers.WareHouseBOMMapper;
import com.zworks.pdsys.mappers.WareHouseDeliveryBOMMapper;
import com.zworks.pdsys.mappers.WareHousePnMapper;
import com.zworks.pdsys.models.OrderModel;
import com.zworks.pdsys.models.WareHouseBOMModel;
import com.zworks.pdsys.models.WareHouseDeliveryBOMModel;
import com.zworks.pdsys.models.WareHousePnModel;

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
