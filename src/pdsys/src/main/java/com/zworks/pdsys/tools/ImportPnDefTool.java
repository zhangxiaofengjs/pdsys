package com.zworks.pdsys.tools;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.zworks.pdsys.mappers.PnMapper;
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

	@Transactional
	public boolean execute(String filePath){
		if(!read(filePath)) {
			return false;
		}
		
		try {
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
				bomService.add(boms.get(key));
			}
			
			//import pns
			for(String key : pns.keySet()) {
				PnModel pn = pns.get(key);
				pnService.add(pn);
				pnService.addBOM(pn);
				pnService.addPnCls(pn);
			}
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public boolean read(String filePath){
		InputStream is = null;
		try {
			is = new FileInputStream(filePath);  
		             
			Workbook wb = WorkbookFactory.create(is);
			Sheet sheet = wb.getSheet("data");
			
			PnModel pnObj = null;
			for(int i = 3; i <= sheet.getLastRowNum(); i++) {
				Row row = sheet.getRow(i);
	  
				int idx = 0;
	           	String pn = getCellValue(row.getCell(idx++));
	           	String name = getCellValue(row.getCell(idx++));
	            String cls = getCellValue(row.getCell(idx++));
	            String unitName = getCellValue(row.getCell(idx++));
	            String unitSubName = getCellValue(row.getCell(idx++));
	            String unitRatio = getCellValue(row.getCell(idx++));
	            String clsUseNum = getCellValue(row.getCell(idx++));
	            String bomType = getCellValue(row.getCell(idx++));
	            String bomPn = getCellValue(row.getCell(idx++));
	            String bomName = getCellValue(row.getCell(idx++));
	            String bomUnitName = getCellValue(row.getCell(idx++));
	            String bomUnitSubName = getCellValue(row.getCell(idx++));
	            String bomUnitRatio = getCellValue(row.getCell(idx++));
	            String clsBomUseNum = getCellValue(row.getCell(idx++));
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
	            UnitModel bomUnit = new UnitModel();
	            bomUnit.setName(bomUnitName);
	            bomUnit.setSubName(bomUnitSubName);
	            bomUnit.setRatio(Float.parseFloat(bomUnitRatio));
	            bomUnit = addUnit(bomUnit);
	
	            //供应商
	            SupplierModel supplier = new SupplierModel();
	            supplier.setName(bomSupplier);
	            supplier = addSuppliers(supplier);
	            
	            //BOM
	            BOMModel bom = new BOMModel();
	            if("原".equals(bomType)) {
	            	bom.setType(0);
	            } else if("包".equals(bomType)) {
	            	bom.setType(1);
	            } else {
	            	throw new Exception(i + "行原包类型错误");
	            }
	            bom.setPn(bomPn);
	            bom.setName(bomName);
	            bom.setUnit(bomUnit);
	            bom.setPrice(Float.parseFloat(bomPrice));
	            List<SupplierModel> suppliers = new  ArrayList<SupplierModel>();
	            suppliers.add(supplier);
	            bom.setSuppliers(suppliers);
	            bom = addBoms(bom);
	            
	            if(pn != null && !"".equals(pn) ) {
	            	pnObj = new PnModel();
	            	pnObj.setPn(pn);
	            	pnObj.setName(name);
	            	pnObj.setUnit(unit);
	            }
	            pnObj = addPns(pnObj); 
	            
	            if("--".equals(cls)) {
	            	//共通包材
	            	List<PnBOMRelModel> pnBOMRels = pnObj.getPnBOMRels();
	            	if(pnBOMRels == null) {
	            		pnBOMRels = new ArrayList<PnBOMRelModel>();
	            		pnObj.setPnBOMRels(pnBOMRels);
	            	}
	
	            	PnBOMRelModel pnBomRel = new PnBOMRelModel();
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
	            	pnClsRel.setNum(Float.parseFloat(clsUseNum));
	            	pnClsRel.setPnCls(pnCls);
	            	pnClsRel = addCls(pnClsRel, pnClsRels);
	            	pnCls = pnClsRel.getPnCls();
	            	
	            	List<PnClsBOMRelModel> pnClsBomRels = pnCls.getPnClsBOMRels();
	            	if(pnClsBomRels == null) {
	            		pnClsBomRels = new ArrayList<PnClsBOMRelModel>();
	            		pnCls.setPnClsBOMRels(pnClsBomRels);
	            	}
	            	
	            	PnClsBOMRelModel pnClsBomRel = new PnClsBOMRelModel();
	            	pnClsBomRel.setUseNum(Float.parseFloat(clsBomUseNum));
	            	pnClsBomRel.setBom(bom);
	            	pnClsBomRels.add(pnClsBomRel);
	            }
	        }
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			if(is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
					return false;
				}
			}
		}
		return true;
	}
	
	private PnModel addPns(PnModel pnObj) {
		PnModel pn = pns.get(pnObj.getPn());
		if(pn != null) {
			return pn;
		}
		pns.put(pnObj.getPn(), pnObj);
		return pnObj;
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
