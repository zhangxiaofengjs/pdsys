package com.zworks.pdsys.business.beans;

import com.zworks.pdsys.models.WareHouseBOMModel;
import com.zworks.pdsys.models.WareHouseMachinePartModel;
import com.zworks.pdsys.models.WareHousePnModel;
import com.zworks.pdsys.models.WareHouseSemiPnModel;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2018/04/05
 */
public class WareHouseListFormBean {
	private WareHouseBOMModel wareHouseBOM;
	private WareHousePnModel wareHousePn;
	private WareHouseSemiPnModel wareHouseSemiPn;
	private WareHouseMachinePartModel wareHouseMachinePart;
	
	public WareHouseListFormBean() {
	}
	
	public WareHousePnModel getWareHousePn() {
		return wareHousePn;
	}
	public void setWareHousePn(WareHousePnModel wareHousePn) {
		this.wareHousePn = wareHousePn;
	}

	public WareHouseMachinePartModel getWareHouseMachinePart() {
		return wareHouseMachinePart;
	}

	public void setWareHouseMachinePart(WareHouseMachinePartModel wareHouseMachinePart) {
		this.wareHouseMachinePart = wareHouseMachinePart;
	}

	public WareHouseBOMModel getWareHouseBOM() {
		return wareHouseBOM;
	}

	public void setWareHouseBOM(WareHouseBOMModel wareHouseBOM) {
		this.wareHouseBOM = wareHouseBOM;
	}

	public WareHouseSemiPnModel getWareHouseSemiPn() {
		return wareHouseSemiPn;
	}

	public void setWareHouseSemiPn(WareHouseSemiPnModel wareHouseSemiPn) {
		this.wareHouseSemiPn = wareHouseSemiPn;
	}
}
