
package com.zworks.pdsys.tools;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
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
import com.zworks.pdsys.common.utils.DateUtils;
import com.zworks.pdsys.common.utils.ExcelUtils;
import com.zworks.pdsys.common.utils.PdSysFileWriter;
import com.zworks.pdsys.models.BOMModel;
import com.zworks.pdsys.models.WareHouseBOMModel;
import com.zworks.pdsys.services.BOMService;
import com.zworks.pdsys.services.WareHouseBOMService;

@Component
@Scope("prototype")
public class ImportBOMTool {
	@Autowired
	private WareHouseBOMService whbomService;
	@Autowired
	private BOMService bomService;
	
	private Map<String, WareHouseBOMModel> whboms = new HashMap<String, WareHouseBOMModel>();

	@Transactional(rollbackFor=Exception.class)
	public boolean execute(String filePath) throws InvalidFormatException, IOException{
		whboms.clear();
		
		read(filePath);
		
//		try {//此处不可try 否则事务会不能正确回滚，应把exception交给spring
		
			PdSysFileWriter writer = new PdSysFileWriter();
			writer.open("c:\\pdsys\\importbom.log");
			writer.WriteLine("------------------------" + DateUtils.format(DateUtils.now(), "yyyy/MM/dd HH:mm:ss") + "------------------------");
			writer.WriteLine("id\tpn\twhnum\twhdeliveryremainnum\tupdnum\tupddeliveryremainnum");
			//import 
			for(String key : whboms.keySet()) {
				WareHouseBOMModel whBom = whboms.get(key);
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
				writer.WriteLine("%d\t%s\t%.3f\t%.3f\t%.3f\t%.3f", 
						whBom.getBom().getId(),
						whBom.getBom().getPn(),
						whBomTmp == null ? 0 : whBomTmp.getNum(),
						whBomTmp == null ? 0 : whBomTmp.getDeliveryRemainingNum(),
						whBom.getNum(),
						whBom.getDeliveryRemainingNum());
			}
			writer.close();
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
	            
	            if(whboms.containsKey(pn)) {
	            	throw new PdsysException("重复编号：" + pn);
	            }

	            bom.setPn(pn);
	            bom  = bomService.queryOne(bom);
	            if(bom == null && (!"".equals(num) || !"".equals(remaingNum))) {
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
	           
	            if(whBom.getNum() != -1 || whBom.getDeliveryRemainingNum() != -1) {
	            	whboms.put(pn, whBom);
	            }
	        }
		} catch(Exception e) {
			throw new PdsysException(e.getMessage() + " \n错误行:" + (rowNo + 1), e);
		}
	}
}
