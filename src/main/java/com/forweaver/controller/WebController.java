package com.forweaver.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.forweaver.domain.Weaver;
import com.forweaver.service.WeaverService;


@Controller
public class WebController {

	@Autowired 
	private WeaverService weaverService;
	
	@RequestMapping("/forweaver")
	public void forweaver(Model model) {
		
	}

	@RequestMapping("/")
	public String front(Model model) {
		Weaver weaver = weaverService.getCurrentWeaver();
		if(weaver == null)
			return "redirect:/login";
		return "redirect:/"+weaver.getId();
	}
	
		
}
