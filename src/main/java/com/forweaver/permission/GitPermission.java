package com.forweaver.permission;

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
import com.forweaver.domain.Weaver;
import com.forweaver.service.WeaverService;
import com.forweaver.util.GitUtil;


@Component("gitPermission")
public class GitPermission implements Filter {
	@Autowired
	WeaverService weaverService;

	private FilterConfig config = null;

	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain filterchain) throws IOException, ServletException {
/*		
		String requestUrl = ((HttpServletRequest) req).getRequestURI();
		String[] requstUrlArray = requestUrl.split("/");
		String weaverName = requstUrlArray[2];
		String projectName = requstUrlArray[3].substring(0,
				requstUrlArray[3].indexOf(".git"));
		if (!new File(GitUtil.GitPath + weaverName + "/" + projectName + ".git")
				.exists()) // 저장소가 없는 경우
			return;
		
		Weaver weaver = weaverService.getCurrentWeaver();

		if (weaver.getPass(weaverName+"/"+projectName) != null)*/
			filterchain.doFilter(req, res);		
		
	}

	public void destroy() {

	}

	public void init(FilterConfig config) {
		this.config = config;
	}

}