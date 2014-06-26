package com.forweaver.intercepter;

import java.net.URLDecoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.forweaver.domain.Project;
import com.forweaver.domain.Weaver;
import com.forweaver.service.ProjectService;
import com.forweaver.service.TagService;
import com.forweaver.service.WeaverService;

public class ProjectIntercepter extends HandlerInterceptorAdapter {
	@Autowired 
	WeaverService weaverService;
	
	@Autowired 
	TagService tagService;
	
	@Autowired 
	ProjectService projectService;
		public boolean preHandle(HttpServletRequest request, 
			HttpServletResponse response, Object handler)
		    throws Exception {
			
			String uri = request.getRequestURI();
			String projectName = new String();
			if (uri.split("/").length>3){
				projectName= uri.split("/")[2]+"/"+uri.split("/")[3];
			}else
				return true;
			
			if(uri.endsWith("/join-ok") ||uri.endsWith("/join") || projectName.startsWith("sort:") || projectName.startsWith("tags:"))
				return true;
			
			Weaver weaver = weaverService.getCurrentWeaver();
			Project project = projectService.get(projectName);
			
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
			if(project == null || (project.getCategory() == 1 && weaver.getPass(projectName) == null)){
				response.sendError(404);
				return false;
			}
			
			return true;
		}
	 
}
