package com.zworks.pdsys.tools;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
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

import com.zworks.pdsys.common.enumClass.DeviceState;
import com.zworks.pdsys.common.exception.PdsysException;
import com.zworks.pdsys.common.utils.ExcelUtils;
import com.zworks.pdsys.models.DeviceModel;
import com.zworks.pdsys.models.MachineMachinePartRelModel;
import com.zworks.pdsys.models.MachineModel;
import com.zworks.pdsys.models.MachinePartModel;
import com.zworks.pdsys.models.MachineTroubleModel;
import com.zworks.pdsys.models.PlaceModel;
import com.zworks.pdsys.models.SupplierModel;
import com.zworks.pdsys.models.UnitModel;
import com.zworks.pdsys.models.UserModel;
import com.zworks.pdsys.models.WareHouseMachinePartModel;
import com.zworks.pdsys.services.DeviceService;
import com.zworks.pdsys.services.MachinePartService;
import com.zworks.pdsys.services.MachineService;
import com.zworks.pdsys.services.MachineTroubleService;
import com.zworks.pdsys.services.PlaceService;
import com.zworks.pdsys.services.SupplierService;
import com.zworks.pdsys.services.UnitService;
import com.zworks.pdsys.services.UserService;
import com.zworks.pdsys.services.WareHouseMachinePartService;

@Component
@Scope("prototype")
public class ImportDeviceTool {
	@Autowired
	private UserService userService;
	@Autowired
	private UnitService unitService;
	@Autowired
	private SupplierService supplierService;
	@Autowired
	private PlaceService placeService;
	@Autowired
	private MachinePartService machinePartService;
	@Autowired
	private MachineService machineService;
	@Autowired
	private MachineTroubleService machineTroubleService;
	@Autowired
	private DeviceService deviceService;
	@Autowired
	private WareHouseMachinePartService wareHouseMachinePartService;
	
	private Map<String, UnitModel> units = new HashMap<String, UnitModel>();
	private Map<String, MachineModel> machines = new HashMap<String, MachineModel>();
	private Map<String, DeviceModel> devices = new HashMap<String, DeviceModel>();
	private Map<String, PlaceModel> places = new HashMap<String, PlaceModel>();
	private Map<String, SupplierModel> suppliers = new HashMap<String, SupplierModel>();
	private Map<String, MachinePartModel> mps = new HashMap<String, MachinePartModel>();
	private Map<String, MachineTroubleModel> troubles = new HashMap<String, MachineTroubleModel>();
	private Map<String, WareHouseMachinePartModel> whMps = new HashMap<String, WareHouseMachinePartModel>();

	@Transactional(rollbackFor=Exception.class)
	public boolean execute(String filePath) throws InvalidFormatException, IOException{
		units.clear();
		machines.clear();
		devices.clear();
		places.clear();
		suppliers.clear();
		mps.clear();
		troubles.clear();
		
		readDevice(filePath);
		readMachineParts(filePath);
		readTroubles(filePath);
		
//		try {//此处不可try 否则事务会不能正确回滚，应把exception交给spring
		
			//import 
			for(String key : units.keySet()) {
				UnitModel unit = units.get(key);
				UnitModel unitTmp = unitService.queryOne(unit);
				if(unitTmp == null) {
					unitService.add(unit);
				} else {
					unit.setId(unitTmp.getId());
				}
			}
			
			for(String key : suppliers.keySet()) {
				SupplierModel s = suppliers.get(key);
				SupplierModel sTmp = supplierService.queryOne(s);
				if(sTmp == null) {
					supplierService.add(s);
				} else {
					s.setId(sTmp.getId());
				}
			}
			
			for(String key : places.keySet()) {
				PlaceModel s = places.get(key);
				placeService.add(s);
			}
			
			for(String key : mps.keySet()) {
				MachinePartModel s = mps.get(key);
				machinePartService.add(s);
			}
			
			for(String key : machines.keySet()) {
				MachineModel s = machines.get(key);
				machineService.add(s);
				if(s.getMachineMachinePartRels() != null) {
					machineService.addMachinePart(s);
				}
			}
			
			for(String key : devices.keySet()) {
				DeviceModel s = devices.get(key);
				deviceService.add(s);
			}
			
			for(String key : troubles.keySet()) {
				MachineTroubleModel s = troubles.get(key);
				machineTroubleService.add(s);
			}
			
			for(String key : whMps.keySet()) {
				WareHouseMachinePartModel s = whMps.get(key);
				wareHouseMachinePartService.add(s);
			}
			
//		} catch(Exception e) {
//			e.printStackTrace();
//			return false;
//		}
		return true;
	}
	
