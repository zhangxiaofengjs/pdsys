package com.zworks.pdsys.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zworks.pdsys.mappers.OrderMapper;
import com.zworks.pdsys.mappers.WareHouseBOMMapper;
import com.zworks.pdsys.mappers.WareHousePnMapper;
import com.zworks.pdsys.models.OrderModel;
import com.zworks.pdsys.models.WareHouseBOMModel;
import com.zworks.pdsys.models.WareHousePnModel;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2018/03/30
 */
@Service
public class WareHousePnService {
	@Autowired
    private WareHousePnMapper wareHousePnMapper;
	
	public List<WareHousePnModel> queryList(WareHousePnModel filterObj) {
		List<WareHousePnModel> list = wareHousePnMapper.queryList(filterObj);
		
		return list;
	}
}
