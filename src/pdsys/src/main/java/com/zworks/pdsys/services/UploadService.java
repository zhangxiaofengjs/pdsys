package com.zworks.pdsys.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.zworks.pdsys.common.utils.DateUtils;
import com.zworks.pdsys.common.utils.IOUtils;
import com.zworks.pdsys.config.UploadConfig;
import com.zworks.pdsys.models.ImageModel;

@Service
public class UploadService {
	/** 时间格式(yyyy-MM-dd HH:mm:ss) */
	public final static String DATE_TIME_PATTERN = "yyyyMMddHHmmssSSS";
	
	@Autowired
    private ImageService imageService;

	@Autowired
    private UploadConfig uploadConfig;

	public String saveTemp(MultipartFile mpFile) {
		String fileName = DateUtils.format(DateUtils.now(), DATE_TIME_PATTERN) + mpFile.getOriginalFilename();
    	String filePath = uploadConfig.getLocation() + uploadConfig.getTempFolder() + fileName;

    	if(!IOUtils.save(mpFile, filePath)) {
    		return null;
    	}
    	
    	return filePath;
	}
	public boolean uploadImage(MultipartFile[] files) {
        if(files == null || files.length == 0) {
        	return true;
        }
        
        for(MultipartFile mpFile : files) {
            if(mpFile.isEmpty()) {
            	continue;
            }
            
        	String fileName = mpFile.getOriginalFilename();
        	String filePath = uploadConfig.getLocation() + uploadConfig.getImageFolder() + fileName;
        	
        	if(!IOUtils.save(mpFile, filePath)) {
        		return false;
        	}

        	ImageModel image = new ImageModel();
    		image.setUrl("/" + uploadConfig.getImageFolder() + fileName);
    		image.setName(fileName);
    		//图片信息DB保存
    		imageService.add(image);
        }
		return true;
	}
	
	public void delete(String path) {
		IOUtils.delete(path);
	}
}
