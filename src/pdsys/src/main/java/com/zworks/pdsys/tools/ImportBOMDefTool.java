package com.zworks.pdsys.tools;

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
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.zworks.pdsys.common.exception.PdsysException;
import com.zworks.pdsys.common.utils.ExcelUtils;
import com.zworks.pdsys.models.BOMModel;
import com.zworks.pdsys.models.SupplierModel;
import com.zworks.pdsys.models.UnitModel;
import com.zworks.pdsys.services.BOMService;
import com.zworks.pdsys.services.SupplierService;
import com.zworks.pdsys.services.UnitService;

@Component
@Scope("prototype")
public class ImportBOMDefTool {
	@Autowired
    private UnitService unitService;
	@Autowired
	private BOMService bomService;
	@Autowired
    private SupplierService supplierService;
	
	private Map<String, UnitModel> units = new HashMap<String, UnitModel>();
	private Map<String, BOMModel> boms = new HashMap<String, BOMModel>();
	private Map<String, SupplierModel> suppliers = new HashMap<String, SupplierModel>();

	@Transactional(rollbackFor=Exception.class)
	public boolean execute(String filePath) throws InvalidFormatException, IOException{
		units.clear();
		boms.clear();
		suppliers.clear();
		
		read(filePath);
		
//		try {//此处不可try 否则事务会不能正确回滚，应把exception交给spring
		
			//import unit
			for(String key : units.keySet()) {
				UnitModel unit = units.get(key);
				UnitModel unitTmp = unitService.queryOne(unit);
				if(unitTmp == null) {
					unitService.add(unit);
				} else {
					unit.setId(unitTmp.getId());
				}
			}
		
			//import suppliers
			for(String key : suppliers.keySet()) {
				SupplierModel s = suppliers.get(key);
				SupplierModel sTmp = supplierService.queryOne(s);
				if(sTmp == null) {
					supplierService.add(s);
				} else {
					s.setId(sTmp.getId());
				}
			}
					
			//import boms
			for(String key : boms.keySet()) {
				BOMModel bom = boms.get(key);
				BOMModel bombTmp = new BOMModel();
				bombTmp.setPn(key);
				bombTmp = bomService.queryOne(bombTmp);
				if(bombTmp != null) {
					throw new PdsysException("已经存在BOM：" + key);
				}
				bomService.add(bom);
				bomService.addSupplier(bom);
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
				throw new PdsysException("没找到data sheet");
			}
			for(rowNo = 3; rowNo <= sheet.getLastRowNum(); rowNo++) {
				Row row = sheet.getRow(rowNo);
	  
				int idx = 0;
	            String bomType = ExcelUtils.getCellValue(row.getCell(idx++));
	            String bomPn = ExcelUtils.getCellValue(row.getCell(idx++));
	            String bomName = ExcelUtils.getCellValue(row.getCell(idx++));
	            String bomUnitName = ExcelUtils.getCellValue(row.getCell(idx++));
	            String bomUnitSubName = ExcelUtils.getCellValue(row.getCell(idx++));
	            String bomUnitRatio = ExcelUtils.getCellValue(row.getCell(idx++));
	            String bomPrice = ExcelUtils.getCellValue(row.getCell(idx++));
	            String bomSupplier = ExcelUtils.getCellValue(row.getCell(idx++));
	            
	            //bom-unit
	            UnitModel bomUnit = null;
	            if(!"".equals(bomUnitName)) {
	            	bomUnit = new UnitModel();
		            bomUnit.setName(bomUnitName);
		            bomUnit.setSubName(bomUnitSubName);
		            bomUnit.setRatio(Float.parseFloat(bomUnitRatio));
		            bomUnit = addUnit(bomUnit);
	            }
	
	            //供应商
	            SupplierModel supplier = null;
	            if(!"".equals(bomSupplier)) {
	            	supplier = new SupplierModel();
		            supplier.setName(bomSupplier);
		            supplier = addSuppliers(supplier);
	            }
	            
	            //BOM
	            BOMModel bom = new BOMModel();
	            if("原".equals(bomType)) {
	            	bom.setType(0);
	            } else {
	            	bom.setType(1);
	            }
	            bom.setPn(bomPn);
	            if("".equals(bomPn)) {
	            	throw new PdsysException("未设定BOM编号");
	            }
	            bom.setName(bomName);
	            if("".equals(bomName)) {
	            	throw new PdsysException("未设定BOM名称");
	            }
	            if(bomUnit == null) {
	            	throw new PdsysException("未设定单位");
	            }
	            bom.setUnit(bomUnit);
	            bom.setPrice(Float.parseFloat(bomPrice));
	            if(supplier == null) {
	            	throw new PdsysException("未设定供应商");
	            }
	            List<SupplierModel> suppliers = new  ArrayList<SupplierModel>();
	            suppliers.add(supplier);
	            bom.setSuppliers(suppliers);
	            bom = addBoms(bom);
	            

	        }
		}catch(Exception e) {
			throw new PdsysException(e.getMessage() + " \n错误行:" + (rowNo + 1), e);
		}
	}

	private BOMModel addBoms(BOMModel bom) {
		BOMModel bomTmp = boms.get(bom.getPn());
		if(bomTmp != null) {
			return bomTmp;
		}
		boms.put(bom.getPn(), bom);
		return bom;
	}

	private SupplierModel addSuppliers(SupplierModel supplier) {
		SupplierModel s = suppliers.get(supplier.getName());
		if(s != null) {
			return s;
		}
		suppliers.put(supplier.getName(), supplier);
		return supplier;
	}

	private UnitModel addUnit(UnitModel unit) {
		String key = unit.getName() + "@@" + unit.getSubName() + "@@" + unit.getRatio();
		UnitModel s = units.get(key);
		if(s != null) {
			return s;
		}
		units.put(key, unit);
		return unit;
	}
}
