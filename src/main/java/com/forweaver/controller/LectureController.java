package com.forweaver.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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

import com.forweaver.domain.Data;
import com.forweaver.domain.Lecture;
import com.forweaver.domain.Post;
import com.forweaver.domain.Weaver;
import com.forweaver.domain.git.GitFileInfo;
import com.forweaver.domain.git.GitSimpleCommitLog;
import com.forweaver.service.GitService;
import com.forweaver.service.LectureService;
import com.forweaver.service.PermissionService;
import com.forweaver.service.PostService;
import com.forweaver.service.RePostService;
import com.forweaver.service.TagService;
import com.forweaver.service.WeaverService;
import com.forweaver.util.WebUtil;

@Controller
@RequestMapping("/lecture")
public class LectureController {

	@Autowired
	LectureService lectureService;
	@Autowired
	WeaverService weaverService;
	@Autowired
	GitService gitService;
	@Autowired
	TagService tagService;
	@Autowired
	PostService postService;
	@Autowired
	RePostService rePostService;
	@Autowired
	PermissionService permissionService;
	
	@RequestMapping("/")
	public String lectures() {
		return "redirect:/lecture/sort:age-desc/page:1";
	}
		
	@RequestMapping("/sort:{sort}/page:{page}")
	public String lecturesWithPage(@PathVariable("page") String page,
			@PathVariable("sort") String sort,Model model) {
		int pageNum;
		int number = 15;
		
		if(page.contains(",")){
			pageNum = Integer.parseInt(page.split(",")[0]);
			number = Integer.parseInt(page.split(",")[1]);
		}else{
			pageNum =Integer.parseInt(page);
		}
		
		model.addAttribute("lectures", lectureService.getLectures(sort, pageNum, number));
		model.addAttribute("lectureCount", lectureService.countLectures(sort));
		model.addAttribute("pageIndex", page);
		model.addAttribute("pageUrl", "/lecture/page:");
		return "/lecture/lectures";
	}
	
	
	
	@RequestMapping("/tags:{tagNames}")
	public String lecturesWithTags(HttpServletRequest request){
		return "redirect:"+request.getRequestURI() +"/sort:age-desc/page:1";
	}
	
	@RequestMapping("/tags:{tagNames}/sort:{sort}/page:{page}")
	public String lecturesWithTags(@PathVariable("tagNames") String tagNames,
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
		model.addAttribute("lectures", lectureService.getLecturesWithTags(tagList,sort, pageNum, number));
		model.addAttribute("lectureCount", lectureService.countLecturesWithTags(tagList,sort));
		model.addAttribute("pageIndex", page);
		model.addAttribute("pageUrl", "/lecture/tags:"+tagNames+"/page:");
		return "/lecture/lectures";
	}
			
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public String add(@RequestParam Map<String, String> params) {
		Weaver currentWeaver = weaverService.getCurrentWeaver();
		List<String> tagList = tagService.stringToTagList(params.get("tags"));
		if(!tagService.validateTag(tagList, currentWeaver))
			return "redirect:/lecture/";
		Lecture lecture = new Lecture(params.get("name"),
										WebUtil.removeHtml(params.get("description")),
										currentWeaver,
										tagList);
		lectureService.add(lecture,currentWeaver);
		return "redirect:/lecture/"+lecture.getName();
	}

	@RequestMapping(value = "/{lectureName}/delete")
	public String delete(@PathVariable("lectureName") String lectureName) {
		Weaver currentWeaver = weaverService.getCurrentWeaver();
		Lecture lecture = lectureService.get(lectureName);
		lectureService.delete(currentWeaver,lecture);
		return "redirect:/lecture";
	}
	
	@RequestMapping(value = "/{lectureName}/community")
	public String community(@PathVariable("lectureName") String lectureName,Model model) {			
		return "redirect:/lecture/"+lectureName+"/community/sort:age-desc/page:1";
	}
	
