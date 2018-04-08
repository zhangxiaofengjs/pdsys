package com.zworks.pdsys.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zworks.pdsys.models.PlaceModel;
import com.zworks.pdsys.services.PlaceService;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2018/04/08
 */
@Controller
@RequestMapping("/place")
public class PlaceController {
	@Autowired
	PlaceService placeService;
	
	@RequestMapping(value= {"/list/json"})
	@ResponseBody
    public List<PlaceModel> list(PlaceModel place, Model model) {
		List<PlaceModel> list = placeService.queryList(place);
		
		return list;
    }
}
