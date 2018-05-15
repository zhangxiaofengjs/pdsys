package com.zworks.pdsys.services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zworks.pdsys.common.enumClass.EntryState;
import com.zworks.pdsys.common.enumClass.EntryType;
import com.zworks.pdsys.mappers.WareHouseEntryMapper;
import com.zworks.pdsys.models.WareHouseBOMModel;
import com.zworks.pdsys.models.WareHouseEntryBOMModel;
import com.zworks.pdsys.models.WareHouseEntryMachinePartModel;
import com.zworks.pdsys.models.WareHouseEntryModel;
import com.zworks.pdsys.models.WareHouseEntryPnModel;
import com.zworks.pdsys.models.WareHouseMachinePartModel;
import com.zworks.pdsys.models.WareHousePnModel;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2018/04/05
 */
@Service
public class WareHouseEntryService {
	@Autowired
    private WareHouseEntryMapper wareHouseEntryMapper;
	@Autowired
	private WareHousePnService wareHousePnService;
	@Autowired
	private WareHouseBOMService wareHouseBOMService;
	@Autowired
	private WareHouseMachinePartService wareHouseMachinePartService;
	
	public List<WareHouseEntryModel> queryList(WareHouseEntryModel obj) {
		return wareHouseEntryMapper.queryList(obj);
	}
	
	public WareHouseEntryModel queryOne(WareHouseEntryModel entry) {
		List<WareHouseEntryModel> entries = this.queryList(entry);
		if(entries.size() == 1) {
			return entries.get(0);
		}
		
		return null;
	}
	
	public boolean exists(WareHouseEntryModel entry) {
		return queryOne(entry) != null;
	}
	
	public List<WareHouseEntryModel> queryListWithPn(WareHouseEntryModel obj) {
		return wareHouseEntryMapper.queryListWithPn(obj);
	}
	
	public List<WareHouseEntryModel> queryListWithBOM(WareHouseEntryModel obj) {
		return wareHouseEntryMapper.queryListWithBOM(obj);
	}
	
	public List<WareHouseEntryModel> queryListWithMachinePart(WareHouseEntryModel entry) {
		return wareHouseEntryMapper.queryListWithMachinePart(entry);
	}
	
	public WareHouseEntryModel queryOneWithPn(WareHouseEntryModel entry) {
		List<WareHouseEntryModel> entries = this.queryListWithPn(entry);
		if(entries.size() == 1) {
			return entries.get(0);
		}
		
		return null;
	}
	
	public WareHouseEntryModel queryOneWithBOM(WareHouseEntryModel entry) {
		List<WareHouseEntryModel> entries = this.queryListWithBOM(entry);
		if(entries.size() == 1) {
			return entries.get(0);
		}
		
		return null;
	}

	public WareHouseEntryModel queryOneWithMachinePart(WareHouseEntryModel entry) {
		List<WareHouseEntryModel> entries = this.queryListWithMachinePart(entry);
		if(entries.size() == 1) {
			return entries.get(0);
		}
		
		return null;
	}
	
	public void add(WareHouseEntryModel obj) {
		wareHouseEntryMapper.add(obj);
	}
	
	public void delete(WareHouseEntryModel entry) {
		entry.setState(EntryState.DELETE.ordinal());
		wareHouseEntryMapper.update(entry);
	}

	public boolean entry(WareHouseEntryModel entry) {
		if(entry.getType() == EntryType.PN.ordinal()) {
			entry = queryOneWithPn(entry);
			
			for(WareHouseEntryPnModel entryPn : entry.getWareHouseEntryPns()) {
				WareHousePnModel wareHousePn = entryPn.getWareHousePn();
				
				if(wareHousePn == null) {
					//还没入库过，新建
					wareHousePn = new WareHousePnModel();
					wareHousePn.setPn(entryPn.getPn());
					wareHousePn.setProducedNum(entryPn.getProducedNum());
					wareHousePn.setSemiProducedNum(entryPn.getSemiProducedNum());
					wareHousePnService.add(wareHousePn);
				} else {
					float semiNum = wareHousePn.getSemiProducedNum() + entryPn.getSemiProducedNum();
					wareHousePn.setSemiProducedNum(semiNum);
					
					float num = wareHousePn.getProducedNum() + entryPn.getProducedNum();
					wareHousePn.setProducedNum(num);
					
					wareHousePnService.update(wareHousePn);
				}
			}
		} else if(entry.getType() == EntryType.BOM.ordinal()) {
			entry = queryOneWithBOM(entry);
			
			for(WareHouseEntryBOMModel entryBOM : entry.getWareHouseEntryBOMs()) {
				WareHouseBOMModel wareHouseBOM = entryBOM.getWareHouseBOM();
				
				if(wareHouseBOM == null) {
					//还没入库过，新建
					wareHouseBOM = new WareHouseBOMModel();
					wareHouseBOM.setBom(entryBOM.getBom());
					wareHouseBOM.setNum(entryBOM.getNum());
					wareHouseBOMService.add(wareHouseBOM);
				} else {
					float num = wareHouseBOM.getNum() + entryBOM.getNum();
					wareHouseBOM.setNum(num);
					
					wareHouseBOMService.update(wareHouseBOM);
				}
			}
		} else if(entry.getType() == EntryType.MACHINEPART.ordinal()) {
			entry = queryOneWithMachinePart(entry);
			
			for(WareHouseEntryMachinePartModel entryMachinePart : entry.getWareHouseEntryMachineParts()) {
				WareHouseMachinePartModel wareHouseMachinePart = entryMachinePart.getWareHouseMachinePart();
				
				if(wareHouseMachinePart == null) {
					//还没入库过，新建
					wareHouseMachinePart = new WareHouseMachinePartModel();
					wareHouseMachinePart.setMachinePart(entryMachinePart.getMachinePart());
					wareHouseMachinePart.setNum(entryMachinePart.getNum());
					wareHouseMachinePartService.add(wareHouseMachinePart);
				} else {
					float num = wareHouseMachinePart.getNum() + entryMachinePart.getNum();
					wareHouseMachinePart.setNum(num);
					
					wareHouseMachinePartService.update(wareHouseMachinePart);
				}
			}
		} else {
			return false;
		}
		
		entry.setEntryTime(new Date());
		entry.setState(EntryState.ENTRIED.ordinal());
		
		wareHouseEntryMapper.update(entry);
		return true;
	}
}
