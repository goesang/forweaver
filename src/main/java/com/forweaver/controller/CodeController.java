package com.forweaver.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.forweaver.domain.Code;
import com.forweaver.domain.Data;
import com.forweaver.domain.RePost;
import com.forweaver.domain.Reply;
import com.forweaver.domain.Weaver;
import com.forweaver.service.CodeService;
import com.forweaver.service.DataService;
import com.forweaver.service.RePostService;
import com.forweaver.service.TagService;
import com.forweaver.service.WeaverService;
import com.forweaver.util.WebUtil;


@Controller
@RequestMapping("/code")
public class CodeController {
	@Autowired 
	private CodeService codeService;
	@Autowired 
	private TagService tagService;
	@Autowired 
	private WeaverService weaverService;
	@Autowired 
	private RePostService rePostService;
	@Autowired 
	private DataService dataService;

	@RequestMapping("/")
	public String front(){
		return "redirect:/code/sort:age-desc/page:1";
	}


	@RequestMapping("/sort:{sort}/page:{page}")
	public String page(@PathVariable("page") String page,
			@PathVariable("sort") String sort,Model model){
		int pageNum = WebUtil.getPageNumber(page);
		int size = WebUtil.getPageSize(page);

		model.addAttribute("codes", 
				codeService.getCodes(null, null, null, sort, pageNum, size));
		model.addAttribute("codeCount", 
				codeService.countCodes(null, null, null, sort));

		model.addAttribute("pageIndex", pageNum);
		model.addAttribute("number", size);
		model.addAttribute("pageUrl", "/code/sort:"+sort+"/page:");
		return "/code/front";
	}

	@RequestMapping("/tags:{tagNames}")
	public String tags(HttpServletRequest request){
		return "redirect:"+request.getRequestURI() +"/sort:age-desc/page:1";
	}

	@RequestMapping("/tags:{tagNames}/sort:{sort}/page:{page}")
	public String tagsWithPage(@PathVariable("tagNames") String tagNames,
			@PathVariable("page") String page,
			@PathVariable("sort") String sort,Model model){
		List<String> tags = tagService.stringToTagList(tagNames);

		int pageNum = WebUtil.getPageNumber(page);
		int size = WebUtil.getPageSize(page);

		Weaver currentWeaver = weaverService.getCurrentWeaver();
		if(!tagService.validateTag(tags,currentWeaver)){
			return "redirect:/code/sort:age-desc/page:1";
		}

		model.addAttribute("codes", 
				codeService.getCodes(null, tags, null, sort, pageNum, size));
		model.addAttribute("codeCount", 
				codeService.countCodes(null,tags, null, sort));

		model.addAttribute("tagNames", tagNames);
		model.addAttribute("pageIndex", pageNum);
		model.addAttribute("number", size);
		model.addAttribute("pageUrl", "/code/tags:"+tagNames+"/sort:"+sort+"/page:");

		return "/code/front";
	}

	@RequestMapping("/tags:{tagNames}/search:{search}")
	public String tagsWithSearch(HttpServletRequest request){
		return "redirect:"+ request.getRequestURI() +"/sort:age-desc/page:1";
	}

