package com.zworks.pdsys.common.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.springframework.web.multipart.MultipartFile;

public class UploadFileUtils {
    /**
     * 将上传的图片保存到工程目录
     * @param file 文件
     * @return 
     */
    public static boolean uploadImage(MultipartFile file, String filePath) {
        try {
            if(file == null) {
            	return false;
            }
            
            File uploadedFile = new File(filePath);

            OutputStream os = new FileOutputStream(uploadedFile);
            InputStream is = file.getInputStream();
            byte buf[] = new byte[1024];
            int length = 0;
            while( (length = is.read(buf)) > 0 ) {
                os.write(buf, 0, length);
            }
            
            //关闭流
            os.flush();
            is.close();
            os.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}