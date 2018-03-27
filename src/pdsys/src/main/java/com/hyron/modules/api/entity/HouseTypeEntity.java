package com.hyron.modules.api.entity;

import java.io.Serializable;

import org.hibernate.validator.constraints.NotBlank;

/**
 * 房屋类型
 * 
 * @author allen
 * @webSite https://www.allen-software.cn
 * @date 2018-02-07 13:16:54
 */
public class HouseTypeEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	// 房屋类型编号
	private Long houseTypeId;

	// 房屋类型英文名称
	@NotBlank(message = "房屋类型英文名称不能为空")
	private String houseTypeName;

	// 房屋类型中文名称
	@NotBlank(message = "房屋类型中文名称不能为空")
	private String houseTypeNameCn;

	/**
	 * 设置：房屋类型编号
	 */
	public void setHouseTypeId(Long houseTypeId) {
		this.houseTypeId = houseTypeId;
	}
	
	/**
	 * 获取：房屋类型编号
	 */
	public Long getHouseTypeId() {
		return houseTypeId;
	}
	/**
	 * 设置：房屋类型英文名称
	 */
	public void setHouseTypeName(String houseTypeName) {
		this.houseTypeName = houseTypeName;
	}
	
	/**
	 * 获取：房屋类型英文名称
	 */
	public String getHouseTypeName() {
		return houseTypeName;
	}
	/**
	 * 设置：房屋类型中文名称
	 */
	public void setHouseTypeNameCn(String houseTypeNameCn) {
		this.houseTypeNameCn = houseTypeNameCn;
	}
	
	/**
	 * 获取：房屋类型中文名称
	 */
	public String getHouseTypeNameCn() {
		return houseTypeNameCn;
	}
}
