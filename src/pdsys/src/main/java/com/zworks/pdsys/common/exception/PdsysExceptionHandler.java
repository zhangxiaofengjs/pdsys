package com.zworks.pdsys.common.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import com.zworks.pdsys.common.utils.JSONResponse;


/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2018/04/05
 */
@Controller
@ControllerAdvice
public class PdsysExceptionHandler {

	private Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 处理自定义异常
	 */
	@ExceptionHandler(PdsysException.class)
	public String handlePdsysException(PdsysException e, Model model) {
		logger.error(e.getMessage(), e);
		
		JSONResponse res = JSONResponse.error(e.getMessage()).put("code", e.getCode());
		model.addAttribute("error", res);
		
		return "/common/exception";
	}

//	@ExceptionHandler(DuplicateKeyException.class)
//	public R handleDuplicateKeyException(DuplicateKeyException e) {
//		logger.error(e.getMessage(), e);
//		return R.error("数据库中已存在该记录");
//	}

	@ExceptionHandler(Exception.class)
	public JSONResponse handleException(Exception e) {
		logger.error(e.getMessage(), e);
		
		return JSONResponse.error(e.getMessage());
	}
}
