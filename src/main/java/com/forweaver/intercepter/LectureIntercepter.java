package com.forweaver.intercepter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.forweaver.domain.Lecture;
import com.forweaver.domain.Weaver;
import com.forweaver.service.LectureService;
import com.forweaver.service.WeaverService;


public class LectureIntercepter extends HandlerInterceptorAdapter {
	@Autowired 
	WeaverService weaverService;
	@Autowired 
	LectureService lectureService;

		public boolean preHandle(HttpServletRequest request, 
			HttpServletResponse response, Object handler)
		    throws Exception {
			String uri = request.getRequestURI();
			String lectureName = new String();
			if (uri.split("/").length>2)
				lectureName= uri.split("/")[2];
			Weaver weaver = weaverService.getCurrentWeaver();
			Lecture lecture = lectureService.get(lectureName);
			if(lecture == null && weaver.getPass(lectureName) == null){
				response.sendRedirect("/error404");
				return false;
			}
				
			return true;
		}
	 
}
