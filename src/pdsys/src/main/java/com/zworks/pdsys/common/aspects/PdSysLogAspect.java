package com.zworks.pdsys.common.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zworks.pdsys.common.annotations.PdSysLog;
import com.zworks.pdsys.common.utils.RequestContextUtils;
import com.zworks.pdsys.common.utils.SecurityContextUtils;
import com.zworks.pdsys.models.LogModel;
import com.zworks.pdsys.services.LogService;
import java.lang.reflect.Method;
import java.util.Date;

/**
 * 系统日志，切面处理类
 * 
 * @author ZHAI
 * 
 * @date 2018-01-22
 */
@Aspect
@Component
public class PdSysLogAspect {
	@Autowired
	LogService logService;
	
	@Pointcut("@annotation(com.zworks.pdsys.common.annotations.PdSysLog)")
	public void logPointCut() {
	}

	//定制所有controller的访问日志
	@Pointcut("execution(* com.zworks.pdsys.controllers..*.*(..))")
    public void controllerPointcut() {
    }

	@Around("logPointCut()")
	public Object adviceAnnoLog(ProceedingJoinPoint point) throws Throwable {
		long beginTime = System.currentTimeMillis();
		//执行方法
		Object result = point.proceed();
		//执行时长(毫秒)
		long time = System.currentTimeMillis() - beginTime;
		//保存日志
		saveSysLog(point, time);
		return result;
	}

	@Around("controllerPointcut()")
	public Object adviceCtrlLog(ProceedingJoinPoint point) throws Throwable {
		long beginTime = System.currentTimeMillis();
		//执行方法
		Object result = point.proceed();
		//执行时长(毫秒)
		long time = System.currentTimeMillis() - beginTime;
		//保存日志
		saveSysLog(point, time);
		return result;
	}
	
	private void saveSysLog(ProceedingJoinPoint joinPoint, long time) {
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		Method method = signature.getMethod();
		LogModel log = new LogModel();
		PdSysLog syslog = method.getAnnotation(PdSysLog.class);
		if (syslog != null) {
			//注解上的描述
			log.setDescription(syslog.value());
		}
		//请求的方法名
		String className = joinPoint.getTarget().getClass().getName();
		String methodName = signature.getName();
		log.setMethod(className + "." + methodName + "()");
		//请求的参数
		Object[] args = joinPoint.getArgs();
		try {
			ObjectMapper mapper = new ObjectMapper(); 
			String params = mapper.writeValueAsString(args); 
			log.setArgs(params);
		} catch (Exception e) {
		}
		log.setUrl(RequestContextUtils.getRequestUrl());
		log.setIp(RequestContextUtils.getIpAddr());
		log.setUser(SecurityContextUtils.getLoginUser());
		log.setElapseTime(time);
		log.setTime(new Date());
		logService.save(log);
	}
}
