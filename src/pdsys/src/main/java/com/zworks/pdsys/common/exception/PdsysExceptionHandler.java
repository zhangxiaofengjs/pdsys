package com.zworks.pdsys.common.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.zworks.pdsys.common.utils.JSONResponse;


/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2018/04/05
 */
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
	public String handleException(Exception e, Model model) {
		logger.error(e.getMessage(), e);
		
		JSONResponse res = JSONResponse.error(e.getMessage()).put("code", 0);
		model.addAttribute("error", res);
		
		return "/common/exception";
	}
	
	@ExceptionHandler(Throwable.class)
    //@ResponseStatus(500)
    public String exception(final Throwable throwable, final Model model) {
        logger.error("Exception during execution of SpringSecurity application", throwable);
        String errorMessage = (throwable != null ? throwable.getMessage() : "Unknown error");
        JSONResponse res = JSONResponse.error(errorMessage).put("code", 0);
		model.addAttribute("error", res);
		return "/common/exception";
    }
}
