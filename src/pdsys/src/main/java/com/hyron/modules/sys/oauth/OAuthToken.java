package com.hyron.modules.sys.oauth;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * token
 * 
 * @author Allen
 * @webSite https://www.allen-software.cn
 * @date 2017-05-20 13:22
 */
public class OAuthToken implements AuthenticationToken {
	private static final long serialVersionUID = 1L;
	
	private String token;

	public OAuthToken(String token) {
		this.token = token;
	}

	@Override
	public String getPrincipal() {
		return token;
	}

	@Override
	public Object getCredentials() {
		return token;
	}
}
