//package com.hyron.common.xss;
//
//import java.io.IOException;
//import java.util.Arrays;
//import java.util.List;
//
//import javax.servlet.Filter;
//import javax.servlet.FilterChain;
//import javax.servlet.FilterConfig;
//import javax.servlet.ServletException;
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.apache.commons.lang.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
///**
// * XSS过滤
// * 
// * @author ZHAI
// * 
// * @date 2017-04-01 10:20
// */
//public class XssFilter implements Filter {
//
//	protected Logger logger = LoggerFactory.getLogger(getClass());
//
//	private String allowOrigins;
//
//	private String allowMethods;
//
//	@Override
//	public void init(FilterConfig config) throws ServletException {
//		allowOrigins = config.getInitParameter("allowOrigins");
//		allowMethods = config.getInitParameter("allowMethods");
//	}
//
//	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
//		HttpServletResponse httpServletResponse = (HttpServletResponse) response;
//		String currentOrigin = httpServletRequest.getHeader("Origin");
//		// 跨域支持
//		if (StringUtils.isNotEmpty(allowOrigins) && StringUtils.isNotEmpty(currentOrigin)) {
//			List<String> allowOriginList = Arrays.asList(allowOrigins.split(","));
//			if (allowOriginList.size() > 0) {
//				if (allowOriginList.contains(currentOrigin)) {
//					httpServletResponse.setHeader("Access-Control-Allow-Origin", currentOrigin);
//				} else {
//					logger.warn("未授权的客户端域名：" + currentOrigin);
//				}
//			}
//		}
//		httpServletResponse.setHeader("Access-Control-Allow-Origin", allowOrigins);
//		httpServletResponse.setHeader("Access-Control-Allow-Methods", allowMethods);
//		XssHttpServletRequestWrapper xssRequest = new XssHttpServletRequestWrapper((HttpServletRequest) request);
//		chain.doFilter(xssRequest, response);
//	}
//
//	@Override
//	public void destroy() {
//	}
//}