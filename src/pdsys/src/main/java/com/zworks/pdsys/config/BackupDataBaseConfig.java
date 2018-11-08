package com.zworks.pdsys.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 日期处理
 * 
 * @author zhangxiaofengjs@163.com
 * 
 * @date 2018-01-22
 */
@Component
@ConfigurationProperties(prefix="pdsys.backup.database")  
public class BackupDataBaseConfig {
	private boolean enable;
	private String location;
	private String backupDir;
	private String name;
	private String host;
	private String userName;
	private String password;

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getBackupDir() {
		return backupDir;
	}

	public void setBackupDir(String backupDir) {
		this.backupDir = backupDir;
	}

	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}
}
