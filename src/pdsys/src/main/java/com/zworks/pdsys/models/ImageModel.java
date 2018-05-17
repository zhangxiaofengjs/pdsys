package com.zworks.pdsys.models;

import org.apache.ibatis.type.Alias;

/**
 * @author: ZHAI
 * @version: 2018/04/26
 */
@Alias("imageModel")
public class ImageModel extends BaseModel {
	private String name;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getAlt() {
		return alt;
	}
	public void setAlt(String alt) {
		this.alt = alt;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	private String url;
	private String alt;
	private String comment;
}
