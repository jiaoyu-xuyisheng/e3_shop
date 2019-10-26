package com.sso.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.jiaoyushop.util.CookieUtils;
import com.jiaoyushop.util.E3Result;
import com.sso.service.LoginService;

@Controller
public class LoginController {
	
	@Autowired
	private LoginService loginService;
	
	@Value("${COOKIE_TOKEN_KEY}")
	private String COOKIE_TOKEN_KEY;
	
	@RequestMapping("/page/login")
	public String showLoginPage(String redirect,Model model) {
		model.addAttribute("redirect", redirect);
		
		return "login";
	}
	
	@RequestMapping(value="/user/login",method=RequestMethod.POST)
	@ResponseBody
	public E3Result userLogin(String username, String password,HttpServletRequest request,HttpServletResponse response) {		
		E3Result e3Result = loginService.userLogin(username, password);
		if(e3Result.getStatus()==200) {
			String token = (String) e3Result.getData();
			CookieUtils.setCookie(request, response, COOKIE_TOKEN_KEY, token);
		}
		return e3Result;
	}
		

}
