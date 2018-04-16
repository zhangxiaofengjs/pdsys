package com.zworks.pdsys.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zworks.pdsys.mappers.SupplierMapper;
import com.zworks.pdsys.models.SupplierModel;
import com.zworks.pdsys.models.UnitModel;

@Service
public class SupplierService {
	@Autowired
    private SupplierMapper supplierMapper;
	
	public SupplierModel queryObject(int id) {
		SupplierModel supplier = new SupplierModel();
		supplier.setId(id);
		List<SupplierModel> ss = queryList(supplier);
		
		if(ss.size() == 1) {
			return ss.get(0);
		}
		return null;
	}

	public List<SupplierModel> queryList(SupplierModel supplier) {
		return supplierMapper.queryList( supplier );
	}
	
	public SupplierModel queryOne(SupplierModel supplier) {
		List<SupplierModel> list = queryList(supplier);
		if(list.size()==1) {
			return list.get(0);
		}
		return null;
	}

	public void add(SupplierModel supplier) {
		supplierMapper.add(supplier);
	}
}
