package com.zworks.pdsys.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.zworks.pdsys.common.exception.PdsysException;
import com.zworks.pdsys.common.utils.ListUtils;
import com.zworks.pdsys.common.utils.StringUtils;
import com.zworks.pdsys.models.BOMModel;
import com.zworks.pdsys.models.PnModel;
import com.zworks.pdsys.models.PurchaseBOMModel;
import com.zworks.pdsys.models.WareHouseBOMModel;
import com.zworks.pdsys.models.WareHouseDeliveryBOMModel;
import com.zworks.pdsys.models.WareHouseEntryBOMModel;
import com.zworks.pdsys.services.BOMService;
import com.zworks.pdsys.services.PnService;
import com.zworks.pdsys.services.PurchaseBOMService;
import com.zworks.pdsys.services.WareHouseBOMService;
import com.zworks.pdsys.services.WareHouseDeliveryBOMService;
import com.zworks.pdsys.services.WareHouseEntryBOMService;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2018/09/03
 */
@Service
public class BOMBusiness {
	@Autowired
	private BOMService bomService;
	@Autowired
	private PnService pnService;
	@Autowired
	private WareHouseBOMService wareHouseBOMService;
	@Autowired
	private WareHouseEntryBOMService wareHouseEntryBOMService;
	@Autowired
	private WareHouseDeliveryBOMService wareHouseDeliveryBOMService;
	@Autowired
	private PurchaseBOMService purchaseBOMService;
	
	public void update(BOMModel bom) {
		if(StringUtils.isNullOrEmpty(bom.getPn()) ||
			StringUtils.isNullOrEmpty(bom.getName())) {
			throw new PdsysException("品番或者名称不能为空");
		}
		
		BOMModel b = new BOMModel();
		b.setId(bom.getId());
		b = bomService.queryOne(b);
		if(b == null) {
			throw new PdsysException("该品番不存在");
		}
		
		BOMModel b2 = new BOMModel();
		b2.setPn(bom.getPn());
		b2 = bomService.queryOne(b2);
		if(b2 != null) {
			if(b2.getId() != bom.getId()) {
				throw new PdsysException("同品番的品目已经存在:" + bom.getPn());
			}
		}
		
		if(b.getType() != bom.getType()) {
			//@todo 因为涉及到原包材消耗的计算，暂时只能更新未使用过的BOM种类
			List<PnModel> pns = pnService.queryByBOMId(bom.getId());
			if(!ListUtils.isNullOrEmpty(pns)) {
				String str = "";
				for(PnModel pn : pns) {
					str += "<br>" + pn.getPn();
				}
				throw new PdsysException("该品番已经和品目关联:" + str);
			}
		}
		
		bomService.update(bom);
	}
	
	private void checkUsed(BOMModel bom) {
		//品番使用中？
		List<PnModel> pns = pnService.queryByBOMId(bom.getId());
		if(!ListUtils.isNullOrEmpty(pns)) {
			throw new PdsysException("该品番品目使用中。品目:" + pns.get(0).getPn());
		}
		
		//库存使用中？
		WareHouseBOMModel whBom = wareHouseBOMService.queryByBomId(bom.getId());
		if(whBom != null && (whBom.getNum() !=0 || whBom.getDefectiveNum() !=0 || whBom.getDeliveryRemainingNum() != 0)) {
			throw new PdsysException("该品番库存使用中。");
		}
		
		//入出库单使用中？
		List<WareHouseEntryBOMModel> wheBOMs = wareHouseEntryBOMService.queryByBOMId(bom.getId());
		if(!ListUtils.isNullOrEmpty(wheBOMs)) {
			throw new PdsysException("该品番入库单使用中。入库单号:" + wheBOMs.get(0).getWareHouseEntry().getNo());
		}
		List<WareHouseDeliveryBOMModel> whdBOMs = wareHouseDeliveryBOMService.queryByBOMId(bom.getId());
		if(!ListUtils.isNullOrEmpty(whdBOMs)) {
			throw new PdsysException("该品番出库单使用中。出库单号:" + whdBOMs.get(0).getWareHouseDelivery().getNo());
		}
		
		//采购单使用中
		PurchaseBOMModel purchaseBOM = new PurchaseBOMModel();
		purchaseBOM.setBom(bom);
		List<PurchaseBOMModel> list = purchaseBOMService.queryList(purchaseBOM);
		if(!ListUtils.isNullOrEmpty(list)) {
			throw new PdsysException("该品番采购单使用中。采购单号:" + list.get(0).getPurchase().getNo());
		}
	}
	
	public void delete(BOMModel bom) {
		BOMModel b = bomService.queryOne(bom);
		if(b == null) {
			throw new PdsysException("该品番不存在");
		}
		
		checkUsed(bom);
		
		//删除库存0件的关联
		wareHouseBOMService.deleteByBOMId(bom.getId());

		//删除供应商关联
		bomService.deleteSupplier(b);

		//删除自己
		bomService.delete(b);
	}
}
