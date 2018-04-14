package com.zworks.pdsys.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zworks.pdsys.mappers.BOMMapper;
import com.zworks.pdsys.models.BOMModel;

@Service
public class BOMService {
	@Autowired
    private BOMMapper bomMapper;
	
	public List<BOMModel> queryList(BOMModel bomModel) {
		return bomMapper.queryList(bomModel);
	}

	public BOMModel queryById(int id) {
		BOMModel c = new BOMModel();
		c.setId(id);
		List<BOMModel> cs = queryList(c);
		
		if(cs.size() ==1) {
			return cs.get(0);
		}
		return null;
	}
	
	public void add(BOMModel filterObj) {
		bomMapper.add(filterObj);
	}
	public void update(BOMModel filterObj) {
		bomMapper.update(filterObj);
	}
}
