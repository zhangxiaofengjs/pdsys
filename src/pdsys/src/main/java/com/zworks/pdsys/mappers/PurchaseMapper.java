package com.zworks.pdsys.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.zworks.pdsys.models.PurchaseBOMModel;
import com.zworks.pdsys.models.PurchaseModel;

@Mapper
public interface PurchaseMapper {

	void add(PurchaseModel purchase);
	
	void delete(PurchaseModel purchase);
	
	PurchaseModel queryOne(PurchaseModel purchase);
	
	PurchaseBOMModel queryPurchaseBOM(PurchaseBOMModel purchaseBom);
	
	List<PurchaseBOMModel> showPurchaseDetail(PurchaseBOMModel purchaseBom);
	
	void update(PurchaseModel purchase);
	
	List<PurchaseModel> queryList(PurchaseModel purchase);
}
