package com.zworks.pdsys.config;

import java.io.File;

import javax.servlet.MultipartConfigElement;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 创建tomcat的文件上传临时路径，否则会在%appdata%下面创建，然后系统一段时间会清理这个目录，导致发生错误
 * 
 * @author zhangxiaofengjs@163.com
 * 
 * @date 2018-11-08
 */
@Configuration
public class MultipartConfig {
	@Value("${pdsys.upload.location}")
    private String uploadMultipartLocation;
	
	@Value("${pdsys.upload.temp-folder}")
    private String uploadMultipartTempFolder;

	@Bean
    MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        String location = uploadMultipartLocation + uploadMultipartTempFolder;
        File tmpFile = new File(location);
        if (!tmpFile.exists()) {
            tmpFile.mkdirs();
        }
        factory.setLocation(location);
        return factory.createMultipartConfig();
    }
}
