package com.zworks.pdsys.services;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.zworks.pdsys.common.utils.DateUtils;
import com.zworks.pdsys.common.utils.IOUtils;
import com.zworks.pdsys.config.UploadConfig;
import com.zworks.pdsys.models.ImageModel;
import com.zworks.pdsys.models.OrderModel;

@Service
public class FileService {
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
	
	public String getOrderAttachmentPath(String attachment) {
		String filePath = uploadConfig.getLocation() + uploadConfig.getOrderAttachmentFolder() + attachment;
		return filePath;
	}
	
	public String saveOrderAttachment(OrderModel o, MultipartFile mpFile) {
		String fileName = IOUtils.appendFileName(mpFile.getOriginalFilename(), "(" + o.getNo() + ")", "");
    	String filePath = getOrderAttachmentPath(fileName);

    	if(!IOUtils.save(mpFile, filePath)) {
    		return null;
    	}
    	
    	return filePath;
	}
	
	public void deleteOrderAttachment(OrderModel o) {
		String filePath = getOrderAttachmentPath(o.getAttachment());
		IOUtils.delete(filePath);
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

	public boolean download(String filePath, HttpServletResponse response) {
		//设置文件路径
        File file = new File(filePath);
        if (!file.exists()) {
        	return false;
        }
        
        response.setContentType("application/force-download");// 设置强制下载不打开
        response.addHeader("Content-Disposition", "attachment;fileName=" + file.getName());// 设置文件名
        byte[] buffer = new byte[1024];
        FileInputStream fis = null;
        BufferedInputStream bis = null;
        try {
            fis = new FileInputStream(file);
            bis = new BufferedInputStream(fis);
            OutputStream os = response.getOutputStream();
            int i = bis.read(buffer);
            while (i != -1) {
                os.write(buffer, 0, i);
                i = bis.read(buffer);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        
        return true;
    }
}
