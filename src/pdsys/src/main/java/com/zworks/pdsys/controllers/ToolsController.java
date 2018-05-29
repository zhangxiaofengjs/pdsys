package com.zworks.pdsys.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zworks.pdsys.common.utils.JSONResponse;
import com.zworks.pdsys.tools.ImportPnDefTool;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2018/05/29
 */
@Controller
@RequestMapping("/tools")
public class ToolsController {
	@Autowired
	ImportPnDefTool importPnDefTool;
	
	@RequestMapping("/importpndef")
	@ResponseBody
	public JSONResponse importPnDef(@RequestParam String path, Model model) {
		String file = "c:/pdsys/" + path;
		if(!importPnDefTool.execute(file)) {
			return JSONResponse.error().put("msg", "发生错误");
		}
		return JSONResponse.success().put("msg", "成功导入");
	}
}
