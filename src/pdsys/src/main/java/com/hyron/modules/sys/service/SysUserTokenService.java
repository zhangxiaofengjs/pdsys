package com.hyron.modules.sys.service;

import com.hyron.common.utils.R;
import com.hyron.modules.sys.entity.SysUserTokenEntity;

/**
 * 用户Token
 * 
 * @author Allen
 * @webSite https://www.allen-software.cn
 * @date 2017-03-23 15:22:07
 */
public interface SysUserTokenService {

	SysUserTokenEntity queryByUserId(Long userId);

	void save(SysUserTokenEntity token);

	void update(SysUserTokenEntity token);

	/**
	 * 生成token
	 * @param userId 用户ID
	 */
	R createToken(long userId);

	/**
	 * 退出，修改token值
	 * @param userId 用户ID
	 */
	void logout(long userId);
}
