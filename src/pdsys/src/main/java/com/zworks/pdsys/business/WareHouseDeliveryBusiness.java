package com.zworks.pdsys.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zworks.pdsys.business.form.beans.WareHouseHistoryFormBean;
import com.zworks.pdsys.common.enumClass.DeliveryState;
import com.zworks.pdsys.common.enumClass.EntryItemKind;
import com.zworks.pdsys.common.enumClass.EntryState;
import com.zworks.pdsys.models.BOMModel;
import com.zworks.pdsys.models.PnModel;
import com.zworks.pdsys.models.WareHouseDeliveryBOMModel;
import com.zworks.pdsys.models.WareHouseDeliveryMachinePartModel;
import com.zworks.pdsys.models.WareHouseDeliveryModel;
import com.zworks.pdsys.models.WareHouseDeliveryPnModel;
import com.zworks.pdsys.models.WareHouseDeliverySemiPnModel;
import com.zworks.pdsys.models.WareHouseEntryBOMModel;
import com.zworks.pdsys.models.WareHouseEntryModel;
import com.zworks.pdsys.services.WareHouseDeliveryBOMService;
import com.zworks.pdsys.services.WareHouseDeliveryMachinePartService;
import com.zworks.pdsys.services.WareHouseDeliveryPnService;
import com.zworks.pdsys.services.WareHouseDeliverySemiPnService;
import com.zworks.pdsys.services.WareHouseEntryBOMService;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2018/08/19
 */
@Service
public class WareHouseDeliveryBusiness {
	@Autowired
	WareHouseDeliveryBOMService wareHouseDeliveryBOMService;
	@Autowired
	WareHouseDeliveryPnService wareHouseDeliveryPnService;
	@Autowired
	WareHouseEntryBOMService wareHouseEntryBOMService;
	@Autowired
	WareHouseDeliverySemiPnService wareHouseDeliverySemiPnService;
	@Autowired
	WareHouseDeliveryMachinePartService wareHouseDeliveryMachinePartService;
	
	//出库统计
	public List<WareHouseDeliveryBOMModel> calcDeliveryBOMs(WareHouseHistoryFormBean formBean) {
		WareHouseDeliveryBOMModel deliveryBom = new WareHouseDeliveryBOMModel();
		
		WareHouseDeliveryModel delivery = new WareHouseDeliveryModel();
		delivery.setState(DeliveryState.DELIVERIED.ordinal());
		
		BOMModel bom = new BOMModel();
		bom.setPn(formBean.getPn());
		bom.setType(formBean.getBomType());
		
		deliveryBom.setWareHouseDelivery(delivery);
		deliveryBom.setBom(bom);
		deliveryBom.putFilterCond(WareHouseDeliveryBOMModel.FCK_FUZZYPNSEARCH, true);
		deliveryBom.putFilterCond(WareHouseDeliveryBOMModel.FCK_DELIVERYSTART, formBean.getStart());
		deliveryBom.putFilterCond(WareHouseDeliveryBOMModel.FCK_DELIVERYEND, formBean.getEnd());
		deliveryBom.putFilterCond(WareHouseDeliveryBOMModel.FCK_GROUPBYBOM, true);

		//出库
		List<WareHouseDeliveryBOMModel> deliveryList = wareHouseDeliveryBOMService.queryList(deliveryBom);
		
		//入库退库
		WareHouseEntryBOMModel entryBom = new WareHouseEntryBOMModel();
		
		WareHouseEntryModel entry = new WareHouseEntryModel();
		entry.setState(EntryState.ENTRIED.ordinal());
		entry.setItemKind(EntryItemKind.DEFECTIVE.ordinal());//排除不良品入库单

		entryBom.setWareHouseEntry(entry);
		entryBom.setBom(bom);
		entryBom.putFilterCond(WareHouseEntryBOMModel.FCK_FUZZYPNSEARCH, true);
		entryBom.putFilterCond(WareHouseEntryBOMModel.FCK_ENTRYSTART, formBean.getStart());
		entryBom.putFilterCond(WareHouseEntryBOMModel.FCK_ENTRYEND, formBean.getEnd());
		entryBom.putFilterCond(WareHouseEntryBOMModel.FCK_GROUPBYBOM, true);

		List<WareHouseEntryBOMModel> entryDefectiveList = wareHouseEntryBOMService.queryList(entryBom);
		
		//@TODO 这里虽然扣除退库，但可能退库入库的BOM不是本时间段内出库的，所以结果还会偏差
		for(WareHouseDeliveryBOMModel dBom : deliveryList) {
			for(WareHouseEntryBOMModel eBom : entryDefectiveList) {
				if(dBom.getBom().getId() == eBom.getBom().getId()) {
					dBom.setNum(dBom.getNum() - eBom.getNum());
				}
			}
		}
		return deliveryList;
	}
	
