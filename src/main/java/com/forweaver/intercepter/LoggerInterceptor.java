package com.forweaver.intercepter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class LoggerInterceptor extends HandlerInterceptorAdapter {
    protected Log log = LogFactory.getLog(LoggerInterceptor.class);
     
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!request.getRequestURI().startsWith("/resources/") && log.isInfoEnabled()) {
            log.info("================   start   ================");
            log.info("Name\t:  " + request.getRemoteUser());
            log.info("URI\t:  " + request.getRequestURI());
            log.info("ContentLength\t:  " + request.getContentLength());
        }
        return super.preHandle(request, response, handler);
    }
     
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if (!request.getRequestURI().startsWith("/resources/") && log.isInfoEnabled()) {
        	log.info("ResponseBufferSize\t:  " + response.getBufferSize());
        	log.info("================   end   ================");
        }
    }
}