	@RequestMapping("/{lectureName}/community/sort:{sort}/page:{page}")
	public String community(@PathVariable("lectureName") String lectureName,
			@PathVariable("sort") String sort,
			@PathVariable("page") String page,Model model) {
		int pageNum;
		int number = 15;
		
		if(page.contains(",")){
			pageNum = Integer.parseInt(page.split(",")[0]);
			number = Integer.parseInt(page.split(",")[1]);
		}else{
			pageNum =Integer.parseInt(page);
		}	
		
		Lecture lecture = lectureService.get(lectureName);
		Weaver currentWeaver = weaverService.getCurrentWeaver();
		List<String> tagList = new ArrayList<String>();
		tagList.add("@"+lectureName);
		
		model.addAttribute("lecture", lecture);
		model.addAttribute("posts", 
				postService.getPostsWithTags(currentWeaver, tagList, sort, pageNum, number));
		model.addAttribute("postCount", 
				postService.countPostsWithTags(currentWeaver, tagList, sort));
		
		model.addAttribute("pageIndex", page);
		model.addAttribute("pageUrl", "/lecture/"+lectureName+"/community/sort:"+sort+"/page:");
		return "/lecture/community";
	}

	@RequestMapping("/{lectureName}/community/tags:{tagNames}")
	public String tags(HttpServletRequest request,@PathVariable("lectureName") String lectureName,
			@PathVariable("tagNames") String tagNames,Model model){
		return "redirect:"+request.getRequestURI() +"/sort:age-desc/page:1";
	}
	
	@RequestMapping("/{lectureName}/community/tags:{tagNames}/sort:{sort}/page:{page}")
	public String tags(@PathVariable("lectureName") String lectureName,
			@PathVariable("tagNames") String tagNames,
			@PathVariable("page") String page,
			@PathVariable("sort") String sort,
			Model model){
		int pageNum;
		int number = 15;
		
		if(page.contains(",")){
			pageNum = Integer.parseInt(page.split(",")[0]);
			number = Integer.parseInt(page.split(",")[1]);
		}else{
			pageNum =Integer.parseInt(page);
		}	
		
		Lecture lecture = lectureService.get(lectureName);
		List<String> tagList = tagService.stringToTagList(tagNames);
		tagList.add(new String("@"+lectureName));
		Weaver currentWeaver = weaverService.getCurrentWeaver();
	
		model.addAttribute("lecture", lecture);
		model.addAttribute("posts", 
				postService.getPostsWithTags(currentWeaver, tagList, sort, pageNum, number));
		model.addAttribute("postCount", 
				postService.countPostsWithTags(currentWeaver, tagList, sort));

		model.addAttribute("pageIndex", page);
		model.addAttribute("pageUrl", 
				"/lecture/"+lectureName+"/community/tags:"+tagNames+"/sort:"+sort+"/page:");
		return "/lecture/community";
	}
	
	@RequestMapping(value = "/{lectureName}/community/add")
	public String addPost(@PathVariable("lectureName") String lectureName,
			HttpServletRequest request) throws UnsupportedEncodingException {
			
		final MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
		final Map<String, MultipartFile> files = multiRequest.getFileMap();
		
		ArrayList<Data> datas = new ArrayList<Data>();
			
		String tags = request.getParameter("tags");
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		
		if(tags == null || title == null) // 태그가 없을 때
			return "redirect:/lecture/"+lectureName;
		else if(content == null)
			content = "";
		List<String> tagList = tagService.stringToTagList(
				WebUtil.removeHtml(WebUtil.specialSignDecoder(URLDecoder.decode(tags))));
		tagList.add(new String("@"+lectureName));
		Weaver weaver = weaverService.getCurrentWeaver();

		if(!tagService.validateTag(tagList,weaver)) // 태그에 권한이 없을때
			return "redirect:/lecture/"+lectureName;
			
		for (MultipartFile file : files.values()) {
			if(!file.isEmpty())
				datas.add(new Data(file,weaver.getId()));
        }
        
		Post post = new Post(weaver,
				WebUtil.removeHtml(WebUtil.specialSignDecoder(URLDecoder.decode(title))), 
				WebUtil.convertHtml(WebUtil.removeHtml(WebUtil.specialSignDecoder(URLDecoder.decode(content)))), 
				tagList);
		
		postService.add(post,datas);
		return "redirect:/lecture/"+lectureName;
	}
	
	@RequestMapping(value = {"/{lectureName}/example","/{lectureName}"})
	public String example(@PathVariable("lectureName") String lectureName,Model model) {
		
		Lecture lecture = lectureService.get(lectureName);		
		List<String> gitBranchList = gitService.getBranchList(lectureName, "example");
		
		model.addAttribute("lecture", lecture);
		model.addAttribute("gitFileInfoList",gitService.
				getGitSimpleFileInfoList(lectureName, "example","HEAD"));
		model.addAttribute("gitBranchList", gitBranchList.subList(1, gitBranchList.size()));
		model.addAttribute("selectBranch",gitBranchList.get(0));
		return "/lecture/example";
	}
	
