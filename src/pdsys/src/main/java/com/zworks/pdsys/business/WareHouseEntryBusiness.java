package com.zworks.pdsys.business;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zworks.pdsys.business.beans.BOMUseNumBean;
import com.zworks.pdsys.business.form.beans.WareHouseHistoryFormBean;
import com.zworks.pdsys.common.enumClass.EntryItemKind;
import com.zworks.pdsys.common.enumClass.EntryState;
import com.zworks.pdsys.common.exception.PdsysException;
import com.zworks.pdsys.common.utils.DateUtils;
import com.zworks.pdsys.models.BOMModel;
import com.zworks.pdsys.models.PnModel;
import com.zworks.pdsys.models.PnPnClsRelModel;
import com.zworks.pdsys.models.WareHouseBOMModel;
import com.zworks.pdsys.models.WareHouseEntryBOMModel;
import com.zworks.pdsys.models.WareHouseEntryMachinePartModel;
import com.zworks.pdsys.models.WareHouseEntryModel;
import com.zworks.pdsys.models.WareHouseEntryPnModel;
import com.zworks.pdsys.models.WareHouseEntrySemiPnModel;
import com.zworks.pdsys.models.WareHouseMachinePartModel;
import com.zworks.pdsys.models.WareHousePnModel;
import com.zworks.pdsys.models.WareHouseSemiPnModel;
import com.zworks.pdsys.services.PnService;
import com.zworks.pdsys.services.WareHouseBOMService;
import com.zworks.pdsys.services.WareHouseEntryBOMService;
import com.zworks.pdsys.services.WareHouseEntryMachinePartService;
import com.zworks.pdsys.services.WareHouseEntryPnService;
import com.zworks.pdsys.services.WareHouseEntrySemiPnService;
import com.zworks.pdsys.services.WareHouseEntryService;
import com.zworks.pdsys.services.WareHouseMachinePartService;
import com.zworks.pdsys.services.WareHousePnService;
import com.zworks.pdsys.services.WareHouseSemiPnService;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2018/08/19
 */
@Service
public class WareHouseEntryBusiness {
	@Autowired
	PnService pnService;
	@Autowired
	WareHouseEntryBOMService wareHouseEntryBOMService;
	@Autowired
	WareHouseEntryPnService wareHouseEntryPnService;
	@Autowired
	WareHouseEntrySemiPnService wareHouseEntrySemiPnService;
	@Autowired
	WareHouseEntryMachinePartService wareHouseEntryMachinePartService;
	@Autowired
	WareHouseEntryService wareHouseEntryService;
	@Autowired
	WareHousePnService wareHousePnService;
	@Autowired
	WareHouseBOMService wareHouseBOMService;
	@Autowired
	WareHouseSemiPnService wareHouseSemiPnService;
	@Autowired
	WareHouseMachinePartService wareHouseMachinePartService;
	
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
	
	@Transactional
	public void entryMachinePart(WareHouseEntryModel e) {
		WareHouseEntryModel entry = wareHouseEntryService.queryOneWithMachinePart(e);
		
		for(WareHouseEntryMachinePartModel entryMachinePart : entry.getWareHouseEntryMachineParts()) {
			WareHouseMachinePartModel wareHouseMachinePart = entryMachinePart.getWareHouseMachinePart();
			
			boolean isCreate = false;
			if(wareHouseMachinePart == null) {
				//还没入库过，新建
				wareHouseMachinePart = new WareHouseMachinePartModel();
				wareHouseMachinePart.setMachinePart(entryMachinePart.getMachinePart());
				isCreate = true;
			}
			
			float num = entryMachinePart.getNum();
			
			if(entry.getItemKind() == EntryItemKind.NORMAL.ordinal() ||
				entry.getItemKind() == EntryItemKind.FIXRETURN.ordinal()) {
				num += wareHouseMachinePart.getNum();
				wareHouseMachinePart.setNum(num);
				wareHouseMachinePart.putFilterCond("UPDATE_NUM", true);
			} else if(entry.getItemKind() == EntryItemKind.DEFECTIVE.ordinal()) {
				num += wareHouseMachinePart.getDefectiveNum();
				wareHouseMachinePart.setDefectiveNum(num);
				wareHouseMachinePart.putFilterCond("UPDATE_DEFECTIVE_NUM", true);
			} else if(entry.getItemKind() == EntryItemKind.SCRAP.ordinal()) {
				num += wareHouseMachinePart.getScrapNum();
				wareHouseMachinePart.setScrapNum(num);
				wareHouseMachinePart.putFilterCond("UPDATE_SCRAP_NUM", true);
			} else {
				throw new PdsysException("未想定入库品种类" + entry.getItemKind());
			}
			
			if(isCreate) {
				//还没入库过，新建
				wareHouseMachinePartService.add(wareHouseMachinePart);
			} else {
				wareHouseMachinePartService.update(wareHouseMachinePart);
			}
		}
		
		entry.setEntryTime(new Date());
		entry.setState(EntryState.ENTRIED.ordinal());
		
		wareHouseEntryService.update(entry);
	}

