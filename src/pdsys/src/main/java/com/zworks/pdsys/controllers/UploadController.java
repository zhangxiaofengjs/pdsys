package com.zworks.pdsys.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.zworks.pdsys.common.utils.JSONResponse;
import com.zworks.pdsys.services.UploadService;

/**
 * @author: ZHAI
 * @version: 2018/04/26
 */
@Controller
@RequestMapping("/upload")
public class UploadController {
	@Autowired
	UploadService uploadService;
	
	//上传
	@RequestMapping(value="/image",method=RequestMethod.POST)
	@ResponseBody
	public JSONResponse addImage(@RequestParam("uploadfile") MultipartFile[] files){
		if(uploadService.uploadImage(files)) {
			return JSONResponse.success("上传成功！");
		} else {
			return JSONResponse.error("上传失败！");
		}
	}
}
