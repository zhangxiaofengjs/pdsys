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

import com.zworks.pdsys.common.enumClass.UnitType;
import com.zworks.pdsys.common.exception.PdsysException;
import com.zworks.pdsys.common.utils.ExcelUtils;
import com.zworks.pdsys.models.BOMModel;
import com.zworks.pdsys.models.PnBOMRelModel;
import com.zworks.pdsys.models.PnClsBOMRelModel;
import com.zworks.pdsys.models.PnClsModel;
import com.zworks.pdsys.models.PnModel;
import com.zworks.pdsys.models.PnPnClsRelModel;
import com.zworks.pdsys.models.SupplierModel;
import com.zworks.pdsys.models.UnitModel;
import com.zworks.pdsys.services.BOMService;
import com.zworks.pdsys.services.PnService;
import com.zworks.pdsys.services.SupplierService;
import com.zworks.pdsys.services.UnitService;

@Component
@Scope("prototype")
public class ImportPnDefTool {
	@Autowired
    private UnitService unitService;
	@Autowired
	private BOMService bomService;
	@Autowired
    private SupplierService supplierService;
	@Autowired
	private PnService pnService;
	
	private Map<String, PnModel> pns = new HashMap<String,PnModel>();
	private Map<String, UnitModel> units = new HashMap<String, UnitModel>();
	private Map<String, BOMModel> boms = new HashMap<String, BOMModel>();
	private Map<String, SupplierModel> suppliers = new HashMap<String, SupplierModel>();

	@Transactional(rollbackFor=Exception.class)
	public boolean execute(String filePath) throws InvalidFormatException, IOException{
		pns.clear();
		units.clear();
		boms.clear();
		suppliers.clear();
		
		read(filePath);
		
//		try {//此处不可try 否则事务会不能正确回滚，应把exception交给spring
		
			//check pns
			for(String key : pns.keySet()) {
				PnModel pn = pns.get(key);
				if(pn.getPnClsRels() == null) {
					throw new PdsysException("未设定本体，PN:" + pn.getPn() + " 行:" + pn.getFilterCond().get("row"));
				}
			}
			
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
				bomService.add(bom);
				bomService.addSupplier(bom);
			}
			
