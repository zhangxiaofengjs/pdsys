package com.zworks.pdsys.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.zworks.pdsys.models.SupplierModel;

/**
 * @author: ZHAI
 */
@Mapper
public interface SupplierMapper {
	
	List<SupplierModel> queryList(SupplierModel supplier);

	void add(SupplierModel supplier);
}
