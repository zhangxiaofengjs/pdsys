package com.zworks.pdsys.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zworks.pdsys.mappers.WareHouseEntryPnMapper;
import com.zworks.pdsys.models.WareHouseEntryPnModel;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2018/04/06
 */
@Service
public class WareHouseEntryPnService {
	@Autowired
    private WareHouseEntryPnMapper wareHouseEntryPnMapper;
	
	public List<WareHouseEntryPnModel> queryList(WareHouseEntryPnModel entryPn) {
		return wareHouseEntryPnMapper.queryList(entryPn);
	}
	
	public WareHouseEntryPnModel queryOne(WareHouseEntryPnModel entryPn) {
		List<WareHouseEntryPnModel> ePns = queryList(entryPn);
		if(ePns.size()==1) {
			return ePns.get(0);
		}
		return null;
	}
	
	@Transactional
	public void delete(List<WareHouseEntryPnModel> entryPns) {
		for(WareHouseEntryPnModel entryPn : entryPns) {
			delete(entryPn);
		}
	}
	
	public void delete(WareHouseEntryPnModel ePn) {
		wareHouseEntryPnMapper.delete(ePn);
	}

	public boolean exist(WareHouseEntryPnModel entryPn) {
		return queryOne(entryPn) != null;
	}

	public void update(WareHouseEntryPnModel entryPn) {
		wareHouseEntryPnMapper.update(entryPn);
	}

	public void add(WareHouseEntryPnModel entryPn) {
		wareHouseEntryPnMapper.add(entryPn);
	}
}
