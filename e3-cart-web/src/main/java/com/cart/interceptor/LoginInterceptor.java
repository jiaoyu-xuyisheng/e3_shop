package com.cart.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import com.jiaoyushop.pojo.TbUser;
import com.jiaoyushop.util.CookieUtils;
import com.jiaoyushop.util.E3Result;
import com.sso.service.TokenService;

public class LoginInterceptor implements HandlerInterceptor {
	@Autowired
	private TokenService tokenService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// 前处理 true放行，false拦载
		String token=CookieUtils.getCookieValue(request, "token");
		if(StringUtils.isBlank(token)) {
			return true;
		}
		E3Result e3Result = tokenService.getUserByToken(token);
		if(e3Result.getStatus()!=200) {
			return true;
		}
		TbUser user = (TbUser) e3Result.getData();
		request.setAttribute("user", user);
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		//后处理

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		//完成 。页面渲染 可以处理异常

	}

}
