package com.zworks.pdsys.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zworks.pdsys.mappers.DeviceMapper;
import com.zworks.pdsys.mappers.MachineMapper;
import com.zworks.pdsys.mappers.WareHouseMachinePartMapper;
import com.zworks.pdsys.models.DeviceModel;
import com.zworks.pdsys.models.MachineModel;
import com.zworks.pdsys.models.WareHouseMachinePartModel;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2018/04/07
 */
@Service
public class DeviceService {
	@Autowired
    private DeviceMapper deviceMapper;
	
	public List<DeviceModel> queryList(DeviceModel filterObj) {
		List<DeviceModel> list = deviceMapper.queryList(filterObj);
		
		return list;
	}

	public void update(DeviceModel device) {
		deviceMapper.update(device);
	}

	public void add(DeviceModel device) {
		deviceMapper.add(device);
	}
}
