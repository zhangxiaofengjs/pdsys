package com.zworks.pdsys.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2018/04/05
 */
@Controller
public class IndexController {
	
	@RequestMapping("/")
    public String index(Model model) {
        return "index";
    }
}
