package com.sso.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.jiaoyushop.pojo.TbUser;
import com.jiaoyushop.util.E3Result;
import com.jiaoyushop.util.JedisClient;
import com.jiaoyushop.util.JsonUtils;
import com.sso.service.TokenService;

@Service
public class TokenServiceImpl implements TokenService{

	@Autowired 
	private JedisClient jedisClient;
	@Value("${USER_INFO}")
	private String USER_INFO;
	@Value("${SESSION_EXPIRE}")
	private Integer SESSION_EXPIRE;
	@Override
	public E3Result getUserByToken(String token) {
		String json=jedisClient.get(USER_INFO+":"+token);
		if(StringUtils.isBlank(json)) {
			return E3Result.build(400,"用户登录已经过期，请重新登录");
		}
		jedisClient.expire(USER_INFO+":"+token, SESSION_EXPIRE);
		TbUser user = JsonUtils.jsonToPojo(json, TbUser.class);	
		return E3Result.ok(user);
	}

}
