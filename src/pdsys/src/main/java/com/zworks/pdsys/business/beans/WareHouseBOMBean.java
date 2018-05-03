package com.zworks.pdsys.business.beans;

import org.apache.ibatis.type.Alias;

import com.zworks.pdsys.models.BOMModel;
import com.zworks.pdsys.models.WareHouseBOMModel;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2018/05/02
 */
@Alias("wareHouseBOMBean")
public class WareHouseBOMBean {
	private BOMModel BOM;
	private WareHouseBOMModel wareHouseBOM;
	private float buyNum;
	public BOMModel getBOM() {
		return BOM;
	}
	public void setBOM(BOMModel bOM) {
		BOM = bOM;
	}
	public WareHouseBOMModel getWareHouseBOM() {
		return wareHouseBOM;
	}
	public void setWareHouseBOM(WareHouseBOMModel wareHouseBOM) {
		this.wareHouseBOM = wareHouseBOM;
	}
	public float getBuyNum() {
		return buyNum;
	}
	public void setBuyNum(float buyNum) {
		this.buyNum = buyNum;
	}
}
