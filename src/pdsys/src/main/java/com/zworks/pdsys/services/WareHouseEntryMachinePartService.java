package com.zworks.pdsys.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zworks.pdsys.mappers.WareHouseEntryMachinePartMapper;
import com.zworks.pdsys.models.WareHouseEntryMachinePartModel;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2018/04/25
 */
@Service
public class WareHouseEntryMachinePartService {
	@Autowired
    private WareHouseEntryMachinePartMapper wareHouseEntryMachinePartMapper;
	
	public List<WareHouseEntryMachinePartModel> queryList(WareHouseEntryMachinePartModel entryPn) {
		return wareHouseEntryMachinePartMapper.queryList(entryPn);
	}
	
	public WareHouseEntryMachinePartModel queryOne(WareHouseEntryMachinePartModel entryPn) {
		List<WareHouseEntryMachinePartModel> eMachineParts = queryList(entryPn);
		if(eMachineParts.size()==1) {
			return eMachineParts.get(0);
		}
		return null;
	}
	
	@Transactional
	public void delete(List<WareHouseEntryMachinePartModel> entryMachineParts) {
		for(WareHouseEntryMachinePartModel entryMachinePart : entryMachineParts) {
			delete(entryMachinePart);
		}
	}

	public void delete(WareHouseEntryMachinePartModel eMp) {
		wareHouseEntryMachinePartMapper.delete(eMp);
	}
	
	public boolean exist(WareHouseEntryMachinePartModel entryMachinePart) {
		return queryOne(entryMachinePart) != null;
	}

	public void update(WareHouseEntryMachinePartModel entryMachinePart) {
		wareHouseEntryMachinePartMapper.update(entryMachinePart);
	}

	public void add(WareHouseEntryMachinePartModel entryMachinePart) {
		wareHouseEntryMachinePartMapper.add(entryMachinePart);
	}
}