	@RequestMapping("/tags:{tagNames}/search:{search}/sort:{sort}/page:{page}")
	public String tagsWithSearch(@PathVariable("tagNames") String tagNames,
			@PathVariable("search") String search,
			@PathVariable("sort") String sort,
			@PathVariable("page") String page,Model model){
		List<String> tags = tagService.stringToTagList(tagNames);

		int pageNum = WebUtil.getPageNumber(page);
		int size = WebUtil.getPageSize(page);

		model.addAttribute("codes", 
				codeService.getCodes(null,tags, search, sort, pageNum, size));
		model.addAttribute("codeCount", 
				codeService.countCodes(null,tags, search, sort));

		model.addAttribute("number", size);
		model.addAttribute("tagNames", tagNames);
		model.addAttribute("search", search);
		model.addAttribute("pageIndex", pageNum);
		model.addAttribute("pageUrl", "/tags:"+tagNames+"/search:"+search+"/sort:"+sort+"/page:");

		return "/code/front";
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String add(final HttpServletRequest request,Model model) throws UnsupportedEncodingException {
		final MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;

		MultipartFile file = multiRequest.getFile("file");
		String tags = request.getParameter("tags");
		String name = request.getParameter("name");
		String content = request.getParameter("content");

		if(tags == null || name == null || content == null || file == null || !Pattern.matches("(^[A-Za-z0-9]{5,15}$)", name)){ // 태그가 없을 때
			model.addAttribute("say", "잘못 입력하셨습니다!!!");
			model.addAttribute("url", "/code/");
			return "/alert";
		}
		List<String> tagList = tagService.stringToTagList(tags);
		Weaver weaver = weaverService.getCurrentWeaver();

		codeService.add(new Code(weaver, name, content, tagList), file);
		return "redirect:/code/";
	}

	@RequestMapping("/{codeID}")
	public String view(@PathVariable("codeID") int codeID){
		return "redirect:/code/"+codeID+"/sort:age-desc";
	}

	@RequestMapping("/{codeID}/delete")
	public String delete(@PathVariable("codeID") int codeID,Model model){
		Weaver currentWeaver = weaverService.getCurrentWeaver();
		Code code = codeService.get(codeID);

		if(!codeService.delete(currentWeaver,code)){
			model.addAttribute("say", "코드를 삭제하지 못했습니다. 권한을 확인해보세요!");
			model.addAttribute("url", "/code/");
			return "/alert";
		}
		return "redirect:/code/";
	}

	@RequestMapping("/{codeID}/sort:{sort}")
	public String view(Model model, @PathVariable("codeID") int codeID,
			@PathVariable("sort") String sort) {
		Code code = codeService.get(codeID);

		model.addAttribute("code", code);
		model.addAttribute("rePosts", rePostService.gets(codeID,4,sort));

		return "/code/viewCode";
	}

	@RequestMapping("/{codeID}/{codeName}.zip")
	public void download(HttpServletResponse res, 
			@PathVariable("codeID") int codeID,
			@PathVariable("codeName") String codeName) throws IOException {
		Code code = codeService.get(codeID);
		codeService.dowloadCode(code, res.getOutputStream());
	}


	@RequestMapping(value = "/{codeID}/add-repost", method = RequestMethod.POST)
	public String addRepost(@PathVariable("codeID") int codeID,
			HttpServletRequest request,Model model) throws UnsupportedEncodingException {

		final MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;

		final Map<String, MultipartFile> files = multiRequest.getFileMap();	

		String content = request.getParameter("content");
		Weaver weaver = weaverService.getCurrentWeaver();
		Code code = codeService.get(codeID);

		if(code == null || weaver == null || content.equals("")) {
			model.addAttribute("say", "잘못 입력하셨습니다!!!");
			model.addAttribute("url", "/code/"+codeID);
			return "/alert";
		}

		ArrayList<Data> datas = new ArrayList<Data>();
		for (MultipartFile file : files.values()) {
			if(!file.isEmpty())
				datas.add(new Data(dataService.getObjectID(file.getOriginalFilename(), weaver),file,weaver));
		}

		RePost rePost = new RePost(code,
				weaver,
				content);
		rePostService.add(rePost,datas);
		code.setRePostCount(code.getRePostCount()+1);
		codeService.update(code);

		return "redirect:/code/"+codeID;
	}

	@RequestMapping(value = "/{codeID}/{rePostID}/add-reply", method = RequestMethod.POST)
	public String addReply(@PathVariable("codeID") int codeID,
			@PathVariable("rePostID") int rePostID,
			HttpServletRequest request,Model model) throws UnsupportedEncodingException {
		String content = request.getParameter("content");
		RePost rePost = rePostService.get(rePostID);
		Weaver weaver = weaverService.getCurrentWeaver();

		if(!rePostService.addReply(rePost,new Reply(weaver, content))){
			model.addAttribute("say", "댓글을 추가히지 못했습니다. 권한을 확인해보세요!");
			model.addAttribute("url", "/code/"+codeID);
			return "/alert";
		}
		return "redirect:/code/"+codeID;	
	}

	@RequestMapping(value="/{codeID}/{rePostID}/{number}/delete")
	public String deleteReply(@PathVariable("codeID") int codeID, 
			@PathVariable("rePostID") int rePostID,
			@PathVariable("number") int number,
			HttpServletRequest request,Model model) {		

		RePost rePost = rePostService.get(rePostID);
		Weaver weaver = weaverService.getCurrentWeaver();

		if(!rePostService.deleteReply(rePost, weaver, number)){		
			model.addAttribute("say", "삭제하지 못했습니다. 권한을 확인해보세요!");
			model.addAttribute("url", "/code/"+codeID);
			return "/alert";
		}
		return "redirect:/code/"+codeID;	
	}

	@RequestMapping("/{codeID}/{rePostID}/delete")
	public String deleteRePost(Model model, @PathVariable("codeID") int codeID, @PathVariable("rePostID") int rePostID) {
		Code code = codeService.get(codeID);
		RePost rePost = rePostService.get(rePostID);
		Weaver weaver =weaverService.getCurrentWeaver();

		if(!rePostService.delete(code,rePost,weaver)){
			model.addAttribute("say", "삭제하지 못했습니다. 권한을 확인해보세요!");
			model.addAttribute("url", "/code/"+codeID);
			return "/alert";
		}
		return "redirect:/code/"+codeID;	

	}
}
