package com.zworks.pdsys.common.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2018/08/19
 */
@Target(value={ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SessionAttributeValue {
	String type() default "";
	boolean init() default false;
	int daysOffset() default 0;//和当天的差
}