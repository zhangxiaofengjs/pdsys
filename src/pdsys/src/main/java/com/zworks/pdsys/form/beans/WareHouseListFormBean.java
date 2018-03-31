package com.zworks.pdsys.form.beans;

import com.zworks.pdsys.models.WareHouseBOMModel;
import com.zworks.pdsys.models.WareHousePnModel;

public class WareHouseListFormBean {
	private WareHouseBOMModel wareHouseBOM;
	private WareHousePnModel wareHousePn;
	
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
	
	
}
