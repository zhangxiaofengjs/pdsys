package com.zworks.pdsys.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zworks.pdsys.mappers.PlaceMapper;
import com.zworks.pdsys.models.DeviceModel;
import com.zworks.pdsys.models.PlaceModel;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2018/04/08
 */
@Service
public class PlaceService {
	@Autowired
    private PlaceMapper placeMapper;
	
	public List<PlaceModel> queryList(PlaceModel filterObj) {
		List<PlaceModel> list = placeMapper.queryList(filterObj);
		
		return list;
	}
}
