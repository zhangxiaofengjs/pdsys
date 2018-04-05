package com.zworks.pdsys.form.beans;

import com.zworks.pdsys.models.WareHouseDeliveryBOMModel;
import com.zworks.pdsys.models.WareHouseDeliveryMachinePartModel;
import com.zworks.pdsys.models.WareHouseDeliveryPnModel;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2018/04/05
 */
public class WareHouseAddDeliveryObjFormBean {
	private String wareHouseBOMIds;
	private String wareHousePnIds;
	private String wareHouseMachinePartIds;
	private WareHouseDeliveryBOMModel wareHouseDeliveryBOM;
	private WareHouseDeliveryPnModel wareHouseDeliveryPn;
	private WareHouseDeliveryMachinePartModel wareHouseDeliveryMachinePart;
	
	public WareHouseAddDeliveryObjFormBean() {
		setWareHouseDeliveryBOM(new WareHouseDeliveryBOMModel());
	}

	public WareHouseDeliveryBOMModel getWareHouseDeliveryBOM() {
		return wareHouseDeliveryBOM;
	}

	public void setWareHouseDeliveryBOM(WareHouseDeliveryBOMModel wareHouseDeliveryBOM) {
		this.wareHouseDeliveryBOM = wareHouseDeliveryBOM;
	}

	public String getWareHouseBOMIds() {
		return wareHouseBOMIds;
	}

	public void setWareHouseBOMIds(String wareHouseBOMIds) {
		this.wareHouseBOMIds = wareHouseBOMIds;
	}

	public String getWareHousePnIds() {
		return wareHousePnIds;
	}

	public void setWareHousePnIds(String wareHousePnIds) {
		this.wareHousePnIds = wareHousePnIds;
	}

	public WareHouseDeliveryPnModel getWareHouseDeliveryPn() {
		return wareHouseDeliveryPn;
	}

	public void setWareHouseDeliveryPn(WareHouseDeliveryPnModel wareHouseDeliveryPn) {
		this.wareHouseDeliveryPn = wareHouseDeliveryPn;
	}

	public WareHouseDeliveryMachinePartModel getWareHouseDeliveryMachinePart() {
		return wareHouseDeliveryMachinePart;
	}

	public void setWareHouseDeliveryMachinePart(WareHouseDeliveryMachinePartModel wareHouseDeliveryMachinePart) {
		this.wareHouseDeliveryMachinePart = wareHouseDeliveryMachinePart;
	}

	public String getWareHouseMachinePartIds() {
		return wareHouseMachinePartIds;
	}

	public void setWareHouseMachinePartIds(String wareHouseMachinePartIds) {
		this.wareHouseMachinePartIds = wareHouseMachinePartIds;
	}
	
}
