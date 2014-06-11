package com.forweaver.intercepter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.forweaver.domain.Weaver;
import com.forweaver.service.WeaverService;

public class CommunityIntercepter extends HandlerInterceptorAdapter {
	@Autowired 
	WeaverService weaverService;
	
		public boolean preHandle(HttpServletRequest request, 
			HttpServletResponse response, Object handler)
		    throws Exception {
			Weaver weaver = weaverService.getCurrentWeaver();
			
			return true;
		}
	 
}
