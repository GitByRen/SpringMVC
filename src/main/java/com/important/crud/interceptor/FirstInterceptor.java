package com.important.crud.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class FirstInterceptor implements HandlerInterceptor {

	/**
	 * 目标方法之前被调用 若返回值为true,则继续调用后续的拦截器和目标方法 
	 * 若返回值为false,则不会继续调用后续的拦截器和目标方法
	 * 
	 * 功能：可以考虑做权限，日志，事务等
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		return true;
	}

	/**
	 * 调用目标方法之后，渲染视图之前
	 * 
	 * 功能：可以对请求域中的属性和视图做出修改
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

	}

	/**
	 * 渲染视图之后被调用
	 * 
	 * 功能：释放资源
	 */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {

	}

}
