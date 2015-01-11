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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.forweaver.domain.Data;
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
		int pageNum;
		int number = 15;
		
		if(page.contains(",")){
			pageNum = Integer.parseInt(page.split(",")[0]);
			number = Integer.parseInt(page.split(",")[1]);
		}else{
			pageNum =Integer.parseInt(page);
		}
		Weaver currentWeaver = weaverService.getCurrentWeaver();
		model.addAttribute("lectures", lectureService.getLectures(currentWeaver, pageNum, number));
		model.addAttribute("lectureCount", lectureService.countLectures());
		model.addAttribute("pageIndex", page);
		model.addAttribute("number", number);
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
		model.addAttribute("lectures", lectureService.getLecturesWithTags(currentWeaver,tagList,pageNum, number));
		model.addAttribute("lectureCount", lectureService.countLecturesWithTags(tagList));
		model.addAttribute("pageIndex", page);
		model.addAttribute("number", number);
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
		model.addAttribute("lectures", lectureService.getLecturesWithTagsAndSearch(currentWeaver,tagList,search,pageNum, number));
		model.addAttribute("lectureCount", lectureService.countLecturesWithTagsAndSearch(tagList,search));
		model.addAttribute("pageIndex", page);
		model.addAttribute("number", number);
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
	public String community(HttpServletRequest request) {
		return "redirect:"+request.getRequestURI() +"/sort:age-desc/page:1";
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
	public String tags(HttpServletRequest request){
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
	//���� ������ �ʿ� ����.
	@RequestMapping(value = "/{lectureName}/community/add")
	public String addPost(@PathVariable("lectureName") String lectureName,
			HttpServletRequest request) throws UnsupportedEncodingException {
			
		String tags = request.getParameter("tags");
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		
		if(tags == null || title == null) // �±װ� ���� ��
			return "redirect:/lecture/"+lectureName;
		else if(content == null)
			content = "";
		List<String> tagList = tagService.stringToTagList(
				WebUtil.removeHtml(WebUtil.specialSignDecoder(URLDecoder.decode(tags))));
		tagList.add(new String("@"+lectureName));
		Weaver weaver = weaverService.getCurrentWeaver();

		if(!tagService.validateTag(tagList,weaver)) // �±׿� ������ ������
			return "redirect:/lecture/"+lectureName;
			
        
		Post post = new Post(weaver,
				WebUtil.removeHtml(WebUtil.specialSignDecoder(URLDecoder.decode(title))), 
				WebUtil.removeHtml(WebUtil.specialSignDecoder(URLDecoder.decode(content))), 
				tagList);
		
		postService.add(post,null);
		return "redirect:/lecture/"+lectureName+"/community";
	}
	
	@RequestMapping(value = {"/{lectureName}/example","/{lectureName}"})
	public String example(@PathVariable("lectureName") String lectureName,Model model) {
		
		Lecture lecture = lectureService.get(lectureName);		
		List<String> gitBranchList = gitService.getBranchList(lectureName, "example");
		
		String readme = "";
		
		List<GitSimpleFileInfo> gitFileInfoList = 
				gitService.getGitSimpleFileInfoList(lectureName, "example","HEAD");
		
		if(gitFileInfoList != null) for(GitSimpleFileInfo gitSimpleFileInfo:gitFileInfoList)// ���ϵ��� �˻��ؼ� ����� ������ ã�Ƴ�
			if(gitSimpleFileInfo.getDepth() == 0 && gitSimpleFileInfo.getName().toUpperCase().equals("README.MD"))
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
			@PathVariable("commit") String commit,Model model) {
		
		Lecture lecture = lectureService.get(lectureName);
		commit = commit.replace(",", ".");
		String readme = "";
		List<GitSimpleFileInfo> gitFileInfoList = 
				gitService.getGitSimpleFileInfoList(lectureName, "example",commit);
		
		if(gitFileInfoList != null) for(GitSimpleFileInfo gitSimpleFileInfo:gitFileInfoList)// ���ϵ��� �˻��ؼ� ����� ������ ã�Ƴ�
			if(gitSimpleFileInfo.getDepth() == 0 && gitSimpleFileInfo.getName().toUpperCase().equals("README.MD"))
				readme = WebUtil.markDownEncoder(
						gitService.getFileInfo(
								lectureName, 
								"example", 
								commit, 
								gitSimpleFileInfo.getName()).getContent());
		
		model.addAttribute("lecture", lecture);
		model.addAttribute("gitFileInfoList",gitService.
				getGitSimpleFileInfoList(lectureName, "example",commit));
		List<String> gitBranchList = gitService.getBranchList(lectureName, "example");
		gitBranchList.remove(commit);
		model.addAttribute("gitBranchList", gitBranchList);
		model.addAttribute("selectBranch",commit);
		model.addAttribute("readme",readme);
		return "/lecture/example";
	}
	
	@RequestMapping("/{lectureName}/example/commit:{commit}/filepath:{filePath}")
	public String fileViewer(@PathVariable("lectureName") String lectureName,
			@PathVariable("commit") String commit,
			@PathVariable("filePath") String filePath,Model model) {
		
		Lecture lecture = lectureService.get(lectureName);		
		commit = commit.replace(",", ".");
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


	@RequestMapping( "/{lectureName}/weaver/{weaverName}/delete") // ȸ�� ������
	public String deleteWeaver(@PathVariable("lectureName") String lectureName,
			@PathVariable("weaverName") String weaverName) {
		Lecture lecture = lectureService.get(lectureName);
		Weaver currentWeaver = weaverService.getCurrentWeaver();
		Weaver deleteWeaver = weaverService.get(weaverName);
		
		if(lectureService.deleteWeaver(lecture, currentWeaver,deleteWeaver)){
			Post post;
			if(currentWeaver.getId().equals(lecture.getCreatorName())){//�����ڰ� Ż���ų�ÿ� �޼���
				post = new Post(currentWeaver, 
						deleteWeaver.getId()+"���� Ż�� ó���Ͽ����ϴ�.", "", 
						tagService.stringToTagList("@"+lecture.getName()+",Ż��"));//���ǽǿ� �޼��� ����
				postService.add(post,null);
				post = new Post(currentWeaver, 
						"���Ǹ�:"+lecture.getName()+"���� Ż����ϼ̽��ϴ�.", "", 
						tagService.stringToTagList("$"+deleteWeaver.getId()));//���ǽǿ� �޼��� ����
				postService.add(post,null);
			}else{//����ڰ� Ż���ҽÿ� �޼���
				post = new Post(currentWeaver, 
						deleteWeaver.getId()+"���� Ż���ϼ̽��ϴ�.", "", 
						tagService.stringToTagList("@"+lecture.getName()+",Ż��"));//���ǽǿ� �޼��� ����
				postService.add(post,null);
			}
			
		}
		return "redirect:/lecture/" + lectureName;
	}
	
	@RequestMapping("/{lectureName}/weaver/{weaver}/add-weaver")
	public String addWeaver(	@PathVariable("lectureName") String lectureName,
			@PathVariable("weaver") String weaver) {
		Lecture lecture = lectureService.get(lectureName);
		Weaver waitingWeaver = weaverService.get(weaver);
		Weaver proposer = weaverService.getCurrentWeaver();

		if(waitJoinService.isCreateLectureWaitJoin(lecture, waitingWeaver, proposer)){
			Weaver lectureCreator = weaverService.get(lecture.getCreatorName());
			String title ="���Ǹ�:"+lectureName+"�� ���� �ʴ븦 <a href='/lecture/"+lectureName+"/weaver/"+weaver+"/join-ok'>�¶��Ͻðڽ��ϱ�?</a> "
					+ "�ƴϸ� <a href='/lecture/"+lectureName+"/weaver/"+weaver+"/join-cancel'>�����Ͻðڽ��ϱ�?</a>";
			
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
	
	
	@RequestMapping("/{lectureName}/join") //������ ���� ��û
	public String join(@PathVariable("lectureName") String lectureName) {
		Lecture lecture = lectureService.get(lectureName);
		Weaver waitingWeaver = weaverService.getCurrentWeaver();

		if(waitJoinService.isCreateLectureWaitJoin(lecture, waitingWeaver, waitingWeaver)){
			String title = waitingWeaver.getId()+"���� ���Ǹ�:"+lectureName+"�� ���� ��û�� <a href='/lecture/"+lectureName+"/weaver/"+waitingWeaver.getId()+"/join-ok'>�¶��Ͻðڽ��ϱ�?</a> "
					+ "�ƴϸ� <a href='/lecture/"+lectureName+"/weaver/"+waitingWeaver.getId()+"/join-cancel'>�����Ͻðڽ��ϱ�?</a>";
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
	
	
	@RequestMapping("/{lectureName}/weaver/{weaver}/join-ok") // ���� ���� ����
	public String joinOK(@PathVariable("lectureName") String lectureName,@PathVariable("weaver") String weaver) {
		Lecture lecture = lectureService.get(lectureName);
		Weaver currentWeaver = weaverService.getCurrentWeaver();
		Weaver waitingWeaver = weaverService.get(weaver);
		WaitJoin waitJoin = waitJoinService.get(lectureName, weaver);
		Pass pass = new Pass(lectureName, 0);

		if(waitJoinService.isOkJoin(waitJoin, lecture.getCreatorName(), currentWeaver) //��û�ڰ� ������ ������ �����ڰ� ������ �ϴ� ���
				&& lecture.getCreatorName().equals(currentWeaver.getId())
				&& waitJoinService.deleteLectureWaitJoin(waitJoin, lecture, waitingWeaver)){
						
			lecture.addJoinWeaver(waitingWeaver); //���� ��Ͽ� �߰�
			waitingWeaver.addPass(pass);
			weaverService.update(waitingWeaver);
			lectureService.update(lecture);
			Post post = new Post(waitingWeaver, 
					"������ "+lecture.getCreatorName()+"���� �������� ���Ǹ�:"+
					"<a href='/lecture/"+lectureName+"/'>"+
					lectureName+
					"</a>"+
					"�� ������ ���εǾ����ϴ�!", 
					"", 
					tagService.stringToTagList("@"+lecture.getName()+",����")); //@���Ǹ�,���� �±׸� �ɾ���
			
			postService.add(post,null);
			
			return "redirect:/lecture/"+lectureName+"/weaver";
			
		}else if(lecture != null //�����ڰ� ������ ������ �����ڰ� ������ �ϴ� ���
				&& waitJoinService.isOkJoin(waitJoin, lecture.getCreatorName(), currentWeaver)
				&& !lecture.getCreatorName().equals(currentWeaver.getId())
				&& waitJoinService.deleteLectureWaitJoin(waitJoin, lecture, currentWeaver)){
			lecture.addJoinWeaver(currentWeaver); //���� ��Ͽ� �߰�
			currentWeaver.addPass(pass);
			weaverService.update(currentWeaver);
			lectureService.update(lecture);
			
			Post post = new Post(currentWeaver, //�����ڰ� �����ڿ��� ������ �޼���
					currentWeaver.getId()+"���� ���Ǹ�:"+
					"<a href='/lecture/"+lectureName+"'>"+
							lectureName+
							"</a>"+"�� ���� �ʴ븦 �����ϼ̽��ϴ�!", 
					"", 
					tagService.stringToTagList("@"+lecture.getName()+",����")); //@���Ǹ�,���� �±׸� �ɾ���
			
			postService.add(post,null);
			
			return "redirect:/lecture/"+lectureName+"/manage";
		}
		
		return "redirect:/";//������ ����� ���ö� �׳� ��������
	}
	
	@RequestMapping("/{lectureName}/weaver/{weaver}/join-cancel") //���ǿ� ���� ���� ���
	public String joinCancel(@PathVariable("lectureName") String lectureName,@PathVariable("weaver") String weaver) {
		Lecture lecture = lectureService.get(lectureName);
		Weaver currentWeaver = weaverService.getCurrentWeaver();
		WaitJoin waitJoin = waitJoinService.get(lecture.getName(), weaver);
			
		if(lecture != null //��û�ڰ� ������ ������ �����ڰ� ������ �ϴ� ���
				&& waitJoinService.isOkJoin(waitJoin, lecture.getCreatorName(), currentWeaver)
				&& lecture.getCreatorName().equals(currentWeaver.getId())
				&& waitJoinService.deleteLectureWaitJoin(waitJoin, lecture, currentWeaver)){
			
			Post post = new Post(currentWeaver,  //�����ڰ� �����ڿ��� ������ �޼���
					"������ "+lecture.getCreatorName()+"���� ���Ǹ�:"+
					lectureName+
					"�� ������ �����Ǿ����ϴ�.", 
					"", 
					tagService.stringToTagList("$"+weaver));
			
			postService.add(post,null);
			
			return "redirect:/lecture/"+lectureName+"/weaver";
			
		}else if(lecture != null //�����ڰ� ������ ������ �����ڰ� ���� �ϴ� ���
				&& waitJoinService.isOkJoin(waitJoin, lecture.getCreatorName(), currentWeaver)
				&& !lecture.getCreatorName().equals(currentWeaver.getId())
				&& waitJoinService.deleteLectureWaitJoin(waitJoin, lecture, currentWeaver)){
						
			Post post = new Post(currentWeaver, //�����ڰ� �����ڿ��� ������ �޼���
					currentWeaver.getId()+"���� ���Ǹ�:"+
					"<a href='/lecture/"+lectureName+"'>"+
							lectureName+
							"</a>"+"�� ���� �ʴ븦 �����ϼ̽��ϴ�.", 
					"", 
					tagService.stringToTagList("$"+lecture.getCreatorName()));
			
			postService.add(post,null);
			
			return "redirect:/lecture/"+lectureName+"/manage";
		}
		
		return "redirect:/";//������ ����� ���ö� �׳� ��������
	}
	
	@RequestMapping("/{lectureName}/chat") //ä��
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