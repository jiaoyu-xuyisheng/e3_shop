package com.sso.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jiaoyushop.util.E3Result;
import com.jiaoyushop.util.JsonUtils;
import com.sso.service.TokenService;

@Controller
public class TokenController {

	@Autowired
	private TokenService tokenService;
	
	@RequestMapping(value="/user/token/{token}",produces="application/json;charset=utf-8")
	@ResponseBody
	public String getUserByToken(@PathVariable String token,String callback) {
		
		 E3Result userByToken = tokenService.getUserByToken(token);
		 if(StringUtils.isNotBlank(callback)) {
			return callback+"("+JsonUtils.objectToJson(userByToken)+");";
		 }
		 return JsonUtils.objectToJson(userByToken);
	}
}
