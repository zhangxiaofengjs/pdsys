package com.zworks.pdsys.models;

import org.apache.ibatis.type.Alias;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2018/05/02
 */
@Alias("wareHouseEntryPnModel")
public class WareHouseEntryPnModel extends WareHousePnModel{
	public static final String FCK_FUZZYPNSEARCH = "fuzzyPnSearch";
	public static final String FCK_ENTRYSTART = "entryStart";
	public static final String FCK_ENTRYEND = "entryEnd";
	public static final String FCK_GROUPBYPN = "groupByPn";

	private WareHouseEntryModel wareHouseEntry;
	private WareHousePnModel wareHousePn;//实际库存

	public WareHousePnModel getWareHousePn() {
		return wareHousePn;
	}

	public void setWareHousePn(WareHousePnModel wareHousePn) {
		this.wareHousePn = wareHousePn;
	}

	public WareHouseEntryModel getWareHouseEntry() {
		return wareHouseEntry;
	}

	public void setWareHouseEntry(WareHouseEntryModel wareHouseEntry) {
		this.wareHouseEntry = wareHouseEntry;
	}
}
