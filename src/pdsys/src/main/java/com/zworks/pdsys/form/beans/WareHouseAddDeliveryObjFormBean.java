package com.zworks.pdsys.form.beans;

import com.zworks.pdsys.models.WareHouseDeliveryBOMModel;
import com.zworks.pdsys.models.WareHouseDeliveryPnModel;

public class WareHouseAddDeliveryObjFormBean {
	private String wareHouseBOMIds;
	private String wareHousePnIds;
	private WareHouseDeliveryBOMModel wareHouseDeliveryBOM;
	private WareHouseDeliveryPnModel wareHouseDeliveryPn;
	
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
	
}
