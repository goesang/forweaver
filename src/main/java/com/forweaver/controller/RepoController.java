package com.forweaver.controller;

import java.util.List;

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

import com.forweaver.domain.Lecture;
import com.forweaver.domain.Project;
import com.forweaver.domain.Repo;
import com.forweaver.domain.Weaver;
import com.forweaver.domain.git.GitFileInfo;
import com.forweaver.domain.git.GitSimpleCommitLog;
import com.forweaver.domain.git.GitSimpleFileInfo;
import com.forweaver.service.GitService;
import com.forweaver.service.LectureService;
import com.forweaver.service.WeaverService;
import com.forweaver.util.WebUtil;

// 숙제 저장소 컨트롤러입니다.
@Controller
@RequestMapping("/lecture/{lectureName}")
public class RepoController {

	@Autowired 
	private WeaverService weaverService;
	@Autowired
	private LectureService lectureService;
	@Autowired 
	private GitService gitService;

	@RequestMapping(value = "/add")
	public String add(@PathVariable("lectureName") String lectureName,
			HttpServletRequest request) {
		Lecture lecture = lectureService.get(lectureName);
		Weaver weaver = weaverService.getCurrentWeaver();
		String repoName = request.getParameter("name");		
		String category = request.getParameter("category");
		String period = request.getParameter("period");
		int periodInt = 0;
		if(period != null)
			periodInt = Integer.parseInt(request.getParameter("period"));
		Repo repo;
		if(category == null)
			repo = new Repo(repoName, 
					1, 
					WebUtil.removeHtml(request.getParameter("description")), 
					periodInt, 
					lecture,weaver);	
		else
			repo = new Repo(repoName, 
					2, 
					WebUtil.removeHtml(request.getParameter("description")), 
					periodInt, 
					lecture,weaver);	

		lectureService.addRepo(lecture, repo);

		return "redirect:/lecture/"+lectureName+"/"+repoName; 
	}

	@RequestMapping("/{repoName}/delete")
	public String delete(Model model,
			@PathVariable("lectureName") String lectureName,
			@PathVariable("repoName") String repoName) {
		Lecture lecture = lectureService.get(lectureName);
		Repo repo = lecture.getRepo(repoName);
		lectureService.removeRepo(lecture, repo);
		return "redirect:/lecture/"+lectureName+"/repo"; 
	}



	@RequestMapping(value={"/{repoName}","/{repoName}/browser"})
	public String manage(Model model,@PathVariable("lectureName") String lectureName,
			@PathVariable("repoName") String repoName) {
		Lecture lecture = lectureService.get(lectureName);
		Repo repo = lecture.getRepo(repoName);
		Weaver weaver = weaverService.getCurrentWeaver();
		String readme = "";

		if(!lecture.getCreatorName().equals(weaver.getId()) && repo.getCategory() == 1)
			gitService.hideBranch(lectureName, repoName, weaver.getId());

		List<GitSimpleFileInfo> gitFileInfoList = 
				gitService.getGitSimpleFileInfoList(lectureName, repoName,"HEAD");

		if(gitFileInfoList != null) 
			for(GitSimpleFileInfo gitSimpleFileInfo:gitFileInfoList)// 파일들을 검색해서 리드미 파일을 찾아냄
				if(gitSimpleFileInfo.getDepth() == 0 && gitSimpleFileInfo.getName().toUpperCase().equals("README.MD"))
					readme = WebUtil.markDownEncoder(
							gitService.getFileInfo(
									lectureName, 
									repoName, 
									"HEAD", 
									gitSimpleFileInfo.getName()).getContent());

		List<String> gitBranchList = gitService.getBranchList(lectureName, repoName);
		model.addAttribute("repo", repo);
		model.addAttribute("gitFileInfoList",gitFileInfoList);
		if(gitBranchList.size() > 1){
			model.addAttribute("gitBranchList", gitBranchList.subList(1, gitBranchList.size()));
			model.addAttribute("selectBranch",gitBranchList.get(0));
		}else
			model.addAttribute("selectBranch",gitBranchList.get(0));
		model.addAttribute("readme",readme);

		if(!lecture.getCreatorName().equals(weaver.getId()) && repo.getCategory() == 1)
			gitService.showBranch(lectureName, repoName);

		return "/repo/browser";
	}

