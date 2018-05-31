package com.zworks.pdsys.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zworks.pdsys.common.enumClass.DeliveryState;
import com.zworks.pdsys.common.exception.PdsysException;
import com.zworks.pdsys.common.exception.PdsysExceptionCode;
import com.zworks.pdsys.common.utils.JSONResponse;
import com.zworks.pdsys.common.utils.RequestContextUtils;
import com.zworks.pdsys.common.utils.SecurityContextUtils;
import com.zworks.pdsys.models.WareHouseDeliveryBOMModel;
import com.zworks.pdsys.models.WareHouseDeliveryMachinePartModel;
import com.zworks.pdsys.models.WareHouseDeliveryModel;
import com.zworks.pdsys.models.WareHouseDeliveryPnModel;
import com.zworks.pdsys.models.WareHouseDeliverySemiPnModel;
import com.zworks.pdsys.services.WareHouseDeliveryBOMService;
import com.zworks.pdsys.services.WareHouseDeliveryMachinePartService;
import com.zworks.pdsys.services.WareHouseDeliveryPnService;
import com.zworks.pdsys.services.WareHouseDeliverySemiPnService;
import com.zworks.pdsys.services.WareHouseDeliveryService;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2018/04/06
 */
@Controller
@RequestMapping("/warehouse/delivery")
public class WareHouseDeliveryController {
	@Autowired
	WareHouseDeliveryService wareHouseDeliveryService;
	@Autowired
	WareHouseDeliveryPnService wareHouseDeliveryPnService;
	@Autowired
	WareHouseDeliverySemiPnService wareHouseDeliverySemiPnService;
	@Autowired
	WareHouseDeliveryBOMService wareHouseDeliveryBOMService;
	@Autowired
	WareHouseDeliveryMachinePartService wareHouseDeliveryMachinePartService;
	
	@RequestMapping(value= {"/main", "/main/{type}"})
    public String main(
    		@PathVariable(name="type" ,required=false)String type,
    		@RequestParam(name="no", required=false)String no,
    		@RequestParam(name="fuzzyNo", required=false)String fuzzyNo,
    		@RequestParam(name="content", required=false)String content,
    		Model model) {

		if(type == null) {
			type = RequestContextUtils.getSessionAttribute(this, "type", "pn");
		} else if(!(type.equals("bom") || type.equals("pn") || type.equals("semipn") || type.equals("machinepart"))) {
			throw new PdsysException("错误参数:/delivery/main/type=" + type, PdsysExceptionCode.ERROR_REQUEST_PARAM);
		}
		RequestContextUtils.setSessionAttribute(this, "type", type);
		
		if(no == null) {
			no = RequestContextUtils.getSessionAttribute(this, "no" + type, null);
		}
		RequestContextUtils.setSessionAttribute(this, "no" + type, no);
		
		if(content == null) {
			content = RequestContextUtils.getSessionAttribute(this, "content" + content, "list");
		}
		RequestContextUtils.setSessionAttribute(this, "content" + content, content);
		
		List<?> list = null;
		WareHouseDeliveryModel delivery = new WareHouseDeliveryModel();
		delivery.getFilterCond().put("fuzzyNoSearch", content.equals("list"));
		delivery.setNo(no);
			
		if(type.equals("pn")) {
			list = wareHouseDeliveryService.queryListWithPn(delivery);
		} else if(type.equals("semipn")) {
			list = wareHouseDeliveryService.queryListWithSemiPn(delivery);
		} else if(type.equals("bom")) {
			list = wareHouseDeliveryService.queryListWithBOM(delivery);
		} else if(type.equals("machinepart")) {
			list = wareHouseDeliveryService.queryListWithMachinePart(delivery);
		} else {
			list = new ArrayList<WareHouseDeliveryModel>();
		}
		
		if(!content.equals("list")&& list.size()>0) {
			delivery = (WareHouseDeliveryModel)list.get(0);
		}
		
		model.addAttribute("delivery", delivery);
		model.addAttribute("deliveries", list);
		model.addAttribute("type", type);
		model.addAttribute("content", content);
		return "warehouse/delivery/main";
    }
	
	/**
	 * 新建出库单
	 * */
	@RequestMapping(value="/add/delivery")
	@ResponseBody
    public JSONResponse addDelivery(@RequestBody WareHouseDeliveryModel delivery, Model model) {
		WareHouseDeliveryModel d = new WareHouseDeliveryModel();
		d.setNo(delivery.getNo());
		if(wareHouseDeliveryService.exists(d)) {
			return JSONResponse.error("已经存在单号:" + delivery.getNo());
		}
		
		d = new WareHouseDeliveryModel();
		d.setType(delivery.getType());
		d.setUser(delivery.getUser());
		d.setState(DeliveryState.PLANNING.ordinal());
		List<WareHouseDeliveryModel> ds = wareHouseDeliveryService.queryList(d);
		if(ds.size()!=0) {
			return JSONResponse.error("当前用户[" + ds.get(0).getUser().getName() + "]存在未处理单号:" + ds.get(0).getNo());
		}
		
		wareHouseDeliveryService.add(delivery);
		return JSONResponse.success().put("delivery", delivery);
    }
	
