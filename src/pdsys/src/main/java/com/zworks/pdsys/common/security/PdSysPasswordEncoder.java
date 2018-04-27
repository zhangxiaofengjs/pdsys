package com.zworks.pdsys.common.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

//密码加密器，懒得做直接用BCryptPasswordEncoder
@Component
public class PdSysPasswordEncoder extends BCryptPasswordEncoder {
	public PdSysPasswordEncoder()
	{
		super();
	}
    @Override
    public String encode(CharSequence arg0) {
        return super.encode(arg0);
    }

    @Override
    public boolean matches(CharSequence arg0, String arg1) {
    	return super.matches(arg0, arg1);
    }
}
