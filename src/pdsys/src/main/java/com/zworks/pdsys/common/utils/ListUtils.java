package com.zworks.pdsys.common.utils;

import java.util.List;

public class ListUtils {
	public static boolean isNullOrEmpty(List<?> list) {
		if(list == null || list.isEmpty()) {
			return true;
		} else {
			return false;
		}
	}
}
