package com.zworks.pdsys.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zworks.pdsys.mappers.MachineTroubleMapper;
import com.zworks.pdsys.models.MachineTroubleModel;

@Service
public class MachineTroubleService {
	@Autowired
    private MachineTroubleMapper machineTroubleMapper;
	
	public List<MachineTroubleModel> queryList(MachineTroubleModel filterObj) {
		List<MachineTroubleModel> list = machineTroubleMapper.queryList(filterObj);
		return list;
	}

	public MachineTroubleModel queryOne(MachineTroubleModel filterObj) {
		List<MachineTroubleModel> list = machineTroubleMapper.queryList(filterObj);
		if(list.size() != 0) {
			return list.get(0);
		}
		return null;
	}

	public void add(MachineTroubleModel machineTrouble) {
		machineTroubleMapper.add(machineTrouble);
	}
	
	public boolean exists(MachineTroubleModel machineTrouble) {
		MachineTroubleModel m = new MachineTroubleModel();
		return queryOne(m) != null;
	}

}
