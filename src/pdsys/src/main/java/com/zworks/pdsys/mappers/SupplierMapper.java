package com.zworks.pdsys.mappers;

import org.apache.ibatis.annotations.Mapper;

import com.zworks.pdsys.models.SupplierModel;

/**
 * @author: ZHAI
 */
@Mapper
public interface SupplierMapper {
	
	SupplierModel queryObject(int id);
}
