package com.zworks.pdsys.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="pdsys.upload")  
public class UploadConfig {
	private String location;
	private String tempFolder;
	private String imageFolder;
	private String orderImportFolder;

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getImageFolder() {
		return imageFolder;
	}

	public void setImageFolder(String imageFolder) {
		this.imageFolder = imageFolder;
	}

	public String getTempFolder() {
		return tempFolder;
	}

	public void setTempFolder(String tempFolder) {
		this.tempFolder = tempFolder;
	}

	public String getOrderImportFolder() {
		return orderImportFolder;
	}

	public void setOrderImportFolder(String orderImportFolder) {
		this.orderImportFolder = orderImportFolder;
	}
}
