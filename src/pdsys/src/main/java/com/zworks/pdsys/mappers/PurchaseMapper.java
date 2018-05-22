package com.zworks.pdsys.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.zworks.pdsys.models.PurchaseBOMModel;
import com.zworks.pdsys.models.PurchaseModel;

@Mapper
public interface PurchaseMapper {

	void add(PurchaseModel purchase);
	
	void delete(PurchaseModel purchase);
	
	void update(PurchaseModel purchase);
	
	List<PurchaseModel> queryList(PurchaseModel purchase);
}
