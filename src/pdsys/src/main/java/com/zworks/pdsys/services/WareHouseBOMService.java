package com.zworks.pdsys.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zworks.pdsys.common.exception.PdsysException;
import com.zworks.pdsys.mappers.WareHouseBOMMapper;
import com.zworks.pdsys.models.BOMModel;
import com.zworks.pdsys.models.WareHouseBOMModel;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2018/03/30
 */
@Service
public class WareHouseBOMService {
	@Autowired
    private WareHouseBOMMapper wareHouseBOMMapper;
	
	public List<WareHouseBOMModel> queryList(WareHouseBOMModel filterObj) {
		List<WareHouseBOMModel> list = wareHouseBOMMapper.queryList(filterObj);
		return list;
	}
	
	public List<?> queryListByPn(String strPn, boolean fuzzyPnSearch) {
		BOMModel bom = new BOMModel();
		bom.setPn(strPn);
		
		WareHouseBOMModel whBom = new WareHouseBOMModel();
		whBom.setBom(bom);
		
		if(fuzzyPnSearch) {
			whBom.putFilterCond("fuzzyPnSearch", true);
		}
		return queryList(whBom);
	}
	
	public WareHouseBOMModel queryByBomId(int bomId) {
		BOMModel bom = new BOMModel();
		bom.setId(bomId);
		
		WareHouseBOMModel whBom = new WareHouseBOMModel();
		whBom.setBom(bom);
		
		return queryOne(whBom);
	}
	
	public WareHouseBOMModel queryOne(WareHouseBOMModel filterObj) {
		List<WareHouseBOMModel> list = queryList(filterObj);
		if(list.size() == 1) {
			return list.get(0);
		} else if(list.size() > 1) {
			throw new PdsysException("期待以外的数据被查询到");
		}
		return null;
	}

	public void add(WareHouseBOMModel wareHouseBOM) {
		wareHouseBOMMapper.add(wareHouseBOM);
	}

	public void update(WareHouseBOMModel wareHouseBOM) {
		wareHouseBOMMapper.update(wareHouseBOM);
	}
}
