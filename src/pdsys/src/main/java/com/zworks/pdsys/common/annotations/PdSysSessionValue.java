package com.zworks.pdsys.common.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2018/08/19
 */
@Target(value={ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Repeatable(value = PdSysSessionValues.class)
public @interface PdSysSessionValue {
	final String VALUE_INSTANCE = "@instance";
	String name(); //参数名
	String defaultValue(); //默认值，可引用PdSysSessionValue.VALUE_XXX
}