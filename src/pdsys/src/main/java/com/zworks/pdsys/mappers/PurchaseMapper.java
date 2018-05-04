package com.zworks.pdsys.mappers;

import org.apache.ibatis.annotations.Mapper;

import com.zworks.pdsys.models.PurchaseModel;

@Mapper
public interface PurchaseMapper {

	void save(PurchaseModel purchase);
}
