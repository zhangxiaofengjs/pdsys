package com.zworks.pdsys.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zworks.pdsys.mappers.WareHouseEntryBOMMapper;
import com.zworks.pdsys.models.WareHouseEntryBOMModel;
import com.zworks.pdsys.models.WareHouseEntryPnModel;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2018/04/23
 */
@Service
public class WareHouseEntryBOMService {
	@Autowired
    private WareHouseEntryBOMMapper wareHouseEntryBOMMapper;
	
	public List<WareHouseEntryBOMModel> queryList(WareHouseEntryBOMModel entryPn) {
		return wareHouseEntryBOMMapper.queryList(entryPn);
	}
	
	public WareHouseEntryBOMModel queryOne(WareHouseEntryBOMModel entryPn) {
		List<WareHouseEntryBOMModel> eBOMs = queryList(entryPn);
		if(eBOMs.size()==1) {
			return eBOMs.get(0);
		}
		return null;
	}
	
	@Transactional
	public void delete(List<WareHouseEntryBOMModel> entryBOMs) {
		for(WareHouseEntryBOMModel entryBOM : entryBOMs) {
			delete(entryBOM);
		}
	}
	
	public void delete(WareHouseEntryBOMModel eBOM) {
		wareHouseEntryBOMMapper.delete(eBOM);
	}

	public boolean exist(WareHouseEntryBOMModel entryBOM) {
		return queryOne(entryBOM) != null;
	}

	public void update(WareHouseEntryBOMModel entryBOM) {
		wareHouseEntryBOMMapper.update(entryBOM);
	}

	public void add(WareHouseEntryBOMModel entryBOM) {
		wareHouseEntryBOMMapper.add(entryBOM);
	}
}
