package com.zworks.pdsys.business;

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
import com.zworks.pdsys.models.WareHouseBOMModel;
import com.zworks.pdsys.services.FileService;
import com.zworks.pdsys.services.WareHouseBOMService;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2018/10/10
 */
@Service
public class WareHouseBusiness {
	@Autowired
	FileService fileService;
	@Autowired
	private WareHouseBOMTemplateReader templateReader;
	@Autowired
	private WareHouseBOMService whbomService;
	
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
			whBoms = templateReader.read(path);
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
}
