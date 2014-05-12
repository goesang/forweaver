package com.forweaver.intercepter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.forweaver.service.WeaverService;

public class ProjectIntercepter extends HandlerInterceptorAdapter {
	//before the actual handler will be executed

	
		public boolean preHandle(HttpServletRequest request, 
			HttpServletResponse response, Object handler)
		    throws Exception {
			System.out.println("preHandle");
			return true;
		}
	 
		//after the handler is executed
		public void postHandle(
			HttpServletRequest request, HttpServletResponse response, 
			Object handler, ModelAndView modelAndView)
			throws Exception {
	 
			System.out.println("postHandle");
		}
}
