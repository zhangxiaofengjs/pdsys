package com.zworks.pdsys.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zworks.pdsys.common.exception.PdsysException;
import com.zworks.pdsys.mappers.MachineMapper;
import com.zworks.pdsys.models.MachineMachinePartRelModel;
import com.zworks.pdsys.models.MachineModel;
import com.zworks.pdsys.models.MachinePartModel;

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
		MachineModel m = queryOne(machine);
		if(m == null) {
			throw new PdsysException("不存在该品番机器");
		}
		for(MachineMachinePartRelModel mmRel : machine.getMachineMachinePartRels()) {
			MachinePartModel mp = mmRel.getMachinePart();
			
			for(MachineMachinePartRelModel targetMmRel: m.getMachineMachinePartRels()) {
				MachinePartModel targetMp = targetMmRel.getMachinePart();
				
				if(mp.getId() == targetMp.getId()) {
					return true;
				}
			}
		}
		return false;
	}

	@Transactional
	public void updateMachinePart(MachineModel machine) {
		List<MachineMachinePartRelModel> deleteRels = new ArrayList<MachineMachinePartRelModel>();
		List<MachineMachinePartRelModel> machineMachinePartRels = machine.getMachineMachinePartRels();
		for(int i = machineMachinePartRels.size() - 1; i >= 0; i--) {
			MachineMachinePartRelModel rel = machineMachinePartRels.get(i);
			if(rel.getMaitenacePartNum() == 0) {
				deleteRels.add(rel);
				machineMachinePartRels.remove(rel);
			} else {
				//do nothing
			}
		}
		
		if(deleteRels.size()!=0) {
			machine.setMachineMachinePartRels(deleteRels);
			machineMapper.deleteMachinePart(machine);
		}
		
		if(machineMachinePartRels.size()!=0) {
			machine.setMachineMachinePartRels(machineMachinePartRels);
			machineMapper.updateMachinePart(machine);
		}
	}

	public void addMachinePart(MachineModel machine) {
		machineMapper.addMachinePart(machine);
	}
}
