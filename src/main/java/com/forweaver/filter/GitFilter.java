package com.forweaver.filter;

import java.io.File;
import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.forweaver.domain.Pass;
import com.forweaver.domain.Project;
import com.forweaver.domain.Weaver;
import com.forweaver.service.ProjectService;
import com.forweaver.service.WeaverService;
import com.forweaver.util.GitUtil;


/**git clone이나 그외 git 프로그램으로 접근시 먼저 접근을 막고 검사하는 필터
 *git에서 권한 설정 기능이 없어서 필터로 구현함.
 */
@Component("GitFilter")
public class GitFilter implements Filter {
	@Autowired private WeaverService weaverService;
	@Autowired private ProjectService projectService;
	@Autowired private GitUtil gitUtil;
	private FilterConfig config = null;

	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain filterchain) throws IOException, ServletException {
		String requestUrl = ((HttpServletRequest) req).getRequestURI();
		String[] requstUrlArray = requestUrl.split("/");
		String lectureName = requstUrlArray[2];
		String repoName = requstUrlArray[3].substring(0, requstUrlArray[3].indexOf(".git"));

		if (!new File(gitUtil.getGitPath() + lectureName + "/" + repoName + ".git").exists()) // 저장소가 없는 경우
			return;

		Weaver weaver = weaverService.getCurrentWeaver();
		Pass pass = weaver.getPass(lectureName + "/" + repoName);
		Project project = projectService.get(lectureName + "/" + repoName);

		if(project == null){ // 프로젝트가 없을 때
			((HttpServletResponse) res).sendError(500);
			return;
		}

		if(project.getCategory()==0){ // 프로젝트가 공개 프로젝트일때
			filterchain.doFilter(req, res);
			return;
		}

		if(project.getCategory()>0 && pass != null){ // 프로젝트가 비공개이고 권한이 있을 때
			filterchain.doFilter(req, res);
			return;
		}

		((HttpServletResponse) res).sendError(403);
		
	}

	public void destroy() {

	}

	public void init(FilterConfig config) {
		this.config = config;
	}

}