package com.zworks.pdsys.common.utils;

import java.io.File;
import org.springframework.web.multipart.MultipartFile;

public class IOUtils {
	private final static char[] INVALID_FILE_NAME_CHAR = {'\\', '/', ':', '*', '?', '\"', '<', '>', '|'};
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

    public static String safeFileName(String fileName) {
    	String str = fileName;
    	for(char ch : INVALID_FILE_NAME_CHAR) {
    		str = str.replace(ch, '-');
    	}
    	
    	return str;
    }
    
	public static boolean delete(String path) {
		 File file = new File(path);
		 return file.delete();
	}

	public static String fileName(String path) {
		File file = new File(path);
		return file.getName();
	}

	public static String appendFileName(String originalFilename, String strAppend, String seperator) {
		int index = originalFilename.lastIndexOf(".");
		String suffix = "";
		String fileName = "";
		if(index >= 0) {
			fileName = originalFilename.substring(0, index);
			suffix = originalFilename.substring(index + 1);
		} else {
			fileName = originalFilename;
		}
		
		return IOUtils.safeFileName(fileName + seperator + strAppend + (suffix.isEmpty()?"":"." + suffix));
	}
}