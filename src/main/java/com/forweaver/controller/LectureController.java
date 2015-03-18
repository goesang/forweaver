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

import com.forweaver.domain.Lecture;
import com.forweaver.domain.Pass;
import com.forweaver.domain.Post;
import com.forweaver.domain.WaitJoin;
import com.forweaver.domain.Weaver;
import com.forweaver.domain.chat.ChatRoom;
import com.forweaver.domain.git.GitFileInfo;
import com.forweaver.domain.git.GitSimpleCommitLog;
import com.forweaver.domain.git.GitSimpleFileInfo;
import com.forweaver.service.ChatService;
import com.forweaver.service.GitService;
import com.forweaver.service.LectureService;
import com.forweaver.service.PostService;
import com.forweaver.service.RePostService;
import com.forweaver.service.TagService;
import com.forweaver.service.WaitJoinService;
import com.forweaver.service.WeaverService;
import com.forweaver.util.WebUtil;

@Controller
@RequestMapping("/lecture")
public class LectureController {

	@Autowired 
	private WaitJoinService waitJoinService;
	@Autowired 
	private LectureService lectureService;
	@Autowired 
	private WeaverService weaverService;
	@Autowired 
	private GitService gitService;
	@Autowired 
	private TagService tagService;
	@Autowired 
	private PostService postService;
	@Autowired 
	private RePostService rePostService;
	@Autowired 
	private ChatService chatService;
	
	@RequestMapping("/")
	public String lectures() {
		return "redirect:/lecture/page:1";
	}
		
	@RequestMapping("/page:{page}")
	public String page(@PathVariable("page") String page,Model model) {
		int pageNum = WebUtil.getPageNumber(page);
		int size = WebUtil.getPageSize(page);
		
		Weaver currentWeaver = weaverService.getCurrentWeaver();
		model.addAttribute("lectures", lectureService.getLectures(currentWeaver, null, null, pageNum, size));
		model.addAttribute("lectureCount", lectureService.countLectures(null, ""));
		model.addAttribute("pageIndex", page);
		model.addAttribute("number", size);
		model.addAttribute("pageUrl", "/lecture/page:");
		return "/lecture/lectures";
	}
	
	
	
	@RequestMapping("/tags:{tagNames}")
	public String tagsWithPage(HttpServletRequest request){
		return "redirect:"+request.getRequestURI() +"/page:1";
	}
	
