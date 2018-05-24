package com.zworks.pdsys.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zworks.pdsys.mappers.WareHouseEntrySemiPnMapper;
import com.zworks.pdsys.models.WareHouseEntrySemiPnModel;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2018/05/23
 */
@Service
public class WareHouseEntrySemiPnService {
	@Autowired
    private WareHouseEntrySemiPnMapper wareHouseEntrySemiPnMapper;
	
	public List<WareHouseEntrySemiPnModel> queryList(WareHouseEntrySemiPnModel entryPn) {
		return wareHouseEntrySemiPnMapper.queryList(entryPn);
	}
	
	public WareHouseEntrySemiPnModel queryOne(WareHouseEntrySemiPnModel entryPn) {
		List<WareHouseEntrySemiPnModel> ePns = queryList(entryPn);
		if(ePns.size()==1) {
			return ePns.get(0);
		}
		return null;
	}
	
	@Transactional
	public void delete(List<WareHouseEntrySemiPnModel> entryPns) {
		for(WareHouseEntrySemiPnModel entryPn : entryPns) {
			delete(entryPn);
		}
	}
	
	public void delete(WareHouseEntrySemiPnModel ePn) {
		wareHouseEntrySemiPnMapper.delete(ePn);
	}

	public boolean exist(WareHouseEntrySemiPnModel entryPn) {
		return queryOne(entryPn) != null;
	}

	public void update(WareHouseEntrySemiPnModel entryPn) {
		wareHouseEntrySemiPnMapper.update(entryPn);
	}

	public void add(WareHouseEntrySemiPnModel entryPn) {
		wareHouseEntrySemiPnMapper.add(entryPn);
	}
}
