package com.zworks.pdsys.business;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zworks.pdsys.common.enumClass.PurchaseState;
import com.zworks.pdsys.common.utils.DateUtils;
import com.zworks.pdsys.models.PurchaseBOMModel;
import com.zworks.pdsys.models.PurchaseModel;
import com.zworks.pdsys.models.WareHouseEntryBOMModel;
import com.zworks.pdsys.models.WareHouseEntryModel;
import com.zworks.pdsys.services.PurchaseService;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2018/08/27
 */
@Service
public class PurchaseBusiness {
	@Autowired
    private PurchaseService purchaseService;
	
	public void CreateEntryBOMs(PurchaseModel p, WareHouseEntryModel entry) {
		List<WareHouseEntryBOMModel> eBoms = new ArrayList<WareHouseEntryBOMModel>();
		for(PurchaseBOMModel pBOM : p.getPurchaseBOMs()) {
			WareHouseEntryBOMModel whBOM = new WareHouseEntryBOMModel();
			whBOM.setWareHouseEntry(entry);
			whBOM.setBom(pBOM.getBom());
			whBOM.setNum(pBOM.getNum());
			whBOM.setSupplier(pBOM.getSupplier());
			
			eBoms.add(whBOM);
		}
		entry.setWareHouseEntryBOMs(eBoms);
	}

	@Transactional
	public void entry(PurchaseModel p) {
		//更新采购单
		p.setState(PurchaseState.FINISHED.ordinal());
		p.setPurchaseDate(DateUtils.getCurrentDate());
		purchaseService.update(p);
	}
}
