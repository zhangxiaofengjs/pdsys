package com.zworks.pdsys.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zworks.pdsys.common.utils.JSONResponse;
import com.zworks.pdsys.form.beans.BomDetailModel;
import com.zworks.pdsys.models.BOMModel;
import com.zworks.pdsys.models.OrderModel;
import com.zworks.pdsys.models.OrderPnModel;
import com.zworks.pdsys.models.PnClsModel;
import com.zworks.pdsys.models.PnModel;
import com.zworks.pdsys.services.OrderService;
import com.zworks.pdsys.services.PnService;

@Controller
@RequestMapping("/pn")
public class PnController {
	@Autowired
	PnService pnService;
	
	@Autowired
	OrderService orderService;
	
	/**
	 * 品目一览
	 */
	@RequestMapping("/list/json")
	@ResponseBody
	public List<PnModel> listJson(PnModel pn, Model model) {
		List<PnModel> list = pnService.queryList(pn);
		return list;
	}
	
	/**
	 * 查询品目
	 * */
	@RequestMapping(value="/get")
	@ResponseBody
    public JSONResponse get(@RequestBody PnModel pn) {
		PnModel retPn = pnService.queryOne(pn);
		return JSONResponse.success().put("pn", retPn);
    }

	/**
	 * 添加品目
	 * */
	@RequestMapping(value="/add")
	@ResponseBody
    public JSONResponse add(@RequestBody PnModel pn) {
		pnService.add(pn);
		return JSONResponse.success();
    }
	
	/**
	 * 修改品目
	 * */
	@RequestMapping(value="/update")
	@ResponseBody
    public JSONResponse update(@RequestBody PnModel pn) {
		pnService.update(pn);
		return JSONResponse.success();
    }
	
	/**
	 * 添加子类
	 * */
	@RequestMapping(value="/addPnCls")
	@ResponseBody
    public JSONResponse addPnCls(@RequestBody PnModel pn) {
		if(pnService.existsPnCls(pn)) {
			return JSONResponse.error("已经存在该子类");
		}
		pnService.addPnCls(pn);
		return JSONResponse.success();
    }
	
	/**
	 * 添加原包材
	 * */
	@RequestMapping(value="/addBOM")
	@ResponseBody
	public JSONResponse addBOM(@RequestBody PnModel pn) {
		if(pnService.existsBOM(pn)) {
			//已经存在该原包材 则做更新
			pnService.updateBOM(pn);
			return JSONResponse.success();
		}
		pnService.addBOM(pn);
		return JSONResponse.success();
	}
	
	/**
	 * 当前品目下的子类一览
	 */
	@RequestMapping("/clsList/json")
	@ResponseBody
	public List<PnClsModel> pnClsListJson(@RequestBody PnModel pn) {
		List<PnClsModel> list = pnService.queryClsList(pn);
		return list;
	}
	
	/**
	 * 添加品目
	 * */
	@RequestMapping(value="/add/pn")
	@ResponseBody
    public JSONResponse save(@RequestBody OrderPnModel orderPn) {
		pnService.save(orderPn);
		return JSONResponse.success();
    }
	
	/**
	 * 删除品目
	 */
	@RequestMapping("/delete/pn")
	@ResponseBody
	public JSONResponse delete(@RequestBody List<OrderPnModel> orderPns) {
		for(OrderPnModel orderPn : orderPns) {
			pnService.delete(orderPn);
		}
		return JSONResponse.success();
	}
	
	/**
	 * BOM详细
	 */
	@RequestMapping("/bomInfo/list")
    public String list(@RequestParam(name="id") int id, Model model) {
		OrderModel order = orderService.queryObject(id);
		if( order!=null )
		{
			List<BomDetailModel> list = pnService.queryBomList(order);
			model.addAttribute("boms", list);
			model.addAttribute("order", order);
		}
		
        return "order/bomDetail";
    }
	
	/**
	 * 通过订单详细的ID取得品目
	 */
	@RequestMapping("/showPnInfo")
	@ResponseBody
	public JSONResponse queryPnByOrderPnId( OrderPnModel orderPn ){
		List<OrderPnModel> ops = pnService.queryPnByOrderPnId( orderPn );
		
		return JSONResponse.success().put("data", ops);
	}
	
	/**
	 * 通过订单详细的ID取得品目
	 */
	@RequestMapping("/showClsInfo")
	@ResponseBody
	public JSONResponse queryClsByOrderPnId( OrderPnModel orderPn ){
		List<PnClsModel> clss = pnService.queryClsByOrderPnId( orderPn );
		return JSONResponse.success().put("data", clss);
	}
	
	@RequestMapping("/updateOrderPnInfo")
	@ResponseBody
	public JSONResponse update(@RequestBody OrderPnModel orderPn, Model model) {
		orderService.updateOrderPn(orderPn);
		return JSONResponse.success();
	}

}
