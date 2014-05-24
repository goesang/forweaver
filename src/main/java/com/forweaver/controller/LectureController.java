/*package com.forweaver.controller;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mobile.device.Device;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;






//// 그냥 주석
import com.forweaver.domain.Lecture;
import com.forweaver.domain.Post;
import com.forweaver.domain.Tag;
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
	public String lectures(Model model) {
		model.addAttribute("lectures", lectureService.checkJoinLecture(
				lectureService.getLectures(0, 10),weaverService.getCurrentWeaver()));
		model.addAttribute("lectureCount", lectureService.countLectures());
		model.addAttribute("pageIndex", 1);
		model.addAttribute("pageUrl", "/lecture/page:");
		return "/lecture/lectures";
	}
		
	@RequestMapping("/page:{page}")
	public String lecturesWithPage(Model model,@PathVariable("page") int page) {
		model.addAttribute("lectures", lectureService.checkJoinLecture(
				lectureService.getLectures((page-1)*10, 10),weaverService.getCurrentWeaver()));
		model.addAttribute("lectureCount", lectureService.countLectures());
		model.addAttribute("pageIndex", page);
		model.addAttribute("pageUrl", "/lecture/page:");
		return "/lecture/lectures";
	}
	
	@RequestMapping("/tags:{tagNames}")
	public String lecturesWithTags(Model model,@PathVariable("tagNames") String tagNames) {
		List<Tag> tagList = tagService.stringToTagList(tagNames);
		model.addAttribute("lectures", lectureService.checkJoinLecture(
				lectureService.getLecturesWithTags(tagList, 0, 10),weaverService.getCurrentWeaver()));
		model.addAttribute("lectureCount", lectureService.countLecturesWithTags(tagList));
		model.addAttribute("pageIndex", 1);
		model.addAttribute("pageUrl", "/lecture/tags:"+tagNames+"/page:");
		return "/lecture/lectures";
	}
	
	@RequestMapping("/tags:{tagNames}/page:{page}")
	public String lecturesWithTags(Model model,
			@PathVariable("tagNames") String tagNames,@PathVariable("page") int page) {
		List<Tag> tagList = tagService.stringToTagList(tagNames);
		model.addAttribute("lectures", lectureService.checkJoinLecture(
				lectureService.getLecturesWithTags(tagList, (page-1)*10, 10),weaverService.getCurrentWeaver()));
		model.addAttribute("lectureCount", lectureService.countLecturesWithTags(tagList));
		model.addAttribute("pageIndex", page);
		model.addAttribute("pageUrl", "/lecture/tags:"+tagNames+"/page:");
		return "/lecture/lectures";
	}
			
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public String add(@RequestParam Map<String, String> params) {
		Weaver currentWeaver = weaverService.getCurrentWeaver();
		List<Tag> tagList = tagService.stringToTagList(params.get("tags"));
		if(!tagService.validateTag(tagList, currentWeaver))
			return "false";
		Lecture lecture = new Lecture(params.get("name"), 
										Integer.parseInt(params.get("category")), 
										WebUtil.removeHtml(params.get("description")), 
										Integer.parseInt(params.get("period")), 
										currentWeaver,
										tagList);
		lectureService.add(lecture,currentWeaver);
		return "true";
	}

	@RequestMapping(value = "/{lectureName}/delete")
	public String delete(@PathVariable("lectureName") String lectureName) {
		
		Lecture lecture = lectureService.get(lectureName);
		if(!permissionService.lecturePermission(lecture, weaverService.getCurrentWeaver()))
			return "redirect:/lecture";
		
		weaverService.deleteCurrentWeaverPass(lecture.getWeavers(), lecture.getName());
		lectureService.delete(lecture);
		return "redirect:/lecture";
	}
	
	@RequestMapping(value = "/{lectureName}/community")
	public String community(@PathVariable("lectureName") String lectureName,Model model,Device device) {	
		Lecture lecture = lectureService.get(lectureName);
		if(!permissionService.lecturePermission(lecture, weaverService.getCurrentWeaver())){
			  if (device.isMobile())
				  return "redirect:/m/lecture/";
			return "redirect:/lecture/";
		}
		
		return "redirect:/lecture/"+lectureName+"/community/sort:age-desc/page:1";
	}
	
	@RequestMapping("/{lectureName}/community/sort:{sort}/page:{page}")
	public String community(@PathVariable("lectureName") String lectureName,
			@PathVariable("sort") String sort,
			@PathVariable("page") int page,Model model,Device device) {
		Lecture lecture = lectureService.get(lectureName);
		if(!permissionService.lecturePermission(lecture, weaverService.getCurrentWeaver())){
			  if (device.isMobile())
				  return "redirect:/m/lecture/";
			return "redirect:/lecture/";
		}
		List<Tag> tagList = new ArrayList<Tag>();
		tagList.add(new Tag("@"+lectureName));
		model.addAttribute("lecture", lecture);
		model.addAttribute("posts", 
				tagService.getPostsWhenWeaver(sort,tagList,tagList.size()-1,(page-1)*10,10));
		model.addAttribute("postCount", 
				tagService.countPostsWhenWeaver(sort,tagList,tagList.size()-1));
		model.addAttribute("pageIndex", page);
		model.addAttribute("pageUrl", "/lecture/"+lectureName+"/community/sort:"+sort+"/page:");
		return "/lecture/community";
	}

	@RequestMapping("/{lectureName}/community/tags:{tagNames}")
	public String tags(HttpServletRequest request,@PathVariable("lectureName") String lectureName,
			@PathVariable("tagNames") String tagNames,Model model,Device device){
		Lecture lecture = lectureService.get(lectureName);
		
		if(!permissionService.lecturePermission(lecture, weaverService.getCurrentWeaver())){
			  if (device.isMobile())
				  return "redirect:/m/lecture/";
			return "redirect:/lecture/";
		}
		List<Tag> tagList = tagService.stringToTagList(tagNames);
		tagList.add(new Tag("@"+lectureName));
		
		if(!tagService.validateTag(tagList)){
			return "redirect:/lecture/"+lectureName+"/community/";
		}
		
		return "redirect:"+request.getRequestURI() +"/sort:age-desc/page:1";
	}
	
	@RequestMapping("/{lectureName}/community/tags:{tagNames}/sort:{sort}/page:{page}")
	public String tags(@PathVariable("lectureName") String lectureName,
			@PathVariable("tagNames") String tagNames,
			@PathVariable("page") int page,
			@PathVariable("sort") String sort,
			Model model,Device device){
		Lecture lecture = lectureService.get(lectureName);
		if(!permissionService.lecturePermission(lecture, weaverService.getCurrentWeaver())){
			  if (device.isMobile())
				  return "redirect:/m/lecture/";
			return "redirect:/lecture/";
		}
		List<Tag> tagList = tagService.stringToTagList(tagNames);
		tagList.add(new Tag("@"+lectureName));
		
		if(!tagService.validateTag(tagList)){
			return "redirect:/lecture/"+lectureName+"/community/";
		}

		model.addAttribute("lecture", lecture);
		model.addAttribute("posts", 
				tagService.getPostsWhenWeaver(sort,tagList,tagList.size()-1,(page-1)*10,10));
		model.addAttribute("postCount", 
				tagService.countPostsWhenWeaver(sort,tagList,tagList.size()-1));

		model.addAttribute("pageIndex", page);
		model.addAttribute("pageUrl", 
				"/lecture/"+lectureName+"/community/tags:"+tagNames+"/sort:"+sort+"/page:");
		return "/lecture/community";
	}
	
	@RequestMapping(value = "/{lectureName}/community/add")
	public String addPost(@PathVariable("lectureName") String lectureName,
			HttpServletRequest request) {
		String tags = request.getParameter("tags");
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		
		if(tags == null || title == null) // 태그가 없을 때
			return "redirect:/lecture/"+lectureName;
		else if(content == null)
			content = "";
		List<Tag> tagList = tagService.stringToTagList(
				WebUtil.removeHtml(WebUtil.specialSignDecoder(URLDecoder.decode(tags))));
		tagList.add(new Tag("@"+lectureName));
		Weaver weaver = weaverService.getCurrentWeaver();

		if(!tagService.validateTag(tagList,weaver)) // 태그에 권한이 없을때
			return "redirect:/lecture/"+lectureName;

		Post post = new Post(weaver,
				WebUtil.removeHtml(WebUtil.specialSignDecoder(URLDecoder.decode(title))), 
				WebUtil.convertHtml(WebUtil.removeHtml(WebUtil.specialSignDecoder(URLDecoder.decode(content)))), 
				tagList);
		
		postService.add(post);
		return "redirect:/lecture/"+lectureName;
	}
	
	@RequestMapping(value = {"/{lectureName}/example","/{lectureName}"})
	public String example(@PathVariable("lectureName") String lectureName,Model model,Device device) {
		
		Lecture lecture = lectureService.get(lectureName);
		if(!permissionService.lecturePermission(lecture, weaverService.getCurrentWeaver())){
			  if (device.isMobile())
				  return "redirect:/m/lecture/";
			return "redirect:/lecture/";
		}
		
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
			@PathVariable("commit") String commit,Model model,Device device) {
		
		Lecture lecture = lectureService.get(lectureName);
		if(!permissionService.lecturePermission(lecture, weaverService.getCurrentWeaver())){
			  if (device.isMobile())
				  return "redirect:/m/lecture/";
			return "redirect:/lecture/";
		}
		
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
			@PathVariable("filePath") String filePath,Model model,Device device) {
		
		Lecture lecture = lectureService.get(lectureName);
		if(!permissionService.lecturePermission(lecture, weaverService.getCurrentWeaver())){
			  if (device.isMobile())
				  return "redirect:/m/lecture/";
			return "redirect:/lecture/";
		}
		
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
			Model model,Device device) {
		
		Lecture lecture = lectureService.get(lectureName);
		
		if(!permissionService.lecturePermission(lecture, weaverService.getCurrentWeaver())){
			  if (device.isMobile())
				  return "redirect:/m/lecture/";
			return "redirect:/lecture/";
		}
		
		model.addAttribute("lecture", lecture);
		return "/lecture/manageRepo";
	}
	
	@RequestMapping("/{lectureName}/weaver")
	public String manageWeaver(@PathVariable("lectureName") String lectureName,
			Model model,Device device) {
		Lecture lecture = lectureService.get(lectureName);
		if(!permissionService.lecturePermission(lecture, weaverService.getCurrentWeaver())){
			  if (device.isMobile())
				  return "redirect:/m/lecture/";
			return "redirect:/lecture/";
		}
		
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
			if(currentWeaver.getNickName().equals(lecture.getCreatorName())){//관리자가 탈퇴시킬시에 메세지
				post = new Post(currentWeaver, 
						deleteWeaver.getNickName()+"님을 탈퇴 처리하였습니다.", "", 
						tagService.stringToTagList("@"+lecture.getName()+",탈퇴"));//강의실에 메세지 보냄
				postService.add(post);
				post = new Post(currentWeaver, 
						"강의명:"+lecture.getName()+"에서 탈퇴당하셨습니다.", "", 
						tagService.stringToTagList("$"+deleteWeaver.getNickName()));//강의실에 메세지 보냄
				postService.add(post);
			}else{//사용자가 탈퇴할시에 메세지
				post = new Post(currentWeaver, 
						deleteWeaver.getNickName()+"님이 탈퇴하셨습니다.", "", 
						tagService.stringToTagList("@"+lecture.getName()+",탈퇴"));//강의실에 메세지 보냄
				postService.add(post);
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
*/