package com.zworks.pdsys.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.zworks.pdsys.models.PurchaseBOMModel;
import com.zworks.pdsys.models.PurchaseModel;

@Mapper
public interface PurchaseMapper {

	void savePurchase(PurchaseModel purchase);
	
	void savePurchaseDetail(List<PurchaseBOMModel> purchaseBoms);
	
	void delPurchaseDetail(PurchaseBOMModel purchaseBoms);
	
	PurchaseModel queryOne(PurchaseModel purchase);
	
	PurchaseBOMModel queryPurchaseBOM(PurchaseBOMModel purchaseBom);
	
	void updatePB(PurchaseBOMModel purchaseBom);
	
	List<PurchaseBOMModel> showPurchaseDetail(PurchaseBOMModel purchaseBom);
	
	void updatePurchase(PurchaseModel purchase);
}
