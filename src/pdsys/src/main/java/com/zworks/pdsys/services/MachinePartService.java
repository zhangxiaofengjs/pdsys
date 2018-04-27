package com.zworks.pdsys.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zworks.pdsys.mappers.MachinePartMapper;
import com.zworks.pdsys.models.MachinePartModel;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2018/04/15
 */
@Service
public class MachinePartService {
	@Autowired
    private MachinePartMapper machinePartMapper;
	
	public List<MachinePartModel> queryList(MachinePartModel filterObj) {
		List<MachinePartModel> list = machinePartMapper.queryList(filterObj);
		
		return list;
	}

	public MachinePartModel queryOne(MachinePartModel machinePart) {
		List<MachinePartModel> list = queryList(machinePart);
		if(list.size() != 0) {
			return list.get(0);
		}
		return null;
	}

	public boolean exists(MachinePartModel machinePart) {
		MachinePartModel m = new MachinePartModel();
		m.setPn(machinePart.getPn());
		return queryOne(m) != null;
	}

	public void add(MachinePartModel machinePart) {
		machinePartMapper.add(machinePart);
	}

	public void update(MachinePartModel machinePart) {
		machinePartMapper.update(machinePart);
	}
}
