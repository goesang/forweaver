package com.forweaver.controller;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.forweaver.domain.Post;
import com.forweaver.domain.Project;
import com.forweaver.domain.Weaver;
import com.forweaver.domain.git.GitFileInfo;
import com.forweaver.domain.git.GitSimpleCommitLog;
import com.forweaver.service.GitService;
import com.forweaver.service.PostService;
import com.forweaver.service.ProjectService;
import com.forweaver.service.RePostService;
import com.forweaver.service.TagService;
import com.forweaver.service.WeaverService;
import com.forweaver.util.WebUtil;

@Controller
@RequestMapping("/project")
public class ProjectController {
	@Autowired
	WeaverService weaverService;
	@Autowired
	ProjectService projectService;
	@Autowired
	PostService postService;
	@Autowired
	RePostService rePostService;
	@Autowired
	GitService gitService;
	@Autowired
	TagService tagService;

	@RequestMapping("/")
	public String projects() {
		return "redirect:/project/sort:age-desc/page:1";
	}
		
	@RequestMapping("/sort:{sort}/page:{page}")
	public String projectsWithPage(@PathVariable("page") String page,
			@PathVariable("sort") String sort,Model model) {
		int pageNum;
		int number = 15;
		
		if(page.contains(",")){
			pageNum = Integer.parseInt(page.split(",")[0]);
			number = Integer.parseInt(page.split(",")[1]);
		}else{
			pageNum =Integer.parseInt(page);
		}
		Weaver currentWeaver = weaverService.getCurrentWeaver();
		model.addAttribute("projects", projectService.getProjects(currentWeaver,sort, pageNum, number));
		model.addAttribute("projectCount", projectService.countProjects(sort));
		model.addAttribute("pageIndex", pageNum);
		model.addAttribute("number", number);
		model.addAttribute("pageUrl", "/project/sort:"+sort+"/page:");
		return "/project/projects";
	}
	
	
	
	@RequestMapping("/tags:{tagNames}")
	public String projectsWithTags(HttpServletRequest request){
		return "redirect:"+request.getRequestURI() +"/sort:age-desc/page:1";
	}
	
	@RequestMapping("/tags:{tagNames}/sort:{sort}/page:{page}")
	public String projectsWithTags(@PathVariable("tagNames") String tagNames,
			@PathVariable("page") String page,
			@PathVariable("sort") String sort,Model model) {
		int pageNum;
		int number = 15;
		List<String> tagList = tagService.stringToTagList(tagNames);
		if(page.contains(",")){
			pageNum = Integer.parseInt(page.split(",")[0]);
			number = Integer.parseInt(page.split(",")[1]);
		}else{
			pageNum =Integer.parseInt(page);
		}		
		Weaver currentWeaver = weaverService.getCurrentWeaver();
		model.addAttribute("projects", projectService.getProjectsWithTags(currentWeaver,tagList,sort, pageNum, number));
		model.addAttribute("projectCount", projectService.countProjectsWithTags(tagList,sort));
		model.addAttribute("pageIndex", pageNum);
		model.addAttribute("number", number);
		model.addAttribute("pageUrl", "/project/tags:"+tagNames+"sort:"+sort+"/page:");
		return "/project/projects";
	}
	
	@RequestMapping("/tags:{tagNames}/search:{search}")
	public String tagsWithSearch(HttpServletRequest request){
		return "redirect:"+request.getRequestURI() +"/sort:age-desc/page:1";
	}
	
