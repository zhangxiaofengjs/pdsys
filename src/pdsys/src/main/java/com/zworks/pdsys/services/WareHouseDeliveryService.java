package com.zworks.pdsys.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zworks.pdsys.mappers.WareHouseDeliveryBOMMapper;
import com.zworks.pdsys.mappers.WareHouseDeliveryMapper;
import com.zworks.pdsys.models.WareHouseDeliveryBOMModel;
import com.zworks.pdsys.models.WareHouseDeliveryModel;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2018/04/03
 */
@Service
public class WareHouseDeliveryService {
	@Autowired
    private WareHouseDeliveryMapper wareHouseDeliveryMapper;
	
	public List<WareHouseDeliveryModel> queryList(WareHouseDeliveryModel obj) {
		return wareHouseDeliveryMapper.queryList(obj);
	}
	
	public void add(WareHouseDeliveryModel obj) {
		wareHouseDeliveryMapper.add(obj);
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
}
