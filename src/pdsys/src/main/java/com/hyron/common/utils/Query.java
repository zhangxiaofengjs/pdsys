package com.hyron.common.utils;

import org.apache.commons.lang.StringUtils;

import com.hyron.common.xss.SQLFilter;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 查询参数
 * 
 * @author Allen
 * @webSite https://www.allen-software.cn
 * @date 2018-01-22
 */
public class Query extends LinkedHashMap<String, Object> {
	private static final long serialVersionUID = 1L;
	//当前页码
	private int page;
	//每页条数
	private int limit;

	public Query(Map<String, Object> params) {
		this.putAll(params);
		//分页参数
		this.page = StringUtils.isNotBlank((String) params.get("page")) ? Integer.parseInt(params.get("page").toString()) : 1;
		this.limit = StringUtils.isNotBlank((String) params.get("limit")) ? Integer.parseInt(params.get("limit").toString()) : 999;
		this.put("offset", (page - 1) * limit);
		this.put("page", page);
		this.put("limit", limit);
		//防止SQL注入（因为sidx、order是通过拼接SQL实现排序的，会有SQL注入风险）
		String sidx = (String) params.get("sidx");
		String order = (String) params.get("order");
		if (StringUtils.isNotBlank(sidx)) {
			this.put("sidx", SQLFilter.sqlInject(sidx));
		}
		if (StringUtils.isNotBlank(order)) {
			this.put("order", SQLFilter.sqlInject(order));
		}
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}
}