	@Transactional
	public void entryBOM(WareHouseEntryModel e) {
		WareHouseEntryModel entry = wareHouseEntryService.queryOneWithBOM(e);
		
		for(WareHouseEntryBOMModel entryBOM : entry.getWareHouseEntryBOMs()) {
			WareHouseBOMModel wareHouseBOM = entryBOM.getWareHouseBOM();
			
			boolean isCreate = false;
			if(wareHouseBOM == null) {
				//还没入库过，新建
				wareHouseBOM = new WareHouseBOMModel();
				wareHouseBOM.setBom(entryBOM.getBom());
				wareHouseBOM.setNum(0);
				wareHouseBOM.setDefectiveNum(0);
				isCreate = true;
			}
			
			if(entry.getItemKind() == EntryItemKind.NORMAL.ordinal()) {
				float num = wareHouseBOM.getNum() + entryBOM.getNum();
				wareHouseBOM.setNum(num);
			} else if(entry.getItemKind() == EntryItemKind.DEFECTIVE.ordinal()) {
				//不良品退库
				float num = wareHouseBOM.getDefectiveNum() + entryBOM.getNum();
				wareHouseBOM.setDefectiveNum(num);
			} else {
				throw new PdsysException("未想定入库品种类" + entry.getItemKind());
			}
			
			if(isCreate) {
				wareHouseBOMService.add(wareHouseBOM);
			} else {
				if(entry.getItemKind() == EntryItemKind.NORMAL.ordinal()) {
					wareHouseBOM.getFilterCond().put("UPDATE_NUM", true);
				} else if(entry.getItemKind() == EntryItemKind.DEFECTIVE.ordinal()) {
					//不良品退库
					wareHouseBOM.getFilterCond().put("UPDATE_DEFECTIVE_NUM", true);
				} else {
					//nothing
				}
				wareHouseBOMService.update(wareHouseBOM);
			}
		}
		
		entry.setEntryTime(new Date());
		entry.setState(EntryState.ENTRIED.ordinal());
		
		wareHouseEntryService.update(entry);
	}
			
	@Transactional
	public void entryPn(WareHouseEntryModel e) {
		WareHouseEntryModel entry = wareHouseEntryService.queryOneWithPn(e);
		
		Map<Integer, BOMUseNumBean> calcUsedBOMs = new HashMap<Integer, BOMUseNumBean>();
		Map<Object, Collection<BOMUseNumBean>> pnUsedBOMs = new HashMap<Object, Collection<BOMUseNumBean>>();
				
		for(WareHouseEntryPnModel entryPn : entry.getWareHouseEntryPns()) {
			WareHousePnModel wareHousePn = entryPn.getWareHousePn();
			
			float pnum = entryPn.getProducedNum();
			if(wareHousePn == null) {
				//还没入库过，新建
				wareHousePn = new WareHousePnModel();
				wareHousePn.setPn(entryPn.getPn());
				wareHousePn.setProducedNum(pnum);
				wareHousePnService.add(wareHousePn);
			} else {
				wareHousePn.setProducedNum(wareHousePn.getProducedNum() + pnum);
				wareHousePnService.update(wareHousePn);
			}

			Map<Integer, BOMUseNumBean> tmpCalcUsedBOMs = pnService.calcUsedBOM(entryPn.getPn(), null, false, pnum);
			MergeBOMUsedMap(calcUsedBOMs, tmpCalcUsedBOMs);
			
			pnUsedBOMs.put(entryPn, tmpCalcUsedBOMs.values());
		}
		
		UpdateRemainingBOM(calcUsedBOMs);
		
		entry.setEntryTime(new Date());
		entry.setState(EntryState.ENTRIED.ordinal());
		
		wareHouseEntryService.update(entry);
		
		writeUsedBOMLog(pnUsedBOMs, false);
	}

