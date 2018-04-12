package com.zworks.pdsys.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zworks.pdsys.mappers.SupplierMapper;
import com.zworks.pdsys.models.SupplierModel;

@Service
public class SupplierService {
	@Autowired
    private SupplierMapper supplierMapper;
	
	public SupplierModel queryObject(int id) {
		return supplierMapper.queryObject( id );
	}

}
