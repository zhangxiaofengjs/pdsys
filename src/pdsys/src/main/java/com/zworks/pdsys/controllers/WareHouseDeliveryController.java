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

import com.zworks.pdsys.business.WareHouseDeliveryBusiness;
import com.zworks.pdsys.common.enumClass.DeliveryState;
import com.zworks.pdsys.common.enumClass.DeliveryType;
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
	@Autowired
	WareHouseDeliveryBusiness wareHouseDeliveryBusiness;
	
	@RequestMapping(value= {"/main", "/main/{type}"})
    public String main(
    		@PathVariable(name="type" ,required=false)String type,
    		@RequestParam(name="no", required=false)String no,
    		@RequestParam(name="state", required=false)Integer state,
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
		
		if(state == null) {
			state = RequestContextUtils.getSessionAttribute(this, "state" + type, 0);
		}
		RequestContextUtils.setSessionAttribute(this, "state" + type, state);
		
		if(content == null) {
			content = RequestContextUtils.getSessionAttribute(this, "content" + content, "list");
		}
		RequestContextUtils.setSessionAttribute(this, "content" + content, content);
		
		List<?> list = null;
		WareHouseDeliveryModel delivery = new WareHouseDeliveryModel();
		delivery.getFilterCond().put("fuzzyNoSearch", content.equals("list"));
		delivery.setNo(no);
		delivery.setState(state);
		
		if(content.equals("list")) {
			if(type.equals("pn")) {
				delivery.setType(DeliveryType.PN.ordinal());
				list = wareHouseDeliveryService.queryList(delivery);
			} else if(type.equals("semipn")) {
				delivery.setType(DeliveryType.SEMIPN.ordinal());
				list = wareHouseDeliveryService.queryList(delivery);
			} else if(type.equals("bom")) {
				delivery.setType(DeliveryType.BOM.ordinal());
				list = wareHouseDeliveryService.queryList(delivery);
			} else if(type.equals("machinepart")) {
				delivery.setType(DeliveryType.MACHINEPART.ordinal());
				list = wareHouseDeliveryService.queryList(delivery);
			} else {
				list = new ArrayList<WareHouseDeliveryModel>();
			}
		} else {
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
		wareHouseDeliveryBusiness.addDeliveryPn(deliveryPn);
		return JSONResponse.success();
    }
	@RequestMapping(value="/add/semipn")
	@ResponseBody
	public JSONResponse addDeliverySemiPn(@RequestBody WareHouseDeliverySemiPnModel deliveryPn, Model model) {
		wareHouseDeliveryBusiness.addDeliverySemiPn(deliveryPn);
		return JSONResponse.success();
	}
	/**
	 * 新建出库明细
	 * */
	@RequestMapping(value="/add/bom")
	@ResponseBody
	public JSONResponse addDeliveryBOM(@RequestBody WareHouseDeliveryBOMModel deliveryBOM, Model model) {
		wareHouseDeliveryBusiness.addDeliveryBOM(deliveryBOM);
		return JSONResponse.success();
	}
	/**
	 * 新建出库明细
	 * */
	@RequestMapping(value="/add/machinepart")
	@ResponseBody
	public JSONResponse addDeliveryMachinePart(@RequestBody WareHouseDeliveryMachinePartModel deliveryMachinePart, Model model) {
		wareHouseDeliveryBusiness.addDeliveryMachinePart(deliveryMachinePart);
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
		if(!SecurityContextUtils.isLoginUser(delivery.getUser())) {
			return JSONResponse.error("当前用户不是提交者");
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
			return JSONResponse.error("当前用户不是提交者");
		}
		
		try {
			if(delivery.getType() == DeliveryType.BOM.ordinal()) {
				wareHouseDeliveryService.deliveryBOM(delivery);
			}
			else if(delivery.getType() == DeliveryType.MACHINEPART.ordinal()) {
				wareHouseDeliveryBusiness.deliveryMachinePart(delivery);
			} else {
				wareHouseDeliveryService.delivery(delivery);
			}
			return JSONResponse.success();
		} catch(PdsysException ex) {
			return JSONResponse.error(ex.getMessage());
		}
    }
}
