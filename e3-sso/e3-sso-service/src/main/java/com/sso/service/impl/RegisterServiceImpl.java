package com.sso.service.impl;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.jiaoyushop.mapper.TbUserMapper;
import com.jiaoyushop.pojo.TbUser;
import com.jiaoyushop.pojo.TbUserExample;
import com.jiaoyushop.pojo.TbUserExample.Criteria;
import com.jiaoyushop.util.E3Result;
import com.sso.service.RegisterService;


@Service
public class RegisterServiceImpl implements RegisterService {
	
	@Autowired
	private TbUserMapper userMapper;

	@Override
	public E3Result insertUser(TbUser user) {
		if(StringUtils.isBlank(user.getUsername())) {
			return E3Result.build(400, "用户名不能为空");
		}
		
		if(StringUtils.isBlank(user.getPassword())) {
			return E3Result.build(400, "密码不能为空");
		}
		
		E3Result result = checkData(user.getUsername(),1);
		if(!(boolean) result.getData()) {
			return E3Result.build(400, "用户名重复！！");
		}
		result = checkData(user.getPhone(),2);
		if(!(boolean) result.getData()) {
			return E3Result.build(400, "手机号重复！！");
		}
		user.setCreated(new Date());
		user.setUpdated(new Date());
		String md5Pass = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
		user.setPassword(md5Pass);
		userMapper.insert(user);
		return E3Result.ok();
	}

	@Override
	public E3Result checkData(String param, int type) {
		TbUserExample example=new TbUserExample();
		Criteria criteria = example.createCriteria();
		// 2、查询条件根据参数动态生成。
		//1、2、3分别代表username、phone、email
		if(type==1) {
			criteria.andUsernameEqualTo(param);
		}else if (type==2) {
			criteria.andPhoneEqualTo(param);
		}else if(type==3) {
			criteria.andEmailEqualTo(param);
		}else {
			return E3Result.build(400,"非法参数");
		}	
		List<TbUser> list = userMapper.selectByExample(example);
		if(list==null ||list.size()==0) {
			return E3Result.ok(true);
		}
		return E3Result.ok(false);
	}

}
