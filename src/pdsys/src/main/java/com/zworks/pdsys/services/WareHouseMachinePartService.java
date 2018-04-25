package com.zworks.pdsys.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zworks.pdsys.mappers.WareHouseMachinePartMapper;
import com.zworks.pdsys.models.MachinePartModel;
import com.zworks.pdsys.models.WareHouseMachinePartModel;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2018/04/05
 */
@Service
public class WareHouseMachinePartService {
	@Autowired
    private WareHouseMachinePartMapper wareHouseMapper;
	
	public List<WareHouseMachinePartModel> queryList(WareHouseMachinePartModel filterObj) {
		List<WareHouseMachinePartModel> list = wareHouseMapper.queryList(filterObj);
		
		return list;
	}
	
	public WareHouseMachinePartModel queryOne(WareHouseMachinePartModel filterObj) {
		List<WareHouseMachinePartModel> list = wareHouseMapper.queryList(filterObj);
		if(list.size() == 1) {
			return list.get(0);
		}
		return null;
	}

	public WareHouseMachinePartModel queryByPartId(int partId) {
		MachinePartModel part= new MachinePartModel();
		part.setId(partId);

		WareHouseMachinePartModel whPart = new WareHouseMachinePartModel();
		whPart.setMachinePart(part);
		
		return queryOne(whPart);
	}

	public void add(WareHouseMachinePartModel wareHouseMachinePart) {
		wareHouseMapper.add(wareHouseMachinePart);
	}

	public void update(WareHouseMachinePartModel wareHouseMachinePart) {
		wareHouseMapper.update(wareHouseMachinePart);
	}
}
