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

import twitter4j.auth.RequestToken;

import com.forweaver.domain.Data;
import com.forweaver.domain.Weaver;
import com.forweaver.service.PostService;
import com.forweaver.service.TagService;
import com.forweaver.service.WeaverService;
import com.forweaver.util.WebUtil;

@Controller
public class WeaverController {

	private RequestToken requestToken;

	@Autowired
	WeaverService weaverService;

	@Autowired
	PostService postService;

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

	/*
	 * @RequestMapping(value = "/facebook/call") public ModelAndView
	 * callFacebook(Device device) {
	 * 
	 * if (device.isMobile()) { RedirectView redirectView = new RedirectView(
	 * "http://www.facebook.com/dialog/oauth?client_id=587971871251695&redirect_uri=http://www.forweaver.com/m/facebook/login&scope=email"
	 * ); return new ModelAndView(redirectView); } else { RedirectView
	 * redirectView = new RedirectView(
	 * "http://www.facebook.com/dialog/oauth?client_id=587971871251695&redirect_uri=http://www.forweaver.com/facebook/login&scope=email"
	 * ); return new ModelAndView(redirectView); }
	 * 
	 * }
	 * 
	 * @RequestMapping(value = "/facebook/join") public ModelAndView
	 * joinFacebook(@Validated Weaver weaver,Device device, BindingResult
	 * result,HttpServletRequest request) { if (result.hasErrors()) return new
	 * ModelAndView("/join"); if(weaverService.get(weaver.getName()) != null )
	 * return new ModelAndView("/join"); weaverService.add(weaver);
	 * 
	 * if (device.isMobile()) { RedirectView redirectView = new RedirectView(
	 * "http://www.facebook.com/dialog/oauth?client_id=587971871251695&redirect_uri=http://www.forweaver.com/m/facebook/login&scope=email"
	 * ); return new ModelAndView(redirectView); } else { RedirectView
	 * redirectView = new RedirectView(
	 * "http://www.facebook.com/dialog/oauth?client_id=587971871251695&redirect_uri=http://www.forweaver.com/facebook/login&scope=email"
	 * ); return new ModelAndView(redirectView); } }
	 * 
	 * @RequestMapping(value = "/facebook/login") public ModelAndView
	 * loginFacebook(Model model,Device device,HttpServletRequest request) {
	 * String appKey = "587971871251695"; String appSecret =
	 * "7f3874bd2ecabc1f9277831460a0fa0a"; String code =
	 * request.getParameter("code"); String accesstoken = ""; String result =
	 * ""; try{ if( code != null ) { HttpGet get = null; if (device.isMobile())
	 * { get = new HttpGet("https://graph.facebook.com/oauth/access_token"+
	 * "?client_id="+appKey+ "&client_secret="+appSecret+
	 * "&redirect_uri=http://www.forweaver.com/m/facebook/login" +
	 * "&code="+code); } else { get = new
	 * HttpGet("https://graph.facebook.com/oauth/access_token"+
	 * "?client_id="+appKey+ "&client_secret="+appSecret+
	 * "&redirect_uri=http://www.forweaver.com/facebook/login" + "&code="+code);
	 * }
	 * 
	 * DefaultHttpClient http = new DefaultHttpClient(); result =
	 * http.execute(get, new BasicResponseHandler());
	 * 
	 * accesstoken = result.substring(result.indexOf("=")+1); accesstoken =
	 * accesstoken.substring(0, accesstoken.indexOf("&")); } }catch(Exception
	 * e){
	 * 
	 * RedirectView redirectView = new RedirectView("/login"); return new
	 * ModelAndView(redirectView); } Facebook facebook = new
	 * FacebookTemplate(accesstoken); FacebookProfile profile =
	 * facebook.userOperations().getUserProfile(); Weaver weaver =
	 * weaverService.get(profile.getEmail());
	 * 
	 * if(weaver == null){ model.addAttribute("email", profile.getEmail());
	 * model.addAttribute("imgSrc",
	 * "http://graph.facebook.com/"+profile.getId()+"/picture?type=normal");
	 * RedirectView redirectView = new RedirectView("/weaver/facebookJoin");
	 * return new ModelAndView(redirectView); }else{ if(weaver.getSocialMode()
	 * == 0 || weaver.getSocialMode() == 2){ RedirectView redirectView = new
	 * RedirectView("/login"); return new ModelAndView(redirectView); }
	 * weaverService.autoLoginWeaver(weaver, request); RedirectView redirectView
	 * = new RedirectView("/"); return new ModelAndView(redirectView); }
	 * 
	 * 
	 * }
	 * 
	 * @RequestMapping(value = "/twitter/call") public ModelAndView
	 * callTwitter(Model model,Device device) { Twitter twitter = new
	 * TwitterFactory().getInstance(); if (device.isMobile()) {
	 * twitter.setOAuthConsumer("5xj6xvEEzTlcdwrFIvw",
	 * "wykduLSeWhn9i002QszizUpk8IrCMfjKQ4VydiegwM"); } else {
	 * twitter.setOAuthConsumer("cVkDOCmIPmpZ0gdSQRXTw",
	 * "gCy9Zh56gWV9lukiSs8ja6p6XEDJlle5sH6iJCd24"); }
	 * 
	 * try { this.requestToken = twitter.getOAuthRequestToken(); } catch
	 * (TwitterException e) { } RedirectView redirectView = new
	 * RedirectView(this.requestToken.getAuthorizationURL()); return new
	 * ModelAndView(redirectView); }
	 * 
	 * 
	 * @RequestMapping(value = "/twitter/join") public String
	 * joinTwitter(@Validated Weaver weaver, BindingResult
	 * result,HttpServletRequest request) { if (result.hasErrors()) return
	 * "/join"; if(weaverService.get(weaver.getName()) != null ) return "/join";
	 * weaverService.add(weaver); weaverService.autoLoginWeaver(weaver,
	 * request); return "redirect:/"; }
	 * 
	 * @RequestMapping(value = "/twitter/login") public ModelAndView
	 * loginTwitter(Model model,Device device,HttpServletRequest request) {
	 * Twitter twitter = new TwitterFactory().getInstance();
	 * 
	 * if (device.isMobile()) { twitter.setOAuthConsumer("5xj6xvEEzTlcdwrFIvw",
	 * "wykduLSeWhn9i002QszizUpk8IrCMfjKQ4VydiegwM"); } else {
	 * twitter.setOAuthConsumer("cVkDOCmIPmpZ0gdSQRXTw",
	 * "gCy9Zh56gWV9lukiSs8ja6p6XEDJlle5sH6iJCd24"); } String oauthToken =
	 * request.getParameter("oauth_token"); String oauthVerifier =
	 * request.getParameter("oauth_verifier"); AccessToken accessToken = null;
	 * if (this.requestToken.getToken().equals(oauthToken)) { try { accessToken
	 * = twitter.getOAuthAccessToken(this.requestToken,oauthVerifier);
	 * twitter.setOAuthAccessToken(accessToken); User user =
	 * twitter.showUser(twitter.getId());
	 * 
	 * Weaver weaver = weaverService.get(twitter.getId());
	 * 
	 * if(weaver == null){ model.addAttribute("imgSrc",
	 * user.getProfileImageURL()); model.addAttribute("twitterID",
	 * twitter.getId()); return new ModelAndView("/weaver/twitterJoin"); }else{
	 * if(weaver.getSocialMode() == 0 || weaver.getSocialMode() == 1){
	 * RedirectView redirectView = new RedirectView("/login"); return new
	 * ModelAndView(redirectView); } weaverService.autoLoginWeaver(weaver,
	 * request);
	 * 
	 * RedirectView redirectView = new RedirectView("/"); return new
	 * ModelAndView(redirectView); }
	 * 
	 * } catch (TwitterException e) { RedirectView redirectView = new
	 * RedirectView("/login"); return new ModelAndView(redirectView); } }
	 * RedirectView redirectView = new RedirectView("/login"); return new
	 * ModelAndView(redirectView);
	 * 
	 * }
	 */
	@RequestMapping("/{id}")
	public String home(@PathVariable("id") String id, Model model) {

		return "redirect:/" + id + "/sort:age-desc/page:1";
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
				currentWeaver, id, sort, pageNum, number));
		model.addAttribute("postCount",
				postService.countPostsWhenWeaverHome(currentWeaver, id, sort));

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
				currentWeaver, tagList, id, sort, pageNum, number));
		model.addAttribute("postCount", postService
				.countPostsWhenWeaverHomeWithTags(currentWeaver, tagList, id,
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
						tagList, id, search, sort, pageNum, number));
		model.addAttribute("postCount", postService
				.countPostsWhenWeaverHomeWithTagsAndSearch(currentWeaver,
						tagList, id, search, sort));

		model.addAttribute("tagNames", tagNames);
		model.addAttribute("search", search);
		model.addAttribute("pageIndex", page);
		model.addAttribute("pageUrl", "/tags:" + tagNames + "/search:" + search
				+ "/sort:" + sort + "/page:");

		return "/weaver/home";
	}

	@RequestMapping(value = "/{id}/edit", method = RequestMethod.POST)
	public String editWeaver(@PathVariable("id") String id,
			HttpServletRequest req) {

		String currentPassword = req.getParameter("currentPassword");
		String password = req.getParameter("password");
		String email = req.getParameter("email");

		if (!weaverService.getCurrentWeaver().getId().equals(id)) // 본인이 아닐 경우
			return "redirect:/manage";

		if (!weaverService.getCurrentWeaver().getPassword()
				.equals(currentPassword))
			return "redirect:/manage";

		Weaver weaver = new Weaver(id, password, email);

		weaverService.update(weaver);

		return "redirect:/manage";
	}

	@RequestMapping(value = "/img/{id}")
	public void img(@PathVariable("id") String id, HttpServletResponse res)
			throws IOException {
		Weaver weaver = weaverService.get(id);
		if (weaver == null) {
			res.sendRedirect("http://www.gravatar.com/avatar/a.jpg");
			return;
		} else if (weaver.getImage().getContent().length > 0) {
			byte[] imgData = weaver.getImage().getContent();
			res.setContentType(weaver.getImage().getType());
			OutputStream o = res.getOutputStream();
			o.write(imgData);
			o.flush();
			o.close();
			return;
		} else if (weaver.getRealImgSrc() == null) {
			res.sendRedirect("http://www.gravatar.com/avatar/"
					+ WebUtil.convertMD5(weaver.getEmail()) + ".jpg");
		} else {
			res.sendRedirect(weaver.getRealImgSrc());
		}

	}

	@RequestMapping(value = "/check", method = RequestMethod.POST)
	@ResponseBody
	public boolean nickNameCheck(HttpServletRequest req) {
		return weaverService.idCheck(req.getParameter("id"));
	}

}
