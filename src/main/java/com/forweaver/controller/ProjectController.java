package com.forweaver.controller;

import java.io.UnsupportedEncodingException;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.forweaver.domain.CherryPickRequest;
import com.forweaver.domain.Data;
import com.forweaver.domain.Lecture;
import com.forweaver.domain.Pass;
import com.forweaver.domain.Post;
import com.forweaver.domain.Project;
import com.forweaver.domain.RePost;
import com.forweaver.domain.Reply;
import com.forweaver.domain.Repo;
import com.forweaver.domain.WaitJoin;
import com.forweaver.domain.Weaver;
import com.forweaver.domain.chat.ChatRoom;
import com.forweaver.domain.git.GitCommitLog;
import com.forweaver.domain.git.GitFileInfo;
import com.forweaver.domain.git.GitSimpleCommitLog;
import com.forweaver.domain.git.GitSimpleFileInfo;
import com.forweaver.service.ChatService;
import com.forweaver.service.CherryPickRequestService;
import com.forweaver.service.DataService;
import com.forweaver.service.GitService;
import com.forweaver.service.LectureService;
import com.forweaver.service.PostService;
import com.forweaver.service.ProjectService;
import com.forweaver.service.RePostService;
import com.forweaver.service.TagService;
import com.forweaver.service.WaitJoinService;
import com.forweaver.service.WeaverService;
import com.forweaver.util.WebUtil;

@Controller
@RequestMapping("/project")
public class ProjectController {

	@Autowired WaitJoinService waitJoinService;
	@Autowired WeaverService weaverService;
	@Autowired ProjectService projectService;
	@Autowired PostService postService;
	@Autowired RePostService rePostService;
	@Autowired GitService gitService;
	@Autowired TagService tagService;
	@Autowired ChatService chatService;
	@Autowired CherryPickRequestService cherryPickRequestService;
	@Autowired LectureService lectureService;
	@Autowired DataService dataService;

	@RequestMapping("/")
	public String projects() {
		return "redirect:/project/sort:age-desc/page:1";
	}

	@RequestMapping(value = "/{creatorName}/{projectName}/{commitName}/{download}.zip")
	public void getProjectZip(@PathVariable("projectName") String projectName,
			@PathVariable("creatorName") String creatorName,
			@PathVariable("commitName") String commitName,
			HttpServletResponse response) {
		if(!gitService.existCommit(creatorName, projectName, commitName))
			return;

		gitService.getProjectZip(creatorName, projectName, commitName, response);

		return;
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
		if(projectService.delete(currentWeaver, project)){
			if(project.getCategory() == 2){
				Lecture lecture = lectureService.get(project.getOriginalProject().split("/")[0]);
				Repo repo = lecture.getRepo(project.getOriginalProject().split("/")[1]);
				repo.getIsNotJoinWeaver().add(currentWeaver);
				lectureService.update(lecture);
			}
		}
		return "redirect:/project/";
	}

	@RequestMapping(value=
		{	"/{creatorName}/{projectName}", 
		"/{creatorName}/{projectName}/browser"}
			)
	public String browser(@PathVariable("projectName") String projectName,
			@PathVariable("creatorName") String creatorName,Model model) {
		Project project = projectService.get(creatorName+"/"+projectName);

		List<String> gitBranchList = gitService.getBranchList(creatorName, projectName);
		String readme = "";

		List<GitSimpleFileInfo> gitFileInfoList = 
				gitService.getGitSimpleFileInfoList(creatorName, projectName,"HEAD");

		if(gitFileInfoList != null) for(GitSimpleFileInfo gitSimpleFileInfo:gitFileInfoList)// 파일들을 검색해서 리드미 파일을 찾아냄
			if(gitSimpleFileInfo.getDepth() == 0 && gitSimpleFileInfo.getName().toUpperCase().equals("README.MD"))
				readme = WebUtil.markDownEncoder(
						gitService.getFileInfo(
								creatorName, 
								projectName, 
								"HEAD", 
								gitSimpleFileInfo.getName()).getContent());

		model.addAttribute("project", project);
		model.addAttribute("gitFileInfoList", 
				gitService.getGitSimpleFileInfoList(creatorName, projectName,"HEAD"));
		model.addAttribute("gitBranchList", gitBranchList.subList(1, gitBranchList.size()));
		model.addAttribute("selectBranch",gitBranchList.get(0));
		model.addAttribute("readme",readme);
		return "/project/browser";
	}