	public void readDevice(String filePath) throws InvalidFormatException, IOException{
		int rowNo = 0;
		InputStream is = null;
		try {
			
			is = new FileInputStream(filePath);  
		             
			Workbook wb = WorkbookFactory.create(is);
			Sheet sheet = wb.getSheet("设备管理");
			if(sheet == null) {
				throw new PdsysException("没找到 sheet ");
			}
			
			for(rowNo = 1; rowNo <= sheet.getLastRowNum(); rowNo++) {
				Row row = sheet.getRow(rowNo);
	  
				int idx = 0;
	           	String dPn = ExcelUtils.getCellValue(row.getCell(idx++));
				String mPn = ExcelUtils.getCellValue(row.getCell(idx++));
	            String mName = ExcelUtils.getCellValue(row.getCell(idx++));
	            String unitName = ExcelUtils.getCellValue(row.getCell(idx++));
	            String supplierName = ExcelUtils.getCellValue(row.getCell(idx++));
	            String placeStr = ExcelUtils.getCellValue(row.getCell(idx++));
	            String matenDays = ExcelUtils.getCellValue(row.getCell(idx++));
	            String lastMantenDate = ExcelUtils.getCellValue(row.getCell(idx++));
	            String state = ExcelUtils.getCellValue(row.getCell(idx++));
	            String userName = ExcelUtils.getCellValue(row.getCell(idx++));

	            if("".equals(dPn)) {
	            	break;
	            }
	            
	            if("".equals(unitName)) {
	            	throw new PdsysException("未设定单位");
	            }
	            
	            UnitModel unit = new UnitModel();
	            unit.setName(unitName);
	            unit.setRatio(1);
	            unit.setSubName(unitName);
	            unit = addUnit(unit);
	            
	            if("".equals(mPn) || "".equals(mName)) {
	            	throw new PdsysException("未设定机器型号或名称");
	            }
	            if("".equals(matenDays)) {
	            	throw new PdsysException("未设定机器保养日期");
	            }
	            
	            if("".equals(supplierName)) {
	            	throw new PdsysException("未设定供应商");
	            }
	            SupplierModel supp = new SupplierModel();
	            supp.setName(supplierName);
	            supp = addSupplier(supp);
	            
	            MachineModel machine = new MachineModel();
	            machine.setPn(mPn);
	            machine.setName(mName);
	            machine.setMaitenacePeriod(Float.parseFloat(matenDays));
	            machine.setUnit(unit);
	            machine.setSupplier(supp);
	            machine = addMachine(machine);
	            
	            if("".equals(placeStr)) {
	            	throw new PdsysException("未设定地点");
	            }
	            PlaceModel place = new PlaceModel();
	            place.setName(placeStr);
	            place = addPlace(place);
	            
	            if(devices.containsKey(dPn)) {
	            	throw new PdsysException("重复编号：" + dPn);
	            }
	            
	            UserModel user = new UserModel();
	            user.setName(userName);
	            user = userService.queryOne(user);
	            if(user == null) {
	            	throw new PdsysException("不存在用户");
	            }
	            
	            DeviceModel device = new DeviceModel();
	            device.setNo(dPn);
	            device.setMachine(machine);
	            device.setPlace(place);
	            device.setUser(user);
	            if("正常".equals(state)) {
	            	device.setState(DeviceState.WORKING.ordinal());
	            } else  if("停用".equals(state)) {
	            	device.setState(DeviceState.NOTUSE.ordinal());
	            } else {
	            	device.setState(DeviceState.BROKEN.ordinal());
	            }
	            
	            SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
	            device.setMaitenacedDate(df.parse(lastMantenDate));
	            device = addDevice(device);
	        }
		} catch(Exception e) {
			throw new PdsysException("【设备管理】" + e.getMessage() + " \n错误行:" + (rowNo + 1), e);
		} finally {
			if(is != null) {
				is.close();
			}
		}
	}
	
