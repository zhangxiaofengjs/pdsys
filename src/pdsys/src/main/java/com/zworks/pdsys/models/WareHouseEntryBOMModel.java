package com.zworks.pdsys.models;

import org.apache.ibatis.type.Alias;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2018/04/19
 */
@Alias("wareHouseEntryBOMModel")
public class WareHouseEntryBOMModel extends WareHouseBOMModel{
	public static final String FCK_FUZZYPNSEARCH = "fuzzyPnSearch";
	public static final String FCK_ENTRYSTART = "entryStart";
	public static final String FCK_ENTRYEND = "entryEnd";
	public static final String FCK_GROUPBYBOM = "groupByBOM";
			
	private WareHouseEntryModel wareHouseEntry;
	private WareHouseBOMModel wareHouseBOM;//实际库存
	private SupplierModel supplier;
	
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
	
	public SupplierModel getSupplier() {
		return supplier;
	}

	public void setSupplier(SupplierModel supplier) {
		this.supplier = supplier;
	}
}
