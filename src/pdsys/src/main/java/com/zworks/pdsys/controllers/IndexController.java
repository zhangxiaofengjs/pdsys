package com.zworks.pdsys.controllers;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zworks.pdsys.common.security.PdSysLoginUser;
import com.zworks.pdsys.common.utils.SecurityContextUtils;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2018/12/25
 */
@Controller
public class IndexController {
	
	@RequestMapping(value= {"/", "/index"})
    public String index(Model model) {
		PdSysLoginUser loginUser = (PdSysLoginUser) SecurityContextUtils.getLoginUser();
		model.addAttribute("loginUser", loginUser);

        return "index";
    }
	
	@RequestMapping("/login")
    public String login(Model model) {
        return "login";
    }
	
	@RequestMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    if (auth != null){
	        new SecurityContextLogoutHandler().logout(request, response, auth);
	    }
	    try {
			request.logout();
		} catch (ServletException e) {
			e.printStackTrace();
		}
        return "login";
    }
	
	@RequestMapping("/403")
    public String err403(Model model) {
        return "403";
    }
	
	@RequestMapping("/404")
    public String err404(Model model) {
        return "404";
    }
}
