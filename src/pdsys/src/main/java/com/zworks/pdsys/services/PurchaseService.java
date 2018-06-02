package com.zworks.pdsys.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zworks.pdsys.common.annotations.ResetApprovalInfo;
import com.zworks.pdsys.common.enumClass.ApprovalState;
import com.zworks.pdsys.common.enumClass.PurchaseState;
import com.zworks.pdsys.common.utils.DateUtils;
import com.zworks.pdsys.mappers.PurchaseBOMMapper;
import com.zworks.pdsys.mappers.PurchaseMapper;
import com.zworks.pdsys.models.ApprovalInfoModel;
import com.zworks.pdsys.models.ApprovalNodeModel;
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
	@Autowired
	PurchaseBOMService purchaseBOMService;
	@Autowired
	ApprovalService approvalService;
	@Autowired
	ApprovalInfoService approvalInfoService;
	
	@Transactional
	public void add(PurchaseModel purchase) {
		//TODO 现在默认设定为仅有的一条批复节点，以后其他需要承认需要界面设定
		ApprovalNodeModel node = approvalService.queryList(null).get(0);
		
		ApprovalInfoModel info = new ApprovalInfoModel();
		info.setNode(node);
		info.setState(ApprovalState.WORKING.ordinal());
		approvalInfoService.add(info);

		purchase.setApprovalInfo(info);
		purchaseMapper.add( purchase );
		purchaseBOMService.add(purchase.getPurchaseBOMs());
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

	@ResetApprovalInfo
	public PurchaseModel queryOne(PurchaseModel purchase) {
		List<PurchaseModel> ps = queryList(purchase);
		if(ps.size() == 1) {
			return ps.get(0);
		}
		return null;
	}
	
	public void update(PurchaseModel purchase) {
		purchaseMapper.update(purchase);
	}

	@ResetApprovalInfo
	public List<PurchaseModel> queryList(PurchaseModel purchase) {
		List<PurchaseModel> purchases = purchaseMapper.queryList(purchase);
		return purchases;
	}
	
	public boolean checkSupplierIdIsNull(PurchaseModel purchase)
	{
		List<PurchaseBOMModel> purchaseBoms = purchase.getPurchaseBOMs();
		for(PurchaseBOMModel pb : purchaseBoms) {
			if( pb.getSupplier() == null ) {
				return true;
			}
		}
		return false;
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