	private void writeUsedBOMLog(Map<Object, Collection<BOMUseNumBean>> pnUsedBOMs, boolean isSemi) {
		FileWriter fw = null;
		try {
			//如果文件存在，则追加内容；如果文件不存在，则创建文件
			File f=new File("c:\\pdsys\\usedbom.log");
			fw = new FileWriter(f, true);
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		
		PrintWriter pw = new PrintWriter(fw);
		
		for(Object key : pnUsedBOMs.keySet()) {
			Collection<BOMUseNumBean> collection = pnUsedBOMs.get(key);
			
			for(BOMUseNumBean usedBom : collection) {
				if(isSemi) {
					WareHouseEntrySemiPnModel entrySemiPn = (WareHouseEntrySemiPnModel)key;
					PnPnClsRelModel pnClsRel = entrySemiPn.getPnClsRel();
					PnModel pn = entrySemiPn.getPn();
					
					pw.print(DateUtils.format(DateUtils.getCurrentDate()) + ",");
					pw.print("半成品入库,");
					pw.print(pn.getPn() + "," + pn.getName()+ "," + pnClsRel.getPnCls().getName()+",");
					pw.print(entrySemiPn.getNum() + ",");
				} else {
					WareHouseEntryPnModel entryPn = (WareHouseEntryPnModel)key;
					
					pw.print(DateUtils.format(DateUtils.getCurrentDate()) + ",");
					pw.print("成品入库,");
					pw.print(entryPn.getPn().getPn() + "," + entryPn.getPn().getName() +",");
					pw.print(entryPn.getProducedNum() + ",");
				}
				
				pw.print(usedBom.getBom().getPn() + "," + usedBom.getBom().getName() +",");
				pw.println(String.format("%.6f", usedBom.getUseNum()));
			}
		}

		pw.flush();
		
		try {
			fw.flush();
			pw.close();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void MergeBOMUsedMap(Map<Integer, BOMUseNumBean> calcUsedBOMs, Map<Integer, BOMUseNumBean> tmpCalcUsedBOMs) {
		//マージする
		for(Integer bomId : tmpCalcUsedBOMs.keySet()) {
			BOMUseNumBean tmpBean = tmpCalcUsedBOMs.get(bomId);
			
			BOMUseNumBean bean = calcUsedBOMs.get(bomId);
			if(bean == null) {
				calcUsedBOMs.put(bomId, tmpBean);
			} else {
				bean.setUseNum(bean.getUseNum() + tmpBean.getUseNum());
			}
		}
	}
	
	@Transactional
	public void entrySemiPn(WareHouseEntryModel e) {
		WareHouseEntryModel entry = wareHouseEntryService.queryOneWithSemiPn(e);
		
		Map<Integer, BOMUseNumBean> calcUsedBOMs = new HashMap<Integer, BOMUseNumBean>();
		Map<Object, Collection<BOMUseNumBean>> pnUsedBOMs = new HashMap<Object, Collection<BOMUseNumBean>>();
		
		for(WareHouseEntrySemiPnModel entrySemiPn : entry.getWareHouseEntrySemiPns()) {
			WareHouseSemiPnModel wareHouseSemiPn = entrySemiPn.getWareHouseSemiPn();
			PnPnClsRelModel pnClsRel = entrySemiPn.getPnClsRel();
			PnModel pn = entrySemiPn.getPn();
			boolean isCreate = false;
			if(wareHouseSemiPn == null) {
				//还没入库过，新建
				wareHouseSemiPn = new WareHouseSemiPnModel();
				wareHouseSemiPn.setPn(entrySemiPn.getPn());
				wareHouseSemiPn.setPnClsRel(entrySemiPn.getPnClsRel());
				wareHouseSemiPn.setNum(0);
				wareHouseSemiPn.setDefectiveNum(0);
				isCreate = true;
			}
			
			float semiNum = entrySemiPn.getNum();
			if(entry.getItemKind() == EntryItemKind.NORMAL.ordinal()) {
				wareHouseSemiPn.setNum(semiNum + wareHouseSemiPn.getNum());
			} else if(entry.getItemKind() == EntryItemKind.DEFECTIVE.ordinal()) {
				//不良品退库
				wareHouseSemiPn.setDefectiveNum(semiNum + wareHouseSemiPn.getDefectiveNum());
			} else {
				throw new PdsysException("未想定入库品种类" + entry.getItemKind());
			}

			if(isCreate) {
				wareHouseSemiPnService.add(wareHouseSemiPn);
			} else {
				if(entry.getItemKind() == EntryItemKind.NORMAL.ordinal()) {
					wareHouseSemiPn.getFilterCond().put("UPDATE_NUM", true);
				} else if(entry.getItemKind() == EntryItemKind.DEFECTIVE.ordinal()) {
					wareHouseSemiPn.getFilterCond().put("UPDATE_DEFECTIVE_NUM", true);
				} else {
					//do nothing
				}
				wareHouseSemiPnService.update(wareHouseSemiPn);
			}
			
			//マージする
			Map<Integer, BOMUseNumBean> tmpCalcUsedBOMs = pnService.calcUsedBOM(pn, pnClsRel, true, semiNum);
			MergeBOMUsedMap(calcUsedBOMs, tmpCalcUsedBOMs);
			
			pnUsedBOMs.put(entrySemiPn, tmpCalcUsedBOMs.values());
		}
		
		UpdateRemainingBOM(calcUsedBOMs);
		
		entry.setEntryTime(new Date());
		entry.setState(EntryState.ENTRIED.ordinal());
		
		wareHouseEntryService.update(entry);
		
		writeUsedBOMLog(pnUsedBOMs, true);
	}

	//更新原包材现场数据
	private void UpdateRemainingBOM(Map<Integer, BOMUseNumBean> calcUsedBOMs) {
		List<WareHouseBOMModel> updateWHBOMs = new ArrayList<WareHouseBOMModel>();
		List<BOMUseNumBean> errBOMs = new ArrayList<BOMUseNumBean>();
		
		for(Integer bomId : calcUsedBOMs.keySet()) {
			BOMUseNumBean bean = calcUsedBOMs.get(bomId);
			BOMModel bom = bean.getBom();
			
			WareHouseBOMModel whBOM = new WareHouseBOMModel();
			whBOM.setBom(bom);
			whBOM = wareHouseBOMService.queryOne(whBOM);
			float whNum = 0;
			float remainNum = -1;
			if(whBOM != null) {
				whNum = whBOM.getDeliveryRemainingNum();
			}
			remainNum = whNum - bean.getUseNum();
			if(whBOM == null || remainNum < 0 && Math.abs(remainNum) > 0.0099999) { //忽略小数点3位后面的数字
				//生产所耗BOM居然比之前出库的多，出库有问题，做错误处理
				bean.setWareHouseNum(whNum);
				errBOMs.add(bean);
			} else {
				if(Math.abs(remainNum) < 0.0099999) {
					remainNum = 0;//忽略掉这部分库存
				}
				
				whBOM.getFilterCond().put("UPDATE_DELIVERYREMAINNUM", true);
				whBOM.setDeliveryRemainingNum(remainNum);
				updateWHBOMs.add(whBOM);
			}
		}
		
		if(errBOMs.size() != 0) {
			String strHtml = "<table class=\"table table-striped table-bordered table-hover table-condensed\" contenteditable=\"false\">";
			strHtml += "<tr><th>原包材</th><th>U</th><th>现场</th><th>预计消耗</th></tr>";
			for(BOMUseNumBean bean : errBOMs) {
				BOMModel bom = bean.getBom();
				strHtml += String.format("<tr><td title=\"#%d#%s\">%s</td><td>%s</td><td>%.2f</td><td>%.2f</td></tr>", 
						bom.getId(),
						bom.getName(),
						bom.getPn(),
						bom.getUnit().getName(),
						bean.getWareHouseNum(),
						bean.getUseNum());
			}
			strHtml += "</table>";
			String msg = String.format("检测到该入库产品的原包材领料不足，请检查近期原包材出库单。<hr>%s",strHtml);
			throw new PdsysException(msg);
		}
		for(WareHouseBOMModel whBOM : updateWHBOMs) {
			wareHouseBOMService.update(whBOM);
		}
	}
}
