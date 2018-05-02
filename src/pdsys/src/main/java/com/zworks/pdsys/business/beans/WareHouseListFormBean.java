package com.zworks.pdsys.business.beans;

import com.zworks.pdsys.models.BOMModel;
import com.zworks.pdsys.models.WareHouseBOMModel;
import com.zworks.pdsys.models.WareHouseMachinePartModel;
import com.zworks.pdsys.models.WareHousePnModel;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2018/04/05
 */
public class WareHouseListFormBean {
	private BOMModel BOM;
	private WareHousePnModel wareHousePn;
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

	public BOMModel getBOM() {
		return BOM;
	}

	public void setBOM(BOMModel bOM) {
		BOM = bOM;
	}
}