	@RequestMapping("/{creatorName}/{projectName}/browser/commit:{commit}")
	public String browser(@PathVariable("projectName") String projectName,
			@PathVariable("creatorName") String creatorName,
			@PathVariable("commit") String commit,Model model) {
		Project project = projectService.get(creatorName+"/"+projectName);
		String readme = "";

		commit = commit.replace(",", ".");
		List<GitSimpleFileInfo> gitFileInfoList = 
				gitService.getGitSimpleFileInfoList(creatorName, projectName,commit);

		for(GitSimpleFileInfo gitSimpleFileInfo:gitFileInfoList) // 파일들을 검색해서 리드미 파일을 찾아냄
			if(gitSimpleFileInfo.getName().toUpperCase().equals("README.MD"))
				readme = WebUtil.markDownEncoder(
						gitService.getFileInfo(
								creatorName, 
								projectName, 
								commit, 
								gitSimpleFileInfo.getName()).getContent());


		model.addAttribute("project", project);
		model.addAttribute("gitFileInfoList", gitFileInfoList);
		List<String> gitBranchList = gitService.getBranchList(creatorName, projectName);
		gitBranchList.remove(commit);
		model.addAttribute("gitBranchList", gitBranchList);
		model.addAttribute("selectBranch",commit);
		model.addAttribute("readme",readme);
		return "/project/browser";
	}

