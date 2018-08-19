package com.zworks.pdsys.common.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import com.zworks.pdsys.common.annotations.NormalizeDate;
import com.zworks.pdsys.common.utils.DateUtils;
import com.zworks.pdsys.common.utils.RequestContextUtils;

import java.lang.reflect.Method;
import java.util.Date;

/**
 * @author zhangxiaofengjs@163.com
 * @version 2018-08-19
 */
@Aspect
@Component
public class SessionAttributeValueAspect {
	@Pointcut("@annotation(com.zworks.pdsys.common.annotations.SessionAttributeValue)")
	public void sessionAttributeValuePointCut() {
	}

	@Around("sessionAttributeValuePointCut()")
	public Object adviceSessionAttributeValuePointCut(ProceedingJoinPoint joinPoint) throws Throwable {
		Object result = joinPoint.proceed();

//		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
//		Method method = signature.getMethod();
//		String methodName = method.getName();
//		if(methodName.startsWith("set")) {
//			//RequestContextUtils.setSessionAttribute(this, methodName.substring(3) + type, s);
//		}
//		NormalizeDate anno = method.getAnnotation(NormalizeDate.class);
//		if (anno != null) {
//			if(anno.start()) {
//				result = DateUtils.startOfDay((Date)result);
//			}
//			if(anno.end()) {
//				result = DateUtils.endOfDay((Date)result);
//			}
//		}
		
		return result;
	}
}
