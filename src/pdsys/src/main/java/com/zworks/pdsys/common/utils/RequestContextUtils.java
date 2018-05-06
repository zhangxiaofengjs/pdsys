package com.zworks.pdsys.common.utils;

import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class RequestContextUtils {
	@SuppressWarnings("unchecked")
	public static <T> T getSessionAttribute(Object obj, String key, T defaultValue) {
		ServletRequestAttributes requestAttrs = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
		HttpSession session = requestAttrs.getRequest().getSession();
		
		T v = (T)session.getAttribute(obj.getClass().getName()+key);
		if(v == null) {
			v = defaultValue;
		}
		return v;
	}
	
	public static void setSessionAttribute(Object obj, String key, Object value) {
		ServletRequestAttributes requestAttrs = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
		HttpSession session = requestAttrs.getRequest().getSession();
		
		session.setAttribute(obj.getClass().getName()+key, value);
	}
}
