package com.hyron.modules.api.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hyron.common.utils.PageUtils;
import com.hyron.common.utils.Query;
import com.hyron.common.utils.R;
import com.hyron.modules.api.entity.HouseBaseInfoEntity;
import com.hyron.modules.api.entity.HouseTypeEntity;
import com.hyron.modules.api.service.HouseService;

/**
 * 房屋信息接口
 * 
 * @author ZHAI
 * 
 * @date 2018-02-05 15:47
 */
@RestController
@RequestMapping("/api/house")
@Api("房屋信息接口")
public class ApiHouseController {

	@Autowired
	private HouseService houseService;

	/**
	 * 房屋类型列表
	 */
	@RequestMapping("/typeList")
	@ApiOperation("获取房屋类型列表")
	public R all() {
		List<HouseTypeEntity> houseTypeList = houseService.queryAllTypeList();
		return R.ok().put("houseTypeList", houseTypeList);
	}

	/**
	 * 列表
	 */
	@GetMapping("/list")
	@ApiOperation("获取房屋基础信息列表")
	public R list(@RequestParam Map<String, Object> params) {
		if (params == null || params.size() == 0) {
			params = new LinkedHashMap<String, Object>();
		}
		//查询列表数据
		Query query = new Query(params);
		List<HouseBaseInfoEntity> houseBaseInfoList = houseService.queryList(query);
		int total = houseService.queryTotal(query);
		PageUtils pageUtil = new PageUtils(houseBaseInfoList, total, query.getLimit(), query.getPage());
		return R.ok().put("page", pageUtil);
	}

	/**
	 * 信息
	 */
	@GetMapping("/info/{houseId}")
	@ApiOperation("获取房屋基础信息")
	public R info(@PathVariable("houseId") Long houseId) {
		HouseBaseInfoEntity houseBaseInfo = houseService.queryObject(houseId);
		return R.ok().put("houseBaseInfo", houseBaseInfo);
	}
}
