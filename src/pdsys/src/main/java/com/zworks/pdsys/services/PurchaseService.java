package com.zworks.pdsys.services;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
	
	@Transactional
	public void delPurchaseDetail(List<PurchaseBOMModel> purchaseBoms){
		for(PurchaseBOMModel purchaseBom : purchaseBoms) 
		{
			purchaseMapper.delPurchaseDetail(purchaseBom);
		}
	}
	
	@Transactional
	public void delPurchase(List<PurchaseModel> purchases){
		for(PurchaseModel purchase : purchases) 
		{
			purchaseMapper.delPurchase(purchase);
		}
	}

	public PurchaseModel queryOne(PurchaseModel purchase) {
		PurchaseModel p = purchaseMapper.queryOne(purchase);
		return p;
	}
	
	public PurchaseBOMModel queryPurchaseBOM(PurchaseBOMModel purchaseBom) {
		PurchaseBOMModel pb = purchaseMapper.queryPurchaseBOM(purchaseBom);
		return pb;
	}
	
	public void updatePB(PurchaseBOMModel purchaseBom) {
		purchaseMapper.updatePB(purchaseBom);
	}
	
	public List<PurchaseBOMModel> showPurchaseDetail(PurchaseBOMModel purchaseBom) {
		return purchaseMapper.showPurchaseDetail(purchaseBom);
	}
	
	public void updatePurchase(PurchaseModel purchase) {
		purchaseMapper.updatePurchase(purchase);
	}
	
	public List<PurchaseModel> queryList(PurchaseModel purchase) {
		List<PurchaseModel> purchases = purchaseMapper.queryList(purchase);
		return purchases;
	}
	
	//去除重复的状态
	public int[] removeDuplicate(List<PurchaseModel> list) {
        Set<Integer> set=new TreeSet<Integer>();
        for (int i = 0; i < list.size(); i++) {
        	PurchaseModel o=list.get(i);
        	set.add(o.getState());
        }
        
        Integer[] arr2=set.toArray(new Integer[0]);
        int[] result=new int[arr2.length];
        
        for (int i = 0; i < result.length; i++) {
            result[i]=arr2[i];
        }
        return result;

	}
	
	public boolean checkSupplierIdIsNull(PurchaseModel purchase)
	{
		boolean ret = false;
		PurchaseBOMModel purchaseBom = new PurchaseBOMModel();
		purchaseBom.setPurchase(purchase);
		List<PurchaseBOMModel> purchaseBoms = this.showPurchaseDetail(purchaseBom);
		for(int i =0;i<purchaseBoms.size();i++)
		{
			PurchaseBOMModel pb = purchaseBoms.get(i);
			if( pb.getSupplier() == null )
			{
				ret = true;
				break;
			}
		}
		return ret;
	}
	
}
