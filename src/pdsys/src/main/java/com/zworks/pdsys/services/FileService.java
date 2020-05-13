package com.zworks.pdsys.services;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
    	String filePath = getTempFilePath(fileName);

    	if(!IOUtils.save(mpFile, filePath)) {
    		return null;
    	}
    	
    	return filePath;
	}
	
	public String getTempFilePath(String fileName) {
		String filePath = uploadConfig.getLocation() + uploadConfig.getTempFolder() + IOUtils.fileName(fileName);
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
	
	public static byte[] readToByteArr(String filePath) throws IOException {
        File f = new File(filePath);
 
        ByteArrayOutputStream bos = new ByteArrayOutputStream((int) f.length());
        BufferedInputStream in = null;
        try {
            in = new BufferedInputStream(new FileInputStream(f));
            int buf_size = 1024;
            byte[] buffer = new byte[buf_size];
            int len = 0;
            while (-1 != (len = in.read(buffer, 0, buf_size))) {
                bos.write(buffer, 0, len);
            }
            return bos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            bos.close();
        }
    }
	
	public ResponseEntity<byte[]> download(String filePath) {
		String fileName = "";
        try {
        	File f = new File(filePath);
            fileName = new String(f.getName().getBytes("UTF-8"), "iso-8859-1");// 为了解决中文名称乱码问题
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        // 下载文件
        HttpHeaders headers = new HttpHeaders();
        headers.setContentDispositionFormData("attachment", fileName);
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

        try {
            return new ResponseEntity<byte[]>(
            		readToByteArr(filePath), headers,
                    HttpStatus.CREATED);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
	}
}
