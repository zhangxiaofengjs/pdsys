package com.zworks.pdsys.business;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.zworks.pdsys.common.exception.PdsysException;
import com.zworks.pdsys.common.utils.DateUtils;
import com.zworks.pdsys.common.utils.PdSysFileCsvWriter;
import com.zworks.pdsys.config.PdSysApplicationConfig;
import com.zworks.pdsys.io.WareHouseBOMTemplateReader;
import com.zworks.pdsys.io.WareHousePnSemiPnTemplateReader;
import com.zworks.pdsys.models.WareHouseBOMModel;
import com.zworks.pdsys.models.WareHousePnModel;
import com.zworks.pdsys.models.WareHouseSemiPnModel;
import com.zworks.pdsys.services.FileService;
import com.zworks.pdsys.services.WareHouseBOMService;
import com.zworks.pdsys.services.WareHousePnService;
import com.zworks.pdsys.services.WareHouseSemiPnService;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2018/10/10
 */
@Service
public class WareHouseBusiness {
	@Autowired
	FileService fileService;
	@Autowired
	private WareHouseBOMTemplateReader templateBomReader;
	@Autowired
	private WareHousePnSemiPnTemplateReader templatePnReader;
	@Autowired
	private WareHouseBOMService whbomService;
	@Autowired
	private WareHousePnService whPnService;
	@Autowired
	private WareHouseSemiPnService whSemiPnService;
	
	@Transactional(rollbackFor=Exception.class)
	public void importBOM(MultipartFile[] files) {
		if(files == null || files.length != 1) {
			throw new PdsysException("请选定一个文件进行导入");
		}
		
		MultipartFile mpFile = files[0];
        if(mpFile.isEmpty()) {
        	throw new PdsysException("选定的文件为空");
        }

        String tempPath = fileService.saveTemp(mpFile);
        importBOM(tempPath);
	}
	
	@Transactional(rollbackFor=Exception.class)
	public void importBOM(String path) {
        List<WareHouseBOMModel> whBoms;
		try {
			whBoms = templateBomReader.read(path);
		} catch (Exception e) {
			throw new PdsysException(e.getMessage());
		}

		PdSysFileCsvWriter writer = new PdSysFileCsvWriter();
		writer.open(PdSysApplicationConfig.getLoggerFolder() + "importbom_" + DateUtils.format(DateUtils.now(), "yyyyMMddHHmmss") + ".log");
		writer.write("[%s]更新开始", DateUtils.format(DateUtils.now(), "yyyy/MM/dd HH:mm:ss"));
		writer.newRow();
		writer.writeColumn("id");
		writer.writeColumn("品番");
		writer.writeColumn("在库");
		writer.writeColumn("在库(现场)");
		writer.writeColumn("在库(更新)");
		writer.writeColumn("在库(现场更新)");
		
        for(WareHouseBOMModel whBom : whBoms) {
        	WareHouseBOMModel whBomTmp = whbomService.queryOne(whBom);
			if(whBomTmp == null) {
				whbomService.add(whBom);
			} else {
				whBom.setId(whBomTmp.getId());
				if(whBom.getNum() != -1) {
					whBom.getFilterCond().put("UPDATE_NUM", true);
				}
				if(whBom.getDeliveryRemainingNum() != -1) {
					whBom.getFilterCond().put("UPDATE_DELIVERYREMAINNUM", true);
				}
				whbomService.update(whBom);
			}
			
			writer.newRow();
			writer.writeColumn("%d", whBom.getBom().getId());
			writer.writeColumn("%s", whBom.getBom().getPn());
			writer.writeColumn("%.3f", whBomTmp == null ? 0 : whBomTmp.getNum());
			writer.writeColumn("%.3f", whBomTmp == null ? 0 : whBomTmp.getDeliveryRemainingNum());
			writer.writeColumn("%.3f", whBom.getNum());
			writer.writeColumn("%.3f", whBom.getDeliveryRemainingNum());
        }
        writer.write("\n[%s]正常更新终了", DateUtils.format(DateUtils.now(), "yyyy/MM/dd HH:mm:ss"));
        writer.close();
	}
	
	@Transactional(rollbackFor=Exception.class)
	public void importPn(MultipartFile[] files) {
		if(files == null || files.length != 1) {
			throw new PdsysException("请选定一个文件进行导入");
		}
		
		MultipartFile mpFile = files[0];
        if(mpFile.isEmpty()) {
        	throw new PdsysException("选定的文件为空");
        }

        String tempPath = fileService.saveTemp(mpFile);
        
		templatePnReader.read(tempPath, WareHousePnSemiPnTemplateReader.TYPE_PN);
		Collection<WareHousePnModel> whPns = templatePnReader.getWhpns();
		for(WareHousePnModel whPn : whPns) {
			WareHousePnModel whPnTmp = whPnService.queryOne(whPn);
			if(whPnTmp == null) {
				whPnService.add(whPn);
			} else {
				whPn.setId(whPnTmp.getId());
				whPnService.update(whPn);
			}
		}
	}
	@Transactional(rollbackFor=Exception.class)
	public void importSemiPn(MultipartFile[] files) {
		if(files == null || files.length != 1) {
			throw new PdsysException("请选定一个文件进行导入");
		}
		
		MultipartFile mpFile = files[0];
		if(mpFile.isEmpty()) {
			throw new PdsysException("选定的文件为空");
		}
		
		String tempPath = fileService.saveTemp(mpFile);
		
		templatePnReader.read(tempPath, WareHousePnSemiPnTemplateReader.TYPE_SEMIPN);
		Collection<WareHouseSemiPnModel> whPns = templatePnReader.getWhsemipns();
		for(WareHouseSemiPnModel whsemiPn : whPns) {
			WareHouseSemiPnModel whsemiPnTmp = whSemiPnService.queryOne(whsemiPn);
			if(whsemiPnTmp == null) {
				whSemiPnService.add(whsemiPn);
			} else {
				whsemiPn.setId(whsemiPnTmp.getId());
				whsemiPn.getFilterCond().put("UPDATE_NUM", true);
				//whsemiPn.getFilterCond().put("UPDATE_DEFECTIVE_NUM", true);
				whSemiPnService.update(whsemiPn);
			}
		}
	}
}
