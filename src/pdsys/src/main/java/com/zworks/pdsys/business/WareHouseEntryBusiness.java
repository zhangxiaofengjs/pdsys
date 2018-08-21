package com.zworks.pdsys.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zworks.pdsys.business.form.beans.WareHouseHistoryFormBean;
import com.zworks.pdsys.common.enumClass.EntryItemKind;
import com.zworks.pdsys.common.enumClass.EntryState;
import com.zworks.pdsys.models.BOMModel;
import com.zworks.pdsys.models.PnModel;
import com.zworks.pdsys.models.WareHouseEntryBOMModel;
import com.zworks.pdsys.models.WareHouseEntryModel;
import com.zworks.pdsys.models.WareHouseEntryPnModel;
import com.zworks.pdsys.services.WareHouseEntryBOMService;
import com.zworks.pdsys.services.WareHouseEntryPnService;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2018/08/19
 */
@Service
public class WareHouseEntryBusiness {
	@Autowired
	WareHouseEntryBOMService wareHouseEntryBOMService;
	@Autowired
	WareHouseEntryPnService wareHouseEntryPnService;
	
	//出库统计
	public List<WareHouseEntryBOMModel> calcEntryBOMs(WareHouseHistoryFormBean formBean) {
		WareHouseEntryBOMModel entryBom = new WareHouseEntryBOMModel();
		
		WareHouseEntryModel entry = new WareHouseEntryModel();
		entry.setState(EntryState.ENTRIED.ordinal());
		entry.setItemKind(EntryItemKind.NORMAL.ordinal());//排除不良品入库单
		
		BOMModel bom = new BOMModel();
		bom.setPn(formBean.getPn());
		bom.setType(formBean.getBomType());
		
		entryBom.setWareHouseEntry(entry);
		entryBom.setBom(bom);
		entryBom.putFilterCond(WareHouseEntryBOMModel.FCK_FUZZYPNSEARCH, true);
		entryBom.putFilterCond(WareHouseEntryBOMModel.FCK_ENTRYSTART, formBean.getStart());
		entryBom.putFilterCond(WareHouseEntryBOMModel.FCK_ENTRYEND, formBean.getEnd());
		entryBom.putFilterCond(WareHouseEntryBOMModel.FCK_GROUPBYBOM, true);
		
		List<WareHouseEntryBOMModel> list = wareHouseEntryBOMService.queryList(entryBom);
		return list;
	}

	public List<WareHouseEntryPnModel> calcEntryPns(WareHouseHistoryFormBean formBean) {
		WareHouseEntryPnModel entryPn = new WareHouseEntryPnModel();
		
		WareHouseEntryModel entry = new WareHouseEntryModel();
		entry.setState(EntryState.ENTRIED.ordinal());
		//entry.setItemKind(EntryItemKind.NORMAL.ordinal());//排除不良品入库单
		
		PnModel pn = new PnModel();
		pn.setPn(formBean.getPn());
		
		entryPn.setWareHouseEntry(entry);
		entryPn.setPn(pn);
		entryPn.putFilterCond(WareHouseEntryPnModel.FCK_FUZZYPNSEARCH, true);
		entryPn.putFilterCond(WareHouseEntryPnModel.FCK_ENTRYSTART, formBean.getStart());
		entryPn.putFilterCond(WareHouseEntryPnModel.FCK_ENTRYEND, formBean.getEnd());
		entryPn.putFilterCond(WareHouseEntryPnModel.FCK_GROUPBYPN, true);
		
		List<WareHouseEntryPnModel> list = wareHouseEntryPnService.queryList(entryPn);
		return list;
	}
}
