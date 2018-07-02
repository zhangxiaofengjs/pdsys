package com.zworks.pdsys.services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.zworks.pdsys.common.enumClass.EntryState;
import com.zworks.pdsys.common.enumClass.EntryType;
import com.zworks.pdsys.common.utils.JSONResponse;
import com.zworks.pdsys.common.utils.SecurityContextUtils;
import com.zworks.pdsys.mappers.WareHouseEntryMapper;
import com.zworks.pdsys.models.WareHouseBOMModel;
import com.zworks.pdsys.models.WareHouseEntryBOMModel;
import com.zworks.pdsys.models.WareHouseEntryMachinePartModel;
import com.zworks.pdsys.models.WareHouseEntryModel;
import com.zworks.pdsys.models.WareHouseEntryPnModel;
import com.zworks.pdsys.models.WareHouseEntrySemiPnModel;
import com.zworks.pdsys.models.WareHouseMachinePartModel;
import com.zworks.pdsys.models.WareHousePnModel;
import com.zworks.pdsys.models.WareHouseSemiPnModel;
import com.zworks.pdsys.tools.EntryTemplateReader;

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
    private UploadService uploadService;
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

	public JSONResponse checkAddable(WareHouseEntryModel entry) {
		WareHouseEntryModel e = new WareHouseEntryModel();
		e.setNo(entry.getNo());
		if(exists(e)) {
			return JSONResponse.error("已经存在单号:" + entry.getNo());
		}
		
		e = new WareHouseEntryModel();
		e.setType(entry.getType());
		e.setUser(entry.getUser());
		e.setState(EntryState.PLANNING.ordinal());
		List<WareHouseEntryModel> es = queryList(e);
		if(es.size()!=0) {
			return JSONResponse.error("当前用户[" + es.get(0).getUser().getName() + "]存在未处理单号:" + es.get(0).getNo());
		}
		
		return JSONResponse.success();
	}
	public boolean entry(WareHouseEntryModel entry) {
		if(entry.getType() == EntryType.PN.ordinal()) {
			entry = queryOneWithPn(entry);
			
			for(WareHouseEntryPnModel entryPn : entry.getWareHouseEntryPns()) {
				WareHousePnModel wareHousePn = entryPn.getWareHousePn();
				
				if(wareHousePn == null) {
					//还没入库过，新建
					wareHousePn = new WareHousePnModel();
					wareHousePn.setPn(entryPn.getPn());
					wareHousePn.setProducedNum(entryPn.getProducedNum());
					wareHousePn.setSemiProducedNum(entryPn.getSemiProducedNum());
					wareHousePnService.add(wareHousePn);
				} else {
					float semiNum = wareHousePn.getSemiProducedNum() + entryPn.getSemiProducedNum();
					wareHousePn.setSemiProducedNum(semiNum);
					
					float num = wareHousePn.getProducedNum() + entryPn.getProducedNum();
					wareHousePn.setProducedNum(num);
					
					wareHousePnService.update(wareHousePn);
				}
			}
		} else if(entry.getType() == EntryType.SEMIPN.ordinal()) {
			entry = queryOneWithSemiPn(entry);
			
			for(WareHouseEntrySemiPnModel entryPn : entry.getWareHouseEntrySemiPns()) {
				WareHouseSemiPnModel wareHousePn = entryPn.getWareHouseSemiPn();
				
				if(wareHousePn == null) {
					//还没入库过，新建
					wareHousePn = new WareHouseSemiPnModel();
					wareHousePn.setPn(entryPn.getPn());
					wareHousePn.setPnClsRel(entryPn.getPnClsRel());
					wareHousePn.setNum(entryPn.getNum());
					wareHouseSemiPnService.add(wareHousePn);
				} else {
					float semiNum = wareHousePn.getNum() + entryPn.getNum();
					wareHousePn.setNum(semiNum);
					
					wareHouseSemiPnService.update(wareHousePn);
				}
			}
		} else if(entry.getType() == EntryType.BOM.ordinal()) {
			entry = queryOneWithBOM(entry);
			
			for(WareHouseEntryBOMModel entryBOM : entry.getWareHouseEntryBOMs()) {
				WareHouseBOMModel wareHouseBOM = entryBOM.getWareHouseBOM();
				
				if(wareHouseBOM == null) {
					//还没入库过，新建
					wareHouseBOM = new WareHouseBOMModel();
					wareHouseBOM.setBom(entryBOM.getBom());
					wareHouseBOM.setNum(entryBOM.getNum());
					wareHouseBOMService.add(wareHouseBOM);
				} else {
					float num = wareHouseBOM.getNum() + entryBOM.getNum();
					wareHouseBOM.setNum(num);
					
					wareHouseBOMService.update(wareHouseBOM);
				}
			}
		} else if(entry.getType() == EntryType.MACHINEPART.ordinal()) {
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
			return false;
		}
		
		entry.setEntryTime(new Date());
		entry.setState(EntryState.ENTRIED.ordinal());
		
		wareHouseEntryMapper.update(entry);
		return true;
	}

	public JSONResponse importEntry(MultipartFile[] files) {
		if(files == null || files.length != 1) {
			return JSONResponse.error("请选定一个文件进行导入");
		}
		
		MultipartFile mpFile = files[0];
        if(mpFile.isEmpty()) {
        	return JSONResponse.error("选定的文件为空");
        }

        String tempPath = uploadService.saveTemp(mpFile);
        WareHouseEntryModel entry = null;
        try {
        	entry = reader.readBOM(tempPath);
        } catch(Exception e) {
        	return JSONResponse.error(e.getMessage());
        }

        if(!SecurityContextUtils.isLoginUser(entry.getUser())) {
			return JSONResponse.error("登录用户不是提交者！");
		}
        
        WareHouseEntryModel e = new WareHouseEntryModel();
        e.setNo(entry.getNo());
        e.setType(entry.getType());
        if(queryOne(e) != null) {
        	return JSONResponse.error("已经存在的入库单编号");
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
 
        return JSONResponse.success().put("entry", entry);
	}
}
