package com.sso.service;

import com.jiaoyushop.util.E3Result;

public interface LoginService {
	E3Result userLogin(String username,String password);
}
