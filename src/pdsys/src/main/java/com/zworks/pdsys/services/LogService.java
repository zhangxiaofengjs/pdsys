package com.zworks.pdsys.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zworks.pdsys.models.LogModel;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2018/05/07
 */
@Service
public class LogService {
	protected static final Logger logger = LoggerFactory.getLogger(LogService.class);
	
	public void save(LogModel l) {
		ObjectMapper mapper = new ObjectMapper(); 
		try {
			logger.info(mapper.writeValueAsString(l));
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
