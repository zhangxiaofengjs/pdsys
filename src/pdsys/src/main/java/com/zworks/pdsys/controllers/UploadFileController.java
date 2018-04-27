package com.zworks.pdsys.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.zworks.pdsys.common.utils.JSONResponse;
import com.zworks.pdsys.common.utils.UploadFileUtils;
import com.zworks.pdsys.models.ImageModel;
import com.zworks.pdsys.services.ImageUploadService;

/**
 * @author: ZHAI
 * @version: 2018/04/26
 */
@Controller
@RequestMapping("/upload")
public class UploadFileController {
	@Autowired
	ImageUploadService imageUploadService;
	
	//上传
	@RequestMapping(value="/imageFile",method=RequestMethod.POST)
	@ResponseBody
	public JSONResponse addPicFile(@RequestParam("uploadfile") MultipartFile[] files){
	    try{

	        if(files!=null&& files.length>0)
	        {

	            String picUrl =""; 
	            for(int i=0;i<files.length;i++){
	                if(!files[i].isEmpty())
	                {
	                	String fileName = files[i].getOriginalFilename();
	                	picUrl = UploadFileUtils.uploadImage(files[i]);
	                	//上传成功
	                	if( picUrl!= null && picUrl.length()>0 )
	                	{
		                	ImageModel imageModel = new ImageModel();
	                		imageModel.setImageUrl(picUrl);
	                		imageModel.setImageName(fileName);
	                		//图片信息DB保存
	                		imageUploadService.add(imageModel);
	                	}
	                }
	            }
	        }
	    }
	    catch (Exception e)
	    {
	    	return JSONResponse.error("上传失败！");
	    }
	    return JSONResponse.success("上传成功！");
	}
}
