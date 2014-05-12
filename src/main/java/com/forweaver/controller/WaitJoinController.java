package com.forweaver.controller;



/*
@Controller
public class WaitJoinController {
	
	@Autowired ProjectService projectService;
	@Autowired WeaverService weaverService;
	@Autowired WaitJoinService waitJoinService;
	@Autowired PostService postService;
	@Autowired TagService tagService;
	@Autowired PassService passService;

	@RequestMapping("/project/{creatorName}/{projectName}/weaver:{weaver}/add-weaver")
	public String addWeaver(	@PathVariable("creatorName") String creatorName,
			@PathVariable("projectName") String projectName,
			@PathVariable("weaver") String weaver) {
		Project project = projectService.get(creatorName+"/"+projectName);
		
		Weaver waitingWeaver = weaverService.get(weaver);
		Weaver proposer = weaverService.getCurrentWeaver();

		if(waitJoinService.isCreateProjectWaitJoin(project, waitingWeaver, proposer)){
			Weaver projectCreator = weaverService.get(project.getCreatorName());
			
			String title ="프로젝트명:"+creatorName+"/"+projectName+
					"에 가입 초대를 <a href='/project/"+creatorName+"/"+projectName+"/weaver:"+weaver+
					"/join-ok'>승락하시겠습니까?</a> "
					+ "아니면 <a href='/project/"+creatorName+"/"+projectName+"/weaver:"+weaver+
					"/join-cancel'>거절하시겠습니까?</a>";
			
			Post post = new Post(projectCreator, 
					title, 
					"", 
					tagService.stringToTagList("$"+weaver));
			
			waitJoinService.createWaitJoin(
					project.getName(), 
					proposer.getName(), 
					waitingWeaver.getName(), 
					postService.add(post));
		}
		return "redirect:/project/"+ creatorName+"/"+projectName+"/weaver";	
		
	}
	
	
	@RequestMapping("/project/{creatorName}/{projectName}/join") //본인이 직접 프로젝트에 가입 신청함
	public String join(@PathVariable("creatorName") String creatorName,
			@PathVariable("projectName") String projectName) {
		Project project = projectService.get(creatorName+"/"+projectName);
		Weaver waitingWeaver = weaverService.getCurrentWeaver();
		
		if(waitJoinService.isCreateProjectWaitJoin(project, waitingWeaver, waitingWeaver)){
			
			String title = waitingWeaver.getName()+"님의 프로젝트명:"+
					creatorName+"/"+projectName+
					"에 가입을 <a href='/project/"+creatorName+"/"+projectName
					+"/weaver:"+waitingWeaver.getName()+"/join-ok'>승락하시겠습니까?</a> "
					+ "아니면 <a href='/project/"+creatorName+"/"+projectName+"/weaver:"+waitingWeaver.getName()+
					"/join-cancel'>거절하시겠습니까?</a>";
			
			Post post = new Post(waitingWeaver, 
					title, 
					"", 
					tagService.stringToTagList("$"+project.getCreatorName()));
			
			waitJoinService.createWaitJoin(
					project.getName(), 
					waitingWeaver.getName(), 
					waitingWeaver.getName(), 
					postService.add(post));
	}
		return "redirect:/";	
		
	}
	
	
	@RequestMapping("/project/{creatorName}/{projectName}/weaver:{weaver}/join-ok") // 프로젝트 가입 승인
	public String joinOK(@PathVariable("creatorName") String creatorName,
			@PathVariable("projectName") String projectName,@PathVariable("weaver") String weaver) {
		Project project = projectService.get(creatorName+"/"+projectName);
		Weaver currentWeaver = weaverService.getCurrentWeaver();
		Weaver waitingWeaver = weaverService.get(weaver);
		WaitJoin waitJoin = waitJoinService.get(creatorName+"/"+projectName, weaver);
		Pass pass = new Pass(weaver, creatorName+"/"+projectName, 0);
			
		if(project != null //요청자가 쪽지를 보내고 관리자가 승인을 하는 경우
				&& waitJoinService.isOkJoin(waitJoin, project.getCreatorName(), currentWeaver)
				&& project.getCreatorName().equals(currentWeaver.getName())
				&& waitJoinService.deleteProjectWaitJoin(waitJoin, project, currentWeaver)){
			project.addJoinWeaver(waitingWeaver); //강의 목록에 추가
			waitingWeaver.getPasses().add(pass); // 위버에 권한 추가
			passService.add(pass); //디비에 권한 저장
			
			Post post = new Post(currentWeaver,  //관리자가 가입자에게 보내는 메세지
					"관리자 "+project.getCreatorName()+"님의 승인으로 "+
					waitJoin.getWaitingWeaver()+
					"님이 가입되었습니다!!", 
					"", 
					tagService.stringToTagList("@"+project.getName()+",가입")); //@프로젝트명,가입 태그를 걸어줌
			
			postService.add(post);
			
			return "redirect:/project/"+creatorName+"/"+projectName+"/weaver";
			
		}else if(project != null //관리자가 쪽지를 보내고 가입자가 승인을 하는 경우
				&& waitJoinService.isOkJoin(waitJoin, project.getCreatorName(), currentWeaver)
				&& !project.getCreatorName().equals(currentWeaver.getName())
				&& waitJoinService.deleteProjectWaitJoin(waitJoin, project, currentWeaver)){
			project.addJoinWeaver(currentWeaver); //강의 목록에 추가
			currentWeaver.getPasses().add(pass); // 위버에 권한 추가
			passService.add(pass); //디비에 권한 저장
						
			Post post = new Post(currentWeaver, //가입자가 프로젝트에 보내는 메세지
					currentWeaver.getName()+"님이 가입 초대를 수락하셨습니다!", 
					"", 
					tagService.stringToTagList("@"+project.getName()+",가입")); //@프로젝트명,가입 태그를 걸어줌
			
			postService.add(post);
			
			return "redirect:/project/"+creatorName+"/"+projectName+"/project";
		}
		
		return "redirect:/";//엉뚱한 사람이 들어올때 그냥 돌려보냄
	}
	
	
	@RequestMapping("/project/{creatorName}/{projectName}/weaver:{weaver}/join-cancel")//프로젝트에 가입 승인 취소
	public String joinCancel(@PathVariable("creatorName") String creatorName,
			@PathVariable("projectName") String projectName,@PathVariable("weaver") String weaver) {
		Project project = projectService.get(creatorName+"/"+projectName);
		Weaver currentWeaver = weaverService.getCurrentWeaver();
		WaitJoin waitJoin = waitJoinService.get(project.getName(), weaver);
			
		if(project != null //요청자가 쪽지를 보내고 관리자가 취소 하는 경우
				&& waitJoinService.isOkJoin(waitJoin, project.getCreatorName(), currentWeaver)
				&& project.getCreatorName().equals(currentWeaver.getName())
				&& waitJoinService.deleteProjectWaitJoin(waitJoin, project, currentWeaver)){
			
			Post post = new Post(currentWeaver,  //관리자가 가입자에게 보내는 메세지
					"관리자 "+project.getCreatorName()+"님의 프로젝트명:"+
					"<a href='/project/"+creatorName+"/"+projectName+"'>"+
					creatorName+"/"+projectName+
					"</a>"+
					"에 가입이 거절되었습니다.", 
					"", 
					tagService.stringToTagList("$"+weaver));
			
			postService.add(post);
			
			return "redirect:/project/"+creatorName+"/"+projectName+"/weaver";
			
		}else if(project != null //관리자가 쪽지를 보내고 가입자가 취소 하는 경우
				&& waitJoinService.isOkJoin(waitJoin, project.getCreatorName(), currentWeaver)
				&& !project.getCreatorName().equals(currentWeaver.getName())
				&& waitJoinService.deleteProjectWaitJoin(waitJoin, project, currentWeaver)){
						
			Post post = new Post(currentWeaver, //가입자가 관리자에게 보내는 메세지
					currentWeaver.getName()+"님이 프로젝트명:"+
					"<a href='/project/"+	creatorName+"/"+projectName+"'>"+
					creatorName+"/"+projectName+
					"</a>"+"를 가입 초대를 거절하셨습니다.", 
					"", 
					tagService.stringToTagList("$"+project.getCreatorName()));
			
			postService.add(post);
			
			return "redirect:/project/"+creatorName+"/"+projectName+"/project";
		}
		
		return "redirect:/";//엉뚱한 사람이 들어올때 그냥 돌려보냄
		
	}
}
*/