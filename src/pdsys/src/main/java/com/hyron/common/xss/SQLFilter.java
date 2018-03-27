package com.hyron.common.xss;

import org.apache.commons.lang.StringUtils;

import com.hyron.common.exception.RRException;
import com.hyron.common.utils.CamelUtils;

/**
 * SQL过滤
 * 
 * @author Allen
 * @webSite https://www.allen-software.cn
 * @date 2017-04-01 16:16
 */
public class SQLFilter {

	/**
	 * SQL注入过滤
	 * @param str 待验证的字符串
	 */
	public static String sqlInject(String str) {
		if (StringUtils.isBlank(str)) {
			return null;
		}
		//去掉'|"|;|\字符
		str = StringUtils.replace(str, "'", "");
		str = StringUtils.replace(str, "\"", "");
		str = StringUtils.replace(str, ";", "");
		str = StringUtils.replace(str, "\\", "");
		//转换成小写
		String strLower = str.toLowerCase();
		//非法字符
		String[] keywords = { "master", "truncate", "insert", "select", "delete", "update", "declare", "alert", "drop" };
		//判断是否包含非法字符
		for (String keyword : keywords) {
			if (strLower.indexOf(keyword) != -1) {
				throw new RRException("包含非法字符");
			}
		}
		//驼峰转换
		return CamelUtils.camelToUnderline(str);
	}
}
