package com.forweaver.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.forweaver.domain.Data;
import com.forweaver.domain.Lecture;
import com.forweaver.domain.Pass;
import com.forweaver.domain.Project;
import com.forweaver.domain.Weaver;
import com.forweaver.service.CodeService;
import com.forweaver.service.LectureService;
import com.forweaver.service.PostService;
import com.forweaver.service.ProjectService;
import com.forweaver.service.TagService;
import com.forweaver.service.WeaverService;
import com.forweaver.util.WebUtil;

@Controller
public class WeaverController {

	@Autowired
	WeaverService weaverService;

	@Autowired
	PostService postService;
	
	@Autowired
	ProjectService projectService;
	
	@Autowired
	LectureService lectureService;
	
	@Autowired
	CodeService codeService;

	@Autowired
	TagService tagService;

	@RequestMapping("/login")
	public String login() {
		return "/weaver/login";
	}

	@RequestMapping("/join")
	public String join() {
		return "/weaver/join";
	}

	@RequestMapping(value = "/join", method = RequestMethod.POST)
	public String join(@RequestParam("id") String id,
			@RequestParam("email") String email,
			@RequestParam("password") String password,
			@RequestParam("say") String say,
			@RequestParam("image") MultipartFile image,
			HttpServletRequest request) {

		if (weaverService.idCheck(id) || weaverService.idCheck(email))
			return "/weaver/join";
		
		Weaver weaver = new Weaver(id, password, email,say, new Data(image,id));
		weaverService.add(weaver);
		weaverService.autoLoginWeaver(weaver, request);
		return "redirect:/";
	}

	@RequestMapping("/{id}")
	public String home(@PathVariable("id") String id, Model model) {

		return "redirect:/" + id + "/sort:age-desc/page:1";
	}

	@RequestMapping("/{id}/project")
	public String project(@PathVariable("id") String id, Model model) {
		Weaver weaver = weaverService.get(id);

		if (weaver == null)
			return "redirect:/";
		
		List<Project> projects = new ArrayList<Project>();
		
		for(Pass pass :weaver.getPasses()){
			Project project = projectService.get(pass.getJoinName());
			if(project != null)
				projects.add(project);
		}
		model.addAttribute("weaver", weaver);
		model.addAttribute("projects", projects);
		model.addAttribute("search", false);
		return "/weaver/home";
	}
	
	@RequestMapping("/{id}/lecture")
	public String lecture(@PathVariable("id") String id, Model model) {
		Weaver weaver = weaverService.get(id);

		if (weaver == null) {
			return "redirect:/";
		}

		List<Lecture> lectures = new ArrayList<Lecture>();
		
		for(Pass pass :weaver.getPasses()){
			Lecture lecture = lectureService.get(pass.getJoinName());
			if(lecture != null)
				lectures.add(lecture);
		}
		model.addAttribute("weaver", weaver);
		model.addAttribute("lectures", lectures);
		model.addAttribute("search", false);
		return "/weaver/home";
	}
	
	@RequestMapping("/{id}/code")
	public String code(@PathVariable("id") String id, Model model) {
		Weaver weaver = weaverService.get(id);

		if (weaver == null) {
			return "redirect:/";
		}
		
		model.addAttribute("weaver", weaver);
		model.addAttribute("codes", codeService.getCodesWhenWeaverHome(weaver, "", 1, 100));
		model.addAttribute("search", false);
		return "/weaver/home";
	}
	
	@RequestMapping("/{id}/sort:{sort}/page:{page}")
	public String page(@PathVariable("id") String id,
			@PathVariable("page") String page,
			@PathVariable("sort") String sort, Model model) {
		int pageNum;
		int number = 15;
		Weaver weaver = weaverService.get(id);

		if (weaver == null) {
			return "redirect:/";
		}

		if (page.contains(",")) {
			pageNum = Integer.parseInt(page.split(",")[0]);
			number = Integer.parseInt(page.split(",")[1]);
		} else {
			pageNum = Integer.parseInt(page);
		}
		Weaver currentWeaver = weaverService.getCurrentWeaver();

		model.addAttribute("weaver", weaver);

		model.addAttribute("posts", postService.getPostsWhenWeaverHome(
				currentWeaver, weaver, sort, pageNum, number));
		model.addAttribute("postCount",
				postService.countPostsWhenWeaverHome(currentWeaver, weaver, sort));

		model.addAttribute("pageIndex", pageNum);
		model.addAttribute("number", number);
		model.addAttribute("pageUrl", "/community/sort:" + sort + "/page:");
		return "/weaver/home";
	}

	@RequestMapping("/{id}/tags:{tagNames}")
	public String tags(HttpServletRequest request) {
		return "redirect:" + request.getRequestURI() + "/sort:age-desc/page:1";
	}