	@RequestMapping("/{lectureName}/example/commit:{commit}")
	public String example(@PathVariable("lectureName") String lectureName,
			@PathVariable("commit") String commit,Model model) {
		
		Lecture lecture = lectureService.get(lectureName);
		model.addAttribute("lecture", lecture);
		model.addAttribute("gitFileInfoList",gitService.
				getGitSimpleFileInfoList(lectureName, "example",commit));
		List<String> gitBranchList = gitService.getBranchList(lectureName, "example");
		gitBranchList.remove(commit);
		model.addAttribute("gitBranchList", gitBranchList);
		model.addAttribute("selectBranch",commit);
		
		return "/lecture/example";
	}
	
	@RequestMapping("/{lectureName}/example/commit:{commit}/filepath:{filePath}")
	public String fileViewer(@PathVariable("lectureName") String lectureName,
			@PathVariable("commit") String commit,
			@PathVariable("filePath") String filePath,Model model) {
		
		Lecture lecture = lectureService.get(lectureName);		
		model.addAttribute("lecture", lecture);
		GitFileInfo gitFileInfo = gitService.getFileInfo(lectureName, "example", commit, filePath);
		model.addAttribute("fileName", gitFileInfo.getName());
		model.addAttribute("fileContent", gitFileInfo.getContent());
		model.addAttribute("gitLogList", gitFileInfo.getGitLogList());
		model.addAttribute("selectCommitIndex", gitFileInfo.getSelectCommitIndex());
		model.addAttribute("gitCommitLog", 
				new GitSimpleCommitLog(gitFileInfo.getSelectCommitLog()));
		return "/lecture/exampleViewer";
	}
	
	@RequestMapping("/{lectureName}/repo")
	public String manageRepo(@PathVariable("lectureName") String lectureName,
			Model model) {
		
		Lecture lecture = lectureService.get(lectureName);		
		model.addAttribute("lecture", lecture);
		return "/lecture/manageRepo";
	}
	
	@RequestMapping("/{lectureName}/weaver")
	public String manageWeaver(@PathVariable("lectureName") String lectureName,
			Model model) {
		Lecture lecture = lectureService.get(lectureName);		
		model.addAttribute("lecture", lecture);
		return "/lecture/manageWeaver";
	}


	@RequestMapping(value = "/{lectureName}/weaver:{weaverName}/delete") // 회원 삭제용
	public String deleteWeaver(@PathVariable("lectureName") String lectureName,
			@PathVariable("weaverName") String weaverName) {
		Lecture lecture = lectureService.get(lectureName);
		Weaver currentWeaver = weaverService.getCurrentWeaver();
		Weaver deleteWeaver = weaverService.get(weaverName);
		
		if(lectureService.deleteWeaver(lecture, currentWeaver,deleteWeaver)){
			Post post;
			if(currentWeaver.getId().equals(lecture.getCreatorName())){//관리자가 탈퇴시킬시에 메세지
				post = new Post(currentWeaver, 
						deleteWeaver.getId()+"님을 탈퇴 처리하였습니다.", "", 
						tagService.stringToTagList("@"+lecture.getName()+",탈퇴"));//강의실에 메세지 보냄
				postService.add(post,null);
				post = new Post(currentWeaver, 
						"강의명:"+lecture.getName()+"에서 탈퇴당하셨습니다.", "", 
						tagService.stringToTagList("$"+deleteWeaver.getId()));//강의실에 메세지 보냄
				postService.add(post,null);
			}else{//사용자가 탈퇴할시에 메세지
				post = new Post(currentWeaver, 
						deleteWeaver.getId()+"님이 탈퇴하셨습니다.", "", 
						tagService.stringToTagList("@"+lecture.getName()+",탈퇴"));//강의실에 메세지 보냄
				postService.add(post,null);
			}
			
		}
		return "redirect:/lecture/" + lectureName;
	}
	
	@RequestMapping("/{lectureName}/push")
	public String push(@PathVariable("lectureName") String lectureName) {
		Lecture lecture = lectureService.get(lectureName);
		Weaver currentWeaver = weaverService.getCurrentWeaver();
		
		lectureService.push(lecture, currentWeaver);
		
		return "redirect:/lecture/";
	}

}