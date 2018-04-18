package com.zworks.pdsys.business.beans;

import com.zworks.pdsys.models.BOMModel;
import com.zworks.pdsys.models.CustomerModel;
import com.zworks.pdsys.models.PnModel;
import com.zworks.pdsys.models.WareHouseBOMModel;
import com.zworks.pdsys.models.WareHouseMachinePartModel;
import com.zworks.pdsys.models.WareHousePnModel;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2018/04/17
 */
public class SysMasterFormBean {
	private PnModel pn;
	private BOMModel bom;

	public PnModel getPn() {
		return pn;
	}

	public void setPn(PnModel pn) {
		this.pn = pn;
	}

	public BOMModel getBom() {
		return bom;
	}

	public void setBom(BOMModel bom) {
		this.bom = bom;
	}
}
