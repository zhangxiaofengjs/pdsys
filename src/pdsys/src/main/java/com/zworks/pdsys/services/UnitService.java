package com.zworks.pdsys.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zworks.pdsys.mappers.UnitMapper;
import com.zworks.pdsys.models.UnitModel;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2018/04/14
 */
@Service
public class UnitService {
	@Autowired
    private UnitMapper unitMapper;
	
	public UnitModel queryObject(int id) {
		UnitModel unit = new UnitModel();
		unit.setId(id);
		List<UnitModel> ss = queryList(unit);
		
		if(ss.size() == 1) {
			return ss.get(0);
		}
		return null;
	}

	public List<UnitModel> queryList(UnitModel unit) {
		return unitMapper.queryList( unit );
	}

	public UnitModel queryOne(UnitModel unit) {
		List<UnitModel> list = queryList(unit);
		if(list.size()==1) {
			return list.get(0);
		}
		return null;
	}

	public void add(UnitModel unit) {
		unitMapper.add(unit);
	}
}
