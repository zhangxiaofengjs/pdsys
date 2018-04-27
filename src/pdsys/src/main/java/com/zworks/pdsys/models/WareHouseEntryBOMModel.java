package com.zworks.pdsys.models;

import org.apache.ibatis.type.Alias;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2018/04/19
 */
@Alias("wareHouseEntryBOMModel")
public class WareHouseEntryBOMModel extends BaseModel{
	private WareHouseEntryModel wareHouseEntry;
	private WareHouseBOMModel wareHouseBOM;
	private BOMModel bom;
	private float num;
	public BOMModel getBom() {
		return bom;
	}
	public void setBom(BOMModel bom) {
		this.bom = bom;
	}
	public float getNum() {
		return num;
	}
	public void setNum(float num) {
		this.num = num;
	}
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
