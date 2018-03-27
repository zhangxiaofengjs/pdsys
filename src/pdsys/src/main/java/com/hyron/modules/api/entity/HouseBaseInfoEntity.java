package com.hyron.modules.api.entity;

import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

/**
 * 房屋基础信息
 * 
 * @author allen
 * @webSite https://www.allen-software.cn
 * @date 2018-02-05 16:06:10
 */
public class HouseBaseInfoEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	// 房屋编号
	private Long houseId;

	// 房屋标题
	private String houseTitle;

	// 类型
	private String type;

	// 地理位置
	private String location;

	// 建筑面积
	private BigDecimal areaConstruction;

	// 使用面积
	private BigDecimal areaUsing;

	// 面积单位
	private String areaUnit;

	// 创建时间
	private Date createTime;

	/**
	 * 设置：房屋编号
	 */
	public void setHouseId(Long houseId) {
		this.houseId = houseId;
	}

	/**
	 * 获取：房屋编号
	 */
	public Long getHouseId() {
		return houseId;
	}

	/**
	 * 设置：房屋标题
	 */
	public void setHouseTitle(String houseTitle) {
		this.houseTitle = houseTitle;
	}

	/**
	 * 获取：房屋标题
	 */
	public String getHouseTitle() {
		return houseTitle;
	}

	/**
	 * 设置：类型
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * 获取：类型
	 */
	public String getType() {
		return type;
	}

	/**
	 * 设置：地理位置
	 */
	public void setLocation(String location) {
		this.location = location;
	}

	/**
	 * 获取：地理位置
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * 设置：建筑面积
	 */
	public void setAreaConstruction(BigDecimal areaConstruction) {
		this.areaConstruction = areaConstruction;
	}

	/**
	 * 获取：建筑面积
	 */
	public BigDecimal getAreaConstruction() {
		return areaConstruction;
	}

	/**
	 * 设置：使用面积
	 */
	public void setAreaUsing(BigDecimal areaUsing) {
		this.areaUsing = areaUsing;
	}

	/**
	 * 获取：使用面积
	 */
	public BigDecimal getAreaUsing() {
		return areaUsing;
	}

	/**
	 * 设置：面积单位
	 */
	public void setAreaUnit(String areaUnit) {
		this.areaUnit = areaUnit;
	}

	/**
	 * 获取：面积单位
	 */
	public String getAreaUnit() {
		return areaUnit;
	}

	/**
	 * 设置：创建时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * 获取：创建时间
	 */
	public Date getCreateTime() {
		return createTime;
	}
}
