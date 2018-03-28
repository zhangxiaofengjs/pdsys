package com.hyron.modules.pm.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hyron.common.annotation.SysLog;
import com.hyron.common.utils.PageUtils;
import com.hyron.common.utils.Query;
import com.hyron.common.utils.R;
import com.hyron.modules.pm.entity.OrderEntity;
import com.hyron.modules.pm.service.OrderService;

/**
 * 订单
 * 
 * @author ZHAI
 * @date 2018年03月28日 下午2:40:10
 */
@RestController
@RequestMapping("/pm/order")
public class OrderController {

	@Autowired
	private OrderService orderService;

	/**
	 * 所有订单
	 */
	@RequestMapping("/list")
	public R list(@RequestParam Map<String, Object> params) {
		//查询列表数据
		Query query = new Query(params);
		List<OrderEntity> orderList = orderService.queryList(query);
		int total = orderService.queryTotal(query);
		//分页
		PageUtils pageUtil = new PageUtils(orderList, total, query.getLimit(), query.getPage());
		return R.ok().put("page", pageUtil);
	}

	/**
	 * 删除订单
	 */
	@SysLog("删除订单")
	@RequestMapping("/delete")
	public R delete(@RequestBody Long[] orderIds) {
		orderService.deleteBatch(orderIds);
		return R.ok();
	}
}
