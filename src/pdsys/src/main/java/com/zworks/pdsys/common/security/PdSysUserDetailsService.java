package com.zworks.pdsys.common.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.zworks.pdsys.models.UserModel;
import com.zworks.pdsys.models.UserRoleModel;
import com.zworks.pdsys.services.UserService;

@Service
public class PdSysUserDetailsService implements UserDetailsService {
	@Autowired
    private UserService userService;
	
	@Override
	public UserDetails loadUserByUsername(String account) throws UsernameNotFoundException {
		//从数据库查找用户，以及权限
		UserModel user = new UserModel();
		user.setNo(account);
		user = userService.queryOne(user);
		if(user == null) {
			throw new UsernameNotFoundException("用户名不存在");
		}
		
//		PdSysPasswordEncoder s = new PdSysPasswordEncoder();
//		String ss = s.encode("pdsys111");
		
		List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
		List<UserRoleModel> roles = user.getRoles();
		for(UserRoleModel role : roles) {
			//定义权限集合  
	        GrantedAuthority ga = new SimpleGrantedAuthority(role.getRole());
	        grantedAuthorities.add(ga);
		}
		
		//将已登录的用户信息保存到SecurityContext  
        User u = new User(user.getNo(), user.getPassword(), true,true,true, true, grantedAuthorities);  
        return u;
	}
}
