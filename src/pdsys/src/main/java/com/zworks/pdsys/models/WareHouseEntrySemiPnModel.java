package com.zworks.pdsys.models;

import org.apache.ibatis.type.Alias;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2018/05/23
 */
@Alias("wareHouseEntrySemiPnModel")
public class WareHouseEntrySemiPnModel extends WareHouseSemiPnModel{
	private WareHouseEntryModel wareHouseEntry;
	private WareHouseSemiPnModel wareHouseSemiPn;//实际库存

	public WareHouseEntryModel getWareHouseEntry() {
		return wareHouseEntry;
	}

	public void setWareHouseEntry(WareHouseEntryModel wareHouseEntry) {
		this.wareHouseEntry = wareHouseEntry;
	}

	public WareHouseSemiPnModel getWareHouseSemiPn() {
		return wareHouseSemiPn;
	}

	public void setWareHouseSemiPn(WareHouseSemiPnModel wareHouseSemiPn) {
		this.wareHouseSemiPn = wareHouseSemiPn;
	}
}
