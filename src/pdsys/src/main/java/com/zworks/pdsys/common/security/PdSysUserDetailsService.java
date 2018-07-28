package com.zworks.pdsys.common.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.zworks.pdsys.common.annotations.PdSysLog;
import com.zworks.pdsys.models.UserModel;
import com.zworks.pdsys.services.UserService;

@Service
public class PdSysUserDetailsService implements UserDetailsService {
	@Autowired
    private UserService userService;
	
	@Override
	@PdSysLog(description="user login")
	public UserDetails loadUserByUsername(String account) throws UsernameNotFoundException {
		//从数据库查找用户，以及权限
		UserModel user = new UserModel();
		user.setNo(account);
		user = userService.queryOneWithPassword(user);
		if(user == null) {
			throw new UsernameNotFoundException("用户名不存在");
		}
		
		//将已登录的用户信息保存到SecurityContext  
		PdSysLoginUser loginUser = new PdSysLoginUser(user);
        return loginUser;
	}
}
