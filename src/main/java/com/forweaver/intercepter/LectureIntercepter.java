package com.forweaver.intercepter;

import java.net.URLDecoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.forweaver.domain.Lecture;
import com.forweaver.domain.Weaver;
import com.forweaver.service.LectureService;
import com.forweaver.service.TagService;
import com.forweaver.service.WeaverService;


public class LectureIntercepter extends HandlerInterceptorAdapter {
	@Autowired 
	WeaverService weaverService;
	@Autowired 
	LectureService lectureService;
	@Autowired 
	TagService tagService;

	public boolean preHandle(HttpServletRequest request, 
			HttpServletResponse response, Object handler)
					throws Exception {
		String uri = request.getRequestURI();
		String lectureName = new String();
		if (uri.split("/").length>2)
			lectureName= uri.split("/")[2];
		else
			return true;

		if(uri.endsWith("/join-ok") || uri.endsWith("/join") || lectureName.startsWith("page:") || lectureName.startsWith("tags:") || lectureName.startsWith("add"))
			return true;

		Weaver weaver = weaverService.getCurrentWeaver();
		Lecture lecture = lectureService.get(lectureName);

		if(lecture == null || weaver.getPass(lectureName) == null){
			response.sendError(404);
			return false;
		}

		if(uri.contains("/repo:")){
			String repoName = uri.substring(uri.indexOf("/repo:")+6);
			if(repoName.contains("/"))
				repoName = repoName.substring(0, repoName.indexOf("/"));
			if(lecture.getRepo(repoName) == null){
				response.sendError(404);
				return false;
			}
		}

		if(uri.contains("/tags:")){
			String tags = uri.substring(uri.indexOf("/tags:")+6);
			if(tags.contains("/"))
				tags = tags.substring(0, tags.indexOf("/"));
			tags = URLDecoder.decode(tags, "UTF-8");
			List<String> tagList = tagService.stringToTagList(tags);
			if(!tagService.validateTag(tagList, weaver)){
				response.sendError(404);
				return false;
			}
		}

		return true;
	}

}
