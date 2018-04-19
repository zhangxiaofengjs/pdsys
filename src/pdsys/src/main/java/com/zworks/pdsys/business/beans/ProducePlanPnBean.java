package com.zworks.pdsys.business.beans;

import java.util.List;

import com.zworks.pdsys.models.MachineModel;
import com.zworks.pdsys.models.MachinePartModel;
import com.zworks.pdsys.models.OrderModel;
import com.zworks.pdsys.models.OrderPnModel;
import com.zworks.pdsys.models.PnMachineModel;
import com.zworks.pdsys.models.PnModel;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2018/04/18
 */
public class ProducePlanPnBean {
	private OrderPnModel orderPn;
	private List<PnMachineModel> pnMachines;

	public OrderPnModel getOrderPn() {
		return orderPn;
	}
	public void setOrderPn(OrderPnModel orderPn) {
		this.orderPn = orderPn;
	}
	public List<PnMachineModel> getPnMachines() {
		return pnMachines;
	}
	public void setPnMachines(List<PnMachineModel> pnMachines) {
		this.pnMachines = pnMachines;
	}
}
