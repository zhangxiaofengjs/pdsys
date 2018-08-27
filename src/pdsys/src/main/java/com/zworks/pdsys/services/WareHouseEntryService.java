package com.zworks.pdsys.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.zworks.pdsys.common.enumClass.EntryState;
import com.zworks.pdsys.common.enumClass.EntryType;
import com.zworks.pdsys.common.exception.PdsysException;
import com.zworks.pdsys.common.utils.SecurityContextUtils;
import com.zworks.pdsys.io.EntryTemplateReader;
import com.zworks.pdsys.mappers.WareHouseEntryMapper;
import com.zworks.pdsys.models.WareHouseEntryBOMModel;
import com.zworks.pdsys.models.WareHouseEntryMachinePartModel;
import com.zworks.pdsys.models.WareHouseEntryModel;
import com.zworks.pdsys.models.WareHouseEntryPnModel;
import com.zworks.pdsys.models.WareHouseEntrySemiPnModel;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2018/04/05
 */
@Service
public class WareHouseEntryService {
	@Autowired
    private WareHouseEntryMapper wareHouseEntryMapper;
	@Autowired
	private WareHouseEntryPnService wareHouseEntryPnService;
	@Autowired
	private WareHouseEntrySemiPnService wareHouseEntrySemiPnService;
	@Autowired
	private WareHouseEntryBOMService wareHouseEntryBOMService;
	@Autowired
	private WareHouseEntryMachinePartService wareHouseEntryMachinePartService;
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
	public void addWithBOMs(WareHouseEntryModel entry) {
		add(entry);

		List<WareHouseEntryBOMModel> wareHouseEntryBOMs = entry.getWareHouseEntryBOMs();
		if(wareHouseEntryBOMs != null) {
			for(WareHouseEntryBOMModel ebom : wareHouseEntryBOMs) {
	        	ebom.setWareHouseEntry(entry);
	    		wareHouseEntryBOMService.add(ebom);
	        }
		}
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

	public void update(WareHouseEntryModel entry) {
		wareHouseEntryMapper.update(entry);
	}
}
