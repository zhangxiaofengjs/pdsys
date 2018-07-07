package com.zworks.pdsys.io;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zworks.pdsys.common.enumClass.EntryType;
import com.zworks.pdsys.common.exception.PdsysException;
import com.zworks.pdsys.common.utils.ExcelUtils;
import com.zworks.pdsys.common.utils.StringUtils;
import com.zworks.pdsys.models.BOMModel;
import com.zworks.pdsys.models.UserModel;
import com.zworks.pdsys.models.WareHouseEntryBOMModel;
import com.zworks.pdsys.models.WareHouseEntryModel;
import com.zworks.pdsys.services.BOMService;
import com.zworks.pdsys.services.UserService;

@Component
public class EntryTemplateReader {
	@Autowired
	private UserService userService;
	@Autowired
	private BOMService bomService;
	
	public WareHouseEntryModel readBOM(String filePath) {
		int rowNo = 0;
		InputStream is = null;
		try {
			is = new FileInputStream(filePath);  
		             
			Workbook wb = WorkbookFactory.create(is);
			Sheet sheet = wb.getSheetAt(0);
			if(sheet == null) {
				throw new PdsysException("没找到sheet");
			}
			
			WareHouseEntryModel entry = new WareHouseEntryModel();
			entry.setType(EntryType.BOM.ordinal());
			List<WareHouseEntryBOMModel> entryBoms = new ArrayList<WareHouseEntryBOMModel>();
			Set<String> bomSet = new HashSet<String>();
			boolean lastRow = false;
			for(rowNo = 0; rowNo <= sheet.getLastRowNum() && !lastRow; rowNo++) {
				Row row = sheet.getRow(rowNo);
	  
				//入库单号，提交人
				switch(rowNo)
				{
				case 0:
					String no = ExcelUtils.getCellValue(row.getCell(1));
					if(no == null || no.isEmpty()) {
						throw new PdsysException("入库单编号未填写");
					}
					entry.setNo(no);
					break;
				case 1:
					String userName = ExcelUtils.getCellValue(row.getCell(1));
					UserModel user = new UserModel();
					user.setName(userName);
					user = userService.queryOne(user);
					if(user == null) {
						throw new PdsysException("提交人未填写或者不存在");
					}
					entry.setUser(user);
					break;
				case 2:
					String memo = ExcelUtils.getCellValue(row.getCell(1));
					entry.setComment(memo);
					break;
				case 3:
				case 4:
					break;
				default:
					String strPn = ExcelUtils.getCellValue(row.getCell(0));
					if(StringUtils.isNullOrEmpty(strPn)) {
						lastRow = true;
						break;
					}
					if(!bomSet.add(strPn)) {
						throw new PdsysException("重复JAN");
					}
					Float num = StringUtils.toFloat(ExcelUtils.getCellValue(row.getCell(2)));
					if(StringUtils.isNullOrEmpty(strPn) || num == 0){
						throw new PdsysException("JAN或者订购量不正确");
					}
					
					BOMModel bom = new BOMModel();
					bom.setPn(strPn);
					bom = bomService.queryOne(bom);
					if(bom == null) {
						throw new PdsysException("JAN不存在:" + strPn);
					}
					
					WareHouseEntryBOMModel eBOM = new WareHouseEntryBOMModel();
					eBOM.setBom(bom);
					eBOM.setNum(num);
					
					entryBoms.add(eBOM);
					break;
				}
			}
			
			entry.setWareHouseEntryBOMs(entryBoms);
			return entry;
		} catch(Exception e) {
			throw new PdsysException(e.getMessage() + " \n错误行:" + (rowNo + 1), e);
		} finally {
			if(is != null) {
				try {
					is.close();
				} catch(Exception e) {
					System.out.println(e);
				}
			}
		}
	}
}
