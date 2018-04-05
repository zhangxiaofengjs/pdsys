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
import com.zworks.pdsys.models.UserModel;
import com.zworks.pdsys.models.WareHouseBOMModel;
import com.zworks.pdsys.models.WareHouseDeliveryBOMModel;
import com.zworks.pdsys.models.WareHouseDeliveryModel;
import com.zworks.pdsys.models.WareHouseEntryModel;
import com.zworks.pdsys.models.WareHouseMachinePartModel;
import com.zworks.pdsys.models.WareHousePnModel;
import com.zworks.pdsys.services.WareHouseBOMService;
import com.zworks.pdsys.services.WareHouseDeliveryBOMService;
import com.zworks.pdsys.services.WareHouseDeliveryMachinePartService;
import com.zworks.pdsys.services.WareHouseDeliveryPnService;
import com.zworks.pdsys.services.WareHouseDeliveryService;
import com.zworks.pdsys.services.WareHouseEntryService;
import com.zworks.pdsys.services.WareHouseMachinePartService;
import com.zworks.pdsys.services.WareHousePnService;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2018/04/05
 */
@Controller
@RequestMapping("/warehouse/entry")
public class WareHouseEntryController {
	@Autowired
	WareHouseBOMService wareHouseBOMService;
	@Autowired
	WareHousePnService wareHousePnService;
	@Autowired
	WareHouseMachinePartService wareHouseMachinePartService;
	@Autowired
	WareHouseDeliveryBOMService wareHouseDeliveryBOMService;
	@Autowired
	WareHouseDeliveryMachinePartService wareHouseDeliveryMachinePartService;
	@Autowired
	WareHouseDeliveryPnService wareHouseDeliveryPnService;
	@Autowired
	WareHouseEntryService wareHouseEntryService;
	
	@RequestMapping("/main")
    public String entryMain(@RequestParam(name="type",required = false, defaultValue="pn")String type,
    		WareHouseEntryModel entry,
    		Model model) {

		if(type.equals("bom")) {
		}
		else if(type.equals("pn")) {
		}
		else if(type.equals("machinepart")) {
		}
		else {
			throw new PdsysException("错误参数:/entry/main?type=" + type, PdsysExceptionCode.ERROR_REQUEST_PARAM);
		}
				
		model.addAttribute("entry", entry);
		model.addAttribute("type", type);
		return "warehouse/entry/main";
    }
	
	/**
	 * 新建入库单
	 * */
	@RequestMapping(value="/add")
    public JSONResponse addEntry(Model model) {
		//wareHouseEntryService.add(wareHouseEntry);
		model.addAttribute("xxx", "xxxxx");
		WareHouseEntryModel entry = new WareHouseEntryModel();
		return JSONResponse.success().put("entry", entry);
    }
}
