package com.zworks.pdsys.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	
	public List<BOMModel> queryList(WareHouseBOMModel filterObj) {
		List<BOMModel> list = wareHouseBOMMapper.queryList(filterObj);
		
		return list;
	}

	public void add(WareHouseBOMModel wareHouseBOM) {
		wareHouseBOMMapper.add(wareHouseBOM);
	}

	public void update(WareHouseBOMModel wareHouseBOM) {
		wareHouseBOMMapper.update(wareHouseBOM);
	}
}
