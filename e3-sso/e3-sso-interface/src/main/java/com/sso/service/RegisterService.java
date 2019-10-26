package com.sso.service;

import com.jiaoyushop.pojo.TbUser;
import com.jiaoyushop.util.E3Result;

public interface RegisterService {
	
	//注册用户
	public E3Result insertUser(TbUser user);
	
	//验证用户
	public E3Result checkData(String param,int type);

}
