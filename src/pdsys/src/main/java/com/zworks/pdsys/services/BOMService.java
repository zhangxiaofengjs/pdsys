package com.zworks.pdsys.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zworks.pdsys.mappers.BOMMapper;
import com.zworks.pdsys.models.BOMModel;
import com.zworks.pdsys.models.SupplierModel;

@Service
public class BOMService {
	@Autowired
    private BOMMapper bomMapper;
	
	public List<BOMModel> queryList(BOMModel bomModel) {
		return bomMapper.queryList(bomModel);
	}

	public BOMModel queryOne(BOMModel b) {
		List<BOMModel> cs = queryList(b);
		
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

	public void addSupplier(BOMModel bom) {
		bomMapper.addSupplier(bom);
	}
	
	public void deleteSupplier(BOMModel bom) {
		bomMapper.deleteSupplier(bom);
	}

	public boolean existsSupplier(BOMModel checkBOM, List<SupplierModel> suppliers) {
		List<SupplierModel> ss = checkBOM.getSuppliers();
		for(SupplierModel s : ss) {
			for(SupplierModel s1 : suppliers) {
				if(s.getId() == s1.getId()) {
					return true;
				}
			}
		}
		return false;
	}
}
