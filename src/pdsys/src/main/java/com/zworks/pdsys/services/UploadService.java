package com.zworks.pdsys.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.zworks.pdsys.common.utils.UploadFileUtils;
import com.zworks.pdsys.config.UploadConfig;
import com.zworks.pdsys.models.ImageModel;

@Service
public class UploadService {
	@Autowired
    private ImageService imageService;

	@Autowired
    private UploadConfig uploadConfig;

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
        	
        	if(!UploadFileUtils.uploadImage(mpFile, filePath)) {
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
}
