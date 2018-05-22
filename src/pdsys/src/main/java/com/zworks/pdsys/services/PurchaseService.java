package com.zworks.pdsys.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zworks.pdsys.common.enumClass.PurchaseState;
import com.zworks.pdsys.common.utils.DateUtils;
import com.zworks.pdsys.mappers.PurchaseBOMMapper;
import com.zworks.pdsys.mappers.PurchaseMapper;
import com.zworks.pdsys.models.PurchaseBOMModel;
import com.zworks.pdsys.models.PurchaseModel;
import com.zworks.pdsys.models.WareHouseEntryBOMModel;
import com.zworks.pdsys.models.WareHouseEntryModel;

@Service
public class PurchaseService {
	@Autowired
    private PurchaseMapper purchaseMapper;
	@Autowired
	private PurchaseBOMMapper purchaseBOMMapper;
	@Autowired
	private WareHouseEntryService wareHouseEntryService;
	@Autowired
	private WareHouseEntryBOMService wareHouseEntryBOMService;
	
	public void add(PurchaseModel purchase) {
		purchaseMapper.add( purchase );
	}
	
	@Transactional
	public void delete(List<PurchaseModel> purchases){
		for(PurchaseModel purchase : purchases) {
			//先删除对应购物单BOM
			for(PurchaseBOMModel phBOM : purchase.getPurchaseBOMs()) {
				purchaseBOMMapper.delete(phBOM);
			}

			purchaseMapper.delete(purchase);
		}
	}

	public PurchaseModel queryOne(PurchaseModel purchase) {
		List<PurchaseModel> ps = queryList(purchase);
		if(ps.size() == 1) {
			return ps.get(0);
		}
		return null;
	}
	
	public PurchaseBOMModel queryPurchaseBOM(PurchaseBOMModel purchaseBom) {
		PurchaseBOMModel pb = purchaseMapper.queryPurchaseBOM(purchaseBom);
		return pb;
	}
	
	public List<PurchaseBOMModel> showPurchaseDetail(PurchaseBOMModel purchaseBom) {
		return purchaseMapper.showPurchaseDetail(purchaseBom);
	}
	
	public void update(PurchaseModel purchase) {
		purchaseMapper.update(purchase);
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

	@Transactional
	public WareHouseEntryModel entry(PurchaseModel p) {
		//创建入库单以及明细
		WareHouseEntryModel entry = p.getWareHouseEntry();
		wareHouseEntryService.add(entry);

		List<WareHouseEntryBOMModel> eBoms = new ArrayList<WareHouseEntryBOMModel>();
		for(PurchaseBOMModel pBOM : p.getPurchaseBOMs()) {
			WareHouseEntryBOMModel whBOM = new WareHouseEntryBOMModel();
			whBOM.setWareHouseEntry(entry);
			whBOM.setBom(pBOM.getBom());
			whBOM.setNum(pBOM.getNum());
			
			wareHouseEntryBOMService.add(whBOM);
			
			eBoms.add(whBOM);
		}
		//进行入库
		entry.setWareHouseEntryBOMs(eBoms);
		wareHouseEntryService.entry(entry);
		
		//更新采购单
		p.setState(PurchaseState.FINISHED.ordinal());
		p.setPurchaseDate(DateUtils.getCurrentDate());
		update(p);
		return entry;
	}
}
