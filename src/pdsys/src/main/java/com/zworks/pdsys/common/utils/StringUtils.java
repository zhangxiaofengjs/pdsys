package com.zworks.pdsys.common.utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author zhangxiaofengjs@163.com
 * @date 2018/4/4
 */
public class StringUtils {
	public static List<Integer> split(String str) {
		List<Integer> list = new ArrayList<Integer>();
		
		String[] strs = str.split(",");
		for(String s : strs) {
			try {
				list.add(Integer.valueOf(s));
			} catch (NumberFormatException e) {
			    e.printStackTrace();
			    return null;
			}
		}
		return list;
    }
}
