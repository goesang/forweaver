package com.forweaver.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

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
import com.forweaver.domain.Weaver;
import com.forweaver.service.CodeService;
import com.forweaver.service.RePostService;
import com.forweaver.service.TagService;
import com.forweaver.service.WeaverService;
import com.forweaver.util.WebUtil;

@Controller
@RequestMapping("/code")
public class CodeController {
	@Autowired
	CodeService codeService;
	
	@Autowired
	TagService tagService;
	
	@Autowired
	WeaverService weaverService;
	
	@Autowired
	RePostService rePostService;
	
	@RequestMapping("/")
	public String front(){
		return "redirect:/code/sort:age-desc/page:1";
	}
	
	
	@RequestMapping("/sort:{sort}/page:{page}")
	public String page(@PathVariable("page") String page,
			@PathVariable("sort") String sort,Model model){
		int pageNum;
		int number = 15;
		
		if(page.contains(",")){
			pageNum = Integer.parseInt(page.split(",")[0]);
			number = Integer.parseInt(page.split(",")[1]);
		}else{
			pageNum =Integer.parseInt(page);
		}
		
		model.addAttribute("codes", 
				codeService.getCodes(sort, pageNum, number));
		model.addAttribute("codeCount", 
				codeService.countCodes(sort));
		
		model.addAttribute("pageIndex", pageNum);
		model.addAttribute("number", number);
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
			List<String> tagList = tagService.stringToTagList(tagNames);
			
			int pageNum;
			int number = 15;
			
			if(page.contains(",")){
				pageNum = Integer.parseInt(page.split(",")[0]);
				number = Integer.parseInt(page.split(",")[1]);
			}else{
				pageNum =Integer.parseInt(page);
			}	
		Weaver currentWeaver = weaverService.getCurrentWeaver();
		if(!tagService.validateTag(tagList,currentWeaver)){
			return "redirect:/code/sort:age-desc/page:1";
		}
		
		model.addAttribute("codes", 
				codeService.getCodesWithTags(tagList, sort, pageNum, number));
		model.addAttribute("codeCount", 
				codeService.countCodesWithTags(tagList, sort));
	
		model.addAttribute("tagNames", tagNames);
		model.addAttribute("pageIndex", pageNum);
		model.addAttribute("number", number);
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
		List<String> tagList = tagService.stringToTagList(tagNames);
				
		int pageNum;
		int number = 15;
		
		if(page.contains(",")){
			pageNum = Integer.parseInt(page.split(",")[0]);
			number = Integer.parseInt(page.split(",")[1]);
		}else{
			pageNum =Integer.parseInt(page);
		}
		
		model.addAttribute("codes", 
				codeService.getCodesWithTagsAndSearch(tagList,search, sort, pageNum, number));
		model.addAttribute("codeCount", 
				codeService.countCodesWithTagsAndSearch(tagList,search, sort));
	
		model.addAttribute("number", number);
		model.addAttribute("tagNames", tagNames);
		model.addAttribute("search", search);
		model.addAttribute("pageIndex", page);
		model.addAttribute("pageUrl", "/tags:"+tagNames+"/search:"+search+"/sort:"+sort+"/page:");
		
		return "/code/front";
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String add(final HttpServletRequest request) throws UnsupportedEncodingException {
		final MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
		
		MultipartFile file = multiRequest.getFile("file");
		
        String tags = request.getParameter("tags");
        String name = request.getParameter("name");
        String content = request.getParameter("content");
        
		if(tags == null || name == null || content == null || file == null) // 태그가 없을 때
			return "redirect:/code/";
		
		List<String> tagList = tagService.stringToTagList(
				WebUtil.removeHtml(URLDecoder.decode(tags)));
		Weaver weaver = weaverService.getCurrentWeaver();
		
		codeService.add(new Code(weaver, name, content, tagList), file);
		return "redirect:/code/";
	}
	
	@RequestMapping("/{codeID}")
	public String view(@PathVariable("codeID") int codeID){
		return "redirect:/code/"+codeID+"/sort:age-desc";
	}
	
	@RequestMapping("/{codeID}/delete")
	public String delete(@PathVariable("codeID") int codeID){
		Weaver currentWeaver = weaverService.getCurrentWeaver();
		Code code = codeService.get(codeID);
		if(currentWeaver.getId().equals(code.getWriterName()))
			codeService.delete(code);
		return "redirect:/code/";
	}
	
	@RequestMapping("/{codeID}/sort:{sort}")
	public String view(Model model, @PathVariable("codeID") int codeID,
			@PathVariable("sort") String sort) {
		Code code = codeService.get(codeID);
		
		model.addAttribute("code", code);
		model.addAttribute("rePosts", rePostService.get(codeID,4,sort));
		
		return "/code/viewCode";
	}
	
	@RequestMapping("/{codeID}/{codeName}.zip")
	public void download(HttpServletResponse res, 
			@PathVariable("codeID") int codeID,
			@PathVariable("codeName") String codeName) throws IOException {
		Code code = codeService.get(codeID);
		
		if(code != null && code.getName().equals(codeName))
			codeService.dowloadCode(code, res.getOutputStream());
	}
	
}
