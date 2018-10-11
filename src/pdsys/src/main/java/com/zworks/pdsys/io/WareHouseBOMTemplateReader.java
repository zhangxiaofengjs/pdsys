package com.zworks.pdsys.io;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zworks.pdsys.common.exception.PdsysException;
import com.zworks.pdsys.common.utils.ExcelUtils;
import com.zworks.pdsys.models.BOMModel;
import com.zworks.pdsys.models.WareHouseBOMModel;
import com.zworks.pdsys.services.BOMService;

@Component
public class WareHouseBOMTemplateReader {
	@Autowired
	private BOMService bomService;
	
	public List<WareHouseBOMModel> read(String filePath) throws InvalidFormatException, IOException {
		Map<String, WareHouseBOMModel> whboms = new HashMap<String, WareHouseBOMModel>();
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
	           	String pn = ExcelUtils.getCellValue(row.getCell(idx++));
	           	@SuppressWarnings("unused")
				String name = ExcelUtils.getCellValue(row.getCell(idx++));
	            String num = ExcelUtils.getCellValue(row.getCell(idx++));
	            String remaingNum = ExcelUtils.getCellValue(row.getCell(idx++));

	            BOMModel bom = new BOMModel();
	            WareHouseBOMModel whBom = new WareHouseBOMModel();
	            if("".equals(pn)) {
	            	break;
	            }
	            
	            if("".equals(num) && "".equals(remaingNum)) {
	            	//没有更新
	            	continue;
	            }
	            
	            if(whboms.containsKey(pn)) {
	            	throw new PdsysException("重复编号：" + pn);
	            }

	            bom.setPn(pn);
	            bom = bomService.queryOne(bom);
	            if(bom == null) {
	            	//不需要更新库存的不做报错
	            	throw new PdsysException("不存在的原包材编号:" + pn);
	            }
	            whBom.setBom(bom);
	            
	            if(!"".equals(num)) {
	            	whBom.setNum(Float.parseFloat(num));
	            } else {
	            	whBom.setNum(-1);
	            }
	            if(!"".equals(remaingNum)) {
	            	whBom.setDeliveryRemainingNum(Float.parseFloat(remaingNum));
	            } else {
	            	whBom.setDeliveryRemainingNum(-1);
	            }
	           
	            whboms.put(pn, whBom);
	        }
			
			return new ArrayList<WareHouseBOMModel>(whboms.values());
		} catch(Exception e) {
			throw new PdsysException(e.getMessage() + " \n错误行:" + (rowNo + 1), e);
		}
	}
}
