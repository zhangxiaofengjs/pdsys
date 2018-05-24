package com.zworks.pdsys.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zworks.pdsys.business.beans.DeviceMaitenaceMachinePartsBean;
import com.zworks.pdsys.common.utils.JSONResponse;
import com.zworks.pdsys.models.DeviceModel;
import com.zworks.pdsys.models.DeviceRepairModel;
import com.zworks.pdsys.services.DeviceService;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2018/04/07
 */
@Controller
@RequestMapping("/device")
public class DeviceController {
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
    public String showRepairInfo(DeviceRepairModel deviceRepair, Model model) {
		List<DeviceRepairModel> list = deviceService.showRepairInfos(deviceRepair);
		
		model.addAttribute("deviceRepair", deviceRepair);
		model.addAttribute("list", list);
		return "device/repair/list";
    }
	
	@RequestMapping(value= {"/addRepair"})
	@ResponseBody
    public JSONResponse addRepair(@RequestBody DeviceRepairModel deviceRepair, Model model) {
		deviceService.addDeviceRepair(deviceRepair);
		return JSONResponse.success();
    }
	
}
