package com.zworks.pdsys.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zworks.pdsys.common.exception.PdsysException;
import com.zworks.pdsys.common.utils.ListUtils;
import com.zworks.pdsys.mappers.WareHouseEntryPnMapper;
import com.zworks.pdsys.models.PnModel;
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
		if(ListUtils.isNullOrEmpty(ePns)) {
			return null;
		}
		if(ePns.size()==1) {
			return ePns.get(0);
		}
		throw new PdsysException("未想定数目：WareHouseEntryPnService-queryOne");
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
		List<WareHouseEntryPnModel> ePns = queryList(entryPn);
		return ePns.size() != 0;
	}

	public void update(WareHouseEntryPnModel entryPn) {
		wareHouseEntryPnMapper.update(entryPn);
	}

	public void add(WareHouseEntryPnModel entryPn) {
		wareHouseEntryPnMapper.add(entryPn);
	}

	public void checkUsedPn(PnModel pn) {
		WareHouseEntryPnModel whPn = new WareHouseEntryPnModel();
		whPn.setPn(pn);
	
		List<WareHouseEntryPnModel> list = queryList(whPn);
		if(!ListUtils.isNullOrEmpty(list)) {
			throw new PdsysException("入库单使用中");
		}
	}
}
