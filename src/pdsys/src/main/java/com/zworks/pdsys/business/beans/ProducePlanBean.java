package com.zworks.pdsys.business.beans;

import java.util.List;

import com.zworks.pdsys.models.MachineModel;
import com.zworks.pdsys.models.MachinePartModel;
import com.zworks.pdsys.models.OrderModel;
import com.zworks.pdsys.models.PnModel;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2018/04/18
 */
public class ProducePlanBean {
	private OrderModel order;
	private List<ProducePlanPnBean> pns;
	public OrderModel getOrder() {
		return order;
	}
	public void setOrder(OrderModel order) {
		this.order = order;
	}
	public List<ProducePlanPnBean> getPns() {
		return pns;
	}
	public void setPns(List<ProducePlanPnBean> pns) {
		this.pns = pns;
	}
}
