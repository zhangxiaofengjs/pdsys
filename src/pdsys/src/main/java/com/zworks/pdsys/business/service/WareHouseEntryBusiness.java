package com.zworks.pdsys.business.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zworks.pdsys.business.form.beans.WareHouseHistoryFormBean;
import com.zworks.pdsys.common.enumClass.EntryItemKind;
import com.zworks.pdsys.common.enumClass.EntryState;
import com.zworks.pdsys.models.BOMModel;
import com.zworks.pdsys.models.WareHouseEntryBOMModel;
import com.zworks.pdsys.models.WareHouseEntryModel;
import com.zworks.pdsys.services.WareHouseEntryBOMService;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2018/08/19
 */
@Service
public class WareHouseEntryBusiness {
	@Autowired
	WareHouseEntryBOMService wareHouseEntryBOMService;
	
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
}
