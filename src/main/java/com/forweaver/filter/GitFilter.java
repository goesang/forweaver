package com.forweaver.filter;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.forweaver.domain.Pass;
import com.forweaver.domain.Project;
import com.forweaver.domain.Repo;
import com.forweaver.domain.Weaver;
import com.forweaver.service.LectureService;
import com.forweaver.service.ProjectService;
import com.forweaver.service.WeaverService;
import com.forweaver.util.GitUtil;

/**
 * Servlet Filter implementation class MyFilterExample
 */
@Component("GitFilter")
public class GitFilter implements Filter {
	@Autowired
	WeaverService weaverService;
	@Autowired
	LectureService lectureService;
	@Autowired
	ProjectService projectService;

	private FilterConfig config = null;

	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain filterchain) throws IOException, ServletException {


		String requestUrl = ((HttpServletRequest) req).getRequestURI();
		String[] requstUrlArray = requestUrl.split("/");
		String lectureName = requstUrlArray[2];
		String repoName = requstUrlArray[3].substring(0,
				requstUrlArray[3].indexOf(".git"));

		if (!new File(GitUtil.GitPath + lectureName + "/" + repoName + ".git")
		.exists()) // 저장소가 없는 경우
			return;

		Weaver weaver = weaverService.getCurrentWeaver();
		Pass pass = weaver.getPass(lectureName + "/" + repoName);
		Project project = projectService.get(lectureName + "/" + repoName);
		if (pass == null)
			pass = weaver.getPass(lectureName);

		if (pass == null)
			return;

		if (pass.getJoinName().contains("/")) { // 프로젝트에 권한이 있는 경우
			if(project.getCategory() ==2 && project.getDDay() == -1)
				return;
			filterchain.doFilter(req, res);
			return;
		}else if(project != null && project.getCategory() ==2 ){ // 팀 프로젝트이고 강사가 방문하는 경우.
			pass = weaver.getPass(project.getOriginalProject().split("/")[0]);
			if(pass != null && pass.getPermission() == 1 )
				filterchain.doFilter(req, res);
			return;
		}

		Repo repo = lectureService.getRepo(lectureName + "/" + repoName);

		GitUtil gitUtil = new GitUtil(repo);
		List<String> beforeBranchList = gitUtil.getBranchList();

		if (pass.getPermission() == 1) { // 강의 개설자의 경우
			filterchain.doFilter(req, res);
			if (repo.getCategory() == 1) {
				gitUtil.createStudentBranch(beforeBranchList,	lectureService.get(lectureName));
			}
			return;
		} else if (pass.getPermission() == 0) { 
			// 강의 수강자의 경우

			if (repo.getCategory() == 0 || repo.getCategory() == 2) { // 예제 저장소 및 팀 예제 저장소의 경우

				gitUtil.notWriteBranches();
				filterchain.doFilter(req, res);
				gitUtil.writeBranches();
			} else{ // 숙제 저장소의 경우

				if(repo.getDDay() == -1) // 마감일이 지났을 못올림.
				{
					return;
				}
				gitUtil.hideNotUserBranches(weaver.getId());
				gitUtil.checkOutBranch(weaver.getId());
				filterchain.doFilter(req, res);
				gitUtil.showBranches();
				gitUtil.checkOutMasterBranch();

			}
		} 
	}

	public void destroy() {

	}

	public void init(FilterConfig config) {
		this.config = config;
	}

}