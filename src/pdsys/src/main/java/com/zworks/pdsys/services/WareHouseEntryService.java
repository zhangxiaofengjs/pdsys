package com.zworks.pdsys.services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zworks.pdsys.common.enumClass.EntryState;
import com.zworks.pdsys.mappers.WareHouseEntryMapper;
import com.zworks.pdsys.models.WareHouseEntryModel;
import com.zworks.pdsys.models.WareHouseEntryPnModel;
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
	
	public List<WareHouseEntryModel> queryList(WareHouseEntryModel obj) {
		return wareHouseEntryMapper.queryList(obj);
	}
	
	public void add(WareHouseEntryModel obj) {
		wareHouseEntryMapper.add(obj);
	}

	public WareHouseEntryModel queryOne(int id) {
		WareHouseEntryModel entry = new WareHouseEntryModel();
		entry.setId(id);

		List<WareHouseEntryModel> entries = this.queryList(entry);
		if(entries.size() == 1) {
			return entries.get(0);
		}
		
		return null;
	}

	public void delete(WareHouseEntryModel entry) {
		entry.setState(EntryState.DELETE.ordinal());
		wareHouseEntryMapper.update(entry);
	}

	public boolean entry(WareHouseEntryModel entry) {
		for(WareHouseEntryPnModel entryPn : entry.getWareHouseEntryPns()) {
			WareHousePnModel wareHousePn = entryPn.getWareHousePn();
			
			if(wareHousePn == null) {
				//还没入库过，新建
				wareHousePn = new WareHousePnModel();
				wareHousePn.setOrderPn(entryPn.getOrderPn());
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
		
		entry.setEntryTime(new Date());
		entry.setState(EntryState.ENTRIED.ordinal());
		
		wareHouseEntryMapper.update(entry);
		return true;
	}
}