	public void readMachineParts(String filePath) throws InvalidFormatException, IOException{
		int rowNo = 0;
		InputStream is = null;
		try {
			
			is = new FileInputStream(filePath);  
		             
			Workbook wb = WorkbookFactory.create(is);
			Sheet sheet = wb.getSheet("保养耗材");
			if(sheet == null) {
				throw new PdsysException("没找到 sheet 保养耗材");
			}
			
			for(rowNo = 1; rowNo <= sheet.getLastRowNum(); rowNo++) {
				Row row = sheet.getRow(rowNo);
	  
				int idx = 0;
	           	String mPn = ExcelUtils.getCellValue(row.getCell(idx++));
				String pPn = ExcelUtils.getCellValue(row.getCell(idx++));
	            String pName = ExcelUtils.getCellValue(row.getCell(idx++));
	            String useNum = ExcelUtils.getCellValue(row.getCell(idx++));
	            String unitName = ExcelUtils.getCellValue(row.getCell(idx++));
	            String supplierName = ExcelUtils.getCellValue(row.getCell(idx++));
	            String minNumStr = ExcelUtils.getCellValue(row.getCell(idx++));
	            String whNumStr = ExcelUtils.getCellValue(row.getCell(idx++));
	            
	            if("".equals(mPn)) {
	            	break;
	            }
	            
	            if("".equals(unitName)) {
	            	throw new PdsysException("未设定单位");
	            }
	            
	            UnitModel unit = new UnitModel();
	            unit.setName(unitName);
	            unit.setRatio(1);
	            unit.setSubName(unitName);
	            unit = addUnit(unit);
	            
	            if("".equals(pPn)) {
	            	throw new PdsysException("未设定备件型号");
	            }
	            if("".equals(pName)) {
	            	throw new PdsysException("未设定备件名称");
	            }
	            
	            if(!machines.containsKey(mPn)) {
	            	throw new PdsysException("未定义机器型号");
	            }
	            
	            MachineModel machine = machines.get(mPn);
	            
	            if("".equals(supplierName)) {
	            	throw new PdsysException("未设定供应商");
	            }
	            SupplierModel supp = new SupplierModel();
	            supp.setName(supplierName);
	            supp = addSupplier(supp);
	            
	            MachinePartModel mp = new MachinePartModel();
	            mp.setPn(pPn);
	            mp.setName(pName);
	            mp.setSupplier(supp);
	            mp.setUnit(unit);
	            mp.setWareHouseMinNum(Float.parseFloat(minNumStr));
	            
	            mp = addMachinePart(mp);
	            
	            List<MachineMachinePartRelModel> mpRels = machine.getMachineMachinePartRels();
	            if(mpRels == null) {
	            	mpRels = new ArrayList<MachineMachinePartRelModel>();
	            	machine.setMachineMachinePartRels(mpRels);
	            }
	            MachineMachinePartRelModel mpRel = new MachineMachinePartRelModel();
	            mpRel.setMachinePart(mp);
	            mpRel.setMaitenacePartNum(Float.parseFloat(useNum));
				mpRels.add(mpRel );
				
				WareHouseMachinePartModel whPart = new WareHouseMachinePartModel();
				whPart.setMachinePart(mp);
				whPart.setNum(Float.parseFloat(whNumStr));
				whMps.put(mp.getPn(), whPart);
	        }
		} catch(Exception e) {
			throw new PdsysException("【保养耗材】" + e.getMessage() + " \n错误行:" + (rowNo + 1), e);
		} finally {
			if(is != null) {
				is.close();
			}
		}
	}
	
	public void readTroubles(String filePath) throws InvalidFormatException, IOException{
		int rowNo = 0;
		InputStream is = null;
		try {
			
			is = new FileInputStream(filePath);  
		             
			Workbook wb = WorkbookFactory.create(is);
			Sheet sheet = wb.getSheet("机台故障名称");
			if(sheet == null) {
				throw new PdsysException("没找到 sheet 机台故障名称");
			}
			
			for(rowNo = 1; rowNo <= sheet.getLastRowNum(); rowNo++) {
				Row row = sheet.getRow(rowNo);
	  
				int idx = 0;
	           	String name = ExcelUtils.getCellValue(row.getCell(idx++));
				String code = ExcelUtils.getCellValue(row.getCell(idx++));

	            if("".equals(name)) {
	            	break;
	            }
	            
	            if("".equals(code)) {
	            	throw new PdsysException("未设定代码");
	            }
	            
	            if(troubles.containsKey(code)) {
	            	throw new PdsysException("重复代码:" + code);
	            }
	            MachineTroubleModel trouble = new MachineTroubleModel();
	            trouble.setComment(name);
	            trouble.setCode(code);
	            trouble = addTrouble(trouble);
	        }
		} catch(Exception e) {
			throw new PdsysException("机台故障名称" + e.getMessage() + " \n错误行:" + (rowNo + 1), e);
		} finally {
			if(is != null) {
				is.close();
			}
		}
	}
	
	private MachineTroubleModel addTrouble(MachineTroubleModel trouble) {
		if(!troubles.containsKey(trouble.getCode())) {
			troubles.put(trouble.getCode(), trouble);
		}
		return (MachineTroubleModel)troubles.get(trouble.getCode());
	}

	private MachinePartModel addMachinePart(MachinePartModel mp) {
		if(!mps.containsKey(mp.getPn())) {
			mps.put(mp.getPn(), mp);
		}
		return (MachinePartModel)mps.get(mp.getPn());
	}

	private UnitModel addUnit(UnitModel u) {
		if(!units.containsKey(u.getName())) {
			units.put(u.getName(), u);
		}
		return (UnitModel)units.get(u.getName());
	}
	
	private PlaceModel addPlace(PlaceModel u) {
		if(!places.containsKey(u.getName())) {
			places.put(u.getName(), u);
		}
		return (PlaceModel)places.get(u.getName());
	}
	
	private DeviceModel addDevice(DeviceModel u) {
		if(!devices.containsKey(u.getNo())) {
			devices.put(u.getNo(), u);
		}
		return (DeviceModel)devices.get(u.getNo());
	}
	
	private SupplierModel addSupplier(SupplierModel supp) {
		if(!suppliers.containsKey(supp.getName())) {
			suppliers.put(supp.getName(), supp);
		}
		return (SupplierModel)suppliers.get(supp.getName());
	}
	
	private MachineModel addMachine(MachineModel u) {
		if(!machines.containsKey(u.getPn())) {
			machines.put(u.getPn(), u);
		}
		return (MachineModel)machines.get(u.getPn());
	}
}
