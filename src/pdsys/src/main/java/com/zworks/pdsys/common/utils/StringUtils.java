package com.zworks.pdsys.common.utils;

import java.util.ArrayList;
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

	public static Float toFloat(String str) {
		try {
			return Float.valueOf(str);
		} catch (NumberFormatException e) {
		    return 0.0f;
		}
    }
	
	public static boolean isNullOrEmpty(String subName) {
		if(subName == null || subName.equals("")) {
			return true;
		} else {
			return false;
		}
	}
}
