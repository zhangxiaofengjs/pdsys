package com.zworks.pdsys.business.beans;

import java.util.List;

import com.zworks.pdsys.models.PnModel;

public class SemiPnDetailBean {
	private PnModel pn;
	private List<SemiPnClsDetailBean> pnClsDetails;
	
	public int getProducableNum() {
		int maxNum = 0;
		for(SemiPnClsDetailBean b : pnClsDetails) {
			int m = (int)Math.floor(b.getNum()/b.getUnitNum());
			if(maxNum == 0) {
				maxNum = m;
			} else {
				maxNum = Math.min(maxNum, m);
			}
		}
		
		return maxNum;
	}

	public PnModel getPn() {
		return pn;
	}

	public void setPn(PnModel pn) {
		this.pn = pn;
	}

	public List<SemiPnClsDetailBean> getPnClsDetails() {
		return pnClsDetails;
	}

	public void setPnClsDetails(List<SemiPnClsDetailBean> pnClsDetails) {
		this.pnClsDetails = pnClsDetails;
	}
}
