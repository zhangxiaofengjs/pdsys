package com.zworks.pdsys.business.beans;

import org.apache.ibatis.type.Alias;

import com.zworks.pdsys.models.BOMModel;
import com.zworks.pdsys.models.WareHouseBOMModel;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2018/05/14
 */
public class PurchaseBOMListFromBean {
	private String bomPn;
	private int purchaseId;
	public String getBomPn() {
		return bomPn;
	}
	public void setBomPn(String bomPn) {
		this.bomPn = bomPn;
	}
	public int getPurchaseId() {
		return purchaseId;
	}
	public void setPurchaseId(int purchaseId) {
		this.purchaseId = purchaseId;
	}
}