	@RequestMapping("/{creatorName}/{projectName}/browser/commit:{commitID}/filepath:{filePath}")
	public String fileViewer(@PathVariable("projectName") String projectName,
			@PathVariable("creatorName") String creatorName,
			@PathVariable("commitID") String commitID,
			@PathVariable("filePath") String filePath,Model model) {
		Project project = projectService.get(creatorName+"/"+projectName);
		commitID = commitID.replace(",", ".");
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

	@RequestMapping("/{creatorName}/{projectName}/browser/commit:{commitID}/filepath:{filePath}/blame")
	public String blame(@PathVariable("projectName") String projectName,
			@PathVariable("creatorName") String creatorName,
			@PathVariable("commitID") String commitID,
			@PathVariable("filePath") String filePath,Model model) {
		Project project = projectService.get(creatorName+"/"+projectName);
		commitID = commitID.replace(",", ".");
		model.addAttribute("project", project);
		GitFileInfo gitFileInfo = gitService.getFileInfo(creatorName, projectName, commitID, filePath);
		model.addAttribute("fileName", gitFileInfo.getName());
		model.addAttribute("fileContent", gitFileInfo.getContent());
		model.addAttribute("gitLogList", gitFileInfo.getGitLogList());
		model.addAttribute("gitBlameList", gitFileInfo.getGitBlames());
		model.addAttribute("selectCommitIndex", gitFileInfo.getSelectCommitIndex());
		model.addAttribute("gitCommitLog", 
				new GitSimpleCommitLog(gitFileInfo.getSelectCommitLog()));
		return "/project/blame";
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
				WebUtil.removeHtml(WebUtil.specialSignDecoder(URLDecoder.decode(content))), 
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

	@RequestMapping("/{creatorName}/{projectName}/rss") 
	@ResponseBody
	public String rss(@PathVariable("projectName") String projectName,
			@PathVariable("creatorName") String creatorName,Model model) {
		List<String> gitBranchList = gitService.getBranchList(creatorName, projectName);

		String rss = "<?xml version='1.0' encoding='UTF-8'?><rss version='2.0'><channel>";

		rss +="<title>project:"+creatorName+"/"+projectName+"</title>";
		rss +="<link>http://forweaver.com/project/"+creatorName+"/"+projectName+"</link>";
		rss +="<description>project:"+creatorName+"/"+projectName+"</description>";

		for(GitSimpleCommitLog commitLog:gitService.getGitCommitLogList(creatorName, projectName,gitBranchList.get(0),1,10)){
			rss +="<item>";
			rss +="<author>"+commitLog.getCommiterName()+" ("+commitLog.getCommiterEmail()+")</author>";
			rss +="<title>"+commitLog.getShortMassage()+"</title>";
			rss +="<link>http://forweaver.com/project/"+creatorName+"/"+projectName+"</link>";
			rss +="<description>"+commitLog.getShortMassage()+"</description>";
			rss +="<pubDate>"+commitLog.getCommitDate()+"</pubDate>";
			rss +="<image>http://forweaver.com/"+commitLog.getImgSrc()+"</image>";
			rss +="</item>";
		}
		return rss+"</channel></rss>";
	}



	@RequestMapping("/{creatorName}/{projectName}/commitlog/commit:{commit}")
	public String commitLog(@PathVariable("projectName") String projectName,
			@PathVariable("creatorName") String creatorName,
			@PathVariable("commit") String commit,Model model) {
		Project project = projectService.get(creatorName+"/"+projectName);
		commit = commit.replace(",", ".");
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
		GitCommitLog gitCommitLog = gitService.getGitCommitLog(creatorName, projectName, commit);
		if(gitCommitLog == null)
			return "redirect:/project/"+ creatorName+"/"+projectName+"/commitlog";
		model.addAttribute("project", project);
		model.addAttribute("gitCommitLog",gitCommitLog);
		model.addAttribute("rePosts", rePostService.get(project.getName()+"/"+gitCommitLog.getCommitLogID(),5,""));
		return "/project/commitLogViewer";
	}


	@RequestMapping("/{creatorName}/{projectName}/weaver")
	public String manageWeaver(@PathVariable("projectName") String projectName,
			@PathVariable("creatorName") String creatorName,Model model) {
		Project project = projectService.get(creatorName+"/"+projectName);
		if(project.getCategory() == 2)
			model.addAttribute("weavers", lectureService.getRepo(project.getOriginalProject()).getIsNotJoinWeaver());
		model.addAttribute("project", project);
		return "/project/manageWeaver";
	}

	@RequestMapping(value = "/{creatorName}/{projectName}/weaver/{weaverName}/delete") // 회원 삭제용
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

	@RequestMapping("/{creatorName}/{projectName}/weaver/{weaver}/add-weaver")
	public String addWeaver(	@PathVariable("projectName") String projectName,
			@PathVariable("creatorName") String creatorName,
			@PathVariable("weaver") String weaver) {
		Project project = projectService.get(creatorName+"/"+projectName);
		Weaver waitingWeaver = weaverService.get(weaver);
		Weaver proposer = weaverService.getCurrentWeaver();

		if(project.getCategory() == 2  //팀 프로젝트의 경우 오로지 가입하지 않는 위버만 추가 가능.
				&& !lectureService.getRepo(
						project.getOriginalProject()).isNotJoinWeaver(waitingWeaver))
			return "redirect:/project/"+ creatorName+"/"+projectName+"/weaver";

		if(waitJoinService.isCreateProjectWaitJoin(project, waitingWeaver, proposer)){
			Weaver projectCreator = weaverService.get(project.getCreatorName());
			String title ="프로젝트명:"+creatorName+"/"+projectName+"에 가입 초대를 <a href='/project/"+creatorName+"/"+projectName+"/weaver/"+weaver+"/join-ok'>승락하시겠습니까?</a> "
					+ "아니면 <a href='/project/"+creatorName+"/"+projectName+"/weaver/"+weaver+"/join-cancel'>거절하시겠습니까?</a>";

			Post post = new Post(projectCreator,
					title, 
					"", 
					tagService.stringToTagList("$"+weaver));
			waitJoinService.createWaitJoin(
					project.getName(), 
					proposer.getId(), 
					waitingWeaver.getId(), 
					postService.add(post,null));
		}
		return "redirect:/project/"+ creatorName+"/"+projectName+"/weaver";			

	}


	@RequestMapping("/{creatorName}/{projectName}/join") //본인이 직접 신청
	public String join(	@PathVariable("projectName") String projectName,
			@PathVariable("creatorName") String creatorName) {
		Project project = projectService.get(creatorName+"/"+projectName);
		Weaver waitingWeaver = weaverService.getCurrentWeaver();

		if(waitJoinService.isCreateProjectWaitJoin(project, waitingWeaver, waitingWeaver)){
			String title = waitingWeaver.getId()+"님이 프로젝트명:"+creatorName+"/"+projectName+"에 가입 신청을 <a href='/project/"+creatorName+"/"+projectName+"/weaver/"+waitingWeaver.getId()+"/join-ok'>승락하시겠습니까?</a> "
					+ "아니면 <a href='/project/"+creatorName+"/"+projectName+"/weaver/"+waitingWeaver.getId()+"/join-cancel'>거절하시겠습니까?</a>";
			Post post = new Post(waitingWeaver,
					title, 
					"", 
					tagService.stringToTagList("$"+project.getCreatorName()));
			waitJoinService.createWaitJoin(
					project.getName(), 
					waitingWeaver.getId(), 
					waitingWeaver.getId(), 
					postService.add(post,null));

		}
		return "redirect:/";			

	}


	@RequestMapping("/{creatorName}/{projectName}/weaver/{weaver}/join-ok") // 프로젝트 가입 승인
	public String joinOK(@PathVariable("projectName") String projectName,
			@PathVariable("creatorName") String creatorName,@PathVariable("weaver") String weaver) {
		Project project = projectService.get(creatorName+"/"+projectName);
		Weaver currentWeaver = weaverService.getCurrentWeaver();
		Weaver waitingWeaver = weaverService.get(weaver);
		WaitJoin waitJoin = waitJoinService.get(creatorName+"/"+projectName, weaver);
		Pass pass = new Pass(creatorName+"/"+projectName, 0);

		if(waitJoinService.isOkJoin(waitJoin, project.getCreatorName(), currentWeaver) //요청자가 쪽지를 보내고 관리자가 승인을 하는 경우
				&& project.getCreatorName().equals(currentWeaver.getId())
				&& waitJoinService.deleteProjectWaitJoin(waitJoin, project, waitingWeaver)){

			project.addJoinWeaver(waitingWeaver); //프로젝트 목록에 추가
			waitingWeaver.addPass(pass);
			weaverService.update(waitingWeaver);
			projectService.update(project);
			Post post = new Post(waitingWeaver, 
					"관리자 "+project.getCreatorName()+"님의 승인으로 프로젝트명:"+
							"<a href='/project/"+creatorName+"/"+projectName+"/'>"+
							creatorName+"/"+projectName+
							"</a>"+
							"에 가입이 승인되었습니다!", 
							"", 
							tagService.stringToTagList("@"+project.getName()+",가입")); //@프로젝트명,가입 태그를 걸어줌

			postService.add(post,null);

			return "redirect:/project/"+creatorName+"/"+projectName+"/weaver";

		}else if(project != null //관리자가 쪽지를 보내고 가입자가 승인을 하는 경우
				&& waitJoinService.isOkJoin(waitJoin, project.getCreatorName(), currentWeaver)
				&& !project.getCreatorName().equals(currentWeaver.getId())
				&& waitJoinService.deleteProjectWaitJoin(waitJoin, project, currentWeaver)){
			project.addJoinWeaver(currentWeaver); //프로젝트 목록에 추가
			currentWeaver.addPass(pass);
			weaverService.update(currentWeaver);
			projectService.update(project);

			Post post = new Post(currentWeaver, //가입자가 관리자에게 보내는 메세지
					currentWeaver.getId()+"님이 프로젝트명:"+
					"<a href='/project/"+creatorName+"/"+projectName+"'>"+
					creatorName+"/"+projectName+
					"</a>"+"를 가입 초대를 수락하셨습니다!", 
					"", 
					tagService.stringToTagList("@"+project.getName()+",가입")); //@프로젝트명,가입 태그를 걸어줌

			postService.add(post,null);

			return "redirect:/project/"+creatorName+"/"+projectName+"/manage";
		}

		return "redirect:/";//엉뚱한 사람이 들어올때 그냥 돌려보냄
	}

	@RequestMapping("/{creatorName}/projectName}/weaver/{weaver}/join-cancel") //프로젝트에 가입 승인 취소
	public String joinCancel(@PathVariable("projectName") String projectName,
			@PathVariable("creatorName") String creatorName,@PathVariable("weaver") String weaver) {
		Project project = projectService.get(creatorName+"/"+projectName);
		Weaver currentWeaver = weaverService.getCurrentWeaver();
		WaitJoin waitJoin = waitJoinService.get(project.getName(), weaver);

		if(project != null //요청자가 쪽지를 보내고 관리자가 승인을 하는 경우
				&& waitJoinService.isOkJoin(waitJoin, project.getCreatorName(), currentWeaver)
				&& project.getCreatorName().equals(currentWeaver.getId())
				&& waitJoinService.deleteProjectWaitJoin(waitJoin, project, currentWeaver)){

			Post post = new Post(currentWeaver,  //관리자가 가입자에게 보내는 메세지
					"관리자 "+project.getCreatorName()+"님의 프로젝트명:"+
					creatorName+"/"+projectName+
					"에 가입이 거절되었습니다.", 
					"", 
					tagService.stringToTagList("$"+weaver));

			postService.add(post,null);

			return "redirect:/project/"+creatorName+"/"+projectName+"/weaver";

		}else if(project != null //관리자가 쪽지를 보내고 가입자가 거절 하는 경우
				&& waitJoinService.isOkJoin(waitJoin, project.getCreatorName(), currentWeaver)
				&& !project.getCreatorName().equals(currentWeaver.getId())
				&& waitJoinService.deleteProjectWaitJoin(waitJoin, project, currentWeaver)){

			Post post = new Post(currentWeaver, //가입자가 관리자에게 보내는 메세지
					currentWeaver.getId()+"님이 프로젝트명:"+
					"<a href='/project/"+creatorName+"/"+projectName+"'>"+
					creatorName+"/"+projectName+
					"</a>"+"를 가입 초대를 거절하셨습니다.", 
					"", 
					tagService.stringToTagList("$"+project.getCreatorName()));

			postService.add(post,null);

			return "redirect:/project/"+creatorName+"/"+projectName+"/manage";
		}

		return "redirect:/";//엉뚱한 사람이 들어올때 그냥 돌려보냄
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

	@RequestMapping("/{creatorName}/{projectName}/info")
	public String info(@PathVariable("projectName") String projectName,
			@PathVariable("creatorName") String creatorName, Model model){
		Project project = projectService.get(creatorName+"/"+projectName);
		model.addAttribute("project", project);
		model.addAttribute("gitInfo", gitService.getGitInfo(creatorName, projectName, "HEAD"));
		return "/project/info";
	}

	@RequestMapping("/{creatorName}/{projectName}/info:stream")
	public String stream(@PathVariable("projectName") String projectName,
			@PathVariable("creatorName") String creatorName, Model model){
		Project project = projectService.get(creatorName+"/"+projectName);

		model.addAttribute("project", project);
		model.addAttribute("gps", gitService.loadStatistics(creatorName, projectName));
		return "/project/stream";
	}

	@RequestMapping("/{creatorName}/{projectName}/info:frequency")
	public String punchcard(@PathVariable("projectName") String projectName,
			@PathVariable("creatorName") String creatorName, Model model){
		Project project = projectService.get(creatorName+"/"+projectName);

		model.addAttribute("project", project);
		model.addAttribute("dayAndHour", gitService.loadDayAndHour(creatorName, projectName));
		return "/project/frequency";
	}

	@RequestMapping("/{creatorName}/{projectName}/chat") //채팅
	public String chat(@PathVariable("projectName") String projectName,
			@PathVariable("creatorName") String creatorName,Model model){
		Project project = projectService.get(creatorName+"/"+projectName);
		Weaver currentWeaver = weaverService.getCurrentWeaver();
		ChatRoom chatRoom = chatService.get(project.getChatRoomName());
		model.addAttribute("project", project);
		model.addAttribute("chatRoom", chatRoom);
		model.addAttribute("weaver", currentWeaver);

		return "/project/chat";
	}

	@RequestMapping("/{creatorName}/{projectName}/fork") // 포크
	public String fork(@PathVariable("projectName") String projectName,
			@PathVariable("creatorName") String creatorName){
		Project project = projectService.get(creatorName+"/"+projectName);
		Weaver currentWeaver = weaverService.getCurrentWeaver();

		String newProjectName=
				projectService.fork(project, 
						new Project(projectName, 
								currentWeaver, 
								project),
								currentWeaver);

		if(newProjectName==null){
			return "redirect:/project/"+project.getName();
		}else{
			return "redirect:/project/"+newProjectName;
		}
	}

	@RequestMapping("/{creatorName}/{projectName}/cherry-pick") // 채리픽 요청 목록
	public String cherryPickRequests(@PathVariable("projectName") String projectName,
			@PathVariable("creatorName") String creatorName,Model model){
		Project project = projectService.get(creatorName+"/"+projectName);
		List<String> gitBranchList = gitService.getBranchList(creatorName, projectName);

		model.addAttribute("project", project);
		model.addAttribute("cherryPicks", cherryPickRequestService.get(project));
		model.addAttribute("gitBranchList", gitBranchList.subList(1, gitBranchList.size()));
		model.addAttribute("selectBranch",gitBranchList.get(0));
		return "/project/cherryPick";
	}

	@RequestMapping("/{creatorName}/{projectName}/cherry-pick/commit:{commit}/add") // 채리픽 요청 추가
	public String addCherryPickRequest(@PathVariable("projectName") String projectName,
			@PathVariable("creatorName") String creatorName,@PathVariable("commit") String commit){
		Project project = projectService.get(creatorName+"/"+projectName);
		Weaver currentWeaver = weaverService.getCurrentWeaver();

		if(!project.isForkProject())
			return "redirect:/project/"+creatorName+"/"+projectName+"/commitlog-viewer/commit:"+commit;

		Project orginalProject = projectService.get(project.getOriginalProject());

		if(!gitService.existCommit(orginalProject.getName().split("/")[0],
				orginalProject.getName().split("/")[1], commit) 
				&& cherryPickRequestService.add(orginalProject, project, currentWeaver, commit))
			return "redirect:/project/"+project.getOriginalProject()+"/cherry-pick";

		return "redirect:/project/"+creatorName+"/"+projectName+"/commitlog-viewer/commit:"+commit;
	}

	@RequestMapping("/{creatorName}/{projectName}/cherry-pick/branch:{branch}/id:{id}/accept") // 채리픽 요청 수락
	public String acceptCherryPickRequest(@PathVariable("projectName") String projectName,
			@PathVariable("creatorName") String creatorName,@PathVariable("branch") String branch,@PathVariable("id") String id){
		Weaver currentWeaver = weaverService.getCurrentWeaver();
		CherryPickRequest cherryPickRequest = cherryPickRequestService.get(id);
		cherryPickRequestService.accept(cherryPickRequest, branch, currentWeaver);
		return "redirect:/project/"+creatorName+"/"+projectName+"/cherry-pick";
	}

	@RequestMapping("/{creatorName}/{projectName}/cherry-pick/id:{id}/delete") // 채리픽 요청 삭제
	public String deleteCherryPickRequest(@PathVariable("projectName") String projectName,
			@PathVariable("creatorName") String creatorName,@PathVariable("id") String id){
		Weaver currentWeaver = weaverService.getCurrentWeaver();
		CherryPickRequest cherryPickRequest = cherryPickRequestService.get(id);
		cherryPickRequestService.delete(cherryPickRequest, currentWeaver);
		return "redirect:/project/"+creatorName+"/"+projectName+"/cherry-pick";
	}

	//코드에 답변달기
	@RequestMapping(value = "/{creatorName}/{projectName}/commitlog-viewer/commit:{commit}/add-repost", method = RequestMethod.POST)
	public String addRepost(@PathVariable("projectName") String projectName,
			@PathVariable("creatorName") String creatorName,
			@PathVariable("commit") String commit,HttpServletRequest request) throws UnsupportedEncodingException {
		final MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;

		final Map<String, MultipartFile> files = multiRequest.getFileMap();	

		String content = request.getParameter("content");
		Weaver weaver = weaverService.getCurrentWeaver();
		Project project = projectService.get(creatorName+"/"+projectName);
		GitCommitLog gitCommitLog =
				gitService.getGitCommitLog(creatorName, projectName, commit);
		if(project == null || weaver == null || gitCommitLog == null || content.equals("")) 
			// 권한 검사,로그인 검사, 글 존재 여부 검사, 내용 존재 여부 검사.
			return "redirect:/project/"+project.getName()+"/commitlog-viewer/commit:"+commit;
		Weaver commiter = weaverService.get(gitCommitLog.getCommiterEmail());
		
		ArrayList<Data> datas = new ArrayList<Data>();
		for (MultipartFile file : files.values()) {
			if(!file.isEmpty())
				datas.add(new Data(dataService.getObjectID(file.getOriginalFilename(), weaver),file,weaver.getId()));
		}

		RePost rePost = new RePost(project.getName()+"/"+gitCommitLog.getCommitLogID(),
				commiter,
				weaver,
				WebUtil.removeHtml(WebUtil.specialSignDecoder(URLDecoder.decode(content))),
				project.getTags(),
				5);
		rePostService.add(rePost,datas);

		return "redirect:/project/"+project.getName()+"/commitlog-viewer/commit:"+commit;
	}

	@RequestMapping(value = "/{creatorName}/{projectName}/commitlog-viewer/commit:{commit}/{rePostID}/add-reply", method = RequestMethod.POST)
	public String addReply(@PathVariable("projectName") String projectName,
			@PathVariable("creatorName") String creatorName,
			@PathVariable("commit") String commit,@PathVariable("rePostID") int rePostID,HttpServletRequest request) throws UnsupportedEncodingException {
		String content = request.getParameter("content");
		RePost rePost = rePostService.get(rePostID);
		Project project = projectService.get(creatorName+"/"+projectName);
		GitCommitLog gitCommitLog =
				gitService.getGitCommitLog(creatorName, projectName, commit);
		Weaver weaver = weaverService.getCurrentWeaver();

		if(project == null || weaver == null || gitCommitLog == null || content.equals(""))  
			// 권한 검사,로그인 검사, 답변 존재 여부 검사, 글 존재 여부 검사, 내용 존재 여부 검사.
			return "redirect:/project/"+project.getName()+"/commitlog-viewer/commit:"+commit;

		rePost.addReply(new Reply(weaver, 
				WebUtil.removeHtml(WebUtil.specialSignDecoder(URLDecoder.decode(content)))));
		rePostService.update(rePost,null);	

		return "redirect:/project/"+project.getName()+"/commitlog-viewer/commit:"+commit;
	}

	@RequestMapping(value="/{creatorName}/{projectName}/commitlog-viewer/commit:{commit}/{rePostID}/{number}/delete")
	public String deleteReply(@PathVariable("projectName") String projectName,
			@PathVariable("creatorName") String creatorName,
			@PathVariable("commit") String commit, 
			@PathVariable("rePostID") int rePostID,
			@PathVariable("number") int number,
			HttpServletRequest request) {		
		Project project = projectService.get(creatorName+"/"+projectName);
		RePost rePost = rePostService.get(rePostID);
		Weaver weaver = weaverService.getCurrentWeaver();
		GitCommitLog gitCommitLog =
				gitService.getGitCommitLog(creatorName, projectName, commit);
		if( project == null || weaver == null || gitCommitLog == null ||  !rePost.removeReply(weaver, number)) 
			// 권한 검사,로그인 검사, 답변 존재 여부 검사, 글 존재 여부 검사, 내용 존재 여부 검사.
			return "redirect:/project/"+project.getName()+"/commitlog-viewer/commit:"+commit;

		rePostService.update(rePost,null);	

		return "redirect:/project/"+project.getName()+"/commitlog-viewer/commit:"+commit;
	}

	@RequestMapping("/{creatorName}/{projectName}/commitlog-viewer/commit:{commit}/{rePostID}/delete")
	public String deleteRePost(Model model, @PathVariable("projectName") String projectName,
			@PathVariable("creatorName") String creatorName,
			@PathVariable("commit") String commit, @PathVariable("rePostID") int rePostID) {
		Project project = projectService.get(creatorName+"/"+projectName);
		RePost rePost = rePostService.get(rePostID);
		Weaver weaver = weaverService.getCurrentWeaver();
		rePostService.delete(rePost,weaver);
		return "redirect:/project/"+project.getName()+"/commitlog-viewer/commit:"+commit;
	}
}
