package com.zworks.pdsys.controllers;

import java.io.File;
import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zworks.pdsys.common.utils.JSONResponse;
import com.zworks.pdsys.tools.ImportBOMTool;
import com.zworks.pdsys.tools.ImportPnDefTool;
import com.zworks.pdsys.tools.ImportPnTool;
/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2018/05/29
 */
@Controller
@RequestMapping("/tools")
public class ToolsController {
	@Autowired
	ImportPnDefTool importPnDefTool;
	@Autowired
	ImportBOMTool importBOMTool;
	@Autowired
	ImportPnTool importPnTool;
	
	@RequestMapping("/importpndef")
	@ResponseBody
	public JSONResponse importPnDef(Model model) throws InvalidFormatException, IOException {
		String path = "c:/pdsys/pndef.xlsx";
		File file = new File(path);
		if(!file.exists()) {
			return JSONResponse.error().put("msg", "将导入文件放在服务器路径：c:/pdsys/pndef.xlsx");
		}
		importPnDefTool.execute(path);
		return JSONResponse.success().put("msg", "成功导入");
	}
	
	@RequestMapping("/importbom")
	@ResponseBody
	public JSONResponse importBOMTool(Model model) throws InvalidFormatException, IOException {
		String path = "c:/pdsys/bom.xlsx";
		File file = new File(path);
		if(!file.exists()) {
			return JSONResponse.error().put("msg", "将导入文件放在服务器路径：c:/pdsys/bom.xlsx");
		}
		importBOMTool.execute(path);
		return JSONResponse.success().put("msg", "成功导入");
	}
	
	@RequestMapping("/importpn")
	@ResponseBody
	public JSONResponse ImportPnTool(Model model) throws InvalidFormatException, IOException {
		String path = "c:/pdsys/pn.xlsx";
		File file = new File(path);
		if(!file.exists()) {
			return JSONResponse.error().put("msg", "将导入文件放在服务器路径：c:/pdsys/pn.xlsx");
		}
		importPnTool.execute(path);
		return JSONResponse.success().put("msg", "成功导入");
	}
	
}