	@RequestMapping("/{id}/tags:{tagNames}/sort:{sort}/page:{page}")
	public String tagsWithPage(@PathVariable("tagNames") String tagNames,
			@PathVariable("id") String id, @PathVariable("page") String page,
			@PathVariable("sort") String sort, Model model) {
		List<String> tagList = tagService.stringToTagList(tagNames);

		int pageNum;
		int number = 15;

		Weaver weaver = weaverService.get(id);

		if (weaver == null) {
			return "redirect:/";
		}

		if (page.contains(",")) {
			pageNum = Integer.parseInt(page.split(",")[0]);
			number = Integer.parseInt(page.split(",")[1]);
		} else {
			pageNum = Integer.parseInt(page);
		}
		Weaver currentWeaver = weaverService.getCurrentWeaver();

		if (!tagService.validateTag(tagList, currentWeaver)) {
			return "redirect:/community/sort:age-desc/page:1";
		}

		model.addAttribute("weaver", weaver);

		model.addAttribute("posts", postService.getPostsWhenWeaverHomeWithTags(
				currentWeaver, tagList, weaver, sort, pageNum, number));
		model.addAttribute("postCount", postService
				.countPostsWhenWeaverHomeWithTags(currentWeaver, tagList, weaver,
						sort));
		model.addAttribute("tagNames", tagNames);
		model.addAttribute("pageIndex", pageNum);
		model.addAttribute("number", number);
		model.addAttribute("pageUrl", "/community/tags:" + tagNames + "/sort:"
				+ sort + "/page:");

		return "/weaver/home";
	}

	@RequestMapping("/{id}/tags:{tagNames}/search:{search}")
	public String tagsWithSearch(HttpServletRequest request) {
		return "redirect:" + request.getRequestURI() + "/sort:age-desc/page:1";
	}

	@RequestMapping("/{id}/tags:{tagNames}/search:{search}/sort:{sort}/page:{page}")
	public String tagsWithSearch(@PathVariable("tagNames") String tagNames,
			@PathVariable("id") String id,
			@PathVariable("search") String search,
			@PathVariable("sort") String sort,
			@PathVariable("page") String page, Model model) {
		List<String> tagList = tagService.stringToTagList(tagNames);
		int pageNum;
		int number = 15;

		Weaver weaver = weaverService.get(id);

		if (weaver == null) {
			return "redirect:/";
		}

		if (page.contains(",")) {
			pageNum = Integer.parseInt(page.split(",")[0]);
			number = Integer.parseInt(page.split(",")[1]);
		} else {
			pageNum = Integer.parseInt(page);
		}
		Weaver currentWeaver = weaverService.getCurrentWeaver();

		if (!tagService.validateTag(tagList, currentWeaver)) {
			return "redirect:/community/sort:age-desc/page:1";
		}

		model.addAttribute("weaver", weaver);

		model.addAttribute("posts", postService
				.getPostsWhenWeaverHomeWithTagsAndSearch(currentWeaver,
						tagList, weaver, search, sort, pageNum, number));
		model.addAttribute("postCount", postService
				.countPostsWhenWeaverHomeWithTagsAndSearch(currentWeaver,
						tagList, weaver, search, sort));

		model.addAttribute("tagNames", tagNames);
		model.addAttribute("search", search);
		model.addAttribute("pageIndex", page);
		model.addAttribute("pageUrl", "/tags:" + tagNames + "/search:" + search
				+ "/sort:" + sort + "/page:");

		return "/weaver/home";
	}

	
	@RequestMapping(value = "/{id}/edit")
	public String editWeaver(@PathVariable("id") String id,Model model) {
		Weaver weaver = weaverService.getCurrentWeaver();
		if (!weaver.getId().equals(id)) // 본인이 아닐 경우
			return "redirect:/manage";
		model.addAttribute("weaver", weaver);
		return "/weaver/edit";
	}
	
	@RequestMapping(value = "/{id}/edit", method = RequestMethod.POST)
	public String editWeaver(@PathVariable("id") String id,
			@RequestParam("password") String password,
			@RequestParam("say") String say,
			@RequestParam("image") MultipartFile image) {
		Weaver weaver = weaverService.getCurrentWeaver();
		if (!weaver.getId().equals(id)) // 본인이 아닐 경우
			return "redirect:/manage";

		if(image != null && image.getSize() > 0)
			weaver.setImage(new Data(image, weaver.getId()));
		
		if(password != null && !password.equals(""))
			weaver.setPassword(password);
		if(say != null && !say.equals(""))
			weaver.setSay(say);

		weaverService.update(weaver);

		return "redirect:/manage";
	}

	@RequestMapping(value = "/{id}/img")
	public void img(@PathVariable("id") String id, HttpServletResponse res)
			throws IOException {
		Weaver weaver = weaverService.get(id);
		if (weaver == null) {
			res.sendRedirect("http://www.gravatar.com/avatar/a.jpg");
			return;
		}else if (weaver.getImage().getName().length() ==0) {
			res.sendRedirect("http://www.gravatar.com/avatar/"
					+ WebUtil.convertMD5(weaver.getEmail()) + ".jpg");
		} else {
			byte[] imgData = weaver.getImage().getContent();
			res.setContentType(weaver.getImage().getType());
			OutputStream o = res.getOutputStream();
			o.write(imgData);
			o.flush();
			o.close();
			return;
		} 

	}

	@RequestMapping(value = "/check", method = RequestMethod.POST)
	@ResponseBody
	public boolean nickNameCheck(HttpServletRequest req) {
		return weaverService.idCheck(req.getParameter("id"));
	}
	
	

}
