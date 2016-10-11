package com.forweaver.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mobile.device.Device;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.forweaver.domain.Weaver;
import com.forweaver.service.WeaverService;
import com.forweaver.util.MailUtil;


@Controller
public class WebController {

	@Autowired
	private MailUtil mailUtil;
	@Autowired 
	private WeaverService weaverService;

	@RequestMapping("intro/forweaver")
	public void forweaver() {}

	@RequestMapping("intro/community")
	public void community() {}

	@RequestMapping("intro/project")
	public void project() {}

	@RequestMapping("intro/code")
	public void code() {}

	@RequestMapping("intro/membertool")
	public void membertool() {}

	@RequestMapping("/error500")
	public void error500() {}

	@RequestMapping("/error400")
	public void error400() {}

	@RequestMapping("/error404")
	public void error404() {
	}

	@RequestMapping("/errorUserNull")
	public void errorUserNull() {}

	@RequestMapping("/")
	public String front(Model model) {
		Weaver weaver = weaverService.getCurrentWeaver();
		if(weaver == null)
			return "redirect:/login?state=null";
		return "redirect:/"+weaver.getId()+"/project";
	}

	@RequestMapping("intro/tutorial/*")
	public void tutorial() {}
}
