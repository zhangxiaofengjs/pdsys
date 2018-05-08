package com.zworks.pdsys.common.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.zworks.pdsys.models.UserModel;
import com.zworks.pdsys.models.UserRoleModel;

public class PdSysLoginUser implements UserDetails{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1367852488564942197L;
	private UserModel user;
	
	public PdSysLoginUser(UserModel user) {
		this.user = user;
	}
	
	public UserModel getUser() {
		return user;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
		if(user != null) {
			List<UserRoleModel> roles = user.getRoles();
			
			if(roles != null) {
				for(UserRoleModel role : roles) {
					//定义权限集合  
			        GrantedAuthority ga = new SimpleGrantedAuthority(role.getRole());
			        grantedAuthorities.add(ga);
				}
			}
		}
		return grantedAuthorities;
	}
	
	@Override
	public String getPassword() {
		return user.getPassword();
	}
	@Override
	public String getUsername() {
		return user.getName();
	}
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	@Override
	public boolean isEnabled() {
		return true;
	}
}
	
