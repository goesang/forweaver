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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.forweaver.domain.Pass;
import com.forweaver.domain.Project;
import com.forweaver.domain.Repo;
import com.forweaver.domain.Weaver;
import com.forweaver.service.LectureService;
import com.forweaver.service.ProjectService;
import com.forweaver.service.WeaverService;
import com.forweaver.util.GitUtil;

//git clone이나 그외 git 프로그램으로 접근시 먼저 접근을 막고 검사하는 필터
//git에서 권한 설정 기능이 없어서 필터로 구현함.
@Component("GitFilter")
public class GitFilter implements Filter {
	@Autowired private WeaverService weaverService;
	@Autowired private LectureService lectureService;
	@Autowired private ProjectService projectService;
	@Autowired private GitUtil gitUtil;
	private FilterConfig config = null;

	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain filterchain) throws IOException, ServletException {
		String requestUrl = ((HttpServletRequest) req).getRequestURI();
		String[] requstUrlArray = requestUrl.split("/");
		String lectureName = requstUrlArray[2];
		String repoName = requstUrlArray[3].substring(0,
				requstUrlArray[3].indexOf(".git"));
		System.out.println(gitUtil.getGitPath() + lectureName + "/" + repoName + ".git");
		System.out.println(lectureName);
		System.out.println(repoName);
		if (!new File(gitUtil.getGitPath() + lectureName + "/" + repoName + ".git").exists()) // 저장소가 없는 경우
			return;

		Weaver weaver = weaverService.getCurrentWeaver();
		Pass pass = weaver.getPass(lectureName + "/" + repoName);

		if (pass == null)
			pass = weaver.getPass(lectureName);

		if (pass == null)
			return;

		if (pass.getJoinName().contains("/")) { // 프로젝트에 권한이 있는 경우
			System.out.println("ssssssssssssssssssssssssqqqqq");
			filterchain.doFilter(req, res);
			return;
		}

		Repo repo = lectureService.getRepo(lectureName + "/" + repoName);

		gitUtil.Init(repo);
		List<String> beforeBranchList = gitUtil.getBranchList();

		if (pass.getPermission() == 1) { // 강의 개설자의 경우
			filterchain.doFilter(req, res);
			if (repo.getCategory() == 1) {
				gitUtil.createStudentBranch(beforeBranchList,	lectureService.get(lectureName));
			}
			return;
		} else if (pass.getPermission() == 0) { 
			// 강의 수강자의 경우

			if (repo.getCategory() == 0) { // 예제 저장소의 경우

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