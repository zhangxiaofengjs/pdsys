package com.zworks.pdsys.config;

import org.springframework.stereotype.Component;

import com.zworks.pdsys.common.utils.IOUtils;
import com.zworks.pdsys.common.utils.PdSysApplicationContextHolder;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2018/10/10
 */
@Component
public class PdSysApplicationConfig {

	public static String getLoggerFolder() {
		String path = PdSysApplicationContextHolder.applicationContext.getEnvironment().getProperty("logging.file");
		return IOUtils.folderPath(path) + "\\";
	}
}