	@RequestMapping("/{repoName}/browser/commit:{commit}")
	public String browser(@PathVariable("lectureName") String lectureName,
			@PathVariable("repoName") String repoName,
			@PathVariable("commit") String commit,Model model) {
		Lecture lecture = lectureService.get(lectureName);
		Repo repo = lecture.getRepo(repoName);
		Weaver weaver = weaverService.getCurrentWeaver();
		String readme = "";

		if(!lecture.getCreatorName().equals(weaver.getId()) && repo.getCategory() == 1)
			gitService.hideBranch(lectureName, repoName, weaver.getId());

		List<GitSimpleFileInfo> gitFileInfoList = 
				gitService.getGitSimpleFileInfoList(lectureName, repoName,commit);

		if(gitFileInfoList != null) for(GitSimpleFileInfo gitSimpleFileInfo:gitFileInfoList)// 파일들을 검색해서 리드미 파일을 찾아냄
			if(gitSimpleFileInfo.getDepth() == 0 && gitSimpleFileInfo.getName().toUpperCase().equals("README.MD"))
				readme = WebUtil.markDownEncoder(
						gitService.getFileInfo(
								lectureName, 
								repoName, 
								commit, 
								gitSimpleFileInfo.getName()).getContent());


		List<String> gitBranchList = gitService.getBranchList(lectureName, repoName);

		model.addAttribute("repo", repo);
		model.addAttribute("gitFileInfoList",gitFileInfoList);
		gitBranchList.remove(commit);
		model.addAttribute("gitBranchList", gitBranchList);
		model.addAttribute("selectBranch",commit);
		model.addAttribute("readme",readme);

		if(!lecture.getCreatorName().equals(weaver.getId()) && repo.getCategory() == 1)
			gitService.showBranch(lectureName, repoName);

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

		if(!lecture.getCreatorName().equals(weaver.getId()) && repo.getCategory() == 1)
			gitService.hideBranch(lectureName, repoName, weaver.getId());

		GitFileInfo gitFileInfo = gitService.getFileInfo(lectureName,repoName, commitID, filePath);


		model.addAttribute("repo", repo);
		model.addAttribute("fileName", gitFileInfo.getName());
		model.addAttribute("fileContent", gitFileInfo.getContent());
		model.addAttribute("gitLogList", gitFileInfo.getGitLogList());
		model.addAttribute("selectCommitIndex", gitFileInfo.getSelectCommitIndex());
		model.addAttribute("gitCommitLog", 
				new GitSimpleCommitLog(gitFileInfo.getSelectCommitLog()));

		if(!lecture.getCreatorName().equals(weaver.getId()) && repo.getCategory() == 1)
			gitService.showBranch(lectureName, repoName);

		return "/repo/fileViewer";
	}

	@RequestMapping("/{repoName}/commitlog")
	public String commitLog(@PathVariable("lectureName") String lectureName,
			@PathVariable("repoName") String repoName,Model model) {
		Lecture lecture = lectureService.get(lectureName);
		Repo repo = lecture.getRepo(repoName);
		Weaver weaver = weaverService.getCurrentWeaver();

		if(!lecture.getCreatorName().equals(weaver.getId()) && repo.getCategory() == 1)
			gitService.hideBranch(lectureName, repoName, weaver.getId());

		List<String> gitBranchList = gitService.getBranchList(lectureName, repoName);

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

		if(!lecture.getCreatorName().equals(weaver.getId()) && repo.getCategory() == 1)
			gitService.showBranch(lectureName, repoName);

		return "/repo/commitLog";
	}

