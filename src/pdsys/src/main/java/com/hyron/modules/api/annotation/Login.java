package com.hyron.modules.api.annotation;

import java.lang.annotation.*;

/**
 * app登录效验
 * 
 * @author Allen
 * @webSite https://www.allen-software.cn
 * @date 2017/9/23 14:30
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Login {
}
