package com.zworks.pdsys.tools;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.zworks.pdsys.common.exception.PdsysException;
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
	
	private DecimalFormat df = new DecimalFormat("0.00000");
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
				unitService.add(units.get(key));
			}
			
			//import suppliers
			for(String key : suppliers.keySet()) {
				supplierService.add(suppliers.get(key));
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
			Sheet sheet = wb.getSheet("data");
			
			PnModel pnObj = null;
			for(rowNo = 3; rowNo <= sheet.getLastRowNum(); rowNo++) {
				Row row = sheet.getRow(rowNo);
	  
				int idx = 0;
	           	String pn = getCellValue(row.getCell(idx++));
	           	String name = getCellValue(row.getCell(idx++));
	            String cls = getCellValue(row.getCell(idx++));
	            String unitName = getCellValue(row.getCell(idx++));
	            String unitSubName = getCellValue(row.getCell(idx++));
	            String unitRatio = getCellValue(row.getCell(idx++));
	            String clsNum = getCellValue(row.getCell(idx++));
	            String bomType = getCellValue(row.getCell(idx++));
	            String bomPn = getCellValue(row.getCell(idx++));
	            String bomName = getCellValue(row.getCell(idx++));
	            String bomUnitName = getCellValue(row.getCell(idx++));
	            String bomUnitSubName = getCellValue(row.getCell(idx++));
	            String bomUnitRatio = getCellValue(row.getCell(idx++));
	            String bomUseNum = getCellValue(row.getCell(idx++));
	            String bomPrice = getCellValue(row.getCell(idx++));
	            String bomSupplier = getCellValue(row.getCell(idx++));
	            
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

	private String getCellValue(Cell cell) {
        String value = "";
        if (cell != null) {
            int cellType = cell.getCellType();
            switch (cellType) {
                case Cell.CELL_TYPE_NUMERIC:            //表示数值
                    value =  df.format(cell.getNumericCellValue());
                    break;
                case Cell.CELL_TYPE_STRING:            //表示字符串
                    value = cell.getStringCellValue();
                    break;
                case Cell.CELL_TYPE_BLANK:            //表示空白
                    value = "";
                    break;
                case Cell.CELL_TYPE_BOOLEAN:            //表示boolean
                    value = cell.getBooleanCellValue() + "";
                    break;
                default:        //表示其他
                    value = "";
                    break;
            }
        } else {
            value = "";
        }
        return value.trim();
    }
}
