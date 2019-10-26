package com.order.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.cart.service.CartService;
import com.jiaoyushop.pojo.TbItem;
import com.jiaoyushop.pojo.TbUser;
import com.jiaoyushop.util.CookieUtils;
import com.jiaoyushop.util.E3Result;
import com.jiaoyushop.util.JsonUtils;
import com.sso.service.TokenService;

public class LoginInterceptor implements HandlerInterceptor {
	
	@Value("${SSO_URL")
	private String SSO_URL;
	@Autowired
	private TokenService tokenService;
	
	
	@Autowired
	private CartService cartService;
	

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		//从cookie中取token
		String token = CookieUtils.getCookieValue(request, "token");
		//判断token是否存在to
		//如果token不存在，未登录状态，
		if(StringUtils.isBlank(token)) {
			response.sendRedirect(SSO_URL+"/page/login?redirect="+request.getRequestURL());
			return false;
		}		
		//如果存在，取用户信息。如果取不到要重新登录！！
		E3Result e3Result = tokenService.getUserByToken(token);
		if(e3Result.getStatus()!=200) {
			response.sendRedirect(SSO_URL+"/page/login?redirect="+request.getRequestURL());
			return false;
		}
		TbUser user=(TbUser) e3Result.getData();
		String jsonCartList = CookieUtils.getCookieValue(request, "cart", true);
		if(StringUtils.isNotBlank(jsonCartList)) {
			cartService.mergeCart(user.getId(),JsonUtils.jsonToList(jsonCartList, TbItem.class));
		}
		request.setAttribute("user", user);
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub

	}

}
