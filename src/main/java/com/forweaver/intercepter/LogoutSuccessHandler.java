package com.forweaver.intercepter;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;

public class LogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler {
	protected Log log = LogFactory.getLog(LoggerInterceptor.class);


	public LogoutSuccessHandler(String defaultTargetURL) {
		this.setDefaultTargetUrl(defaultTargetURL);
	}

	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

		if (log.isInfoEnabled()) 
			log.info("================ "+authentication.getName()+"  logout   ================");

		super.onLogoutSuccess(request, response, authentication);

	}
}