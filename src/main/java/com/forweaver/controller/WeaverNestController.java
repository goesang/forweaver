package com.forweaver.controller;
/*
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.forweaver.domain.Project;
import com.forweaver.domain.Weaver;
import com.forweaver.service.PermissionService;
import com.forweaver.service.ProjectService;
import com.forweaver.service.WeaverService;


@RequestMapping("/wn")
@Controller
public class WeaverNestController {
	@Autowired WeaverService weaverService;
	@Autowired ProjectService projectService;
	@Autowired PermissionService permissionService;

	@RequestMapping("/login")
	public String login() {
		return "/weavernest/login";
	}
	
	@RequestMapping("/project")
	public String working() {
		return "/weavernest/project";
	}
	
	@RequestMapping("/commit")
	public String commit() {
		return "/weavernest/commit";
	}
	
	@RequestMapping("/commitlog")
	public String commitlog() {
		return "/weavernest/commitLog";
	}
	
	@RequestMapping("/branch")
	public String branch() {
		return "/weavernest/branch";
	}
	
	@RequestMapping("/alert")
	public String alert() {
		return "/weavernest/alert";
	}
	
	@RequestMapping("/error")
	public String error() {
		return "/weavernest/error";
	}
	
	@RequestMapping("/project-admin")
	public String projectAdmin() {
		return "/weavernest/projectAdmin";
	}
	
	@RequestMapping("/amend")
	public String amend() {
		return "/weavernest/amend";
	}

	@RequestMapping("/front")
	public String front(Model model) {
		
		Weaver weaver = weaverService.getCurrentWeaver();
		model.addAttribute("weaverEmail", weaver.getEmail());
		//model.addAttribute("projects", weaverService.getProjects(weaver));
		return "/weavernest/front";
	}
	
	@RequestMapping("/{creatorName}/{projectName}/download")
	public String projectDown(@PathVariable("projectName") String projectName,
			@PathVariable("creatorName") String creatorName,Model model) {
		Project project = projectService.get(creatorName+"/"+projectName);
		
		if(!permissionService.projectPermission(project, weaverService.getCurrentWeaver()))
			return "redirect:/wn/front";
		
		model.addAttribute("project", project);
		return "/weavernest/projectDown";
	}
		
}
*/