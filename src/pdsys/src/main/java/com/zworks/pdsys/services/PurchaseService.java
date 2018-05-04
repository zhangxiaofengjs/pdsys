package com.zworks.pdsys.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zworks.pdsys.mappers.PurchaseMapper;
import com.zworks.pdsys.models.PurchaseBOMModel;
import com.zworks.pdsys.models.PurchaseModel;

@Service
public class PurchaseService {
	@Autowired
    private PurchaseMapper purchaseMapper;
	
	public void savePurchase(PurchaseModel purchase) {
		purchaseMapper.savePurchase( purchase );
	}
	
	public void savePurchaseDetail(List<PurchaseBOMModel> purchaseBoms) {
		purchaseMapper.savePurchaseDetail( purchaseBoms );
	}
	
}
