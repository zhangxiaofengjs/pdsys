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
     * @return 完整文件路径
     */
    public static String uploadImage(MultipartFile file) {
        String rootUrl="E"+"://"+"upload/";
        //上传
        try
        {
            if(file!=null)
            {
            	// 文件原名称
                String origName=file.getOriginalFilename();
                System.out.println("上传的文件原名称:"+origName);
                //存放图片文件的路径
                String fileSrc="";
                try
                {
                    File uploadedFile = new File(rootUrl+"//"+origName);
                    System.out.println("upload==="+rootUrl);
                    OutputStream os = new FileOutputStream(uploadedFile);
                    InputStream is =file.getInputStream();
                    byte buf[] = new byte[1024];
                    int length = 0;
                    while( (length = is.read(buf)) > 0 )
                    {
                        os.write(buf, 0, length);
                    }
                    //关闭流
                    os.flush();
                    is.close();
                    os.close();
                    System.out.println("保存成功！路径："+rootUrl+"/"+origName);
                    fileSrc=rootUrl+"/"+origName;
                }catch(Exception e)
                {
                    e.printStackTrace();
                }
                return fileSrc;
            }
	        return null;
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}