	@RequestMapping("/{repoName}/commitlog/commit:{commit}")
	public String commitLog(@PathVariable("lectureName") String lectureName,
			@PathVariable("repoName") String repoName,
			@PathVariable("commit") String commit,Model model) {
		Lecture lecture = lectureService.get(lectureName);
		Repo repo = lecture.getRepo(repoName);
		Weaver weaver = weaverService.getCurrentWeaver();

		if(!lecture.getCreatorName().equals(weaver.getId()) && repo.getCategory() == 1)
			gitService.hideBranch(lectureName, repoName, weaver.getId());

		List<String> gitBranchList = gitService.getBranchList(lectureName, repoName);

		gitBranchList.remove(commit);
		model.addAttribute("gitBranchList", gitBranchList);
		model.addAttribute("selectBranch",commit);
		model.addAttribute("repo", repo);
		model.addAttribute("pageIndex",1);
		model.addAttribute("gitCommitListCount", 
				gitService.getCommitListCount(lectureName,repoName,commit));
		model.addAttribute("gitCommitList", 
				gitService.getGitCommitLogList(lectureName,repoName,commit,1,10));

		if(!lecture.getCreatorName().equals(weaver.getId()) && repo.getCategory() == 1)
			gitService.showBranch(lectureName, repoName);

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

		if(!lecture.getCreatorName().equals(weaver.getId()) && repo.getCategory() == 1)
			gitService.hideBranch(lectureName, repoName, weaver.getId());

		List<String> gitBranchList = gitService.getBranchList(lectureName, repoName);
		gitBranchList.remove(commit);
		model.addAttribute("gitBranchList", gitBranchList);
		model.addAttribute("selectBranch",commit);
		model.addAttribute("repo", repo);
		model.addAttribute("pageIndex",page);
		model.addAttribute("gitCommitListCount", 
				gitService.getCommitListCount(lectureName,repoName,commit));
		model.addAttribute("gitCommitList", 
				gitService.getGitCommitLogList(lectureName,repoName,commit,page,10));

		if(!lecture.getCreatorName().equals(weaver.getId()) && repo.getCategory() == 1)
			gitService.showBranch(lectureName, repoName);

		return "/repo/commitLog";
	}

	@RequestMapping("/{repoName}/commitlog-viewer/commit:{commit}")
	public String commitLogViewer(@PathVariable("lectureName") String lectureName,
			@PathVariable("repoName") String repoName,
			@PathVariable("commit") String commit,Model model) {
		Lecture lecture = lectureService.get(lectureName);
		Repo repo = lecture.getRepo(repoName);	
		Weaver weaver = weaverService.getCurrentWeaver();

		if(!lecture.getCreatorName().equals(weaver.getId()) && repo.getCategory() == 1)
			gitService.hideBranch(lectureName, repoName, weaver.getId());

		model.addAttribute("repo", repo);
		model.addAttribute("gitCommitLog", 
				gitService.getGitCommitLog(lectureName,repoName, commit));

		if(!lecture.getCreatorName().equals(weaver.getId()) && repo.getCategory() == 1)
			gitService.showBranch(lectureName, repoName);

		return "/repo/commitLogViewer";
	}

	@RequestMapping(value = "/{repoName}/{commitName}/{download}.zip")
	public void getRepoZip(@PathVariable("lectureName") String lectureName,
			@PathVariable("repoName") String repoName,
			@PathVariable("commitName") String commitName,
			HttpServletResponse response) {
		Lecture lecture = lectureService.get(lectureName);
		Repo repo = lecture.getRepo(repoName);
		Weaver weaver = weaverService.getCurrentWeaver();
		if(!lecture.getCreatorName().equals(weaver.getId()) && repo.getCategory() == 1)
			gitService.hideBranch(lectureName, repoName, weaver.getId());

		if(	gitService.existCommit(lectureName, repoName, commitName))
			gitService.getProjectZip(lectureName, repoName, commitName, response);

		if(!lecture.getCreatorName().equals(weaver.getId()) && repo.getCategory() == 1)
			gitService.showBranch(lectureName, repoName);
		return;
	}

	@RequestMapping(value="/{repoName}/upload", method=RequestMethod.POST)
	public String uploadZip(Model model,
			@PathVariable("lectureName") String lectureName,
			@PathVariable("repoName") String repoName,
			@RequestParam("message") String message,
			@RequestParam("zip") MultipartFile zip) {
		Lecture lecture = lectureService.get(lectureName);
		Repo repo = lecture.getRepo(repoName);
		Weaver weaver = weaverService.getCurrentWeaver();

		if(!lecture.getCreatorName().equals(weaver.getId()) && repo.getCategory() == 1)
			gitService.hideBranch(lectureName, repoName, weaver.getId());

		lectureService.uploadZip(lecture,repo, weaver, message, zip);

		if(!lecture.getCreatorName().equals(weaver.getId()) && repo.getCategory() == 1)
			gitService.showBranch(lectureName, repoName);

		return "redirect:/lecture/"+lectureName+"/"+repoName+"/browser"; 
	}


}
