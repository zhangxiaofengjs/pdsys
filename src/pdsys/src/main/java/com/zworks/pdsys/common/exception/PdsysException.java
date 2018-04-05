package com.zworks.pdsys.common.exception;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2018/04/05
 */
public class PdsysException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	private String msg;
	private int code = 500;

	public PdsysException(String msg) {
		super(msg);
		this.msg = msg;
	}

	public PdsysException(int code) {
		this.code = code;
	}
	
	public PdsysException(String msg, Throwable e) {
		super(msg, e);
		this.msg = msg;
	}

	public PdsysException(String msg, int code) {
		super(msg);
		this.msg = msg;
		this.code = code;
	}

	public PdsysException(String msg, int code, Throwable e) {
		super(msg, e);
		this.msg = msg;
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
}
