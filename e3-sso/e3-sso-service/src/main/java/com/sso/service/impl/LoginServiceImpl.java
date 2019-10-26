package com.sso.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import com.jiaoyushop.mapper.TbUserMapper;
import com.jiaoyushop.pojo.TbUser;
import com.jiaoyushop.pojo.TbUserExample;
import com.jiaoyushop.pojo.TbUserExample.Criteria;
import com.jiaoyushop.util.E3Result;
import com.jiaoyushop.util.JedisClient;
import com.jiaoyushop.util.JsonUtils;
import com.sso.service.LoginService;

@Service
public class LoginServiceImpl implements LoginService {
	
	@Autowired
	private TbUserMapper userMapper;
	@Autowired
	private JedisClient jedisClient;
	
	@Value("${SESSION_EXPIRE}")
	private Integer SESSION_EXPIRE;

	@Override
	public E3Result userLogin(String username, String password) {
		
		TbUserExample example=new TbUserExample();
		Criteria criteria = example.createCriteria();
		criteria.andUsernameEqualTo(username);
		List<TbUser> list = userMapper.selectByExample(example);
		if(list==null||list.size()==0) {
			return E3Result.build(400,"用户名错误");
		}
		TbUser user = list.get(0);
		String md5password= user.getPassword();
		String newpassword= DigestUtils.md5DigestAsHex(password.getBytes());
		if(!md5password.equals(newpassword)) {
			return E3Result.build(400,"密码错误");
		}
		
		String token=UUID.randomUUID().toString();
		jedisClient.set("SESSION:"+token, JsonUtils.objectToJson(user));
		jedisClient.expire("SESSION:"+token, SESSION_EXPIRE);
		
		return E3Result.ok(token);
	}

}
