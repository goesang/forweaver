package com.forweaver.controller;

/*
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
	@Autowired
	PermissionService permissionService;

	@RequestMapping("/")
	public String projects(Model model) {
		model.addAttribute("projects", projectService.checkJoinProject(
				projectService.getProjects(0, 10),weaverService.getCurrentWeaver()));
		model.addAttribute("projectCount", projectService.countProjects());
		model.addAttribute("pageIndex", 1);
		model.addAttribute("pageUrl", "/project/page:");
		return "/project/projects";
	}
		
	@RequestMapping("/page:{page}")
	public String projectsWithPage(Model model,@PathVariable("page") int page) {
		model.addAttribute("projects", projectService.checkJoinProject(
				projectService.getProjects((page-1)*10, 10),weaverService.getCurrentWeaver()));
		model.addAttribute("projectCount", projectService.countProjects());
		model.addAttribute("pageIndex", page);
		model.addAttribute("pageUrl", "/project/page:");
		return "/project/projects";
	}
	
	@RequestMapping("/tags:{tagNames}")
	public String projectsWithTags(Model model,@PathVariable("tagNames") String tagNames) {
		List<Tag> tagList = tagService.stringToTagList(tagNames);
		model.addAttribute("projects", projectService.checkJoinProject(
				projectService.getProjectsWithTags(tagList, 0, 10),weaverService.getCurrentWeaver()));
		model.addAttribute("projectCount", projectService.countProjectsWithTags(tagList));
		model.addAttribute("pageIndex", 1);
		model.addAttribute("pageUrl", "/project/tags:"+tagNames+"/page:");
		return "/project/projects";
	}
	
	@RequestMapping("/tags:{tagNames}/page:{page}")
	public String projectsWithTags(Model model,
			@PathVariable("tagNames") String tagNames,@PathVariable("page") int page) {
		List<Tag> tagList = tagService.stringToTagList(tagNames);
		model.addAttribute("projects", projectService.checkJoinProject(
				projectService.getProjectsWithTags(tagList, (page-1)*10, 10),weaverService.getCurrentWeaver()));
		model.addAttribute("projectCount", projectService.countProjectsWithTags(tagList));
		model.addAttribute("pageIndex", page);
		model.addAttribute("pageUrl", "/project/tags:"+tagNames+"/page:");
		return "/project/projects";
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public boolean add(@RequestParam Map<String, String> params) {
		Weaver currentWeaver = weaverService.getCurrentWeaver();
		List<Tag> tagList = tagService.stringToTagList(params.get("tags"));
		if(!tagService.isPublicTags(tagList))
			return false;
		Project project = new Project(params.get("name"), 
										Integer.parseInt(params.get("category")), 
										WebUtil.removeHtml(params.get("description")), 
										Integer.parseInt(params.get("period")), 
										currentWeaver,
										tagList);
		projectService.add(project,currentWeaver);
		return true;
	}
	
	
	@RequestMapping("/{creatorName}/{projectName}/delete")
	public String delete(Model model,
			@PathVariable("creatorName") String creatorName,
			@PathVariable("projectName") String projectName) {
		if(!creatorName.equals(weaverService.getCurrentWeaver().getName()))
			return "redirect:/project/";
		Project project = projectService.get(creatorName+"/"+projectName);
		if(project == null)
			return "redirect:/project/";
		weaverService.deleteCurrentWeaverPass(project.getWeavers(), project.getName());
		projectService.delete(project);
		return "redirect:/project/";
	}
	
	@RequestMapping(value = "/down/{creatorName}/{projectName}-{commitName}.zip")
	public void getProjectZip(@PathVariable("projectName") String projectName,
			@PathVariable("creatorName") String creatorName,
			@PathVariable("commitName") String commitName,
			HttpServletResponse response) {
		Project project = projectService.get(creatorName+"/"+projectName);

		if(!permissionService.projectPermission(project, weaverService.getCurrentWeaver()))
			return;

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
			@PathVariable("creatorName") String creatorName,Model model,Device device) {
		Project project = projectService.get(creatorName+"/"+projectName);

		if(!permissionService.projectPermission(project, weaverService.getCurrentWeaver())){
			  if (device.isMobile())
				  return "redirect:/m/project/";
			return "redirect:/project/";
		}
		
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
			@PathVariable("commit") String commit,Model model,Device device) {
		Project project = projectService.get(creatorName+"/"+projectName);
		
		if(!permissionService.projectPermission(project, weaverService.getCurrentWeaver())){
			  if (device.isMobile())
				  return "redirect:/m/project/";
			return "redirect:/project/";
		}
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
			@PathVariable("filePath") String filePath,Model model,Device device) {
		Project project = projectService.get(creatorName+"/"+projectName);
		
		if(!permissionService.projectPermission(project, weaverService.getCurrentWeaver())){
			  if (device.isMobile())
				  return "redirect:/m/project/";
			return "redirect:/project/";
		}
		
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
	public String community(HttpServletRequest request,
			@PathVariable("projectName") String projectName,
			@PathVariable("creatorName") String creatorName,Model model,Device device) {
		Project project = projectService.get(creatorName+"/"+projectName);
		
		if(!permissionService.projectPermission(project, weaverService.getCurrentWeaver())){
			  if (device.isMobile())
				  return "redirect:/m/project/";
			return "redirect:/project/";
		}
		
		List<Tag> tagList = new ArrayList<Tag>();
		tagList.add(new Tag("@"+creatorName+"/"+projectName));
		
		if(!tagService.validateTag(tagList)){
			return "redirect:/"+creatorName+"/"+projectName+"/community/";
		}
		return "redirect:"+request.getRequestURI() +"/sort:age-desc/page:1";
	}
	
	@RequestMapping("/{creatorName}/{projectName}/community/sort:{sort}/page:{page}")
	public String community(@PathVariable("projectName") String projectName,
			@PathVariable("sort") String sort,
			@PathVariable("creatorName") String creatorName,
			@PathVariable("page") int page,Model model,Device device) {
		Project project = projectService.get(creatorName+"/"+projectName);
		
		if(!permissionService.projectPermission(project, weaverService.getCurrentWeaver())){
			  if (device.isMobile())
				  return "redirect:/m/project/";
			return "redirect:/project/";
		}
		
		List<Tag> tagList = new ArrayList<Tag>();
		tagList.add(new Tag("@"+creatorName+"/"+projectName));
		
		if(!tagService.validateTag(tagList)){
			return "redirect:/"+creatorName+"/"+projectName+"/community/";
		}
		
		model.addAttribute("project", project);
		model.addAttribute("posts", 
				tagService.getPostsWhenWeaver(sort,tagList,tagList.size()-1,(page-1)*10,10));
		model.addAttribute("postCount", 
				tagService.countPostsWhenWeaver(sort,tagList,tagList.size()-1));
		model.addAttribute("pageIndex", page);
		model.addAttribute("pageUrl", "/project/"+creatorName+"/"+projectName+"/community/sort:"+sort+"/page:");
		return "/project/community";
	}

	@RequestMapping("/{creatorName}/{projectName}/community/tags:{tagNames}")
	public String tags(HttpServletRequest request,
			@PathVariable("projectName") String projectName,
			@PathVariable("creatorName") String creatorName,
			@PathVariable("tagNames") String tagNames,Model model,Device device){
		Project project = projectService.get(creatorName+"/"+projectName);
		
		if(!permissionService.projectPermission(project, weaverService.getCurrentWeaver())){
			  if (device.isMobile())
				  return "redirect:/m/project/";
			return "redirect:/project/";
		}
		
		List<Tag> tagList = tagService.stringToTagList(tagNames);
		tagList.add(new Tag("@"+creatorName+"/"+projectName));
		
		if(!tagService.validateTag(tagList)){
			return "redirect:/project/"+creatorName+"/"+projectName+"/community/";
		}
		
		return "redirect:"+request.getRequestURI() +"/sort:age-desc/page:1";
	}
	
	@RequestMapping("/{creatorName}/{projectName}/community/tags:{tagNames}/sort:{sort}/page:{page}")
	public String tags(@PathVariable("projectName") String projectName,
			@PathVariable("creatorName") String creatorName,
			@PathVariable("tagNames") String tagNames,
			@PathVariable("sort") String sort,
			@PathVariable("page") int page,Model model,Device device){
		Project project = projectService.get(creatorName+"/"+projectName);
		
		if(!permissionService.projectPermission(project, weaverService.getCurrentWeaver())){
			  if (device.isMobile())
				  return "redirect:/m/project/";
			return "redirect:/project/";
		}
		
		List<Tag> tagList = tagService.stringToTagList(tagNames);
		tagList.add(new Tag("@"+creatorName+"/"+projectName));
		
		if(!tagService.validateTag(tagList)){
			return "redirect:/project/"+creatorName+"/"+projectName+"/community/";
		}
		
		model.addAttribute("project", project);
		model.addAttribute("posts", 
				tagService.getPostsWhenWeaver(sort,tagList,tagList.size()-1,(page-1)*10,10));
		model.addAttribute("postCount", 
				tagService.countPostsWhenWeaver(sort,tagList,tagList.size()-1));

		model.addAttribute("pageIndex", page);
		model.addAttribute("pageUrl", "/project/"+creatorName+"/"+projectName+"/community/tags:"+tagNames+"/sort:"+sort+"/page:");
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
		List<Tag> tagList = tagService.stringToTagList(
				WebUtil.removeHtml(WebUtil.specialSignDecoder(URLDecoder.decode(tags))));
		tagList.add(new Tag("@"+creatorName+"/"+projectName));
		Weaver weaver = weaverService.getCurrentWeaver();

		if(!tagService.validateTag(tagList,weaver)) // 태그에 권한이 없을때
			return "redirect:/project/"+creatorName+"/"+projectName+"/community/";

		Post post = new Post(weaver,
				WebUtil.removeHtml(WebUtil.specialSignDecoder(URLDecoder.decode(title))), 
				WebUtil.convertHtml(WebUtil.removeHtml(WebUtil.specialSignDecoder(URLDecoder.decode(content)))), 
				tagList);
		
		postService.add(post);
		return "redirect:/project/"+creatorName+"/"+projectName+"/community/";
	}
	
	@RequestMapping("/{creatorName}/{projectName}/commitlog")
	public String commitLog(@PathVariable("projectName") String projectName,
			@PathVariable("creatorName") String creatorName,Model model,Device device) {
		Project project = projectService.get(creatorName+"/"+projectName);
		
		if(!permissionService.projectPermission(project, weaverService.getCurrentWeaver())){
			  if (device.isMobile())
				  return "redirect:/m/project/";
			return "redirect:/project/";
		}
		
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
			@PathVariable("commit") String commit,Model model,Device device) {
		Project project = projectService.get(creatorName+"/"+projectName);
		
		if(!permissionService.projectPermission(project, weaverService.getCurrentWeaver())){
			  if (device.isMobile())
				  return "redirect:/m/project/";
			return "redirect:/project/";
		}
		
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
			@PathVariable("page") int page,Model model,Device device) {
		Project project = projectService.get(creatorName+"/"+projectName);
		
		if(!permissionService.projectPermission(project, weaverService.getCurrentWeaver())){
			  if (device.isMobile())
				  return "redirect:/m/project/";
			return "redirect:/project/";
		}
		
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
			@PathVariable("commit") String commit,Model model,Device device) {
		Project project = projectService.get(creatorName+"/"+projectName);
		
		if(!permissionService.projectPermission(project, weaverService.getCurrentWeaver())){
			  if (device.isMobile())
				  return "redirect:/m/project/";
			return "redirect:/project/";
		}

		model.addAttribute("project", project);
		model.addAttribute("gitCommitLog", 
				gitService.getGitCommitLog(creatorName, projectName, commit));
		return "/project/commitLogViewer";
	}
	
		
	@RequestMapping("/{creatorName}/{projectName}/weaver")
	public String manageWeaver(@PathVariable("projectName") String projectName,
			@PathVariable("creatorName") String creatorName,Model model,Device device) {
		Project project = projectService.get(creatorName+"/"+projectName);
		
		if(!permissionService.projectPermission(project, weaverService.getCurrentWeaver())){
			  if (device.isMobile())
				  return "redirect:/m/project/";
			return "redirect:/project/";
		}
		
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
			if(currentWeaver.getName().equals(project.getCreatorName())){//관리자가 탈퇴시킬시에 메세지
				post = new Post(currentWeaver, 
						deleteWeaver.getName()+"님을 탈퇴 처리하였습니다.", "", 
						tagService.stringToTagList("@"+project.getName()+",탈퇴"));//프로젝트에 메세지 보냄
				postService.add(post);
				post = new Post(currentWeaver, 
						"프로젝트명:"+project.getName()+"에서 탈퇴당하셨습니다.", "", 
						tagService.stringToTagList("$"+deleteWeaver.getName()));//프로젝트에 메세지 보냄
				postService.add(post);
			}else{//사용자가 탈퇴할시에 메세지
				post = new Post(currentWeaver, 
						deleteWeaver.getName()+"님이 탈퇴하셨습니다.", "", 
						tagService.stringToTagList("@"+project.getName()+",탈퇴"));//프로젝트에 메세지 보냄
				postService.add(post);
			}
			
		}
		return "redirect:/";
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
*/
