package com.zworks.pdsys.models;

import org.apache.ibatis.type.Alias;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2018/04/19
 */
@Alias("wareHouseEntryBOMModel")
public class WareHouseEntryBOMModel extends WareHouseBOMModel{
	private WareHouseEntryModel wareHouseEntry;
	private WareHouseBOMModel wareHouseBOM;//实际库存

	public WareHouseEntryModel getWareHouseEntry() {
		return wareHouseEntry;
	}
	public void setWareHouseEntry(WareHouseEntryModel wareHouseEntry) {
		this.wareHouseEntry = wareHouseEntry;
	}
	public WareHouseBOMModel getWareHouseBOM() {
		return wareHouseBOM;
	}
	public void setWareHouseBOM(WareHouseBOMModel wareHouseBOM) {
		this.wareHouseBOM = wareHouseBOM;
	}
}
