package com.zworks.pdsys.models;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

/**
 * 机器故障
 * 
 * @author ZHAI
 * @date 2018-03-30 13:22:06
 */
@Alias("machineTroubleModel")
public class MachineTroubleModel extends BaseModel implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//故障CODE
	private String code;
	
	//故障描述
	private String comment;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
	
}
