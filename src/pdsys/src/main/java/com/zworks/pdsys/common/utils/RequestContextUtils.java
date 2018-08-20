package com.zworks.pdsys.common.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * @author zhangxiaofengjs@163.com
 * @date 2018/5/7
 */
public class RequestContextUtils {
	private static final String SESSION_ATTRIBUTE_PREFIX = "pdsys.";
	
	public static HttpServletRequest getRequest() {
		ServletRequestAttributes requestAttrs = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
		return requestAttrs.getRequest();
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T getSessionAttribute(Object obj, String key, T defaultValue) {
		HttpServletRequest request = getRequest();
		HttpSession session = request.getSession();
		
		T v = (T)session.getAttribute(SESSION_ATTRIBUTE_PREFIX + obj.getClass().getName()+key);
		if(v == null) {
			v = defaultValue;
		}
		return v;
	}
	
	public static void setSessionAttribute(Object obj, String key, Object value) {
		HttpServletRequest request = getRequest();
		HttpSession session = request.getSession();
		
		session.setAttribute(SESSION_ATTRIBUTE_PREFIX + obj.getClass().getName()+key, value);
	}

	public static String getIpAddr() {
		HttpServletRequest request = getRequest();
		// 获取请求主机IP地址,如果通过代理进来，则透过防火墙获取真实IP地址  
		  
        String ip = request.getHeader("X-Forwarded-For");  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
                ip = request.getHeader("Proxy-Client-IP");  
            }  
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
                ip = request.getHeader("WL-Proxy-Client-IP");  
            }  
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
                ip = request.getHeader("HTTP_CLIENT_IP");  
            }  
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
                ip = request.getHeader("HTTP_X_FORWARDED_FOR");  
            }  
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
                ip = request.getRemoteAddr();  
            }  
        } else if (ip.length() > 15) {  
            String[] ips = ip.split(",");  
            for (int index = 0; index < ips.length; index++) {  
                String strIp = (String) ips[index];  
                if (!("unknown".equalsIgnoreCase(strIp))) {  
                    ip = strIp;  
                    break;  
                }  
            }  
        }  
        return ip;  
	}

	public static String getRequestUrl() {
		HttpServletRequest request = getRequest();
		if(request != null) {
			return request.getRequestURI();
		}
		return null;
	}
}
