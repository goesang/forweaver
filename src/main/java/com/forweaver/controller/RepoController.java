/*package com.forweaver.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.forweaver.domain.Lecture;
import com.forweaver.domain.Repo;
import com.forweaver.domain.Weaver;
import com.forweaver.domain.git.GitFileInfo;
import com.forweaver.domain.git.GitSimpleCommitLog;
import com.forweaver.service.GitService;
import com.forweaver.service.LectureService;
import com.forweaver.service.PermissionService;
import com.forweaver.service.RepoService;
import com.forweaver.service.WeaverService;
import com.forweaver.util.WebUtil;

@Controller
@RequestMapping("/lecture/{lectureName}/repo")
public class RepoController {

	@Autowired
	WeaverService weaverService;
	@Autowired
	LectureService lectureService;
	@Autowired
	RepoService repoService;
	@Autowired
	GitService gitService;
	@Autowired
	PermissionService permissionService;
	
	@RequestMapping(value = "/add")
	@ResponseBody
	public boolean add(@PathVariable("lectureName") String lectureName,
			@RequestParam Map<String, String> params) {
		Lecture lecture = lectureService.get(lectureName);
		
		if(!permissionService.repoPermission(lecture, weaverService.getCurrentWeaver()))
			return false; 
		
		Repo repo = new Repo(params.get("name"), 
				Integer.parseInt(params.get("category")), 
				WebUtil.removeHtml(params.get("description")), 
				Integer.parseInt(params.get("period")), 
				lecture);	
		lecture.getRepoes().add(repo);
		repoService.add(repo);
		return true;
	}
	
	@RequestMapping("/{repoName}/delete")
	public String delete(Model model,
			@PathVariable("lectureName") String lectureName,
			@PathVariable("repoName") String repoName) {
		Lecture lecture = lectureService.get(lectureName);
		Repo repo = lecture.getRepo(repoName);
		Weaver weaver = weaverService.getCurrentWeaver();
		
		if(repo == null ||!permissionService.repoPermission(lecture, weaver))
			return "redirect:/lecture/"; 
		repoService.delete(repo);
		return "redirect:/lecture/"+lectureName+"/repo"; 
	}
	
	@RequestMapping("/{repoName}/browser")
	public String manage(Model model,@PathVariable("lectureName") String lectureName,
			@PathVariable("repoName") String repoName) {
		Lecture lecture = lectureService.get(lectureName);
		Repo repo = lecture.getRepo(repoName);
		Weaver weaver = weaverService.getCurrentWeaver();
		
		if(repo == null || !permissionService.lecturePermission(lecture,weaver))
			return "redirect:/lecture/"; 
		
		List<String> gitBranchList;
		if(permissionService.repoPermission(lecture,weaver))
			gitBranchList = gitService.getBranchList(lectureName, repoName);
		else
			gitBranchList = gitService.getBranchList(lectureName, repoName,weaver.getNickName());
		
		model.addAttribute("repo", repo);
		model.addAttribute("gitFileInfoList", 
				gitService.getGitSimpleFileInfoList(lectureName, repoName,"master"));
		if(gitBranchList.size() > 1){
			model.addAttribute("gitBranchList", gitBranchList.subList(1, gitBranchList.size()));
			model.addAttribute("selectBranch",gitBranchList.get(0));
		}
		return "/repo/browser";
	}
	
	@RequestMapping("/{repoName}/browser/commit:{commit}")
	public String browser(@PathVariable("lectureName") String lectureName,
			@PathVariable("repoName") String repoName,
			@PathVariable("commit") String commit,Model model) {
		Lecture lecture = lectureService.get(lectureName);
		Repo repo = lecture.getRepo(repoName);
		Weaver weaver = weaverService.getCurrentWeaver();
		
		if(repo == null || !permissionService.lecturePermission(lecture, weaver))
			return "redirect:/lecture/"; 
		
		List<String> gitBranchList;
		
		if(!permissionService.repoPermission(lecture,weaver)){ // 만약 강사가 아닌 경우
			if(!gitService.isReadCommit(lectureName, repoName, weaver, commit)) 
				return "redirect:/lecture/"; // 읽을 수 있는 커밋 네임을 입력했는지 검사
			gitBranchList = gitService.getBranchList(lectureName, repoName,weaver.getNickName());
		}else
			gitBranchList = gitService.getBranchList(lectureName, repoName);
		
		
		model.addAttribute("repo", repo);
		model.addAttribute("gitFileInfoList", 
				gitService.getGitSimpleFileInfoList(lectureName,repoName,commit));
		
		gitBranchList.remove(commit);
		model.addAttribute("gitBranchList", gitBranchList);
		model.addAttribute("selectBranch",commit);
		
		return "/repo/browser";
	}
	
	@RequestMapping("/{repoName}/browser/commit:{commitID}/filepath:{filePath}")
	public String fileViewer(@PathVariable("lectureName") String lectureName,
			@PathVariable("repoName") String repoName,
			@PathVariable("commitID") String commitID,
			@PathVariable("filePath") String filePath,Model model) {
		Lecture lecture = lectureService.get(lectureName);
		Repo repo = lecture.getRepo(repoName);
		Weaver weaver = weaverService.getCurrentWeaver();
		
		if(repo == null || !permissionService.lecturePermission(lecture, weaver))
			return "redirect:/lecture/"; 
		
		GitFileInfo gitFileInfo;
	
		if(!permissionService.repoPermission(lecture,weaver)){ // 만약 강사가 아닌 경우{
			if(!gitService.isReadCommit(lectureName, repoName, weaver, commitID)) 
				return "redirect:/lecture/"; 
			gitFileInfo = gitService.getFileInfo(
					lectureName,repoName, commitID, filePath,weaver.getNickName());
		}else
			gitFileInfo = gitService.getFileInfo(lectureName,repoName, commitID, filePath);
		// 읽을 수 있는 커밋 네임을 입력했는지 검사
		
		model.addAttribute("repo", repo);
		model.addAttribute("fileName", gitFileInfo.getName());
		model.addAttribute("fileContent", gitFileInfo.getContent());
		model.addAttribute("gitLogList", gitFileInfo.getGitLogList());
		model.addAttribute("selectCommitIndex", gitFileInfo.getSelectCommitIndex());
		model.addAttribute("gitCommitLog", 
				new GitSimpleCommitLog(gitFileInfo.getSelectCommitLog()));
		return "/repo/fileViewer";
	}
			
	@RequestMapping("/{repoName}/commitlog")
	public String commitLog(@PathVariable("lectureName") String lectureName,
			@PathVariable("repoName") String repoName,Model model) {
		Lecture lecture = lectureService.get(lectureName);
		Repo repo = lecture.getRepo(repoName);
		Weaver weaver = weaverService.getCurrentWeaver();
		
		if(repo == null || !permissionService.lecturePermission(lecture,weaver))
			return "redirect:/lecture/"; 
		
		List<String> gitBranchList;
		if(permissionService.repoPermission(lecture,weaver))
			gitBranchList = gitService.getBranchList(lectureName, repoName);
		else
			gitBranchList = gitService.getBranchList(lectureName, repoName,weaver.getNickName());
		
		if(gitBranchList.size() > 1)
		model.addAttribute("gitBranchList", gitBranchList.subList(1, gitBranchList.size()));
		if(gitBranchList.size() >= 1){
			
			model.addAttribute("selectBranch",gitBranchList.get(0));
			model.addAttribute("gitCommitListCount", 
					gitService.getCommitListCount(lectureName,repoName,gitBranchList.get(0)));
			model.addAttribute("gitCommitList", 
					gitService.getGitCommitLogList(lectureName,repoName,gitBranchList.get(0),1,10));
		}else{
			model.addAttribute("gitCommitListCount",0);
		}
		model.addAttribute("repo", repo);
		model.addAttribute("pageIndex",1);
		
			
		return "/repo/commitLog";
	}
	
	@RequestMapping("/{repoName}/commitlog/commit:{commit}")
	public String commitLog(@PathVariable("lectureName") String lectureName,
			@PathVariable("repoName") String repoName,
			@PathVariable("commit") String commit,Model model) {
		Lecture lecture = lectureService.get(lectureName);
		Repo repo = lecture.getRepo(repoName);
		Weaver weaver = weaverService.getCurrentWeaver();
		
		if(repo == null || !permissionService.lecturePermission(lecture, weaver))
			return "redirect:/lecture/"; 
		
		List<String> gitBranchList;
		
		if(!permissionService.repoPermission(lecture,weaver)){ // 만약 강사가 아닌 경우
			if(!gitService.isReadCommit(lectureName, repoName, weaver, commit)) 
				return "redirect:/lecture/"; // 읽을 수 있는 커밋 네임을 입력했는지 검사
			gitBranchList = gitService.getBranchList(lectureName, repoName,weaver.getNickName());
		}else
			gitBranchList = gitService.getBranchList(lectureName, repoName);
		
		gitBranchList.remove(commit);
		model.addAttribute("gitBranchList", gitBranchList);
		model.addAttribute("selectBranch",commit);
		model.addAttribute("repo", repo);
		model.addAttribute("pageIndex",1);
		model.addAttribute("gitCommitListCount", 
				gitService.getCommitListCount(lectureName,repoName,commit));
		model.addAttribute("gitCommitList", 
				gitService.getGitCommitLogList(lectureName,repoName,commit,1,10));
		return "/repo/commitLog";
	}
	
	@RequestMapping("/{repoName}/commitlog/commit:{commit}/page:{page}")
	public String commitLog(@PathVariable("lectureName") String lectureName,
			@PathVariable("repoName") String repoName,
			@PathVariable("commit") String commit,
			@PathVariable("page") int page,Model model) {
		Lecture lecture = lectureService.get(lectureName);
		Repo repo = lecture.getRepo(repoName);
		Weaver weaver = weaverService.getCurrentWeaver();
		
		if(repo == null || !permissionService.lecturePermission(lecture, weaver))
			return "redirect:/lecture/"; 
		
		List<String> gitBranchList;
		
		if(!permissionService.repoPermission(lecture,weaver)){ // 만약 강사가 아닌 경우
			if(!gitService.isReadCommit(lectureName, repoName, weaver, commit)) 
				return "redirect:/lecture/"; // 읽을 수 있는 커밋 네임을 입력했는지 검사
			gitBranchList = gitService.getBranchList(lectureName, repoName,weaver.getNickName());
		}else
			gitBranchList = gitService.getBranchList(lectureName, repoName);
		
		gitBranchList.remove(commit);
		model.addAttribute("gitBranchList", gitBranchList);
		model.addAttribute("selectBranch",commit);
		model.addAttribute("repo", repo);
		model.addAttribute("pageIndex",page);
		model.addAttribute("gitCommitListCount", 
				gitService.getCommitListCount(lectureName,repoName,commit));
		model.addAttribute("gitCommitList", 
				gitService.getGitCommitLogList(lectureName,repoName,commit,page,10));
		return "/repo/commitLog";
	}

	@RequestMapping("/{repoName}/commitlog-viewer/commit:{commit}")
	public String commitLogViewer(@PathVariable("lectureName") String lectureName,
			@PathVariable("repoName") String repoName,
			@PathVariable("commit") String commit,Model model) {
		Lecture lecture = lectureService.get(lectureName);
		Repo repo = lecture.getRepo(repoName);
		Weaver weaver = weaverService.getCurrentWeaver();
		
		if(repo == null || !permissionService.lecturePermission(lecture, weaver))
			return "redirect:/lecture/"; 
		
		if(!permissionService.repoPermission(lecture,weaver)) // 만약 강사가 아닌 경우
			if(!gitService.isReadCommit(lectureName, repoName, weaver, commit)) 
				return "redirect:/lecture/"; 
		// 읽을 수 있는 커밋 네임을 입력했는지 검사
		
		model.addAttribute("repo", repo);
		model.addAttribute("gitCommitLog", 
				gitService.getGitCommitLog(lectureName,repoName, commit));
		return "/repo/commitLogViewer";
	}
	
	@RequestMapping(value = "/{repoName}-{commitName}.zip")
	public void getRepoZip(@PathVariable("lectureName") String lectureName,
			@PathVariable("repoName") String repoName,
			@PathVariable("commitName") String commitName,
			HttpServletResponse response) {
		Lecture lecture = lectureService.get(lectureName);
		Repo repo = lecture.getRepo(repoName);
		Weaver weaver = weaverService.getCurrentWeaver();
		
		if(repo == null || !permissionService.lecturePermission(lecture, weaver))
			return;
		
		if(repoName.equals("example") && 
				gitService.existCommit(lectureName, repoName, commitName))
			gitService.getProjectZip(lectureName, repoName, commitName, response);
			
		if(!permissionService.repoPermission(lecture,weaver)) // 만약 강사가 아닌 경우
			if(!gitService.isReadCommit(lectureName, repoName, weaver, commitName)) 
				return; 
		// 읽을 수 있는 커밋 네임을 입력했는지 검사
		
		gitService.getProjectZip(lectureName, repoName, commitName, response);
		
		return;
	}

}
*/