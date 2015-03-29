package com.forweaver.controller;

import java.io.IOException;
import java.io.OutputStream;
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
	private WeaverService weaverService;
	@Autowired
	private PostService postService;
	@Autowired
	private ProjectService projectService;
	@Autowired
	private LectureService lectureService;
	@Autowired
	private CodeService codeService;
	@Autowired 
	private TagService tagService;

	@RequestMapping(value = "/login")
	public String login() {
		return "/weaver/login";
	}

	@RequestMapping(value = "/loginFail")
	public String loginFail(Model model) {
		model.addAttribute("script", "alert('로그인 실패!!! 다시 로그인해주세요!')");
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
			@RequestParam("studentID") String studentID,
			@RequestParam("say") String say,
			@RequestParam("tags") String tags,
			@RequestParam("image") MultipartFile image,
			HttpServletRequest request,Model model) {
		List<String> tagList = tagService.stringToTagList(tags);

		if(!tagService.isPublicTags(tagList))
			return "/weaver/join";

		if(id.length() < 5 || password.length()<4 || email.length()<6){
			model.addAttribute("say", "잘못 입력하셨습니다!.");
			model.addAttribute("url", "/join");
			return "/alert";
		}

		if (weaverService.idCheck(id) || weaverService.idCheck(email)){
			model.addAttribute("say", "이미 존재하는 아이디 혹은 이메일입니다.");
			model.addAttribute("url", "/join");
			return "/alert";
		}

		Weaver weaver = new Weaver(id, password, email,tagList,studentID,say, new Data(image));
		weaverService.add(weaver);
		weaverService.autoLoginWeaver(weaver, request);
		return "redirect:/";
	}


	@RequestMapping("/weaver")
	public String weavers(HttpServletRequest request){
		return "redirect:"+request.getRequestURI() +"page:1";
	}

	@RequestMapping("/weaver/page:{page}")
	public String weavers(@PathVariable("page") String page,Model model) {
		int pageNum = WebUtil.getPageNumber(page);
		int size = WebUtil.getPageSize(page);

		long weaverCount = weaverService.countWeavers();
		List<Weaver> weavers = weaverService.getWeavers(pageNum, size);

		model.addAttribute("weavers", weavers);	
		model.addAttribute("weaverCount", weaverCount);

		model.addAttribute("pageIndex", pageNum);
		model.addAttribute("number", size);
		model.addAttribute("pageUrl", "/weaver/page:");
		return "/weaver/weavers";
	}

	@RequestMapping("/weaver/tags:{tagNames}")
	public String weaversTags(HttpServletRequest request){
		return "redirect:"+request.getRequestURI() +"/page:1";
	}

	@RequestMapping("/weaver/tags:{tagNames}/page:{page}")
	public String weaversTags(Model model,@PathVariable("tagNames") String tagNames,
			@PathVariable("page") String page) {
		List<String> tagList = tagService.stringToTagList(tagNames);

		if(!tagService.isPublicTags(tagList))
			return "redirect:/weaver/page:1";

		int pageNum = WebUtil.getPageNumber(page);
		int size = WebUtil.getPageSize(page);

		List<Weaver> weavers= weaverService.getWeavers(tagList,pageNum,size);
		long weaverCount = weaverService.countWeavers(tagList);

		model.addAttribute("weavers", weavers);	
		model.addAttribute("weaverCount", weaverCount);

		model.addAttribute("pageIndex", pageNum);
		model.addAttribute("number", size);
		model.addAttribute("pageUrl", "/weaver/tags:"+tagNames+"/page:");
		return "/weaver/weavers";
	}

	@RequestMapping({"/{id}","/{id}/code","/{id}/project","/{id}/lecture"})
	public String home(@PathVariable("id") String id,HttpServletRequest request) {
		Weaver weaver = weaverService.get(id);
		if(weaver == null)
			return "/error404";
		else
			return "redirect:" + request.getRequestURI() + "/sort:age-desc/page:1";

	}

	@RequestMapping("/{id}/sort:{sort}/page:{page}")
	public String communityPage(@PathVariable("id") String id,
			@PathVariable("page") String page,
			@PathVariable("sort") String sort, Model model) {
		int pageNum = WebUtil.getPageNumber(page);
		int size = WebUtil.getPageSize(page);
		Weaver weaver = weaverService.get(id);

		if (weaver == null)
			return "redirect:/";

		Weaver currentWeaver = weaverService.getCurrentWeaver();

		model.addAttribute("weaver", weaver);

		model.addAttribute("posts", postService.getPosts(
				currentWeaver, weaver, sort, pageNum, size));
		model.addAttribute("postCount",
				postService.countPosts(currentWeaver, weaver, sort));

		model.addAttribute("pageIndex", pageNum);
		model.addAttribute("number", size);
		model.addAttribute("pageUrl", "/"+id+"/sort:" + sort + "/page:");
		return "/weaver/mypage/community";
	}

	@RequestMapping("/{id}/code/sort:{sort}/page:{page}")
	public String codePage(@PathVariable("id") String id,
			@PathVariable("sort") String sort,
			@PathVariable("page") String page, Model model) {
		Weaver weaver = weaverService.get(id);
		int pageNum = WebUtil.getPageNumber(page);
		int size = WebUtil.getPageSize(page);

		if (weaver == null)
			return "redirect:/";

		model.addAttribute("weaver", weaver);
		model.addAttribute("codes", codeService.getCodes(weaver, null, null, sort, pageNum, size));
		model.addAttribute("codeCount", codeService.countCodes(weaver, null, null, sort));
		model.addAttribute("pageIndex", pageNum);
		model.addAttribute("number", size);
		model.addAttribute("pageUrl", "/"+id+"/code/sort:" + sort + "/page:");
		return "/weaver/mypage/code";
	}

	@RequestMapping("/{id}/project/sort:{sort}/page:{page}")
	public String projectPage(@PathVariable("id") String id,
			@PathVariable("sort") String sort,
			@PathVariable("page") String page, Model model) {
		Weaver currentWeaver = weaverService.getCurrentWeaver();
		Weaver weaver = weaverService.get(id);
		int pageNum = WebUtil.getPageNumber(page);
		int size = WebUtil.getPageSize(page);

		if (weaver == null)
			return "redirect:/";

		model.addAttribute("weaver", weaver);
		model.addAttribute("projects", projectService.getProjects(currentWeaver,weaver,null,sort, pageNum, size));
		model.addAttribute("projectCount", projectService.countProjects(weaver, null, sort));
		model.addAttribute("pageIndex", pageNum);
		model.addAttribute("number", size);
		model.addAttribute("pageUrl", "/"+id+"/project/sort:" + sort + "/page:");
		return "/weaver/mypage/project";
	}

	@RequestMapping("/{id}/lecture/sort:{sort}/page:{page}")
	public String lecturePage(@PathVariable("id") String id,
			@PathVariable("sort") String sort,
			@PathVariable("page") String page, Model model) {
		Weaver currentWeaver = weaverService.getCurrentWeaver();
		Weaver weaver = weaverService.get(id);
		int pageNum = WebUtil.getPageNumber(page);
		int size = WebUtil.getPageSize(page);

		if (weaver == null) 
			return "redirect:/";

		model.addAttribute("weaver", weaver);
		model.addAttribute("lectures", lectureService.getLecturesWithWeaver(currentWeaver, weaver, null, sort, pageNum, size));
		model.addAttribute("lectureCount", lectureService.countLecturesWithWeaver(weaver, null, sort));
		model.addAttribute("pageIndex", pageNum);
		model.addAttribute("number", size);
		model.addAttribute("pageUrl", "/"+id+"/lecture/sort:" + sort + "/page:");
		return "/weaver/mypage/lecture";
	}



	@RequestMapping({"/{id}/tags:{tagNames}",
		"/{id}/code/tags:{tagNames}",
		"/{id}/project/tags:{tagNames}"
		,"/{id}/lecture/tags:{tagNames}"})
	public String tags(HttpServletRequest request) {
		return "redirect:" + request.getRequestURI() + "/sort:age-desc/page:1";
	}

	@RequestMapping("/{id}/tags:{tagNames}/sort:{sort}/page:{page}")
	public String tagsWithPage(@PathVariable("tagNames") String tagNames,
			@PathVariable("id") String id, @PathVariable("page") String page,
			@PathVariable("sort") String sort, Model model) {
		List<String> tagList = tagService.stringToTagList(tagNames);

		int pageNum = WebUtil.getPageNumber(page);
		int size = WebUtil.getPageSize(page);

		Weaver weaver = weaverService.get(id);

		if (weaver == null)
			return "redirect:/";

		Weaver currentWeaver = weaverService.getCurrentWeaver();

		if (!tagService.validateTag(tagList, currentWeaver))
			return "redirect:/";

		model.addAttribute("weaver", weaver);

		model.addAttribute("posts", postService.getPosts(
				currentWeaver, tagList, weaver, sort, pageNum, size));
		model.addAttribute("postCount", postService
				.countPosts(currentWeaver, tagList, weaver,
						sort));
		model.addAttribute("tagNames", tagNames);
		model.addAttribute("pageIndex", pageNum);
		model.addAttribute("number", size);
		model.addAttribute("pageUrl", "/"+id+"/tags:" + tagNames + "/sort:"+ sort + "/page:");

		return "/weaver/mypage/community";
	}

	@RequestMapping("/{id}/code/tags:{tagNames}/sort:{sort}/page:{page}")
	public String codeTagsWithPage(@PathVariable("tagNames") String tagNames,
			@PathVariable("id") String id, @PathVariable("page") String page,
			@PathVariable("sort") String sort, Model model) {
		List<String> tags = tagService.stringToTagList(tagNames);

		int pageNum = WebUtil.getPageNumber(page);
		int size = WebUtil.getPageSize(page);

		Weaver weaver = weaverService.get(id);

		if (weaver == null)
			return "redirect:/";

		Weaver currentWeaver = weaverService.getCurrentWeaver();

		if (!tagService.validateTag(tags, currentWeaver))
			return "redirect:/";

		model.addAttribute("weaver", weaver);
		model.addAttribute("codes", codeService
				.getCodes(weaver, tags, null, sort, pageNum, size));
		model.addAttribute("codeCount", codeService
				.countCodes(weaver, tags, null, sort));
		model.addAttribute("tagNames", tagNames);
		model.addAttribute("pageIndex", page);
		model.addAttribute("number", size);
		model.addAttribute("pageUrl", "/"+id+"/code/tags:" + tagNames+ "/sort:" + sort + "/page:");

		return "/weaver/mypage/code";
	}

	@RequestMapping("/{id}/project/tags:{tagNames}/sort:{sort}/page:{page}")
	public String projectTagsWithPage(@PathVariable("tagNames") String tagNames,
			@PathVariable("id") String id, @PathVariable("page") String page,
			@PathVariable("sort") String sort, Model model) {
		List<String> tags = tagService.stringToTagList(tagNames);

		int pageNum = WebUtil.getPageNumber(page);
		int size = WebUtil.getPageSize(page);


		Weaver weaver = weaverService.get(id);

		if (weaver == null)
			return "redirect:/";

		Weaver currentWeaver = weaverService.getCurrentWeaver();

		if (!tagService.validateTag(tags, currentWeaver))
			return "redirect:/";

		model.addAttribute("weaver", weaver);
		model.addAttribute("projects", projectService
				.getProjects(currentWeaver, weaver, tags, sort, pageNum, size));
		model.addAttribute("projectCount", projectService
				.countProjects(weaver, tags, sort));
		model.addAttribute("tagNames", tagNames);
		model.addAttribute("pageIndex", page);
		model.addAttribute("number", size);
		model.addAttribute("pageUrl", "/"+id+"/code/tags:" + tagNames+ "/sort:" + sort + "/page:");

		return "/weaver/mypage/project";
	}

	@RequestMapping("/{id}/lecture/tags:{tagNames}/sort:{sort}/page:{page}")
	public String lectureTagsWithPage(@PathVariable("tagNames") String tagNames,
			@PathVariable("id") String id, @PathVariable("page") String page,
			@PathVariable("sort") String sort, Model model) {
		List<String> tags = tagService.stringToTagList(tagNames);

		int pageNum = WebUtil.getPageNumber(page);
		int size = WebUtil.getPageSize(page);

		Weaver weaver = weaverService.get(id);

		if (weaver == null)
			return "redirect:/";

		Weaver currentWeaver = weaverService.getCurrentWeaver();

		if (!tagService.validateTag(tags, currentWeaver))
			return "redirect:/";

		model.addAttribute("weaver", weaver);
		model.addAttribute("lectures", lectureService
				.getLecturesWithWeaver(currentWeaver, weaver, tags, sort, pageNum, size));
		model.addAttribute("lectureCount", lectureService
				.countLecturesWithWeaver(weaver, tags, sort));
		model.addAttribute("tagNames", tagNames);
		model.addAttribute("pageIndex", page);
		model.addAttribute("number", size);
		model.addAttribute("pageUrl", "/"+id+"/code/tags:" + tagNames+ "/sort:" + sort + "/page:");

		return "/weaver/mypage/lecture";
	}

	@RequestMapping({"/{id}/tags:{tagNames}/search:{search}",
	"/{id}/code/tags:{tagNames}/search:{search}"})
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
		int pageNum = WebUtil.getPageNumber(page);
		int size = WebUtil.getPageSize(page);
		Weaver weaver = weaverService.get(id);

		if (weaver == null)
			return "redirect:/";

		Weaver currentWeaver = weaverService.getCurrentWeaver();

		if (!tagService.validateTag(tagList, currentWeaver))
			return "redirect:/";

		model.addAttribute("weaver", weaver);

		model.addAttribute("posts", postService
				.getPosts(currentWeaver,
						tagList, weaver, search, sort, pageNum, size));
		model.addAttribute("postCount", postService
				.countPosts(currentWeaver,
						tagList, weaver, search, sort));
		model.addAttribute("tagNames", tagNames);
		model.addAttribute("search", search);
		model.addAttribute("pageIndex", page);
		model.addAttribute("pageUrl", "/"+id+"/tags:" + tagNames + "/search:" + search
				+ "/sort:" + sort + "/page:");

		return "/weaver/mypage/community";
	}

	@RequestMapping("/{id}/code/tags:{tagNames}/search:{search}/sort:{sort}/page:{page}")
	public String codeTagsWithSearch(@PathVariable("tagNames") String tagNames,
			@PathVariable("id") String id,
			@PathVariable("search") String search,
			@PathVariable("sort") String sort,
			@PathVariable("page") String page, Model model) {
		List<String> tags = tagService.stringToTagList(tagNames);
		int pageNum = WebUtil.getPageNumber(page);
		int size = WebUtil.getPageSize(page);
		Weaver weaver = weaverService.get(id);

		if (weaver == null)
			return "redirect:/";

		Weaver currentWeaver = weaverService.getCurrentWeaver();

		if (!tagService.validateTag(tags, currentWeaver)) 
			return "redirect:/";

		model.addAttribute("weaver", weaver);
		model.addAttribute("codes", codeService
				.getCodes(weaver, tags, search, sort, pageNum, size));
		model.addAttribute("codeCount", codeService
				.countCodes(weaver, tags, search, sort));
		model.addAttribute("tagNames", tagNames);
		model.addAttribute("search", search);
		model.addAttribute("pageIndex", page);
		model.addAttribute("pageUrl", "/"+id+"/code/tags:" + tagNames + "/search:" + search
				+ "/sort:" + sort + "/page:");

		return "/weaver/mypage/code";
	}


	@RequestMapping(value = "/{id}/edit")
	public String editWeaver(@PathVariable("id") String id,Model model) {
		Weaver weaver = weaverService.getCurrentWeaver();
		if (!weaver.getId().equals(id)) // 본인이 아닐 경우
			return "redirect:/";
		model.addAttribute("weaver", weaver);
		return "/weaver/edit";
	}

	@RequestMapping(value = "/{id}/edit", method = RequestMethod.POST)
	public String editWeaver(@PathVariable("id") String id,
			@RequestParam("password") String password,
			@RequestParam("newpassword") String newpassword,
			@RequestParam("tags") String tags,
			@RequestParam("studentID") String studentID,
			@RequestParam("say") String say,
			@RequestParam("image") MultipartFile image,Model model) {
		Weaver weaver = weaverService.getCurrentWeaver();
		List<String> tagList = tagService.stringToTagList(tags);

		if(!tagService.isPublicTags(tagList)){
			model.addAttribute("say", "태그를 잘못 입력하셨습니다!");
			model.addAttribute("url", "/"+id+"/edit");
			return "/alert";
		}


		if (!weaver.getId().equals(id) ){ // 본인이 아닐때
			model.addAttribute("say", "권한이 없습니다!");
			model.addAttribute("url", "/");
			return "/alert";
		}
		weaverService.update(weaver,password,newpassword,tagList,studentID,say,image);

		model.addAttribute("say", "정보를 수정하였습니다!");
		model.addAttribute("url", "/"+id+"/edit");
		return "/alert";

	}

	@RequestMapping(value = "/{id}/img")
	public void img(@PathVariable("id") String id, HttpServletResponse res)
			throws IOException {
		Weaver weaver = weaverService.get(id);
		if (weaver == null) {
			if(id.contains("@") && id.contains("."))
				res.sendRedirect("http://www.gravatar.com/avatar/"
						+ WebUtil.convertMD5(id) + ".jpg");
			else
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

	@RequestMapping(value = "/repassword/{email}/{key}")
	public String repassword(@PathVariable("email") String email,@PathVariable("key") String key) {

		if(weaverService.changePassword(email, key))
			return "/weaver/repassword";
		else
			return "/error500";
	}

	@RequestMapping(value = "/repassword", method = RequestMethod.POST)
	@ResponseBody
	public boolean repasswordCheck(@RequestParam("email") String email) {
		return weaverService.sendRepassword(email);
	}


}