	@RequestMapping("/tags:{tagNames}/page:{page}")
	public String tagsWithPage(@PathVariable("tagNames") String tagNames,
			@PathVariable("page") String page,Model model) {
	
		List<String> tagList = tagService.stringToTagList(tagNames);
		int pageNum = WebUtil.getPageNumber(page);
		int size = WebUtil.getPageSize(page);
		
		Weaver currentWeaver = weaverService.getCurrentWeaver();
		model.addAttribute("lectures", lectureService.getLectures(currentWeaver, tagList, null, pageNum, size));
		model.addAttribute("lectureCount", lectureService.countLectures(tagList,null));
		model.addAttribute("pageIndex", page);
		model.addAttribute("number", size);
		model.addAttribute("pageUrl", "/lecture/tags:"+tagNames+"/page:");
		return "/lecture/lectures";
	}
	
	
	@RequestMapping("/tags:{tagNames}/search:{search}")
	public String tagsWithSearch(HttpServletRequest request){
		return "redirect:"+ request.getRequestURI() +"/page:1";
	}
	@RequestMapping("/tags:{tagNames}/search:{search}/page:{page}")
	public String tagsWithSearch(@PathVariable("tagNames") String tagNames,
			@PathVariable("search") String search,
			@PathVariable("page") String page,Model model) {
		List<String> tagList = tagService.stringToTagList(tagNames);
		int pageNum = WebUtil.getPageNumber(page);
		int size = WebUtil.getPageSize(page);
		
		Weaver currentWeaver = weaverService.getCurrentWeaver();
		model.addAttribute("lectures", lectureService.getLectures(currentWeaver,tagList,search,pageNum, size));
		model.addAttribute("lectureCount", lectureService.countLectures(tagList,search));
		model.addAttribute("pageIndex", page);
		model.addAttribute("number", size);
		model.addAttribute("pageUrl", "/lecture/tags:"+tagNames+"/search:"+search+"/page:");
		return "/lecture/lectures";
	}
			
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String add(@RequestParam Map<String, String> params) {
		Weaver currentWeaver = weaverService.getCurrentWeaver();
		List<String> tagList = tagService.stringToTagList(params.get("tags"));
		if(!tagService.isPublicTags(tagList))
			return "redirect:/lecture/";
		Lecture lecture = new Lecture(params.get("name"),
										params.get("description"),
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
	public String community(HttpServletRequest request) {
		return "redirect:"+request.getRequestURI() +"/sort:age-desc/page:1";
	}
	
	@RequestMapping("/{lectureName}/community/sort:{sort}/page:{page}")
	public String community(@PathVariable("lectureName") String lectureName,
			@PathVariable("sort") String sort,
			@PathVariable("page") String page,Model model) {
		int pageNum = WebUtil.getPageNumber(page);
		int size = WebUtil.getPageSize(page);
		
		Lecture lecture = lectureService.get(lectureName);
		Weaver currentWeaver = weaverService.getCurrentWeaver();
		List<String> tagList = new ArrayList<String>();
		tagList.add("@"+lectureName);
		
		model.addAttribute("lecture", lecture);
		model.addAttribute("posts", 
				postService.getPosts(currentWeaver, tagList, sort, pageNum, size));
		model.addAttribute("postCount", 
				postService.countPosts(currentWeaver, tagList, sort));
		
		model.addAttribute("pageIndex", page);
		model.addAttribute("pageUrl", "/lecture/"+lectureName+"/community/sort:"+sort+"/page:");
		return "/lecture/community";
	}

	@RequestMapping("/{lectureName}/community/tags:{tagNames}")
	public String tags(HttpServletRequest request){
		return "redirect:"+request.getRequestURI() +"/sort:age-desc/page:1";
	}
	
	@RequestMapping("/{lectureName}/community/tags:{tagNames}/sort:{sort}/page:{page}")
	public String tags(@PathVariable("lectureName") String lectureName,
			@PathVariable("tagNames") String tagNames,
			@PathVariable("page") String page,
			@PathVariable("sort") String sort,
			Model model){

		int pageNum = WebUtil.getPageNumber(page);
		int size = WebUtil.getPageSize(page);
		
		Lecture lecture = lectureService.get(lectureName);
		List<String> tagList = tagService.stringToTagList(tagNames);
		tagList.add(new String("@"+lectureName));
		Weaver currentWeaver = weaverService.getCurrentWeaver();
	
		model.addAttribute("lecture", lecture);
		model.addAttribute("posts", 
				postService.getPosts(currentWeaver, tagList, sort, pageNum, size));
		model.addAttribute("postCount", 
				postService.countPosts(currentWeaver, tagList, sort));

		model.addAttribute("pageIndex", page);
		model.addAttribute("pageUrl", 
				"/lecture/"+lectureName+"/community/tags:"+tagNames+"/sort:"+sort+"/page:");
		return "/lecture/community";
	}
	//에러 수정할 필요 있음.
	@RequestMapping(value = "/{lectureName}/community/add")
	public String addPost(@PathVariable("lectureName") String lectureName,
			HttpServletRequest request) throws UnsupportedEncodingException {
			
		String tags = request.getParameter("tags");
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		
		if(tags == null || title == null) // 태그가 없을 때
			return "redirect:/lecture/"+lectureName;
		else if(content == null)
			content = "";
		List<String> tagList = tagService.stringToTagList(tags);
		tagList.add(new String("@"+lectureName));
		Weaver weaver = weaverService.getCurrentWeaver();

		if(!tagService.validateTag(tagList,weaver)) // 태그에 권한이 없을때
			return "redirect:/lecture/"+lectureName;
			
        
		Post post = new Post(weaver,title,content,tagList);
		
		postService.add(post,null);
		return "redirect:/lecture/"+lectureName+"/community";
	}
	
	@RequestMapping(value = {"/{lectureName}/example","/{lectureName}"})
	public String example(@PathVariable("lectureName") String lectureName,Model model) {
		
		Lecture lecture = lectureService.get(lectureName);		
		List<String> gitBranchList = gitService.getBranchList(lectureName, "example");
		
		String readme = "";
		
		List<GitSimpleFileInfo> gitFileInfoList = 
				gitService.getGitSimpleFileInfoList(lectureName, "example","HEAD","");
		
		if(gitFileInfoList != null) for(GitSimpleFileInfo gitSimpleFileInfo:gitFileInfoList)// 파일들을 검색해서 리드미 파일을 찾아냄
			if(gitSimpleFileInfo.getName().toUpperCase().equals("README.MD"))
				readme = WebUtil.markDownEncoder(
						gitService.getFileInfo(
								lectureName, 
								"example", 
								"HEAD", 
								gitSimpleFileInfo.getName()).getContent());
		
		
		model.addAttribute("lecture", lecture);
		model.addAttribute("gitFileInfoList",gitFileInfoList);
		model.addAttribute("gitBranchList", gitBranchList.subList(1, gitBranchList.size()));
		model.addAttribute("selectBranch",gitBranchList.get(0));
		model.addAttribute("readme",readme);
		return "/lecture/example";
	}
	
	@RequestMapping("/{lectureName}/example/commit:{commit}")
	public String example(@PathVariable("lectureName") String lectureName,
			@PathVariable("commit") String commit,Model model,
			HttpServletRequest request) {
		
		Lecture lecture = lectureService.get(lectureName);

		String uri = request.getRequestURI();
		commit = uri.substring(uri.indexOf("/commit:")+8);
		
		String readme = "";
		List<GitSimpleFileInfo> gitFileInfoList = 
				gitService.getGitSimpleFileInfoList(lectureName, "example",commit,"");
		
		if(gitFileInfoList != null) for(GitSimpleFileInfo gitSimpleFileInfo:gitFileInfoList)// 파일들을 검색해서 리드미 파일을 찾아냄
			if(gitSimpleFileInfo.getName().toUpperCase().equals("README.MD"))
				readme = WebUtil.markDownEncoder(
						gitService.getFileInfo(
								lectureName, 
								"example", 
								commit, 
								gitSimpleFileInfo.getName()).getContent());
		
		model.addAttribute("lecture", lecture);
		model.addAttribute("gitFileInfoList",gitService.
				getGitSimpleFileInfoList(lectureName, "example",commit,""));
		List<String> gitBranchList = gitService.getBranchList(lectureName, "example");
		gitBranchList.remove(commit);
		model.addAttribute("gitBranchList", gitBranchList);
		model.addAttribute("selectBranch",commit);
		model.addAttribute("readme",readme);
		return "/lecture/example";
	}
	
	@RequestMapping("/{lectureName}/example/commit:{commit}/**")
	public String fileViewer(HttpServletRequest request,@PathVariable("lectureName") String lectureName,
			@PathVariable("commit") String commit,Model model) {
		String uri = request.getRequestURI();
		commit = uri.substring(uri.indexOf("/commit:")+8);
		commit = commit.substring(0, commit.indexOf("/"));
		
		Lecture lecture = lectureService.get(lectureName);		
		String filePath = uri.substring(uri.indexOf("filepath:")+9);

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


	@RequestMapping( "/{lectureName}/weaver/{weaverName}/delete") // 회원 삭제용
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
				return "redirect:/lecture/"+lectureName+"/weaver";
			}else{//사용자가 탈퇴할시에 메세지
				post = new Post(currentWeaver, 
						deleteWeaver.getId()+"님이 탈퇴하셨습니다.", "", 
						tagService.stringToTagList("@"+lecture.getName()+",탈퇴"));//강의실에 메세지 보냄
				postService.add(post,null);
				return "redirect:/";
			}
			
		}
		return "redirect:/";
	}
	
