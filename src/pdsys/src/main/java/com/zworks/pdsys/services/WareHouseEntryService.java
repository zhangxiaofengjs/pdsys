package com.zworks.pdsys.services;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.zworks.pdsys.business.beans.BOMUseNumBean;
import com.zworks.pdsys.common.enumClass.EntryItemKind;
import com.zworks.pdsys.common.enumClass.EntryState;
import com.zworks.pdsys.common.enumClass.EntryType;
import com.zworks.pdsys.common.exception.PdsysException;
import com.zworks.pdsys.common.utils.SecurityContextUtils;
import com.zworks.pdsys.io.EntryTemplateReader;
import com.zworks.pdsys.mappers.WareHouseEntryMapper;
import com.zworks.pdsys.models.BOMModel;
import com.zworks.pdsys.models.WareHouseBOMModel;
import com.zworks.pdsys.models.WareHouseEntryBOMModel;
import com.zworks.pdsys.models.WareHouseEntryMachinePartModel;
import com.zworks.pdsys.models.WareHouseEntryModel;
import com.zworks.pdsys.models.WareHouseEntryPnModel;
import com.zworks.pdsys.models.WareHouseEntrySemiPnModel;
import com.zworks.pdsys.models.WareHouseMachinePartModel;
import com.zworks.pdsys.models.WareHousePnModel;
import com.zworks.pdsys.models.WareHouseSemiPnModel;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2018/04/05
 */
@Service
public class WareHouseEntryService {
	@Autowired
    private WareHouseEntryMapper wareHouseEntryMapper;
	@Autowired
	private WareHousePnService wareHousePnService;
	@Autowired
	private WareHouseSemiPnService wareHouseSemiPnService;
	@Autowired
	private WareHouseBOMService wareHouseBOMService;
	@Autowired
	private WareHouseMachinePartService wareHouseMachinePartService;
	@Autowired
	private WareHouseEntryPnService wareHouseEntryPnService;
	@Autowired
	private WareHouseEntrySemiPnService wareHouseEntrySemiPnService;
	@Autowired
	private WareHouseEntryBOMService wareHouseEntryBOMService;
	@Autowired
	private WareHouseEntryMachinePartService wareHouseEntryMachinePartService;
	@Autowired
    private PnService pnService;
	@Autowired
    private FileService uploadService;
	@Autowired
	EntryTemplateReader reader;
	
	public List<WareHouseEntryModel> queryList(WareHouseEntryModel obj) {
		return wareHouseEntryMapper.queryList(obj);
	}
	
	public WareHouseEntryModel queryOne(WareHouseEntryModel entry) {
		List<WareHouseEntryModel> entries = this.queryList(entry);
		if(entries.size() == 1) {
			return entries.get(0);
		}
		
		return null;
	}
	
	public boolean exists(WareHouseEntryModel entry) {
		return queryOne(entry) != null;
	}
	
	public List<WareHouseEntryModel> queryListWithPn(WareHouseEntryModel obj) {
		return wareHouseEntryMapper.queryListWithPn(obj);
	}
	
	public List<WareHouseEntryModel> queryListWithSemiPn(WareHouseEntryModel entry) {
		return wareHouseEntryMapper.queryListWithSemiPn(entry);
	}
	
	public List<WareHouseEntryModel> queryListWithBOM(WareHouseEntryModel obj) {
		return wareHouseEntryMapper.queryListWithBOM(obj);
	}
	
	public List<WareHouseEntryModel> queryListWithMachinePart(WareHouseEntryModel entry) {
		return wareHouseEntryMapper.queryListWithMachinePart(entry);
	}
	
	public WareHouseEntryModel queryOneWithPn(WareHouseEntryModel entry) {
		List<WareHouseEntryModel> entries = this.queryListWithPn(entry);
		if(entries.size() == 1) {
			return entries.get(0);
		}
		
		return null;
	}
	
	public WareHouseEntryModel queryOneWithSemiPn(WareHouseEntryModel entry) {
		List<WareHouseEntryModel> entries = this.queryListWithSemiPn(entry);
		if(entries.size() == 1) {
			return entries.get(0);
		}
		
		return null;
	}
	
