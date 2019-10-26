package com.sso.service;


import com.jiaoyushop.util.E3Result;

public interface TokenService {
	E3Result getUserByToken(String token);
}