	@RequestMapping("/tags:{tagNames}/search:{search}/sort:{sort}/page:{page}")
	public String tagsWithSearch(@PathVariable("tagNames") String tagNames,
			@PathVariable("page") String page,
			@PathVariable("search") String search,
			@PathVariable("sort") String sort,Model model) {
		int pageNum;
		int number = 15;
		List<String> tagList = tagService.stringToTagList(tagNames);
		if(page.contains(",")){
			pageNum = Integer.parseInt(page.split(",")[0]);
			number = Integer.parseInt(page.split(",")[1]);
		}else{
			pageNum =Integer.parseInt(page);
		}		
		Weaver currentWeaver = weaverService.getCurrentWeaver();
		model.addAttribute("projects", projectService.getProjectsWithTagsAndSearch(currentWeaver,tagList,search,sort, pageNum, number));
		model.addAttribute("projectCount", projectService.countProjectsWithTagsAndSearch(tagList,search,sort));
		model.addAttribute("pageIndex", pageNum);
		model.addAttribute("number", number);
		model.addAttribute("pageUrl", "/project/tags:"+tagNames+"/search:"+search+"/sort:"+sort+"/page:");
		return "/project/projects";
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String add(@RequestParam Map<String, String> params) {
		Weaver currentWeaver = weaverService.getCurrentWeaver();
		List<String> tagList = tagService.stringToTagList(params.get("tags"));
		int category = 1;
		if(params.get("category")!= null && params.get("category").equals("on"))
			category= 0;
		if(!tagService.isPublicTags(tagList))
			return "redirect:/project/";
		
		
		Project project = new Project(params.get("name"), 
										category, 
										WebUtil.removeHtml(params.get("description")), 
										currentWeaver,
										tagList);
		projectService.add(project,currentWeaver);
		return "redirect:/project/"+project.getName();
	}
	
	
	@RequestMapping("/{creatorName}/{projectName}/delete")
	public String delete(Model model,
			@PathVariable("creatorName") String creatorName,
			@PathVariable("projectName") String projectName) {
		Project project = projectService.get(creatorName+"/"+projectName);
		Weaver currentWeaver = weaverService.getCurrentWeaver();
		projectService.delete(currentWeaver, project);
		return "redirect:/project/";
	}
	
	@RequestMapping(value = "/down/{creatorName}/{projectName}-{commitName}.zip")
	public void getProjectZip(@PathVariable("projectName") String projectName,
			@PathVariable("creatorName") String creatorName,
			@PathVariable("commitName") String commitName,
			HttpServletResponse response) {
		if(!gitService.existCommit(creatorName, projectName, commitName))
			return;

		gitService.getProjectZip(creatorName, projectName, commitName, response);

		return;
	}
	
	
	@RequestMapping(value=
	{	"/{creatorName}/{projectName}", 
		"/{creatorName}/{projectName}/browser"}
	)
	public String browser(@PathVariable("projectName") String projectName,
			@PathVariable("creatorName") String creatorName,Model model) {
		Project project = projectService.get(creatorName+"/"+projectName);
		
		List<String> gitBranchList = gitService.getBranchList(creatorName, projectName);
		
		model.addAttribute("project", project);
		model.addAttribute("gitFileInfoList", 
				gitService.getGitSimpleFileInfoList(creatorName, projectName,"HEAD"));
		model.addAttribute("gitBranchList", gitBranchList.subList(1, gitBranchList.size()));
		model.addAttribute("selectBranch",gitBranchList.get(0));
		return "/project/browser";
	}
	
	@RequestMapping("/{creatorName}/{projectName}/browser/commit:{commit}")
	public String browser(@PathVariable("projectName") String projectName,
			@PathVariable("creatorName") String creatorName,
			@PathVariable("commit") String commit,Model model) {
		Project project = projectService.get(creatorName+"/"+projectName);
		

		model.addAttribute("project", project);
		model.addAttribute("gitFileInfoList", 
				gitService.getGitSimpleFileInfoList(creatorName, projectName,commit));
		List<String> gitBranchList = gitService.getBranchList(creatorName, projectName);
		gitBranchList.remove(commit);
		model.addAttribute("gitBranchList", gitBranchList);
		model.addAttribute("selectBranch",commit);
		
		return "/project/browser";
	}
	
	@RequestMapping("/{creatorName}/{projectName}/browser/commit:{commitID}/filepath:{filePath}")
	public String fileViewer(@PathVariable("projectName") String projectName,
			@PathVariable("creatorName") String creatorName,
			@PathVariable("commitID") String commitID,
			@PathVariable("filePath") String filePath,Model model) {
		Project project = projectService.get(creatorName+"/"+projectName);
		

		
		model.addAttribute("project", project);
		GitFileInfo gitFileInfo = gitService.getFileInfo(creatorName, projectName, commitID, filePath);
		model.addAttribute("fileName", gitFileInfo.getName());
		model.addAttribute("fileContent", gitFileInfo.getContent());
		model.addAttribute("gitLogList", gitFileInfo.getGitLogList());
		model.addAttribute("selectCommitIndex", gitFileInfo.getSelectCommitIndex());
		model.addAttribute("gitCommitLog", 
				new GitSimpleCommitLog(gitFileInfo.getSelectCommitLog()));
		return "/project/fileViewer";
	}

	@RequestMapping("/{creatorName}/{projectName}/community")
	public String community(HttpServletRequest request) {
		return "redirect:"+request.getRequestURI() +"/sort:age-desc/page:1";
	}
	
	@RequestMapping("/{creatorName}/{projectName}/community/sort:{sort}/page:{page}")
	public String community(@PathVariable("projectName") String projectName,
			@PathVariable("sort") String sort,
			@PathVariable("creatorName") String creatorName,
			@PathVariable("page") String page,Model model) {
		int pageNum;
		int number = 15;
		
		if(page.contains(",")){
			pageNum = Integer.parseInt(page.split(",")[0]);
			number = Integer.parseInt(page.split(",")[1]);
		}else{
			pageNum =Integer.parseInt(page);
		}	
		
		Project project = projectService.get(creatorName+"/"+projectName);
		Weaver currentWeaver = weaverService.getCurrentWeaver();
		List<String> tagList = new ArrayList<String>();
		tagList.add(new String("@"+creatorName+"/"+projectName));
		
		model.addAttribute("project", project);
		model.addAttribute("posts", 
				postService.getPostsWithTags(currentWeaver, tagList, sort, pageNum, number));
		model.addAttribute("postCount", 
				postService.countPostsWithTags(currentWeaver, tagList, sort));
		model.addAttribute("pageIndex", pageNum);
		model.addAttribute("pageUrl", "/project/"+creatorName+"/"+projectName+"/community/sort:"+sort+"/page:");
		return "/project/community";
	}

	@RequestMapping("/{creatorName}/{projectName}/community/tags:{tagNames}")
	public String tags(HttpServletRequest request){
		return "redirect:"+request.getRequestURI() +"/sort:age-desc/page:1";
	}
	
	@RequestMapping("/{creatorName}/{projectName}/community/tags:{tagNames}/sort:{sort}/page:{page}")
	public String tags(@PathVariable("projectName") String projectName,
			@PathVariable("creatorName") String creatorName,
			@PathVariable("tagNames") String tagNames,
			@PathVariable("sort") String sort,
			@PathVariable("page") String page,Model model){
		int pageNum;
		int number = 15;
		
		if(page.contains(",")){
			pageNum = Integer.parseInt(page.split(",")[0]);
			number = Integer.parseInt(page.split(",")[1]);
		}else{
			pageNum =Integer.parseInt(page);
		}	
		
		Project project = projectService.get(creatorName+"/"+projectName);
		List<String> tagList = tagService.stringToTagList(tagNames);
		tagList.add(new String("@"+creatorName+"/"+projectName));
		Weaver currentWeaver = weaverService.getCurrentWeaver();
	
		model.addAttribute("project", project);
		model.addAttribute("posts", 
				postService.getPostsWithTags(currentWeaver, tagList, sort, pageNum, number));
		model.addAttribute("postCount", 
				postService.countPostsWithTags(currentWeaver, tagList, sort));

		model.addAttribute("pageIndex", pageNum);
		model.addAttribute("pageUrl", 
				"/project/"+projectName+"/community/tags:"+tagNames+"/sort:"+sort+"/page:");
		return "/project/community";
	}
	
	@RequestMapping(value = "/{creatorName}/{projectName}/community/add", method = RequestMethod.POST)
	public String addPost(@PathVariable("projectName") String projectName,
			@PathVariable("creatorName") String creatorName,
			HttpServletRequest request) {
		String tags = request.getParameter("tags");
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		
		
		if(tags == null || title == null) // 태그가 없을 때
			return "redirect:/project/"+creatorName+"/"+projectName+"/community/";
		else if(content == null)
			content = "";
		List<String> tagList = tagService.stringToTagList(
				WebUtil.removeHtml(WebUtil.specialSignDecoder(URLDecoder.decode(tags))));
		tagList.add(new String("@"+creatorName+"/"+projectName));
		Weaver weaver = weaverService.getCurrentWeaver();

		if(!tagService.validateTag(tagList,weaver)) // 태그에 권한이 없을때
			return "redirect:/project/"+creatorName+"/"+projectName+"/community/";

		Post post = new Post(weaver,
				WebUtil.removeHtml(WebUtil.specialSignDecoder(URLDecoder.decode(title))), 
				WebUtil.convertHtml(WebUtil.removeHtml(WebUtil.specialSignDecoder(URLDecoder.decode(content)))), 
				tagList);
		
		postService.add(post,null);
		return "redirect:/project/"+creatorName+"/"+projectName+"/community/";
	}
	
	@RequestMapping("/{creatorName}/{projectName}/commitlog")
	public String commitLog(@PathVariable("projectName") String projectName,
			@PathVariable("creatorName") String creatorName,Model model) {
		Project project = projectService.get(creatorName+"/"+projectName);
	
		List<String> gitBranchList = gitService.getBranchList(creatorName, projectName);
		
		model.addAttribute("gitBranchList", gitBranchList.subList(1, gitBranchList.size()));
		model.addAttribute("selectBranch",gitBranchList.get(0));
		model.addAttribute("project", project);
		model.addAttribute("pageIndex",1);
		model.addAttribute("gitCommitListCount", 
				gitService.getCommitListCount(creatorName, projectName,gitBranchList.get(0)));
		model.addAttribute("gitCommitList", 
				gitService.getGitCommitLogList(creatorName, projectName,gitBranchList.get(0),1,10));
			
		return "/project/commitLog";
	}
	
	@RequestMapping("/{creatorName}/{projectName}/commitlog/commit:{commit}")
	public String commitLog(@PathVariable("projectName") String projectName,
			@PathVariable("creatorName") String creatorName,
			@PathVariable("commit") String commit,Model model) {
		Project project = projectService.get(creatorName+"/"+projectName);

		List<String> gitBranchList = gitService.getBranchList(creatorName, projectName);
		gitBranchList.remove(commit);
		model.addAttribute("gitBranchList", gitBranchList);
		model.addAttribute("selectBranch",commit);
		model.addAttribute("project", project);
		model.addAttribute("pageIndex",1);
		model.addAttribute("gitCommitListCount", 
				gitService.getCommitListCount(creatorName, projectName,commit));
		model.addAttribute("gitCommitList", 
				gitService.getGitCommitLogList(creatorName, projectName,commit,1,10));
		return "/project/commitLog";
	}
	
	@RequestMapping("/{creatorName}/{projectName}/commitlog/commit:{commit}/page:{page}")
	public String commitLog(@PathVariable("projectName") String projectName,
			@PathVariable("creatorName") String creatorName,
			@PathVariable("commit") String commit,
			@PathVariable("page") int page,Model model) {
		Project project = projectService.get(creatorName+"/"+projectName);
		
		List<String> gitBranchList = gitService.getBranchList(creatorName, projectName);
		gitBranchList.remove(commit);
		model.addAttribute("gitBranchList", gitBranchList);
		model.addAttribute("selectBranch",commit);
		model.addAttribute("project", project);
		model.addAttribute("pageIndex",page);
		model.addAttribute("gitCommitListCount", 
				gitService.getCommitListCount(creatorName, projectName,commit));
		model.addAttribute("gitCommitList", 
				gitService.getGitCommitLogList(creatorName, projectName,commit,page,10));
		return "/project/commitLog";
	}

	@RequestMapping("/{creatorName}/{projectName}/commitlog-viewer/commit:{commit}")
	public String commitLogViewer(@PathVariable("projectName") String projectName,
			@PathVariable("creatorName") String creatorName,
			@PathVariable("commit") String commit,Model model) {
		Project project = projectService.get(creatorName+"/"+projectName);
		model.addAttribute("project", project);
		model.addAttribute("gitCommitLog", 
				gitService.getGitCommitLog(creatorName, projectName, commit));
		return "/project/commitLogViewer";
	}
	
		
	@RequestMapping("/{creatorName}/{projectName}/weaver")
	public String manageWeaver(@PathVariable("projectName") String projectName,
			@PathVariable("creatorName") String creatorName,Model model) {
		Project project = projectService.get(creatorName+"/"+projectName);
		model.addAttribute("project", project);
		return "/project/manageWeaver";
	}
	
	@RequestMapping(value = "/{creatorName}/{projectName}/weaver:{weaverName}/delete") // 회원 삭제용
	public String deleteWeaver(@PathVariable("projectName") String projectName,
			@PathVariable("creatorName") String creatorName,
			@PathVariable("weaverName") String weaverName) {
		Project project = projectService.get(creatorName+"/"+projectName);
		Weaver currentWeaver = weaverService.getCurrentWeaver();
		Weaver deleteWeaver = weaverService.get(weaverName);
		
		if(projectService.deleteWeaver(project, currentWeaver,deleteWeaver)){
			Post post;
			if(currentWeaver.getId().equals(project.getCreatorName())){//관리자가 탈퇴시킬시에 메세지
				post = new Post(currentWeaver, 
						deleteWeaver.getId()+"님을 탈퇴 처리하였습니다.", "", 
						tagService.stringToTagList("@"+project.getName()+",탈퇴"));//프로젝트에 메세지 보냄
				postService.add(post,null);
				post = new Post(currentWeaver, 
						"프로젝트명:"+project.getName()+"에서 탈퇴당하셨습니다.", "", 
						tagService.stringToTagList("$"+deleteWeaver.getId()));//프로젝트에 메세지 보냄
				postService.add(post, null);
			}else{//사용자가 탈퇴할시에 메세지
				post = new Post(currentWeaver, 
						deleteWeaver.getId()+"님이 탈퇴하셨습니다.", "", 
						tagService.stringToTagList("@"+project.getName()+",탈퇴"));//프로젝트에 메세지 보냄
				postService.add(post, null);
			}
			
		}
		return "redirect:/";
	}
	
	@RequestMapping(value="/{creatorName}/{projectName}/upload" , method=RequestMethod.POST)
	public String upload(@PathVariable("projectName") String projectName,
			@PathVariable("creatorName") String creatorName,
			@RequestParam("message") String message,
			@RequestParam("zip") MultipartFile zip) {
		Project project = projectService.get(creatorName+"/"+projectName);
		Weaver currentWeaver = weaverService.getCurrentWeaver();
		projectService.uploadZip(project, currentWeaver, message, zip);
		
		return "redirect:/project/"+creatorName+"/"+projectName;
	}
	
	@RequestMapping("/{creatorName}/{projectName}/push")
	public String push(@PathVariable("projectName") String projectName,
			@PathVariable("creatorName") String creatorName) {
		Project project = projectService.get(creatorName+"/"+projectName);
		Weaver currentWeaver = weaverService.getCurrentWeaver();
		
		projectService.push(project, currentWeaver);
		
		return "redirect:/project/";
	}

}