	public WareHouseEntryModel queryOneWithBOM(WareHouseEntryModel entry) {
		List<WareHouseEntryModel> entries = this.queryListWithBOM(entry);
		if(entries.size() == 1) {
			return entries.get(0);
		}
		
		return null;
	}

	public WareHouseEntryModel queryOneWithMachinePart(WareHouseEntryModel entry) {
		List<WareHouseEntryModel> entries = this.queryListWithMachinePart(entry);
		if(entries.size() == 1) {
			return entries.get(0);
		}
		
		return null;
	}
	
	public void add(WareHouseEntryModel obj) {
		wareHouseEntryMapper.add(obj);
	}
	
	@Transactional
	public void delete(WareHouseEntryModel entry) {
		if(entry.getType() == EntryType.PN.ordinal()) {
			WareHouseEntryPnModel ePn = new WareHouseEntryPnModel();
			ePn.setWareHouseEntry(entry);
			wareHouseEntryPnService.delete(ePn);
		}
		
		if(entry.getType() == EntryType.SEMIPN.ordinal()) {
			WareHouseEntrySemiPnModel ePn = new WareHouseEntrySemiPnModel();
			ePn.setWareHouseEntry(entry);
			wareHouseEntrySemiPnService.delete(ePn);
		}
		
		if(entry.getType() == EntryType.BOM.ordinal()) {
			WareHouseEntryBOMModel eBOM = new WareHouseEntryBOMModel();
			eBOM.setWareHouseEntry(entry);
			wareHouseEntryBOMService.delete(eBOM);
		}
		
		if(entry.getType() == EntryType.MACHINEPART.ordinal()) {
			WareHouseEntryMachinePartModel eMp = new WareHouseEntryMachinePartModel();
			eMp.setWareHouseEntry(entry);
			wareHouseEntryMachinePartService.delete(eMp);
		}
		
		wareHouseEntryMapper.delete(entry);
	}

	public void checkAddable(WareHouseEntryModel entry) {
		WareHouseEntryModel e = new WareHouseEntryModel();
		e.setNo(entry.getNo());
		if(exists(e)) {
			throw new PdsysException("已经存在单号:" + entry.getNo());
		}
		
		e = new WareHouseEntryModel();
		e.setType(entry.getType());
		e.setUser(entry.getUser());
		e.setState(EntryState.PLANNING.ordinal());
		List<WareHouseEntryModel> es = queryList(e);
		if(es.size()!=0) {
			throw new PdsysException("当前用户[" + es.get(0).getUser().getName() + "]存在未处理单号:" + es.get(0).getNo());
		}
	}
	
	@Transactional
	public void entry(WareHouseEntryModel entry) {
		 if(entry.getType() == EntryType.MACHINEPART.ordinal()) {
			entry = queryOneWithMachinePart(entry);
			
			for(WareHouseEntryMachinePartModel entryMachinePart : entry.getWareHouseEntryMachineParts()) {
				WareHouseMachinePartModel wareHouseMachinePart = entryMachinePart.getWareHouseMachinePart();
				
				if(wareHouseMachinePart == null) {
					//还没入库过，新建
					wareHouseMachinePart = new WareHouseMachinePartModel();
					wareHouseMachinePart.setMachinePart(entryMachinePart.getMachinePart());
					wareHouseMachinePart.setNum(entryMachinePart.getNum());
					wareHouseMachinePartService.add(wareHouseMachinePart);
				} else {
					float num = wareHouseMachinePart.getNum() + entryMachinePart.getNum();
					wareHouseMachinePart.setNum(num);
					
					wareHouseMachinePartService.update(wareHouseMachinePart);
				}
			}
		} else {
			throw new PdsysException("未想定入库种类");
		}
		
		entry.setEntryTime(new Date());
		entry.setState(EntryState.ENTRIED.ordinal());
		
		wareHouseEntryMapper.update(entry);
	}

	public void entryBOM(WareHouseEntryModel e) {
		WareHouseEntryModel entry = queryOneWithBOM(e);
		
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
		
		wareHouseEntryMapper.update(entry);
	}
			
