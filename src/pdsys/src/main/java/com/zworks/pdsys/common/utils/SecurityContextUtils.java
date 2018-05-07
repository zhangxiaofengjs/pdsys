package com.zworks.pdsys.common.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import com.zworks.pdsys.common.security.PdSysLoginUser;
import com.zworks.pdsys.models.UserModel;

/**
 * @author zhangxiaofengjs@163.com
 * @date 2018/5/7
 */
public class SecurityContextUtils {
	public static PdSysLoginUser getLoginUser() {
		PdSysLoginUser loginUser = null;
		SecurityContext context = SecurityContextHolder.getContext();
		if(context != null) {
			Authentication authentication = context.getAuthentication();
			if(authentication != null) {
				Object obj = authentication.getPrincipal();
				if(obj != null) {
					if("anonymousUser".equals(obj)) {
						UserModel u = new UserModel();
						u.setName("anonymousUser");
						 loginUser = new PdSysLoginUser(u);
					} else {
						loginUser = (PdSysLoginUser) obj;
					}
				}
			}
		}
		return loginUser;
	}
}
