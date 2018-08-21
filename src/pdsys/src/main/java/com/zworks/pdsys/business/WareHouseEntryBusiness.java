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
import com.zworks.pdsys.models.WareHouseEntryMachinePartModel;
import com.zworks.pdsys.models.WareHouseEntryModel;
import com.zworks.pdsys.models.WareHouseEntryPnModel;
import com.zworks.pdsys.models.WareHouseEntrySemiPnModel;
import com.zworks.pdsys.services.WareHouseEntryBOMService;
import com.zworks.pdsys.services.WareHouseEntryMachinePartService;
import com.zworks.pdsys.services.WareHouseEntryPnService;
import com.zworks.pdsys.services.WareHouseEntrySemiPnService;

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
	@Autowired
	WareHouseEntrySemiPnService wareHouseEntrySemiPnService;
	@Autowired
	WareHouseEntryMachinePartService wareHouseEntryMachinePartService;
	
	public void updateEntryPn(WareHouseEntryPnModel entryPn) {
		WareHouseEntryPnModel ePn = wareHouseEntryPnService.queryOne(entryPn);
		
		if(ePn == null) {
			wareHouseEntryPnService.add(entryPn);
		} else {
			//否则追加到既存
			entryPn.setProducedNum( entryPn.getProducedNum() + ePn.getProducedNum());
			wareHouseEntryPnService.update(entryPn);
		}
	}

	public void updateEntrySemiPn(WareHouseEntrySemiPnModel entrySemiPn) {
		WareHouseEntrySemiPnModel eSemiPn = wareHouseEntrySemiPnService.queryOne(entrySemiPn);
		
		if(eSemiPn == null) {
			wareHouseEntrySemiPnService.add(entrySemiPn);
		} else {
			//否则追加到既存
			entrySemiPn.setNum( entrySemiPn.getNum() + eSemiPn.getNum());
			wareHouseEntrySemiPnService.update(entrySemiPn);
		}
	}

	public void updateEntryBOM(WareHouseEntryBOMModel entryBOM) {
		WareHouseEntryBOMModel eBOM = wareHouseEntryBOMService.queryOne(entryBOM);
		
		if(eBOM == null) {
			wareHouseEntryBOMService.add(entryBOM);
		} else {
			//否则追加到既存
			entryBOM.setNum( entryBOM.getNum() + eBOM.getNum());
			wareHouseEntryBOMService.update(entryBOM);
		}
	}
	
	public void updateEntryMachinePart(WareHouseEntryMachinePartModel entryMp) {
		WareHouseEntryMachinePartModel eMp = wareHouseEntryMachinePartService.queryOne(entryMp);
		
		if(eMp == null) {
			wareHouseEntryMachinePartService.add(entryMp);
		} else {
			//否则追加到既存
			entryMp.setNum( entryMp.getNum() + eMp.getNum());
			wareHouseEntryMachinePartService.update(entryMp);
		}
	}
	
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
