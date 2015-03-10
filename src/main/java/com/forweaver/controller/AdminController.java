package com.forweaver.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.forweaver.domain.Weaver;
import com.forweaver.service.CodeService;
import com.forweaver.service.LectureService;
import com.forweaver.service.PostService;
import com.forweaver.service.ProjectService;
import com.forweaver.service.WeaverService;
import com.forweaver.util.WebUtil;

@Controller
public class AdminController {
	@Autowired
	private WeaverService weaverService;
	@Autowired
	private PostService postService;
	@Autowired
	private ProjectService projectService;
	@Autowired
	private LectureService lectureService;
	@Autowired
	private CodeService codeService;
	
	@RequestMapping("/admin")
	public String home(Model model) {
		return "/admin/community";
	}
	
	@RequestMapping({"/admin/code","/admin/project","/admin/lecture", "/admin/weaver"})
	public String home2(@PathVariable("id") String id,HttpServletRequest request) {
		Weaver weaver = weaverService.get(id);
		if(weaver == null)
			return "/error404";
		else
			return "redirect:" + request.getRequestURI() + "/sort:age-desc/page:1";
	}
	
	@RequestMapping("/admin/weaver/sort:{sort}/page:{page}")
	public String weaverPage(@PathVariable("page") String page,
			@PathVariable("sort") String sort,Model model){
		int pageNum = WebUtil.getPageNumber(page);
		int size = WebUtil.getPageSize(page);

		long weaverCount = weaverService.countWeavers();
		List<Weaver> weavers = weaverService.getWeavers(pageNum, size);

		model.addAttribute("weavers", weavers);	
		model.addAttribute("weaverCount", weaverCount);

		model.addAttribute("pageIndex", pageNum);
		model.addAttribute("number", size);
		model.addAttribute("pageUrl", "/weaver/page:");
		return "/admin/weaver";
	}
	
	@RequestMapping("/admin/code/sort:{sort}/page:{page}")
	public String codePage(@PathVariable("page") String page,
			@PathVariable("sort") String sort,Model model){
		int pageNum = WebUtil.getPageNumber(page);
		int size = WebUtil.getPageSize(page);
				
		model.addAttribute("codes", 
				codeService.getCodes(null, null, null, sort, pageNum, size));
		model.addAttribute("codeCount", 
				codeService.countCodes(null, null, null, sort));

		model.addAttribute("pageIndex", pageNum);
		model.addAttribute("number", size);
		model.addAttribute("pageUrl", "/admin/code/sort:"+sort+"/page:");
		return "/admin/code";
	}
	
	@RequestMapping("/admin/lecture/sort:{sort}/page:{page}")
	public String lecturePage(@PathVariable("page") String page,
			@PathVariable("sort") String sort,Model model){
		int pageNum = WebUtil.getPageNumber(page);
		int size = WebUtil.getPageSize(page);
		
		Weaver currentWeaver = weaverService.getCurrentWeaver();
		model.addAttribute("lectures", lectureService.getLectures(currentWeaver, null, null, pageNum, size));
		model.addAttribute("lectureCount", lectureService.countLectures(null, ""));
		model.addAttribute("pageIndex", page);
		model.addAttribute("number", size);
		model.addAttribute("pageUrl", "/lecture/page:");
		return "/admin/lecture";
	}
	
	@RequestMapping("/admin/project/sort:{sort}/page:{page}")
	public String projectPage(@PathVariable("page") String page,
			@PathVariable("sort") String sort,Model model){
		int pageNum = WebUtil.getPageNumber(page);
		int size = WebUtil.getPageSize(page);
		
		Weaver currentWeaver = weaverService.getCurrentWeaver();
		model.addAttribute("projects", 
				projectService.getProjects(currentWeaver, null, "", sort, pageNum, size));
		model.addAttribute("projectCount", projectService.countProjects(null, "", sort));
		model.addAttribute("pageIndex", pageNum);
		model.addAttribute("number", size);
		model.addAttribute("pageUrl", "/project/sort:"+sort+"/page:");
		return "/admin/project";
	}
}