			//import pns
			for(String key : pns.keySet()) {
				PnModel pn = pns.get(key);
				if(pn.getPnClsRels() == null) {
					throw new PdsysException("未设定本体，PN:" + pn.getPn());
				}
				pnService.add(pn);
				pnService.addBOM(pn);
				pnService.addPnCls(pn);
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
			PnModel pnObj = null;
			for(rowNo = 3; rowNo <= sheet.getLastRowNum(); rowNo++) {
				Row row = sheet.getRow(rowNo);
	  
				int idx = 0;
	           	String pn = ExcelUtils.getCellValue(row.getCell(idx++));
	           	String name = ExcelUtils.getCellValue(row.getCell(idx++));
	           	String priceUnitName = ExcelUtils.getCellValue(row.getCell(idx++));
	           	String price = ExcelUtils.getCellValue(row.getCell(idx++));
	            String cls = ExcelUtils.getCellValue(row.getCell(idx++));
	            String unitName = ExcelUtils.getCellValue(row.getCell(idx++));
	            String unitSubName = ExcelUtils.getCellValue(row.getCell(idx++));
	            String unitRatio = ExcelUtils.getCellValue(row.getCell(idx++));
	            String clsNum = ExcelUtils.getCellValue(row.getCell(idx++));
	            String bomType = ExcelUtils.getCellValue(row.getCell(idx++));
	            String bomPn = ExcelUtils.getCellValue(row.getCell(idx++));
	            String bomName = ExcelUtils.getCellValue(row.getCell(idx++));
	            String bomUnitName = ExcelUtils.getCellValue(row.getCell(idx++));
	            String bomUnitSubName = ExcelUtils.getCellValue(row.getCell(idx++));
	            String bomUnitRatio = ExcelUtils.getCellValue(row.getCell(idx++));
	            String bomUseNum = ExcelUtils.getCellValue(row.getCell(idx++));
	            String bomPrice = ExcelUtils.getCellValue(row.getCell(idx++));
	            String bomSupplier = ExcelUtils.getCellValue(row.getCell(idx++));
	            
	            //pn-price unit
	            UnitModel priceUnit = null;
	            if("".equals(priceUnitName)) {
	            	//default
	            	priceUnitName = "USD";
	            }
            	priceUnit = new UnitModel();
            	priceUnit.setName(priceUnitName);
            	priceUnit.setSubName(priceUnitName);
            	priceUnit.setRatio(1);
            	priceUnit.setType(UnitType.Currency.ordinal());
            	priceUnit = addUnit(priceUnit);
	            
	            //pn-unit
	            UnitModel unit = null;
	            if(!"".equals(unitName)) {
		            unit = new UnitModel();
		            unit.setName(unitName);
		            unit.setSubName(unitSubName);
		            unit.setRatio(Float.parseFloat(unitRatio));
		            unit = addUnit(unit);
	            }
	            
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
	            
	            if(pn != null && !"".equals(pn) ) {
	            	pnObj = new PnModel();
	            	pnObj.setPn(pn);
	            	pnObj.setName(name);
	            	
	            	float priceTmp = 0;
	            	try {
	            		priceTmp = Float.parseFloat(price);
	            	} catch(Exception e) {}
	            	pnObj.setPrice(priceTmp);
	            	pnObj.setPriceUnit(priceUnit);
	            	if(unit == null) {
		            	throw new PdsysException("未设定单位");
		            }
	            	pnObj.setUnit(unit);
	            	pnObj.getFilterCond().put("row", rowNo + 1);
	            	if(!addPns(pnObj)) {
	            		throw new PdsysException("重复品目PN:" + pn);
	            	}
	            }
	            
	            if("".equals(cls)) {
	            	throw new PdsysException("本体名列不能为空");
	            } else if("--".equals(cls)) {
	            	//共通包材
	            	List<PnBOMRelModel> pnBOMRels = pnObj.getPnBOMRels();
	            	if(pnBOMRels == null) {
	            		pnBOMRels = new ArrayList<PnBOMRelModel>();
	            		pnObj.setPnBOMRels(pnBOMRels);
	            	}
	
	            	PnBOMRelModel pnBomRel = new PnBOMRelModel();
	            	pnBomRel.setUseNum(Float.parseFloat(bomUseNum));
	            	pnBomRel.setBom(bom);
	            	pnBOMRels.add(pnBomRel);
	            } else {
	            	List<PnPnClsRelModel> pnClsRels = pnObj.getPnClsRels();
	            	if(pnClsRels == null) {
	            		pnClsRels = new ArrayList<PnPnClsRelModel>();
	            		pnObj.setPnClsRels(pnClsRels);
	            	}
	            	
	            	PnClsModel pnCls = new PnClsModel();
	            	pnCls.setName(cls);
	            	pnCls.setUnit(unit);
	            	PnPnClsRelModel pnClsRel = new PnPnClsRelModel();
	            	pnClsRel.setNum(Float.parseFloat(clsNum));
	            	pnClsRel.setPnCls(pnCls);
	            	pnClsRel = addCls(pnClsRel, pnClsRels);
	            	pnCls = pnClsRel.getPnCls();
	            	
	            	List<PnClsBOMRelModel> pnClsBomRels = pnCls.getPnClsBOMRels();
	            	if(pnClsBomRels == null) {
	            		pnClsBomRels = new ArrayList<PnClsBOMRelModel>();
	            		pnCls.setPnClsBOMRels(pnClsBomRels);
	            	}
	            	
	            	PnClsBOMRelModel pnClsBomRel = new PnClsBOMRelModel();
	            	pnClsBomRel.setUseNum(Float.parseFloat(bomUseNum));
	            	pnClsBomRel.setBom(bom);
	            	pnClsBomRels.add(pnClsBomRel);
	            }
	        }
		}catch(Exception e) {
			throw new PdsysException(e.getMessage() + " \n错误行:" + (rowNo + 1), e);
		}
	}
	
	private boolean addPns(PnModel pnObj) {
		PnModel pn = pns.get(pnObj.getPn());
		if(pn != null) {
			return false;
		}
		pns.put(pnObj.getPn(), pnObj);
		return true;
	}

	private BOMModel addBoms(BOMModel bom) {
		BOMModel bomTmp = boms.get(bom.getPn());
		if(bomTmp != null) {
			return bomTmp;
		}
		boms.put(bom.getPn(), bom);
		return bom;
	}

	private PnPnClsRelModel addCls(PnPnClsRelModel pnClsRel, List<PnPnClsRelModel> pnClsRels) {
		for(PnPnClsRelModel rel : pnClsRels) {
			if(rel.getPnCls().getName().equals(pnClsRel.getPnCls().getName())) {
				return rel;
			}
		}
		if(pnClsRel.getPnCls().getUnit() == null) {
        	throw new PdsysException("未设定单位" + pnClsRel.getPnCls().getName());
        }
		pnClsRels.add(pnClsRel);
		return pnClsRel;
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
