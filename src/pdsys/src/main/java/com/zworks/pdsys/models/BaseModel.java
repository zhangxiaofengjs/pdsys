package com.zworks.pdsys.models;

import java.util.Date;
import java.util.HashMap;

import org.apache.ibatis.type.Alias;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2018/03/31
 */
@Alias("baseModel")
public class BaseModel {
	private int id;
	private UserModel updateUser;
	private Date createTime;
	private Date updateTime;
	private HashMap<String, Object> filterCond = new HashMap<String, Object>();
	private PageModel page;
	
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public UserModel getUser() {
		return user;
	}

	public void setUser(UserModel user) {
		this.user = user;
	}

	private UserModel user;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public UserModel getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(UserModel updateUser) {
		this.updateUser = updateUser;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public HashMap<String, Object> getFilterCond() {
		return filterCond;
	}

	public void setFilterCond(HashMap<String, Object> filterCond) {
		this.filterCond = filterCond;
	}

	public PageModel getPage() {
		return page;
	}

	public void setPage(PageModel page) {
		this.page = page;
	}
}
