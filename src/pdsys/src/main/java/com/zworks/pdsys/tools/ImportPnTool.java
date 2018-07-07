package com.zworks.pdsys.tools;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.zworks.pdsys.common.exception.PdsysException;
import com.zworks.pdsys.common.utils.ExcelUtils;
import com.zworks.pdsys.common.utils.StringUtils;
import com.zworks.pdsys.models.PnModel;
import com.zworks.pdsys.models.PnPnClsRelModel;
import com.zworks.pdsys.models.WareHousePnModel;
import com.zworks.pdsys.models.WareHouseSemiPnModel;
import com.zworks.pdsys.services.PnService;
import com.zworks.pdsys.services.WareHousePnService;
import com.zworks.pdsys.services.WareHouseSemiPnService;

@Component
@Scope("prototype")
public class ImportPnTool {
	@Autowired
	private WareHousePnService whPnService;
	@Autowired
	private WareHouseSemiPnService whSemiPnService;
	@Autowired
	private PnService pnService;
	
	private Map<String, WareHousePnModel> whpns = new HashMap<String, WareHousePnModel>();
	private Map<String, WareHouseSemiPnModel> whsemipns = new HashMap<String, WareHouseSemiPnModel>();
	
	@Transactional(rollbackFor=Exception.class)
	public boolean execute(String filePath) throws InvalidFormatException, IOException{
		whpns.clear();
		whsemipns.clear();
		
		read(filePath);
		
//		try {//此处不可try 否则事务会不能正确回滚，应把exception交给spring
		
			//import 
			for(String key : whpns.keySet()) {
				WareHousePnModel whPn = whpns.get(key);
				WareHousePnModel whPnTmp = whPnService.queryOne(whPn);
				if(whPnTmp == null) {
					whPnService.add(whPn);
				} else {
					whPn.setId(whPnTmp.getId());
					whPnService.update(whPn);
				}
			}
			
			for(String key : whsemipns.keySet()) {
				WareHouseSemiPnModel whsemiPn = whsemipns.get(key);
				WareHouseSemiPnModel whsemiPnTmp = whSemiPnService.queryOne(whsemiPn);
				if(whsemiPnTmp == null) {
					whSemiPnService.add(whsemiPn);
				} else {
					whsemiPn.setId(whsemiPnTmp.getId());
					whSemiPnService.update(whsemiPn);
				}
			}
			
//		} catch(Exception e) {
//			e.printStackTrace();
//			return false;
//		}
		return true;
	}
	
	public void read(String filePath) throws InvalidFormatException, IOException{
		int rowNo = 0;
		try {
			InputStream is = null;
			
			is = new FileInputStream(filePath);  
		             
			Workbook wb = WorkbookFactory.create(is);
			Sheet sheet = wb.getSheetAt(0);
			if(sheet == null) {
				throw new PdsysException("没找到 sheet");
			}
			
			for(rowNo = 1; rowNo <= sheet.getLastRowNum(); rowNo++) {
				Row row = sheet.getRow(rowNo);
	  
				int idx = 0;
	           	String strpn = ExcelUtils.getCellValue(row.getCell(idx++));
	           	@SuppressWarnings("unused")
				String name = ExcelUtils.getCellValue(row.getCell(idx++));
	            String clsName = ExcelUtils.getCellValue(row.getCell(idx++));
	            String strnum = ExcelUtils.getCellValue(row.getCell(idx++));
	            String strseminum = ExcelUtils.getCellValue(row.getCell(idx++));

	            PnModel pn = new PnModel();
	            if("".equals(strpn)) {
	            	break;
	            }
	            
	            pn.setPn(strpn);
	            pn  = pnService.queryOne(pn);
	            if(pn == null) {
	            	throw new PdsysException("不存在的编号:" + strpn);
	            }
	            
	            float num = 0;
	            if(!"".equals(strnum)) {
	            	num = Float.parseFloat(strnum);
	            }
	            
	            if(num != 0) {
	            	if(whpns.containsKey(strpn)) {
	            		throw new PdsysException("重复编号：" + strpn);
	            	}
	            	
		            WareHousePnModel whPn = new WareHousePnModel();
		            whPn.setPn(pn);
		            whPn.setProducedNum(num);
		            whpns.put(strpn, whPn);
	            }
	            
	            float seminum = 0;
	            if(!"".equals(strseminum)) {
	            	seminum = Float.parseFloat(strseminum);
	            } else {
	            	if(!StringUtils.isNullOrEmpty(clsName)) {
	            		throw new PdsysException("未设定本体数量");
	            	}
	            }
	            
	            if(seminum != 0) {
	            	List<PnPnClsRelModel> pnClsRels = pn.getPnClsRels();
	            	PnPnClsRelModel pnclsRel = null;
	            	for(PnPnClsRelModel pnClsRelTmp : pnClsRels) {
	            		if(pnClsRelTmp.getPnCls().getName().equals(clsName)) {
	            			pnclsRel = pnClsRelTmp;
	            			break;
	            		}
	            	}
	            	
	            	if(pnclsRel == null) {
	            		throw new PdsysException(strpn + "中不存在本体：" + clsName);
	            	}
	            	
	            	String key = strpn+"@@@" + clsName;
	            	if(whsemipns.containsKey(key)) {
	            		throw new PdsysException("重复本体：" + strpn + " " + clsName);
	            	}
	            	
		            WareHouseSemiPnModel whsemipn = new WareHouseSemiPnModel();
		            whsemipn.setPn(pn);
		            whsemipn.setPnClsRel(pnclsRel);
		            whsemipn.setNum(seminum);
		            whsemipns.put(key, whsemipn);
	            }
	        }
		} catch(Exception e) {
			throw new PdsysException(e.getMessage() + " \n错误行:" + (rowNo + 1), e);
		}
	}
}
