package com.zworks.pdsys.form.beans;

import com.zworks.pdsys.models.WareHouseBOMModel;
import com.zworks.pdsys.models.WareHouseMachinePartModel;
import com.zworks.pdsys.models.WareHousePnModel;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2018/04/05
 */
public class WareHouseListFormBean {
	private WareHouseBOMModel wareHouseBOM;
	private WareHousePnModel wareHousePn;
	private WareHouseMachinePartModel wareHouseMachinePart;
	
	public WareHouseListFormBean() {
		wareHouseBOM = new WareHouseBOMModel();
		wareHousePn = new WareHousePnModel();
	}
	
	public WareHouseBOMModel getWareHouseBOM() {
		return wareHouseBOM;
	}
	public void setWareHouseBOM(WareHouseBOMModel wareHouseBOM) {
		this.wareHouseBOM = wareHouseBOM;
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
}
