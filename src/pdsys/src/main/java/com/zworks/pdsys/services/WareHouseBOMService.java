package com.zworks.pdsys.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zworks.pdsys.mappers.OrderMapper;
import com.zworks.pdsys.mappers.WareHouseBOMMapper;
import com.zworks.pdsys.models.OrderModel;
import com.zworks.pdsys.models.WareHouseBOMModel;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2018/03/30
 */
@Service
public class WareHouseBOMService {
	@Autowired
    private WareHouseBOMMapper wareHouseMapper;
	
	public List<WareHouseBOMModel> queryList(WareHouseBOMModel filterObj) {
		List<WareHouseBOMModel> list = wareHouseMapper.queryList(filterObj);
		
		return list;
	}
}
