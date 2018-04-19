package com.zworks.pdsys.business.beans;

import com.zworks.pdsys.models.BOMModel;
import com.zworks.pdsys.models.PnModel;

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
