package com.forweaver.controller;



public class ChatController {
	

	/*
	public ChatController(){
		init();
	}
	
	public void init() {	
		
		Vertx vertx = Vertx.newVertx();
		vertx.createHttpServer().websocketHandler(new Handler<ServerWebSocket>() {
		    public void handle(final ServerWebSocket ws) {
		    	
		        if (ws.path.equals("/myapp")) {
		            ws.dataHandler(new Handler<Buffer>() {
		                public void handle(Buffer data) {
		                    ws.writeTextFrame(data.toString()); // Echo it back
		                }
		            });
		        } else {
		            ws.reject();
		        }
		    }
		}).requestHandler(new Handler<HttpServerRequest>() {
		     public void handle(HttpServerRequest req) {
		         if (req.path.equals("/")) req.response.write("sddddsdsd"); // Serve the html
		    }
		}).listen(9090);
	}
	
	
	@RequestMapping("/page:{page}")
	public String page(@PathVariable("page") int page,Model model) {	
		Weaver weaver = weaverService.getCurrentWeaver();
		if(weaver != null)
			model.addAttribute("projects", weaverService.getProjects(weaver));
		model.addAttribute("chats", chatRoomService.getChatRoomList(10*(page-1),10));
		model.addAttribute("count", chatRoomService.count());
		model.addAttribute("pageIndex", page);
		model.addAttribute("pageUrl", "/chat/page:");
		return "/chat/front";
	}
	
	
	@RequestMapping("/tags:{tagNames}")
	public String tags(HttpServletRequest request){
		return "redirect:/"+request.getRequestURI() +"/page:1";
	}
	
	@RequestMapping("/tags:{tagNames}/page:{page}")
	public String tags(@PathVariable("tagNames") String tagNames,
			@PathVariable("page") int page,Model model) {	
		
		List<Tag> tagList = tagService.stringToTagList(tagNames);
		Weaver weaver = weaverService.getCurrentWeaver();
		
		if(!tagService.validateTag(tagList,weaver) || tagService.isMassageTags(tagList)){
			return "redirect:/chat/";
		}
		if(weaver != null)
			model.addAttribute("projects", weaverService.getProjects(weaver));
		model.addAttribute("chats", chatRoomService.getChatRoomList(tagList,10*(page-1),10));
		model.addAttribute("count", chatRoomService.getChatRoomListCount(tagList));
		model.addAttribute("pageIndex", page);
		model.addAttribute("pageUrl", "/chat/tags:"+tagNames+"/page:");
		
		return "/chat/front";
	}
	
	@RequestMapping(value="/add", method = RequestMethod.POST)
	@ResponseBody
	public int add(HttpServletRequest request) {
		String description = request.getParameter("description");
		//String projectName = request.getParameter("projectName");
		int category = Integer.parseInt(request.getParameter("category"));
		Weaver currentWeaver = weaverService.getCurrentWeaver();
		String tags = request.getParameter("tags");
		String projectName = request.getParameter("projectName");

		if(tags.length() == 0 || description.length() == 0) // 태그가 없을 때
			return -1;
				
		List<Tag> tagList = tagService.stringToTagList(
				WebUtil.removeHtml(WebUtil.specialSignDecoder(URLDecoder.decode(tags))));
		
		if(!tagService.validateTag(tagList,currentWeaver)) // 태그에 권한이 없을때
			return -1;
		ChatRoom chatRoom = new ChatRoom(WebUtil.removeHtml(WebUtil.specialSignDecoder(URLDecoder.decode(description)))
				, category, currentWeaver,tagList);
		chatRoomService.addWeaver(chatRoom,currentWeaver);
		if(!projectName.equals("") && projectName.split("/")[0].equals(currentWeaver.getName())){
			chatRoom.setGitSimpleFileInfo(
					gitService.getGitSimpleFileInfoList(
							projectName.split("/")[0], 
							projectName.split("/")[1],"HEAD"));
			chatRoom.setProjectName(projectName);
		}
		return chatRoomService.add(chatRoom);
	}
	
	
	@RequestMapping("/{chatID}")
	public String room(Model model,@PathVariable("chatID") int chatID) {
		ChatRoom chatRoom = chatRoomService.get(chatID);
		
		model.addAttribute("chat", chatRoom);
		
		return "/chat/room";
	}
	

	@RequestMapping(value="/{chatID}/update", method = RequestMethod.POST, produces="text/plain;charset=UTF-8")
	@ResponseBody

	public String room(Model model,@PathVariable("chatID") int chatID,HttpServletRequest request) {
		int version = Integer.parseInt(request.getParameter("version"));	
		String content = request.getParameter("content");
		ChatRoom chatRoom = chatRoomService.get(chatID);
		Weaver currentWeaver = weaverService.getCurrentWeaver();
		
		if(chatRoom == null)
			return "";
		chatRoomService.addWeaver(chatRoom,currentWeaver);
		if(content.length() != 0)
			chatRoom.addContent(currentWeaver, content);
		
		return chatRoom.toJson(version);
	}
	
	@RequestMapping("/{chatID}/fileviewer/filepath:{filePath}")
	public String fileViewer(@PathVariable("chatID") int chatID,
			@PathVariable("filePath") String filePath,Model model,Device device) {
		

		ChatRoom chatRoom = chatRoomService.get(chatID);
		GitFileInfo gitFileInfo = gitService.getFileInfo(chatRoom.getProjectName().split("/")[0], 
				chatRoom.getProjectName().split("/")[1], "HEAD", filePath);

		model.addAttribute("fileName", gitFileInfo.getName());
		model.addAttribute("fileContent", gitFileInfo.getContent());
		model.addAttribute("gitCommitLog", 
				new GitSimpleCommitLog(gitFileInfo.getSelectCommitLog()));
		model.addAttribute("chat", chatRoom);

		return "/chat/fileViewer";
	}
	
	*/
	
	
}