	@Transactional
	public void entryPn(WareHouseEntryModel e) {
		WareHouseEntryModel entry = queryOneWithPn(e);
		
		Map<Integer, BOMUseNumBean> calcUsedBOMs = new HashMap<Integer, BOMUseNumBean>();
				
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
		}
		
		UpdateRemainingBOM(calcUsedBOMs);
		
		entry.setEntryTime(new Date());
		entry.setState(EntryState.ENTRIED.ordinal());
		
		wareHouseEntryMapper.update(entry);
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
		WareHouseEntryModel entry = queryOneWithSemiPn(e);
		
		Map<Integer, BOMUseNumBean> calcUsedBOMs = new HashMap<Integer, BOMUseNumBean>();
		for(WareHouseEntrySemiPnModel entrySemiPn : entry.getWareHouseEntrySemiPns()) {
			WareHouseSemiPnModel wareHouseSemiPn = entrySemiPn.getWareHouseSemiPn();

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
			Map<Integer, BOMUseNumBean> tmpCalcUsedBOMs = pnService.calcUsedBOM(entrySemiPn.getPn(), entrySemiPn.getPnClsRel(), true, semiNum);
			MergeBOMUsedMap(calcUsedBOMs, tmpCalcUsedBOMs);
		}
		
		UpdateRemainingBOM(calcUsedBOMs);
		
		entry.setEntryTime(new Date());
		entry.setState(EntryState.ENTRIED.ordinal());
		
		wareHouseEntryMapper.update(entry);
	}

	private void UpdateRemainingBOM(Map<Integer, BOMUseNumBean> calcUsedBOMs) {
		//更新原包材现场数据
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
			if(remainNum < 0 && Math.abs(remainNum) > 0.0099999) { //忽略小数点3位后面的数字
				//生产所耗BOM居然比之前出库的多，出库有问题，做错误处理
				String msg = String.format("检测到该入库产品的原包材领料不足，请检查近期原包材出库单。<hr>原包材:%s | %s<br>现场库存:%.2f %s<br>预计消耗:%.2f %s",
						bom.getPn(),
						bom.getName(),
						whNum,
						bom.getUnit().getName(),
						bean.getUseNum(),
						bom.getUnit().getName());
				throw new PdsysException(msg);
			}
			if(Math.abs(remainNum) < 0.0099999) {
				remainNum = 0;//忽略掉这部分库存
			}
			whBOM.getFilterCond().put("UPDATE_DELIVERYREMAINNUM", true);
			whBOM.setDeliveryRemainingNum(remainNum);
			wareHouseBOMService.update(whBOM);
		}
	}
	
	@Transactional
	public void importEntry(MultipartFile[] files) {
		if(files == null || files.length != 1) {
			throw new PdsysException("请选定一个文件进行导入");
		}
		
		MultipartFile mpFile = files[0];
        if(mpFile.isEmpty()) {
        	throw new PdsysException("选定的文件为空");
        }

        String tempPath = uploadService.saveTemp(mpFile);
        WareHouseEntryModel entry = null;
        try {
        	entry = reader.readBOM(tempPath);
        } catch(Exception e) {
        	throw new PdsysException(e.getMessage());
        }

        if(!SecurityContextUtils.isLoginUser(entry.getUser())) {
        	throw new PdsysException("登录用户不是提交者！");
		}
        
        WareHouseEntryModel e = new WareHouseEntryModel();
        e.setNo(entry.getNo());
        e.setType(entry.getType());
        if(queryOne(e) != null) {
        	throw new PdsysException("已经存在的入库单编号");
        }
        
        List<WareHouseEntryBOMModel> eboms = entry.getWareHouseEntryBOMs();
        
        add(entry);

        //这边要重新new一个order，赋予OrderId，否则发生Exception
        e = new WareHouseEntryModel();
        e.setId(entry.getId());
        
        for(WareHouseEntryBOMModel ebom : eboms) {
        	ebom.setWareHouseEntry(e);
    		wareHouseEntryBOMService.add(ebom);
        }
	}
}
