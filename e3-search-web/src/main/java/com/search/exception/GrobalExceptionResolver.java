package com.search.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

public class GrobalExceptionResolver implements HandlerExceptionResolver {

	private static final Logger logger=LoggerFactory.getLogger(GrobalExceptionResolver.class);
	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {
		ex.printStackTrace();
		logger.debug("系统发生异常");
		logger.error("系统发生异常", ex);
		ModelAndView modelAndView =new ModelAndView();
		modelAndView.setViewName("error/exception");
		return null;
	}

}
