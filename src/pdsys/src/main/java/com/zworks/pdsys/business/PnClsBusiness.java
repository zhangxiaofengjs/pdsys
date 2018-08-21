package com.zworks.pdsys.business;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zworks.pdsys.business.form.beans.RelateBOMFormBean;
import com.zworks.pdsys.common.exception.PdsysException;
import com.zworks.pdsys.models.BOMModel;
import com.zworks.pdsys.models.PnClsBOMRelModel;
import com.zworks.pdsys.models.PnClsModel;
import com.zworks.pdsys.models.PnModel;
import com.zworks.pdsys.services.PnClsService;
import com.zworks.pdsys.services.PnService;
import com.zworks.pdsys.services.WareHouseDeliveryPnService;
import com.zworks.pdsys.services.WareHouseDeliverySemiPnService;
import com.zworks.pdsys.services.WareHouseEntryPnService;
import com.zworks.pdsys.services.WareHouseEntrySemiPnService;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2018/08/21
 */

@Service
public class PnClsBusiness {
	@Autowired
	private PnService pnService;
	@Autowired
	private PnClsService pnClsService;
	@Autowired
	private WareHouseEntryPnService wareHouseEntryPnService;
	@Autowired
	private WareHouseDeliveryPnService wareHouseDeliveryPnService;
	@Autowired
	private WareHouseEntrySemiPnService wareHouseEntrySemiPnService;
	@Autowired
	private WareHouseDeliverySemiPnService wareHouseDeliverySemiPnService;
	
	private boolean existsBOM(PnClsModel pnCls) {
		PnClsModel targetPnCls = pnClsService.queryOne(pnCls);
		if(targetPnCls == null) {
			return false;
		}
		
		List<PnClsBOMRelModel> bomRels = pnCls.getPnClsBOMRels();
		List<PnClsBOMRelModel> targetBomRels = targetPnCls.getPnClsBOMRels();
		
		for(PnClsBOMRelModel bomRel : bomRels) {
			for(PnClsBOMRelModel bRel : targetBomRels) {
				if(bomRel.getBom().getId() == bRel.getBom().getId()) {
					//同样子类下有同样的BOM
					return true;
				}
			}
		}
		
		return false;
	}
	
	public void addBOM(PnClsModel pnCls) {
		if(existsBOM(pnCls)) {
			throw new PdsysException("已经存在该原包材");
		}
		
		pnClsService.addBOM(pnCls);
	}

	private void checkPnUsed(PnModel pn) {
		//入出库单使用中？ 
		wareHouseEntryPnService.checkUsedPn(pn);
		wareHouseDeliveryPnService.checkUsedPn(pn);
	}
	
	private void checkPnClsUsed(PnClsModel pnCls) {
		wareHouseEntrySemiPnService.checkUsedPnCls(pnCls);
		wareHouseDeliverySemiPnService.checkUsedPnCls(pnCls);
	}
	
	public void deleteBOM(PnClsModel pnCls) {
		//入出库涉及到原包材的计算
		PnModel pn = pnService.queryByClsId(pnCls.getId());
		checkPnUsed(pn);
		
		checkPnClsUsed(pnCls);

		pnClsService.deleteBOM(pnCls);
	}
	
	@Transactional
	public void changeBOM(RelateBOMFormBean formBean) {
		BOMModel bom = new BOMModel();
		bom.setId(formBean.getBomId());
		
		PnClsModel pnCls = new PnClsModel();
		pnCls.setId(formBean.getPnClsId());
		
		PnClsBOMRelModel pnClsBOM = new PnClsBOMRelModel();
		pnClsBOM.setBom(bom);
		pnClsBOM.setUseNum(formBean.getUseNum());
		
		List<PnClsBOMRelModel> bomRels = new ArrayList<PnClsBOMRelModel>();
		bomRels.add(pnClsBOM);
	
		pnCls.setPnClsBOMRels(bomRels);
		
		//删除原BOM
		deleteBOM(pnCls);

		//追加新BOM
		bom.setId(formBean.getNewBomId());
		addBOM(pnCls);
	}
}