	@RequestMapping("/{lectureName}/weaver/{weaver}/add-weaver")
	public String addWeaver(	@PathVariable("lectureName") String lectureName,
			@PathVariable("weaver") String weaver) {
		Lecture lecture = lectureService.get(lectureName);
		Weaver waitingWeaver = weaverService.get(weaver);
		Weaver proposer = weaverService.getCurrentWeaver();

		if(waitJoinService.isCreateWaitJoin(lecture, waitingWeaver, proposer)){
			Weaver lectureCreator = weaverService.get(lecture.getCreatorName());
			String title ="강의명:"+lectureName+"에 가입 초대를 <a href='/lecture/"+lectureName+"/weaver/"+weaver+"/join-ok'>승락하시겠습니까?</a> "
					+ "아니면 <a href='/lecture/"+lectureName+"/weaver/"+weaver+"/cancel'>거절하시겠습니까?</a>";
			
			Post post = new Post(lectureCreator,
					title, 
					"", 
					tagService.stringToTagList("$"+weaver));
			waitJoinService.createWaitJoin(
					lecture.getName(), 
					proposer.getId(), 
					waitingWeaver.getId(), 
					postService.add(post,null));
		}
		return "redirect:/lecture/"+ lectureName+"/weaver";			
		
	}
	
	
	@RequestMapping("/{lectureName}/join") //본인이 직접 신청
	public String join(@PathVariable("lectureName") String lectureName) {
		Lecture lecture = lectureService.get(lectureName);
		Weaver waitingWeaver = weaverService.getCurrentWeaver();

		if(waitJoinService.isCreateWaitJoin(lecture, waitingWeaver, waitingWeaver)){
			String title = waitingWeaver.getId()+"님이 강의명:"+lectureName+"에 가입 신청을 <a href='/lecture/"+lectureName+"/weaver/"+waitingWeaver.getId()+"/join-ok'>승락하시겠습니까?</a> "
					+ "아니면 <a href='/lecture/"+lectureName+"/weaver/"+waitingWeaver.getId()+"/cancel'>거절하시겠습니까?</a>";
			Post post = new Post(waitingWeaver,
					title, 
					"", 
					tagService.stringToTagList("$"+lecture.getCreatorName()));
			waitJoinService.createWaitJoin(
					lecture.getName(), 
					waitingWeaver.getId(), 
					waitingWeaver.getId(), 
					postService.add(post,null));
			
		}
		return "redirect:/";			
		
	}
	
	
	@RequestMapping("/{lectureName}/weaver/{weaver}/join-ok") // 강의 가입 승인
	public String joinOK(@PathVariable("lectureName") String lectureName,@PathVariable("weaver") String weaver) {
		Lecture lecture = lectureService.get(lectureName);
		Weaver currentWeaver = weaverService.getCurrentWeaver();
		Weaver waitingWeaver = weaverService.get(weaver);
		WaitJoin waitJoin = waitJoinService.get(lectureName, weaver);
		Pass pass = new Pass(lectureName, 1);

		if(waitJoinService.isOkJoin(waitJoin, lecture.getCreatorName(), currentWeaver) //요청자가 쪽지를 보내고 관리자가 승인을 하는 경우
				&& lecture.getCreatorName().equals(currentWeaver.getId())
				&& waitJoinService.deleteWaitJoin(waitJoin, lecture, waitingWeaver)){
						
			lecture.addJoinWeaver(waitingWeaver); //강의 목록에 추가
			waitingWeaver.addPass(pass);
			weaverService.update(waitingWeaver);
			lectureService.update(lecture);
			Post post = new Post(waitingWeaver, 
					"관리자 "+lecture.getCreatorName()+"님의 승인으로 강의명:"+
					"<a href='/lecture/"+lectureName+"/'>"+
					lectureName+
					"</a>"+
					"에 가입이 승인되었습니다!", 
					"", 
					tagService.stringToTagList("@"+lecture.getName()+",가입")); //@강의명,가입 태그를 걸어줌
			
			postService.add(post,null);
			
			return "redirect:/lecture/"+lectureName+"/weaver";
			
		}else if(lecture != null //관리자가 쪽지를 보내고 가입자가 승인을 하는 경우
				&& waitJoinService.isOkJoin(waitJoin, lecture.getCreatorName(), currentWeaver)
				&& !lecture.getCreatorName().equals(currentWeaver.getId())
				&& waitJoinService.deleteWaitJoin(waitJoin, lecture, currentWeaver)){
			lecture.addJoinWeaver(currentWeaver); //강의 목록에 추가
			currentWeaver.addPass(pass);
			weaverService.update(currentWeaver);
			lectureService.update(lecture);
			
			Post post = new Post(currentWeaver, //가입자가 관리자에게 보내는 메세지
					currentWeaver.getId()+"님이 강의명:"+
					"<a href='/lecture/"+lectureName+"'>"+
							lectureName+
							"</a>"+"를 가입 초대를 수락하셨습니다!", 
					"", 
					tagService.stringToTagList("@"+lecture.getName()+",가입")); //@강의명,가입 태그를 걸어줌
			
			postService.add(post,null);
			
			return "redirect:/lecture/"+lectureName+"/weaver";
		}
		
		return "redirect:/";//엉뚱한 사람이 들어올때 그냥 돌려보냄
	}
	
