package com.zworks.pdsys.controllers;

import static org.assertj.core.api.Assertions.useDefaultDateFormatsOnly;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zworks.pdsys.common.exception.PdsysException;
import com.zworks.pdsys.common.exception.PdsysExceptionCode;
import com.zworks.pdsys.common.utils.JSONResponse;
import com.zworks.pdsys.common.utils.StringUtils;
import com.zworks.pdsys.form.beans.WareHouseAddDeliveryObjFormBean;
import com.zworks.pdsys.form.beans.WareHouseEntryFormBean;
import com.zworks.pdsys.form.beans.WareHouseListFormBean;
import com.zworks.pdsys.models.DeviceModel;
import com.zworks.pdsys.models.MachineModel;
import com.zworks.pdsys.models.UserModel;
import com.zworks.pdsys.models.WareHouseBOMModel;
import com.zworks.pdsys.models.WareHouseDeliveryBOMModel;
import com.zworks.pdsys.models.WareHouseDeliveryModel;
import com.zworks.pdsys.models.WareHouseEntryModel;
import com.zworks.pdsys.models.WareHouseEntryPnModel;
import com.zworks.pdsys.models.WareHouseMachinePartModel;
import com.zworks.pdsys.models.WareHousePnModel;
import com.zworks.pdsys.services.DeviceService;
import com.zworks.pdsys.services.MachineService;
import com.zworks.pdsys.services.WareHouseBOMService;
import com.zworks.pdsys.services.WareHouseDeliveryBOMService;
import com.zworks.pdsys.services.WareHouseDeliveryMachinePartService;
import com.zworks.pdsys.services.WareHouseDeliveryPnService;
import com.zworks.pdsys.services.WareHouseDeliveryService;
import com.zworks.pdsys.services.WareHouseEntryPnService;
import com.zworks.pdsys.services.WareHouseEntryService;
import com.zworks.pdsys.services.WareHouseMachinePartService;
import com.zworks.pdsys.services.WareHousePnService;

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
    public String list(Model model) {
		DeviceModel device = new DeviceModel();
		List<DeviceModel> list = deviceService.queryList(device);
		
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
}
