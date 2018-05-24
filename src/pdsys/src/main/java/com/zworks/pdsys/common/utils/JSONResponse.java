package com.zworks.pdsys.common.utils;

import java.util.HashMap;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2018/03/31
 */
public class JSONResponse extends HashMap<String, Object> {
	private static final long serialVersionUID = 1L;
	
	public JSONResponse() {
		put("success", true);
		put("msg", "success");
	}
	
	public static JSONResponse success(String msg) {
		JSONResponse r = new JSONResponse();
		r.put("success", true);
		r.put("msg", msg);
		return r;
	}
	
	public static JSONResponse success() {
		JSONResponse r = new JSONResponse();
		r.put("success", true);
		return r;
	}
	
	public static JSONResponse error(String msg) {
		JSONResponse r = new JSONResponse();
		r.put("success", false);
		r.put("msg", msg);
		return r;
	}

	public static JSONResponse error() {
		JSONResponse r = new JSONResponse();
		r.put("success", false);
		return r;
	}
	
	public JSONResponse put(String key, Object value) {
		super.put(key, value);
		return this;
	}

	public boolean isSuccess() {
		return (boolean) get("success");
	}
}
