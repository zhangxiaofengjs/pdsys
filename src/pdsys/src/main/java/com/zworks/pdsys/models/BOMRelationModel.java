package com.zworks.pdsys.models;

import org.apache.ibatis.type.Alias;

/**
 * @author: ZHAI
 * @version: 2018/04/03
 */
@Alias("BOMRelationModel")
public class BOMRelationModel {

	private int pnId;
	
	private int bomId;
	private BOMModel bom;
	private String useNum;

	public int getPnId() {
		return pnId;
	}

	public void setPnId(int pnId) {
		this.pnId = pnId;
	}

	public int getBomId() {
		return bomId;
	}

	public void setBomId(int bomId) {
		this.bomId = bomId;
	}

	public String getUseNum() {
		return useNum;
	}

	public void setUseNum(String useNum) {
		this.useNum = useNum;
	}

	public BOMModel getBom() {
		return bom;
	}

	public void setBom(BOMModel bom) {
		this.bom = bom;
	}

}