	/**
	 * 新建出库明细
	 * */
	@RequestMapping(value="/add/pn")
	@ResponseBody
    public JSONResponse addDeliveryPn(@RequestBody WareHouseDeliveryPnModel deliveryPn, Model model) {
		if(wareHouseDeliveryPnService.exists(deliveryPn)) {
			//已经存在，做更新
			wareHouseDeliveryPnService.update(deliveryPn);
		} else {
			wareHouseDeliveryPnService.add(deliveryPn);
		}
		return JSONResponse.success();
    }
	@RequestMapping(value="/add/semipn")
	@ResponseBody
	public JSONResponse addDeliveryPn(@RequestBody WareHouseDeliverySemiPnModel deliveryPn, Model model) {
		if(wareHouseDeliverySemiPnService.exists(deliveryPn)) {
			//已经存在，做更新
			wareHouseDeliverySemiPnService.update(deliveryPn);
		} else {
			wareHouseDeliverySemiPnService.add(deliveryPn);
		}
		return JSONResponse.success();
	}
	/**
	 * 新建出库明细
	 * */
	@RequestMapping(value="/add/bom")
	@ResponseBody
	public JSONResponse addDeliveryBOM(@RequestBody WareHouseDeliveryBOMModel deliveryBOM, Model model) {
		if(wareHouseDeliveryBOMService.exists(deliveryBOM)) {
			//已经存在，做更新
			wareHouseDeliveryBOMService.update(deliveryBOM);
		} else {
			wareHouseDeliveryBOMService.add(deliveryBOM);
		}
		return JSONResponse.success();
	}
	/**
	 * 新建出库明细
	 * */
	@RequestMapping(value="/add/machinepart")
	@ResponseBody
	public JSONResponse addDeliveryMachinePart(@RequestBody WareHouseDeliveryMachinePartModel deliveryMachinePart, Model model) {
		if(wareHouseDeliveryMachinePartService.exists(deliveryMachinePart)) {
			//已经存在，做更新
			wareHouseDeliveryMachinePartService.update(deliveryMachinePart);
		} else {
			wareHouseDeliveryMachinePartService.add(deliveryMachinePart);
		}
		return JSONResponse.success();
	}
	
	/**
	 * 删除出库单
	 * */
	@RequestMapping(value="/delete/delivery/{id}")
	@ResponseBody
	public JSONResponse deleteDelivery(@PathVariable(name="id", required=false)Integer id, Model model) {
		WareHouseDeliveryModel delivery = new WareHouseDeliveryModel();
		delivery.setId(id);
		delivery = wareHouseDeliveryService.queryOne(delivery);
		if(delivery.getState() != DeliveryState.PLANNING.ordinal()) {
			return JSONResponse.error("只能删除计划中出库单");
		}
		
		wareHouseDeliveryService.delete(delivery);
		return JSONResponse.success();
	}
	
	/**
	 * 删除出库明细
	 * */
	@RequestMapping(value="/delete/pn")
	@ResponseBody
    public JSONResponse deleteDeliveryPn(@RequestBody List<WareHouseDeliveryPnModel> deliveryPns, Model model) {
		wareHouseDeliveryPnService.delete(deliveryPns);
		return JSONResponse.success();
    }
	
	@RequestMapping(value="/delete/semipn")
	@ResponseBody
    public JSONResponse deleteDeliverySemiPn(@RequestBody List<WareHouseDeliverySemiPnModel> deliveryPns, Model model) {
		wareHouseDeliverySemiPnService.delete(deliveryPns);
		return JSONResponse.success();
    }
	/**
	 * 删除出库明细
	 * */
	@RequestMapping(value="/delete/bom")
	@ResponseBody
	public JSONResponse deleteDeliveryBOM(@RequestBody List<WareHouseDeliveryBOMModel> deliveryBOMs, Model model) {
		wareHouseDeliveryBOMService.delete(deliveryBOMs);
		return JSONResponse.success();
	}
	
	/**
	 * 删除出库明细
	 * */
	@RequestMapping(value="/delete/machinepart")
	@ResponseBody
	public JSONResponse deleteDeliveryMachinePart(@RequestBody List<WareHouseDeliveryMachinePartModel> deliveryMachineParts, Model model) {
		wareHouseDeliveryMachinePartService.delete(deliveryMachineParts);
		return JSONResponse.success();
	}
	
	/**
	 * 出库
	 * */
	@RequestMapping(value="/delivery/{id}")
	@ResponseBody
    public JSONResponse delivery(@PathVariable(name="id", required=false)Integer id, Model model) {
		WareHouseDeliveryModel delivery = new WareHouseDeliveryModel();
		delivery.setId(id);
		delivery = wareHouseDeliveryService.queryOne(delivery);
		if(delivery == null) {
			return JSONResponse.error("不存在单号,请刷新页面。");
		}
		if(!SecurityContextUtils.isLoginUser(delivery.getUser())) {
			return JSONResponse.error("当前用户不是登录者");
		}
		if(wareHouseDeliveryService.delivery(delivery)) {
			return JSONResponse.success();
		} else {
			return JSONResponse.error("库存不足,请刷新页面。");
		}
    }
}
