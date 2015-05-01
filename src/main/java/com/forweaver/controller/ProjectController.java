package com.forweaver.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

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
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.forweaver.domain.Data;
import com.forweaver.domain.Post;
import com.forweaver.domain.Project;
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

	@Autowired 
	private WaitJoinService waitJoinService;
	@Autowired 
	private WeaverService weaverService;
	@Autowired 
	private ProjectService projectService;
	@Autowired 
	private PostService postService;
	@Autowired 
	private RePostService rePostService;
	@Autowired 
	private GitService gitService;
	@Autowired 
	private TagService tagService;
	@Autowired 
	private ChatService chatService;
	@Autowired 
	private CherryPickRequestService cherryPickRequestService;
	@Autowired 
	private LectureService lectureService;
	@Autowired 
	private DataService dataService;

	@RequestMapping("/")
	public String projects() {
		return "redirect:/project/sort:age-desc/page:1";
	}

	@RequestMapping(value = {"/{creatorName}/{projectName}/{commitName}/{download}.zip"
			,"/{creatorName}/{projectName}/{commitName}/{download}.tar"
	})
	public void getProjectZip(@PathVariable("projectName") String projectName,
			@PathVariable("creatorName") String creatorName,
			@PathVariable("commitName") String commitName,
			HttpServletRequest request,
			HttpServletResponse response) {

		if(!gitService.existCommit(creatorName, projectName, commitName))
			return;
		String url = request.getRequestURI();
		String format = url.substring(url.lastIndexOf(".")+1);
		gitService.getProjectZip(creatorName, projectName, commitName, format,response);

		return;
	}

	@RequestMapping("/sort:{sort}/page:{page}")
	public String projectsWithPage(@PathVariable("page") String page,
			@PathVariable("sort") String sort,Model model) {
		int pageNum = WebUtil.getPageNumber(page);
		int size = WebUtil.getPageSize(page);

		Weaver currentWeaver = weaverService.getCurrentWeaver();
		model.addAttribute("projects", 
				projectService.getProjects(currentWeaver, null, "", sort, pageNum, size));
		model.addAttribute("projectCount", projectService.countProjects(null, "", sort));
		model.addAttribute("pageIndex", pageNum);
		model.addAttribute("number", size);
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
		List<String> tagList = tagService.stringToTagList(tagNames);
		int pageNum = WebUtil.getPageNumber(page);
		int size = WebUtil.getPageSize(page);

		Weaver currentWeaver = weaverService.getCurrentWeaver();
		model.addAttribute("projects", 
				projectService.getProjects(currentWeaver, tagList, null, sort, pageNum, size));
		model.addAttribute("projectCount", projectService.countProjects(tagList, null, sort));
		model.addAttribute("pageIndex", pageNum);
		model.addAttribute("number", size);
		model.addAttribute("pageUrl", "/project/tags:"+tagNames+"/sort:"+sort+"/page:");
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
		List<String> tagList = tagService.stringToTagList(tagNames);
		int pageNum = WebUtil.getPageNumber(page);
		int size = WebUtil.getPageSize(page);

		Weaver currentWeaver = weaverService.getCurrentWeaver();
		model.addAttribute("projects", projectService.getProjects(currentWeaver,tagList,search,sort, pageNum, size));
		model.addAttribute("projectCount", projectService.countProjects(tagList,search,sort));
		model.addAttribute("pageIndex", pageNum);
		model.addAttribute("number", size);
		model.addAttribute("pageUrl", "/project/tags:"+tagNames+"/search:"+search+"/sort:"+sort+"/page:");
		return "/project/projects";
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String add(@RequestParam Map<String, String> params,Model model) {
		Weaver currentWeaver = weaverService.getCurrentWeaver();
		List<String> tagList = tagService.stringToTagList(params.get("tags"));
		int categoryInt = 0;
		if(params.get("category") != null)
			categoryInt = Integer.parseInt(params.get("category"));

		if(!Pattern.matches("^[a-z]{1}[a-z0-9_]{4,14}$", params.get("name")) || 
				params.get("name").length() <5 || 
				params.get("description").length() <5 || 
				params.get("description").length() > 50 || 
				!tagService.isPublicTags(tagList)){
			model.addAttribute("say", "잘못 입력하셨습니다!!!");
			model.addAttribute("url", "/project/");
			return "/alert";
		}

		Project project = new Project(params.get("name"), 
				categoryInt, 
				params.get("description"), 
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

		if(projectService.delete(currentWeaver, project))
			postService.delete(postService.getProjectPosts(project.getName(), null, null, null, null, 0, 0, false));
		else{
			model.addAttribute("say", "삭제하지 못하였습니다!!!");
			model.addAttribute("url", "/project/"+creatorName+"/"+projectName);
			return "/alert";
		}

		return "redirect:/project/";
	}

	
	@RequestMapping(value = {"/{creatorName}/{projectName}/data/commit:{commit}/**"})
	public void data(HttpServletRequest request,@PathVariable("projectName") String projectName,
			@PathVariable("creatorName") String creatorName,
			@PathVariable("commit") String commit,HttpServletResponse res) throws IOException {
		String uri = URLDecoder.decode(request.getRequestURI(),"UTF-8");
		String filePath = uri.substring(uri.indexOf("filepath:")+9);
		filePath = filePath.replace(",jsp", ".jsp");

		commit = uri.substring(uri.indexOf("/commit:")+8);
		commit = commit.substring(0, commit.indexOf("/"));
		
		GitFileInfo gitFileInfo = gitService.getFileInfo(creatorName, projectName, commit, filePath);

		if (gitFileInfo.getContent() == null) {
			res.sendRedirect("http://www.gravatar.com/avatar/a.jpg");
			return;
		} else {
			
			byte[] imgData = gitFileInfo.getData();
			
			res.reset();
			res.setContentType("application/octet-stream");
			String filename = new String(gitFileInfo.getName().getBytes("UTF-8"), "8859_1");
			res.setHeader("Content-Disposition", "attachment; filename = " + filename);
			res.setContentType(WebUtil.getFileExtension(gitFileInfo.getName()));
			OutputStream o = res.getOutputStream();
			o.write(imgData);
			o.flush();
			o.close();
			return;
		} 

	}
	
	@RequestMapping(value=
		{	"/{creatorName}/{projectName}", 
		"/{creatorName}/{projectName}/browser"}
			)
	public String browser(@PathVariable("projectName") String projectName,
			@PathVariable("creatorName") String creatorName) {		
		List<String> gitBranchList = gitService.getBranchList(creatorName, projectName);
		return "redirect:/project/"+creatorName+"/"+projectName+"/browser/commit:"+gitBranchList.get(0)+"/filepath:/";
	}

	@RequestMapping("/{creatorName}/{projectName}/browser/commit:{commit}")
	public String fileBrowser(HttpServletRequest request){
		return "redirect:"+request.getRequestURI()+"/filepath:/"; 
	}

	@RequestMapping("/{creatorName}/{projectName}/browser/commit:{commit}/**")
	public String fileBrowser(HttpServletRequest request,@PathVariable("projectName") String projectName,
			@PathVariable("creatorName") String creatorName,
			@PathVariable("commit") String commit,Model model) throws UnsupportedEncodingException  {
		Project project = projectService.get(creatorName+"/"+projectName);
		String uri = URLDecoder.decode(request.getRequestURI(),"UTF-8");
		String filePath = uri.substring(uri.indexOf("filepath:")+9);
		filePath = filePath.replace(",jsp", ".jsp");

		commit = uri.substring(uri.indexOf("/commit:")+8);
		commit = commit.substring(0, commit.indexOf("/"));

		GitFileInfo gitFileInfo = gitService.getFileInfo(creatorName, projectName, commit, filePath);
		if(gitFileInfo ==null || gitFileInfo.isDirectory()){ // 만약에 주소의 파일이 디렉토리라면
			List<GitSimpleFileInfo> gitFileInfoList = 
					gitService.getGitSimpleFileInfoList(creatorName, projectName,commit,filePath);

			List<String> gitBranchList = gitService.getBranchList(creatorName, projectName);
			gitBranchList.remove(commit);

			model.addAttribute("project", project);
			model.addAttribute("gitFileInfoList", gitFileInfoList);

			model.addAttribute("gitBranchList", gitBranchList);
			model.addAttribute("selectBranch",commit);
			model.addAttribute("readme",gitService.getReadme(creatorName, projectName,commit,gitFileInfoList));
			model.addAttribute("filePath",filePath);
			model.addAttribute("commit",commit);
			
			return "/project/browser";
		}else{ // 파일이라면
			model.addAttribute("project", project);
			model.addAttribute("fileName", gitFileInfo.getName());
			if(!WebUtil.isCodeName(gitFileInfo.getName()))
				gitFileInfo.setContent("이 파일은 화면에 표시할 수 없습니다!");
			model.addAttribute("fileContent", new String(gitFileInfo.getContent().getBytes(Charset.forName("EUC-KR")),Charset.forName("CP949")));
			model.addAttribute("gitLogList", gitFileInfo.getGitLogList());
			model.addAttribute("selectCommitIndex", gitFileInfo.getSelectCommitIndex());
			model.addAttribute("gitCommitLog", 
					new GitSimpleCommitLog(gitFileInfo.getSelectCommitLog()));
			model.addAttribute("filePath",filePath);
			model.addAttribute("isCodeName",WebUtil.isCodeName(filePath));
			model.addAttribute("isImageName",WebUtil.isImageName(filePath));
			return "/project/fileViewer";
		}


	}
	
	@RequestMapping("/{creatorName}/{projectName}/edit/commit:{commit}/**")
	public String fileEdit(HttpServletRequest request,@PathVariable("projectName") String projectName,
			@PathVariable("creatorName") String creatorName,
			@PathVariable("commit") String commit,Model model) throws UnsupportedEncodingException  {
		Project project = projectService.get(creatorName+"/"+projectName);
		String uri = URLDecoder.decode(request.getRequestURI(),"UTF-8");
		String filePath = uri.substring(uri.indexOf("filepath:")+9);
		filePath = filePath.replace(",jsp", ".jsp");

		if(!WebUtil.isCodeName(filePath)) //소스코드만 수정 가능함.
			return "redirect:/project/"+creatorName+"/"+projectName+"/browser/commit:"+commit+"/filepath:"+filePath;

		commit = uri.substring(uri.indexOf("/commit:")+8);
		commit = commit.substring(0, commit.indexOf("/"));

		GitFileInfo gitFileInfo = gitService.getFileInfo(creatorName, projectName, commit, filePath);
			model.addAttribute("project", project);
			model.addAttribute("fileName", gitFileInfo.getName());
			model.addAttribute("fileContent", new String(gitFileInfo.getContent().getBytes(Charset.forName("EUC-KR")),Charset.forName("CP949")));
			model.addAttribute("gitLogList", gitFileInfo.getGitLogList());
			model.addAttribute("selectCommitIndex", gitFileInfo.getSelectCommitIndex());
			model.addAttribute("gitCommitLog", 
					new GitSimpleCommitLog(gitFileInfo.getSelectCommitLog()));
			model.addAttribute("filePath",filePath);
			model.addAttribute("commit",commit);
			
			return "/project/fileEdit";
	}
	
	@RequestMapping(value="/{creatorName}/{projectName}/file-edit",method = RequestMethod.POST )
	public String fileEdit(@PathVariable("projectName") String projectName,
			@PathVariable("creatorName") String creatorName,
			@RequestParam("commit") String commit,
			@RequestParam("message") String message,
			@RequestParam("path") String path,
			@RequestParam("code") String code,Model model)  {
		Project project = projectService.get(creatorName+"/"+projectName);
		Weaver currentWeaver = weaverService.getCurrentWeaver();

		if(!projectService.updateFile(project, currentWeaver,commit, message,path, code)){
			model.addAttribute("say", "업로드 실패! 프로젝트에 가입되어 있는지 혹은 최신 커밋의 파일인지 확인해보세요!");
			model.addAttribute("url", "/project/"+creatorName+"/"+projectName+"/edit/commit:"+commit+"/filepath:/"+path);
			return "/alert";
		}
		return "redirect:/project/"+creatorName+"/"+projectName+"/browser/commit:"+commit+"/filepath:/"+path;
	}

	@RequestMapping("/{creatorName}/{projectName}/blame/commit:{commit}/**")
	public String blame(HttpServletRequest request, @PathVariable("projectName") String projectName,
			@PathVariable("creatorName") String creatorName,
			@PathVariable("commit") String commit,Model model) throws UnsupportedEncodingException{
		Project project = projectService.get(creatorName+"/"+projectName);
		String uri = URLDecoder.decode(request.getRequestURI(),"UTF-8");
		String filePath = uri.substring(uri.indexOf("filepath:")+9);
		filePath = filePath.replace(",jsp", ".jsp");
		
		if(!WebUtil.isCodeName(filePath)) //소스코드만 추적 가능함.
			return "redirect:/project/"+creatorName+"/"+projectName+"/browser/commit:"+commit+"/filepath:"+filePath;
		
		commit = uri.substring(uri.indexOf("/commit:")+8);
		commit = commit.substring(0, commit.indexOf("/"));		
		GitFileInfo gitFileInfo = gitService.getFileInfoWithBlame(creatorName, projectName, commit, filePath);

		if(gitFileInfo==null || gitFileInfo.isDirectory()) // 디렉토리의 경우 blame 기능을 이용할 수 없어 프로젝트 메인으로 돌려보냄.
			return "redirect:/project/"+creatorName+"/"+projectName;

		model.addAttribute("project", project);
		model.addAttribute("fileName", gitFileInfo.getName());
		model.addAttribute("fileContent", gitFileInfo.getContent());
		model.addAttribute("gitLogList", gitFileInfo.getGitLogList());
		model.addAttribute("gitBlameList", gitFileInfo.getGitBlames());
		model.addAttribute("selectCommitIndex", gitFileInfo.getSelectCommitIndex());
		model.addAttribute("gitCommitLog", new GitSimpleCommitLog(gitFileInfo.getSelectCommitLog()));
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
		int pageNum = WebUtil.getPageNumber(page);
		int size = WebUtil.getPageSize(page);

		Project project = projectService.get(creatorName+"/"+projectName);

		model.addAttribute("project", project);
		model.addAttribute("posts", 
				postService.getProjectPosts(creatorName+"/"+projectName, null, null, sort, null, pageNum, size,true));
		model.addAttribute("postCount", 
				postService.countProjectPosts(creatorName+"/"+projectName, null, null, sort, null));
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
		int pageNum = WebUtil.getPageNumber(page);
		int size = WebUtil.getPageSize(page);	

		Project project = projectService.get(creatorName+"/"+projectName);
		List<String> tagList = tagService.stringToTagList(tagNames);

		model.addAttribute("project", project);
		model.addAttribute("posts", 
				postService.getProjectPosts(creatorName+"/"+projectName, tagList, null, sort, null, pageNum, size,true));
		model.addAttribute("postCount", 
				postService.countProjectPosts(creatorName+"/"+projectName, tagList, null, sort, null));

		model.addAttribute("pageIndex", pageNum);
		model.addAttribute("pageUrl", 
				"/project/"+projectName+"/community/tags:"+tagNames+"/sort:"+sort+"/page:");
		return "/project/community";
	}

	@RequestMapping(value = "/{creatorName}/{projectName}/community/add", method = RequestMethod.POST)
	public String addPost(@PathVariable("projectName") String projectName,
			@PathVariable("creatorName") String creatorName,
			HttpServletRequest request,Model model) {
		final MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
		final Map<String, MultipartFile> files = multiRequest.getFileMap();
		ArrayList<Data> datas = new ArrayList<Data>();

		String tags = request.getParameter("tags");
		String title = request.getParameter("title");
		String content = request.getParameter("content");

		if(title.length() < 5 || title.length() > 200
				|| (content.length() >0 && content.length() < 5)){ // 검증함
			model.addAttribute("say", "잘못 입력하셨습니다!!!");
			model.addAttribute("url", "/project/"+creatorName+"/"+projectName+"/community/");
			return "/alert";
		}

		List<String> tagList = tagService.stringToTagList(tags);
		tagList.add(new String("@"+creatorName+"/"+projectName));
		Weaver weaver = weaverService.getCurrentWeaver();

		if(!tagService.validateTag(tagList,weaver)){ // 태그에 권한이 없을때
			model.addAttribute("say", "태그에 권한이 없습니다!!!");
			model.addAttribute("url", "/project/"+creatorName+"/"+projectName+"/community/");
			return "/alert";
		}

		for (MultipartFile file : files.values())
			if(!file.isEmpty()){
				String fileID= dataService.getObjectID(file.getOriginalFilename(), weaver);
				if(!fileID.equals(""))
					datas.add(new Data(fileID,file,weaver));
			}

		Post post = new Post(weaver,title,content,tagList);

		postService.add(post,datas);
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
				gitService.getGitCommitLogList(creatorName, projectName,gitBranchList.get(0),1,15));

		return "/project/commitLog";
	}
	/*
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

	 */

	@RequestMapping("/{creatorName}/{projectName}/commitlog/commit:{commit}")
	public String commitLog(@PathVariable("projectName") String projectName,
			@PathVariable("creatorName") String creatorName,
			@PathVariable("commit") String commit,HttpServletRequest request,Model model) {
		Project project = projectService.get(creatorName+"/"+projectName);
		String uri = request.getRequestURI();
		commit = uri.substring(uri.indexOf("/commit:")+8);
		List<String> gitBranchList = gitService.getBranchList(creatorName, projectName);
		gitBranchList.remove(commit);
		model.addAttribute("gitBranchList", gitBranchList);
		model.addAttribute("selectBranch",commit);
		model.addAttribute("project", project);
		model.addAttribute("pageIndex",1);
		model.addAttribute("gitCommitListCount", 
				gitService.getCommitListCount(creatorName, projectName,commit));
		model.addAttribute("gitCommitList", 
				gitService.getGitCommitLogList(creatorName, projectName,commit,1,15));
		return "/project/commitLog";
	}

	@RequestMapping("/{creatorName}/{projectName}/edit")
	public String edit(@PathVariable("projectName") String projectName,
			@PathVariable("creatorName") String creatorName,Model model) {
		Project project = projectService.get(creatorName+"/"+projectName);
		Weaver currentWeaver = weaverService.getCurrentWeaver();
		if(!project.getCreator().equals(currentWeaver))
			return "redirect:/project/"+project.getName()+"/";
		model.addAttribute("project", project);
		return "/project/edit";
	}

	@RequestMapping(value = "/{creatorName}/{projectName}/edit", method = RequestMethod.POST)
	public String edit(@PathVariable("projectName") String projectName,
			@PathVariable("creatorName") String creatorName,@RequestParam Map<String, String> params,Model model) {
		Weaver currentWeaver = weaverService.getCurrentWeaver();
		Project project = projectService.get(creatorName+"/"+projectName);
		List<String> tagList = tagService.stringToTagList(params.get("tags"));
		int categoryInt = 0;

		if(params.get("category") != null)
			categoryInt = Integer.parseInt(params.get("category"));

		if(!project.getCreator().equals(currentWeaver) || params.get("description") == null || !tagService.isPublicTags(tagList)){
			model.addAttribute("say", "잘못 입력하셨습니다!!!");
			model.addAttribute("url", "/project/"+project.getName()+"/edit");
			return "/alert";
		}

		project.setDescription(params.get("description"));

		if(project.isForked())
			tagList.add("@"+project.getName());	
		else
			project.setCategory(categoryInt);

		project.setTags(tagList);

		projectService.update(project);
		return "redirect:/project/"+project.getName()+"/edit";
	}

	@RequestMapping("/{creatorName}/{projectName}/commitlog/commit:{commit}/page:{page}")
	public String commitLog(@PathVariable("projectName") String projectName,
			@PathVariable("creatorName") String creatorName,
			@PathVariable("commit") String commit,
			@PathVariable("page") String page,
			HttpServletRequest request,Model model) {
		Project project = projectService.get(creatorName+"/"+projectName);
		String uri = request.getRequestURI();
		commit = uri.substring(uri.indexOf("/commit:")+8);
		commit = commit.substring(0, commit.indexOf("/"));
		int pageNum = WebUtil.getPageNumber(page);
		int size = WebUtil.getPageSize(page);
		List<String> gitBranchList = gitService.getBranchList(creatorName, projectName);

		gitBranchList.remove(commit);
		model.addAttribute("gitBranchList", gitBranchList);
		model.addAttribute("selectBranch",commit);
		model.addAttribute("project", project);
		model.addAttribute("pageIndex",page);
		model.addAttribute("gitCommitListCount", 
				gitService.getCommitListCount(creatorName, projectName,commit));
		model.addAttribute("gitCommitList", 
				gitService.getGitCommitLogList(creatorName, projectName,commit,pageNum,size));
		return "/project/commitLog";
	}

	@RequestMapping("/{creatorName}/{projectName}/commitlog-viewer/commit:{commit}")
	public String commitLogViewer(@PathVariable("projectName") String projectName,
			@PathVariable("creatorName") String creatorName,
			@PathVariable("commit") String commit,
			HttpServletRequest request,Model model) {
		Project project = projectService.get(creatorName+"/"+projectName);
		String uri = request.getRequestURI();
		commit = uri.substring(uri.indexOf("/commit:")+8);
		GitCommitLog gitCommitLog = gitService.getGitCommitLog(creatorName, projectName, commit);
		if(gitCommitLog == null)
			return "redirect:/project/"+ creatorName+"/"+projectName+"/commitlog";
		model.addAttribute("project", project);
		model.addAttribute("gitCommitLog",gitCommitLog);
		//model.addAttribute("rePosts", rePostService.get(project.getName()+"/"+gitCommitLog.getCommitLogID(),5,""));
		return "/project/commitLogViewer";
	}


	@RequestMapping("/{creatorName}/{projectName}/weaver")
	public String manageWeaver(@PathVariable("projectName") String projectName,
			@PathVariable("creatorName") String creatorName,Model model) {
		Project project = projectService.get(creatorName+"/"+projectName);
		model.addAttribute("project", project);
		return "/project/manageWeaver";
	}

	@RequestMapping(value = "/{creatorName}/{projectName}/weaver/{weaverName}/delete") // 회원 삭제용
	public String deleteWeaver(@PathVariable("projectName") String projectName,
			@PathVariable("creatorName") String creatorName,
			@PathVariable("weaverName") String weaverName,Model model) {
		Project project = projectService.get(creatorName+"/"+projectName);
		Weaver currentWeaver = weaverService.getCurrentWeaver();
		Weaver deleteWeaver = weaverService.get(weaverName);

		if(projectService.deleteWeaver(project, currentWeaver,deleteWeaver)){
			Post post;
			if(currentWeaver.equals(project.getCreator())){//관리자가 탈퇴시킬시에 메세지
				post = new Post(currentWeaver, 
						deleteWeaver.getId()+"님을 탈퇴 처리하였습니다.", "", 
						tagService.stringToTagList("@"+project.getName()+",탈퇴"));//프로젝트에 메세지 보냄
				postService.add(post,null);
				post = new Post(currentWeaver, 
						deleteWeaver.getId()+"님이 프로젝트명:"+project.getName()+"에서 탈퇴 당하셨습니다.", "", 
						tagService.stringToTagList("$"+deleteWeaver.getId()));//프로젝트에 메세지 보냄
				postService.add(post, null);
				model.addAttribute("url", "/project/"+creatorName+"/"+projectName+"/weaver/");

			}else{//사용자가 탈퇴할시에 메세지
				post = new Post(currentWeaver, 
						deleteWeaver.getId()+"님이 탈퇴하셨습니다.", "", 
						tagService.stringToTagList("@"+project.getName()+",탈퇴"));//프로젝트에 메세지 보냄
				postService.add(post, null);
				model.addAttribute("url", "/");
			}

			model.addAttribute("say", "탈퇴 처리가 성공하였습니다!");
			return "/alert";

		}
		model.addAttribute("url", "/");
		model.addAttribute("say", "탈퇴 처리가 실패하였습니다!");
		return "/alert";

	}

	@RequestMapping("/{creatorName}/{projectName}/weaver/{weaverName}/add-weaver")
	public String addWeaver(	@PathVariable("projectName") String projectName,
			@PathVariable("creatorName") String creatorName,
			@PathVariable("weaverName") String weaverName,Model model) {
		Project project = projectService.get(creatorName+"/"+projectName);
		Weaver waitingWeaver = weaverService.get(weaverName);
		Weaver proposer = weaverService.getCurrentWeaver();


		if(weaverService.get(weaverName) == null){
			model.addAttribute("url", "/project/"+creatorName+"/"+projectName+"/weaver/");
			model.addAttribute("say", "회원이 존재하지 않습니다!");
			return "/alert";		
		}


		if(!waitingWeaver.equals(proposer) && waitJoinService.isCreateWaitJoin(project, waitingWeaver, proposer)){
			Weaver projectCreator = weaverService.get(project.getCreatorName());
			String title ="프로젝트명:"+creatorName+"/"+projectName+"에 가입 초대를 </a><a href='/project/"+creatorName+"/"+projectName+"/weaver/"+waitingWeaver.getId()+"/join-ok'>승락하시겠습니까?</a> "
					+ "아니면 <a href='/project/"+creatorName+"/"+projectName+"/weaver/"+waitingWeaver.getId()+"/join-cancel'>거절하시겠습니까?</a><a>";

			Post post = new Post(projectCreator,
					title, 
					"", 
					tagService.stringToTagList("$"+waitingWeaver.getId()),true);
			waitJoinService.createWaitJoin(
					project.getName(), 
					proposer.getId(), 
					waitingWeaver.getId(), 
					postService.add(post,null));
			model.addAttribute("url", "/project/"+creatorName+"/"+projectName+"/weaver/");
			model.addAttribute("say", "회원에게 초대장을 보냈습니다!");
			return "/alert";	
		}
		model.addAttribute("url", "/project/"+creatorName+"/"+projectName+"/weaver/");
		model.addAttribute("say", "회원을 추가하지 못했습니다!");
		return "/alert";		

	}


	@RequestMapping("/{creatorName}/{projectName}/join") //본인이 직접 신청
	public String join(	@PathVariable("projectName") String projectName,
			@PathVariable("creatorName") String creatorName,Model model) {
		Project project = projectService.get(creatorName+"/"+projectName);
		Weaver waitingWeaver = weaverService.getCurrentWeaver();

		if(waitJoinService.isCreateWaitJoin(project, waitingWeaver, waitingWeaver)){
			String title = waitingWeaver.getId()+"님이 프로젝트명:"+creatorName+"/"+projectName+"에 가입 신청을 </a><a href='/project/"+creatorName+"/"+projectName+"/weaver/"+waitingWeaver.getId()+"/join-ok'>승락하시겠습니까?</a> "
					+ "아니면 <a href='/project/"+creatorName+"/"+projectName+"/weaver/"+waitingWeaver.getId()+"/join-cancel'>거절하시겠습니까?</a><a>";
			Post post = new Post(waitingWeaver,
					title, 
					"", 
					tagService.stringToTagList("$"+project.getCreatorName()),true);
			waitJoinService.createWaitJoin(
					project.getName(), 
					waitingWeaver.getId(), 
					waitingWeaver.getId(), 
					postService.add(post,null));
			model.addAttribute("url", "/");
			model.addAttribute("say", "가입 부탁 메시지를 보냈습니다!");
			return "/alert";	
		}
		model.addAttribute("url", "/");
		model.addAttribute("say", "가입 부탁 메시지를 보내지 못했습니다!");
		return "/alert";		

	}


	@RequestMapping("/{creatorName}/{projectName}/weaver/{weaver}/join-ok") // 프로젝트 가입 승인
	public String joinOK(@PathVariable("projectName") String projectName,
			@PathVariable("creatorName") String creatorName,@PathVariable("weaver") String weaver,Model model) {
		Project project = projectService.get(creatorName+"/"+projectName);
		Weaver currentWeaver = weaverService.getCurrentWeaver();
		Weaver waitingWeaver = weaverService.get(weaver);
		WaitJoin waitJoin = waitJoinService.get(creatorName+"/"+projectName, weaver);

		if(waitJoinService.isOkJoin(waitJoin, project.getCreatorName(), currentWeaver) //요청자가 쪽지를 보내고 관리자가 승인을 하는 경우
				&& project.getCreator().equals(currentWeaver)
				&& waitJoinService.deleteWaitJoin(waitJoin, project, waitingWeaver)){
			postService.delete(postService.get(waitJoin.getPostID()), waitingWeaver);	
			projectService.addWeaver(project, waitingWeaver);
			Post post = new Post(waitingWeaver, 
					"관리자 "+project.getCreatorName()+"님의 승인으로 프로젝트명:"+
							creatorName+"/"+projectName+
							"에 가입이 승인되었습니다!", 
							"", 
							tagService.stringToTagList("@"+project.getName()+",가입"),true); //@프로젝트명,가입 태그를 걸어줌

			postService.add(post,null);

			return "redirect:/project/"+creatorName+"/"+projectName+"/weaver";

		}else if(project != null //관리자가 쪽지를 보내고 가입자가 승인을 하는 경우
				&& waitJoinService.isOkJoin(waitJoin, project.getCreatorName(), currentWeaver)
				&& !project.getCreator().equals(currentWeaver)
				&& waitJoinService.deleteWaitJoin(waitJoin, project, currentWeaver)){
			postService.delete(postService.get(waitJoin.getPostID()), project.getCreator());	
			projectService.addWeaver(project, waitingWeaver);

			Post post = new Post(currentWeaver, //가입자가 관리자에게 보내는 메세지
					currentWeaver.getId()+"님이 프로젝트명:"+creatorName+"/"+projectName+
					"를 가입 초대를 수락하셨습니다!", 
					"", 
					tagService.stringToTagList("@"+project.getName()+",가입"),true); //@프로젝트명,가입 태그를 걸어줌

			postService.add(post,null);

			return "redirect:/project/"+creatorName+"/"+projectName+"/weaver";
		}

		model.addAttribute("url", "/");
		model.addAttribute("say", "권한이 없습니다!");
		return "/alert";		
	}

	@RequestMapping("/{creatorName}/{projectName}/weaver/{weaver}/join-cancel") // 프로젝트 가입 승인 취소
	public String joinCancel(@PathVariable("projectName") String projectName,
			@PathVariable("creatorName") String creatorName,@PathVariable("weaver") String weaver,Model model) {
		Project project = projectService.get(creatorName+"/"+projectName);
		Weaver currentWeaver = weaverService.getCurrentWeaver();
		WaitJoin waitJoin = waitJoinService.get(project.getName(), weaver);
		Weaver waitingWeaver = weaverService.get(weaver);

		if(project != null //요청자가 쪽지를 보내고 관리자가 승인을 하는 경우
				&& waitJoinService.isOkJoin(waitJoin, project.getCreatorName(), currentWeaver)
				&& project.getCreator().equals(currentWeaver)
				&& waitJoinService.deleteWaitJoin(waitJoin, project, currentWeaver)){
			postService.delete(postService.get(waitJoin.getPostID()), waitingWeaver);	
			Post post = new Post(currentWeaver,  //관리자가 가입자에게 보내는 메세지
					"관리자 "+project.getCreatorName()+"님의 프로젝트명:"+
					creatorName+"/"+projectName+
					"에 가입이 거절되었습니다.", 
					"", 
					tagService.stringToTagList("$"+waitingWeaver.getId()),true);

			postService.add(post,null);

			return "redirect:/project/"+creatorName+"/"+projectName+"/weaver";

		}else if(project != null //관리자가 쪽지를 보내고 가입자가 거절 하는 경우
				&& waitJoinService.isOkJoin(waitJoin, project.getCreatorName(), currentWeaver)
				&& !project.getCreatorName().equals(currentWeaver.getId())
				&& waitJoinService.deleteWaitJoin(waitJoin, project, currentWeaver)){
			postService.delete(postService.get(waitJoin.getPostID()), project.getCreator());	
			Post post = new Post(currentWeaver, //가입자가 관리자에게 보내는 메세지
					currentWeaver.getId()+"님이 프로젝트명:"+creatorName+"/"+projectName+
					"를 가입 초대를 거절하셨습니다.", 
					"", 
					tagService.stringToTagList("$"+project.getCreatorName()),true);

			postService.add(post,null);

			return "redirect:/";
		}

		model.addAttribute("url", "/");
		model.addAttribute("say", "권한이 없습니다!");
		return "/alert";		
	}

	@RequestMapping(value="/{creatorName}/{projectName}/{branchName}/upload" , method=RequestMethod.POST)
	public String upload(@PathVariable("projectName") String projectName,
			@PathVariable("creatorName") String creatorName,
			@PathVariable("branchName") String branchName,
			@RequestParam("message") String message,
			@RequestParam("path") String path,
			@RequestParam("zip") MultipartFile zip,Model model) {
		Project project = projectService.get(creatorName+"/"+projectName);
		Weaver currentWeaver = weaverService.getCurrentWeaver();
		if(!projectService.uploadFile(project, currentWeaver,branchName, message,path, zip)){
			model.addAttribute("say", "업로드 실패! 프로젝트에 가입되어 있는지 혹은 압축파일을 다시 확인해주세요!");
			model.addAttribute("url", "/project/"+creatorName+"/"+projectName);
			return "/alert";
		}
		return "redirect:/project/"+creatorName+"/"+projectName;
	}

	@RequestMapping("/{creatorName}/{projectName}/push")
	public String push(@PathVariable("projectName") String projectName,
			@PathVariable("creatorName") String creatorName) {
		Project project = projectService.get(creatorName+"/"+projectName);
		projectService.push(project, weaverService.getCurrentWeaver(),weaverService.getUserIP());

		return "redirect:/project/";
	}

	// 프로젝트 정보 불러오기
	@RequestMapping("/{creatorName}/{projectName}/info")
	public String info(@PathVariable("projectName") String projectName,
			@PathVariable("creatorName") String creatorName, Model model){
		Project project = projectService.get(creatorName+"/"+projectName);
		model.addAttribute("project", project);
		model.addAttribute("gitInfo", gitService.getGitInfo(creatorName, projectName, "HEAD"));
		return "/project/info";
	}

	// 프로젝트 스트림 시각화
	@RequestMapping("/{creatorName}/{projectName}/info:stream")
	public String stream(@PathVariable("projectName") String projectName,
			@PathVariable("creatorName") String creatorName, Model model){
		Project project = projectService.get(creatorName+"/"+projectName);

		model.addAttribute("project", project);
		model.addAttribute("gps", gitService.loadStatistics(creatorName, projectName));
		return "/project/stream";
	}

	// 프로젝트 빈도수 시각화
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
/*
	@RequestMapping("/{creatorName}/{projectName}/fork") // 포크
	public String fork(@PathVariable("projectName") String projectName,
			@PathVariable("creatorName") String creatorName,Model model){
		Project project = projectService.get(creatorName+"/"+projectName);
		Weaver currentWeaver = weaverService.getCurrentWeaver();

		String newProjectName=
				projectService.fork(project, 
						new Project(projectName, 
								currentWeaver, 
								project),
								currentWeaver);

		if(newProjectName==null){
			model.addAttribute("url", "/project/"+project.getName());
			model.addAttribute("say", "포크하지 못했습니다!");
			return "/alert";
		}else{
			return "redirect:/project/"+newProjectName;
		}
	}
/*
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
			@PathVariable("creatorName") String creatorName,@PathVariable("commit") String commit,
			HttpServletRequest request){
		Project project = projectService.get(creatorName+"/"+projectName);
		Weaver currentWeaver = weaverService.getCurrentWeaver();
		String uri = request.getRequestURI();
		commit = uri.substring(uri.indexOf("/commit:")+8);
		commit = commit.substring(0, commit.indexOf("/"));

		if(!project.isForkProject())
			return "redirect:/project/"+creatorName+"/"+projectName+"/commitlog-viewer/commit:"+commit;

		Project orginalProject = projectService.get(project.getOriginalProjectName());

		if(!gitService.existCommit(orginalProject.getName().split("/")[0],
				orginalProject.getName().split("/")[1], commit) 
				&& cherryPickRequestService.add(orginalProject, project, currentWeaver, commit))
			return "redirect:/project/"+project.getOriginalProjectName()+"/cherry-pick";

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

	/*
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
				WebUtil.specialSignDecoder(URLDecoder.decode(content))),
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

		rePost.addReply(new Reply(weaver, content));
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
	}*/
}