	@RequestMapping("/{lectureName}/weaver/{weaver}/cancel") //강의에 가입 승인 취소
	public String joinCancel(@PathVariable("lectureName") String lectureName,@PathVariable("weaver") String weaver) {
		Lecture lecture = lectureService.get(lectureName);
		Weaver currentWeaver = weaverService.getCurrentWeaver();
		WaitJoin waitJoin = waitJoinService.get(lecture.getName(), weaver);
			
		if(lecture != null //요청자가 쪽지를 보내고 관리자가 승인을 하는 경우
				&& waitJoinService.isOkJoin(waitJoin, lecture.getCreatorName(), currentWeaver)
				&& lecture.getCreatorName().equals(currentWeaver.getId())
				&& waitJoinService.deleteWaitJoin(waitJoin, lecture, currentWeaver)){
			
			Post post = new Post(currentWeaver,  //관리자가 가입자에게 보내는 메세지
					"관리자 "+lecture.getCreatorName()+"님의 강의명:"+
					lectureName+
					"에 가입이 거절되었습니다.", 
					"", 
					tagService.stringToTagList("$"+weaver));
			
			postService.add(post,null);
			
			return "redirect:/lecture/"+lectureName+"/weaver";
			
		}else if(lecture != null //관리자가 쪽지를 보내고 가입자가 거절 하는 경우
				&& waitJoinService.isOkJoin(waitJoin, lecture.getCreatorName(), currentWeaver)
				&& !lecture.getCreatorName().equals(currentWeaver.getId())
				&& waitJoinService.deleteWaitJoin(waitJoin, lecture, currentWeaver)){
						
			Post post = new Post(currentWeaver, //가입자가 관리자에게 보내는 메세지
					currentWeaver.getId()+"님이 강의명:"+
					"<a href='/lecture/"+lectureName+"'>"+
							lectureName+
							"</a>"+"를 가입 초대를 거절하셨습니다.", 
					"", 
					tagService.stringToTagList("$"+lecture.getCreatorName()));
			
			postService.add(post,null);
			
			return "redirect:/";
		}
		
		return "redirect:/";//엉뚱한 사람이 들어올때 그냥 돌려보냄
	}
	
	@RequestMapping("/{lectureName}/chat") //채팅
	public String chat(@PathVariable("lectureName") String lectureName,Model model){
		Lecture lecture = lectureService.get(lectureName);	
		Weaver currentWeaver = weaverService.getCurrentWeaver();
		ChatRoom chatRoom = chatService.get(lectureName);
		model.addAttribute("lecture", lecture);
		model.addAttribute("chatRoom", chatRoom);
		model.addAttribute("weaver", currentWeaver);
		
		return "/lecture/chat";
	}

}