package com.zworks.pdsys.models;

import org.apache.ibatis.type.Alias;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2018/04/05
 */
@Alias("wareHouseDeliveryMachinePartModel")
public class WareHouseDeliveryMachinePartModel extends BaseModel{
	public static final WareHouseDeliveryMachinePartModel Empty = new WareHouseDeliveryMachinePartModel();
	
	private WareHouseDeliveryModel wareHouseDelivery;
	private WareHouseMachinePartModel wareHouseMachinePart;
	private float num;
	
	public WareHouseDeliveryMachinePartModel() {
		setWareHouseDelivery(WareHouseDeliveryModel.Empty);
		setWareHouseMachinePart(WareHouseMachinePartModel.Empty);
	}
	
	public float getNum() {
		return num;
	}

	public void setNum(float num) {
		this.num = num;
	}

	public WareHouseDeliveryModel getWareHouseDelivery() {
		return wareHouseDelivery;
	}

	public void setWareHouseDelivery(WareHouseDeliveryModel wareHouseDelivery) {
		this.wareHouseDelivery = wareHouseDelivery;
	}

	public WareHouseMachinePartModel getWareHouseMachinePart() {
		return wareHouseMachinePart;
	}

	public void setWareHouseMachinePart(WareHouseMachinePartModel wareHouseMachinePartModel) {
		this.wareHouseMachinePart = wareHouseMachinePartModel;
	}
}
