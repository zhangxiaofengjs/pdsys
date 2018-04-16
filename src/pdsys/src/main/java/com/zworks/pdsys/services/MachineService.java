package com.zworks.pdsys.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zworks.pdsys.mappers.MachineMapper;
import com.zworks.pdsys.models.MachineModel;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2018/04/07
 */
@Service
public class MachineService {
	@Autowired
    private MachineMapper machineMapper;
	
	public List<MachineModel> queryList(MachineModel filterObj) {
		List<MachineModel> list = machineMapper.queryList(filterObj);
		
		return list;
	}

	public MachineModel queryOne(MachineModel machine) {
		List<MachineModel> list = machineMapper.queryList(machine);
		if(list.size() != 0) {
			return list.get(0);
		}
		return null;
	}

	public boolean exists(MachineModel machine) {
		MachineModel m = new MachineModel();
		m.setPn(machine.getPn());
		return queryOne(m) != null;
	}

	public void add(MachineModel machine) {
		machineMapper.add(machine);
	}

	public void update(MachineModel machine) {
		machineMapper.update(machine);
	}

	public boolean existsMachinePart(MachineModel machine) {
		// TODO Auto-generated method stub
		return false;
	}

	public void updateMachinePart(MachineModel machine) {
		// TODO Auto-generated method stub
		
	}

	public void addMachinePart(MachineModel machine) {
		// TODO Auto-generated method stub
		
	}
}
