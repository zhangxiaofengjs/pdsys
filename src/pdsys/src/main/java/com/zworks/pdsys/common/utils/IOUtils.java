package com.zworks.pdsys.common.utils;

import java.io.File;
import org.springframework.web.multipart.MultipartFile;

public class IOUtils {
    /**
     * 将上传的文件保存到工程目录
     * @param file 文件
     * @return 
     */
    public static boolean save(MultipartFile file, String filePath) {
        try {
            if(file == null) {
            	return false;
            }
            
            File uploadedFile = new File(filePath);

            if(uploadedFile.exists()) {
            	//如果存在先删除
            	uploadedFile.delete();
            }
            
            File fileParent = uploadedFile.getParentFile();
            //判断是否存在
            if (!fileParent.exists()) {
            	//创建父目录文件
            	fileParent.mkdirs();
            }
            
            file.transferTo(uploadedFile);
            
//            OutputStream os = new FileOutputStream(uploadedFile);
//            InputStream is = file.getInputStream();
//            byte buf[] = new byte[1024];
//            int length = 0;
//            while( (length = is.read(buf)) > 0 ) {
//                os.write(buf, 0, length);
//            }
//            
//            //关闭流
//            os.flush();
//            is.close();
//            os.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

	public static boolean delete(String path) {
		 File file = new File(path);
		 return file.delete();
	}
}