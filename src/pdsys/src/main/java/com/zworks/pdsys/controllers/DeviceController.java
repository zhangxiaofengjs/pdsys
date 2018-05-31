package com.zworks.pdsys.controllers;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zworks.pdsys.business.beans.DeviceMaitenaceMachinePartsBean;
import com.zworks.pdsys.business.beans.DeviceRepairFormBean;
import com.zworks.pdsys.common.utils.DateUtils;
import com.zworks.pdsys.common.utils.JSONResponse;
import com.zworks.pdsys.common.utils.RequestContextUtils;
import com.zworks.pdsys.models.DeviceModel;
import com.zworks.pdsys.models.DeviceRepairModel;
import com.zworks.pdsys.services.DeviceService;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2018/04/07
 */
@Controller
@RequestMapping("/device")
public class DeviceController extends BaseController {
	@Autowired
	DeviceService deviceService;
	
	@RequestMapping(value= {"/list"})
    public String list(DeviceModel device, Model model) {
		device.getFilterCond().put("fuzzyNoSearch", true);
		List<DeviceModel> list = deviceService.queryList(device);
		
		model.addAttribute("device", device);
		model.addAttribute("list", list);
		return "device/list";
    }
	
	@RequestMapping(value= {"/update"})
	@ResponseBody
	public JSONResponse update(@RequestBody DeviceModel device, Model model) {
		deviceService.update(device);
		return JSONResponse.success();
	}
	
	@RequestMapping(value= {"/add"})
	@ResponseBody
	public JSONResponse add(@RequestBody DeviceModel device, Model model) {
		deviceService.add(device);
		return JSONResponse.success();
	}
	
	@RequestMapping(value= {"/machineparts"})
	@ResponseBody
	public JSONResponse machineParts(@RequestBody List<Integer> deviceIds, Model model) {
		List<DeviceMaitenaceMachinePartsBean> result = deviceService.getMaitenaceParts(deviceIds);
		return JSONResponse.success().put("data", result);
	}
	
	@RequestMapping(value= {"/repair"})
    public String showRepairInfo(@RequestParam(name="radioState", required=false)String radioState,
    							 DeviceRepairFormBean deviceRepairFormBean, Model model)
	{
		
		DeviceRepairModel deviceRepair = new DeviceRepairModel();
		Date s = DateUtils.startOfDay(deviceRepairFormBean.getStart());
		Date e = DateUtils.endOfDay(deviceRepairFormBean.getEnd());
		if(s == null) {
			s = RequestContextUtils.getSessionAttribute(this, "startDeviceRepair", DateUtils.startOfDay(DateUtils.thisMonthStart()));
			deviceRepairFormBean.setStart(s);
		}
		RequestContextUtils.setSessionAttribute(this, "startDeviceRepair", s);
		if(e == null) {
			e = RequestContextUtils.getSessionAttribute(this, "endDeviceRepair", DateUtils.endOfDay(DateUtils.now()));
			deviceRepairFormBean.setEnd(e);
		}
		RequestContextUtils.setSessionAttribute(this, "endDeviceRepair", e);
		
		deviceRepair.getFilterCond().put("start", s);
		deviceRepair.getFilterCond().put("end", e);
		
		//默认同故障统计
		if(radioState == null) {
			radioState = RequestContextUtils.getSessionAttribute(this, "radioState", "0");
		}
		RequestContextUtils.setSessionAttribute(this, "radioState", radioState);
		
		//同故障统计
		deviceRepair.getFilterCond().put("radioState", radioState);
		List<DeviceRepairModel> list1 = deviceService.showRepairInfos(deviceRepair);
		
		model.addAttribute("deviceRepair", deviceRepair);
		model.addAttribute("formBean",deviceRepairFormBean);
		model.addAttribute("list1", list1);
		
		//同机器统计
		deviceRepair.getFilterCond().put("radioState", "1");
		List<DeviceRepairModel> list2 = deviceService.showRepairInfos(deviceRepair);
		model.addAttribute("list2", list2);
		
		return "device/repair/list";
    }
	
	@RequestMapping(value= {"/addRepair"})
	@ResponseBody
    public JSONResponse addRepair(@RequestBody DeviceRepairModel deviceRepair, Model model) {
		deviceService.addDeviceRepair(deviceRepair);
		return JSONResponse.success();
    }
	
}
