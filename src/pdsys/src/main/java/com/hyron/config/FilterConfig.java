package com.hyron.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.DelegatingFilterProxy;

import com.hyron.common.xss.XssFilter;

import javax.servlet.DispatcherType;

/**
 * Filter配置
 * 
 * @author Allen
 * @webSite https://www.allen-software.cn
 * @date 2017-04-21 21:56
 */
@Configuration
public class FilterConfig {

	@Bean
	public FilterRegistrationBean shiroFilterRegistration() {
		FilterRegistrationBean registration = new FilterRegistrationBean();
		registration.setFilter(new DelegatingFilterProxy("shiroFilter"));
		//该值缺省为false，表示生命周期由SpringApplicationContext管理，设置为true则表示由ServletContainer管理
		registration.addInitParameter("targetFilterLifecycle", "true");
		registration.setEnabled(true);
		registration.setOrder(Integer.MAX_VALUE - 1);
		registration.addUrlPatterns("/*");
		return registration;
	}

	@Bean
	public FilterRegistrationBean xssFilterRegistration() {
		FilterRegistrationBean registration = new FilterRegistrationBean();
		registration.setDispatcherTypes(DispatcherType.REQUEST);
		registration.setFilter(new XssFilter());
		registration.addUrlPatterns("/*");
		registration.setName("xssFilter");
		registration.setOrder(Integer.MAX_VALUE);
		Map<String, String> initParameters = new HashMap<String, String>();
		initParameters.put("allowOrigins", "http://localhost:8080,https://www.allen-software.cn");
		initParameters.put("allowMethods", "GET,POST,PUT,DELETE,OPTIONS");
		registration.setInitParameters(initParameters);
		return registration;
	}
}