	public List<WareHouseDeliveryPnModel> calcDeliveryPns(WareHouseHistoryFormBean formBean) {
		WareHouseDeliveryPnModel deliveryPn = new WareHouseDeliveryPnModel();
		
		WareHouseDeliveryModel d = new WareHouseDeliveryModel();
		d.setState(DeliveryState.DELIVERIED.ordinal());
		
		PnModel pnM = new PnModel();
		pnM.setPn(formBean.getPn());
		
		deliveryPn.setPn(pnM);
		deliveryPn.setWareHouseDelivery(d);
		deliveryPn.putFilterCond(WareHouseDeliveryPnModel.FCK_FUZZYPNSEARCH, true);
		deliveryPn.putFilterCond(WareHouseDeliveryPnModel.FCK_DELIVERYSTART, formBean.getStart());
		deliveryPn.putFilterCond(WareHouseDeliveryPnModel.FCK_DELIVERYEND, formBean.getEnd());
		deliveryPn.putFilterCond(WareHouseDeliveryPnModel.FCK_GROUPBYPN, true);
		
		List<WareHouseDeliveryPnModel> list = wareHouseDeliveryPnService.queryList(deliveryPn);
		return list;
	}

	public void addDeliveryPn(WareHouseDeliveryPnModel deliveryPn) {
		WareHouseDeliveryPnModel dPn = wareHouseDeliveryPnService.queryOne(deliveryPn);
		if(dPn == null) {
			wareHouseDeliveryPnService.add(deliveryPn);
		} else {
			//已经存在，做更新
			deliveryPn.setProducedNum(deliveryPn.getProducedNum() + dPn.getProducedNum());
			wareHouseDeliveryPnService.update(deliveryPn);
		}
	}

	public void addDeliverySemiPn(WareHouseDeliverySemiPnModel deliverySemiPn) {
		WareHouseDeliverySemiPnModel dSemiPn = wareHouseDeliverySemiPnService.queryOne(deliverySemiPn);
		if(dSemiPn == null) {
			wareHouseDeliverySemiPnService.add(deliverySemiPn);
		} else {
			//已经存在，做更新
			deliverySemiPn.setNum(deliverySemiPn.getNum() + dSemiPn.getNum());
			wareHouseDeliverySemiPnService.update(deliverySemiPn);
		}
	}

	public void addDeliveryBOM(WareHouseDeliveryBOMModel deliveryBOM) {
		WareHouseDeliveryBOMModel dBOM = wareHouseDeliveryBOMService.queryOne(deliveryBOM);
		if(dBOM == null) {
			wareHouseDeliveryBOMService.add(deliveryBOM);
		} else {
			//已经存在，做更新
			deliveryBOM.setNum(deliveryBOM.getNum() + dBOM.getNum());
			wareHouseDeliveryBOMService.update(deliveryBOM);
		}
	}

	public void addDeliveryMachinePart(WareHouseDeliveryMachinePartModel deliveryMachinePart) {
		WareHouseDeliveryMachinePartModel dMP = wareHouseDeliveryMachinePartService.queryOne(deliveryMachinePart);
		if(dMP == null) {
			wareHouseDeliveryMachinePartService.add(deliveryMachinePart);
		} else {
			//已经存在，做更新
			deliveryMachinePart.setNum(deliveryMachinePart.getNum() + dMP.getNum());
			wareHouseDeliveryMachinePartService.update(deliveryMachinePart);
		}
	}
}
