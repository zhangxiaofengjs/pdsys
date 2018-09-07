package com.zworks.pdsys.common.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.stereotype.Component;

import com.zworks.pdsys.common.annotations.PdSysSessionDefaultValue;
import com.zworks.pdsys.common.annotations.PdSysSessionValue;
import com.zworks.pdsys.common.annotations.PdSysSessionValues;
import com.zworks.pdsys.common.utils.RequestContextUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author zhangxiaofengjs@163.com
 * @version 2018-09-07
 */
@Aspect
@Component
public class PdSysSessionValueAspect {
	@Pointcut("@annotation(com.zworks.pdsys.common.annotations.PdSysSessionValue)")
	public void pdSysSessionValuePointCut() {
	}

	@Pointcut("@annotation(com.zworks.pdsys.common.annotations.PdSysSessionValues)")
	public void pdSysSessionValuesPointCut() {
	}
	
	@Around("pdSysSessionValuePointCut()")
	public Object advicePdSysSessionValuePointCutPointCut(ProceedingJoinPoint joinPoint) throws Throwable {
		return resetSessionCore(joinPoint);
	}
	
	@Around("pdSysSessionValuesPointCut()")
	public Object advicePdSysSessionValuesPointCutPointCut(ProceedingJoinPoint joinPoint) throws Throwable {
		return resetSessionCore(joinPoint);
	}
	
	public Object resetSessionCore(ProceedingJoinPoint joinPoint) throws Throwable {
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		Method method = signature.getMethod();

		Object obj = joinPoint.getTarget();
		Object[] args = joinPoint.getArgs();
		
		PdSysSessionValue[] sessionValues = null;
		PdSysSessionValue svano = method.getAnnotation(PdSysSessionValue.class);
		if (svano != null) {
			sessionValues = new PdSysSessionValue[] { svano };
		}
		PdSysSessionValues svanos = method.getAnnotation(PdSysSessionValues.class);
		if (svanos != null) {
			sessionValues = svanos.value();
		}
		
		String[] parameterNames = null;
		try {
			ParameterNameDiscoverer parameterNameDiscoverer = 
					new LocalVariableTableParameterNameDiscoverer();
			parameterNames = parameterNameDiscoverer.getParameterNames(method);
		} catch (SecurityException e) {
			e.printStackTrace();
			throw e;
		}
		
		Class<?>[] parameterTypes = method.getParameterTypes();

		for(int i = 0; i < sessionValues.length; i++) {
			String parameterName = parameterNames[i];
			Object arg = args[i];
			
			for(PdSysSessionValue sv : sessionValues) {
				String paramName = sv.name();
				if(!paramName.equals(parameterName)) {
					continue;
				}

				//从Session里取那个值
				Object sessionArg = RequestContextUtils.getSessionAttribute(obj, sv.name(), null);
				if(sessionArg == null) {
					//如果session里也不存在，则取default值
					sessionArg = getDefaultValue(sv.defaultValue(), parameterTypes[i]);
				}

				//检测该参数设定默认值
				if(arg == null) {
					//该值未设定，取Session值
					arg = sessionArg;
				} else {
					//session值给未设定成员赋默认值
					setFieldsSessionDefaultValue(arg, sessionArg);
				}
				
				//替换掉Session参数
				args[i] = arg;

				//将该值保存到Session
				RequestContextUtils.setSessionAttribute(obj, sv.name(), arg);
				break;
			}
		}
		
		return method.invoke(obj, args);
	}

	private Object getDefaultValue(String defaultValue, Class<?> parameterType) throws InstantiationException, IllegalAccessException {
		if(PdSysSessionValue.VALUE_INSTANCE.equals(defaultValue)) {
			Object obj = parameterType.newInstance();
			setFieldsSessionDefaultValue(obj, null);
			return obj;
		}
		return defaultValue;
	}
	
	private void setFieldsSessionDefaultValue(Object arg, Object defaultArg) throws IllegalArgumentException, IllegalAccessException, InstantiationException {
		if(arg.getClass().isPrimitive()) {
			//基本数据类型无需check
			return;
		}

		Field[] fields = arg.getClass().getDeclaredFields();
		for(Field field : fields) {
			field.setAccessible(true);
			
			Object val = field.get(arg);
			if(val == null) {
				//如果未设定，先设定defaultArg的值
				if(defaultArg != null) {
					val = field.get(defaultArg);
				}
				if(val == null) {
					PdSysSessionDefaultValue seDefVal = field.getAnnotation(PdSysSessionDefaultValue.class);
					if(seDefVal != null) {
						//未设定注解 无视
						val = getDefaultValue(seDefVal.value(), field.getType());
					}
				}
				field.set(arg, val);
			}
		}
	}
}
