package com.zworks.pdsys.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zworks.pdsys.mappers.WareHouseBOMMapper;
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
	
	public WareHouseBOMModel queryOne(WareHouseBOMModel filterObj) {
		List<WareHouseBOMModel> list = queryList(filterObj);
		if(list.size()==1) {
			return list.get(0);
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
