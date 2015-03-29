package com.forweaver.service;

import java.util.ArrayList;
import java.util.List;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.forweaver.domain.Pass;
import com.forweaver.domain.Project;
import com.forweaver.domain.WaitJoin;
import com.forweaver.domain.Weaver;
import com.forweaver.mongodb.dao.CherryPickRequestDao;
import com.forweaver.mongodb.dao.PostDao;
import com.forweaver.mongodb.dao.ProjectDao;
import com.forweaver.mongodb.dao.WaitJoinDao;
import com.forweaver.mongodb.dao.WeaverDao;
import com.forweaver.util.GitUtil;

/** 프로젝트 관리 서비스
 *
 */
@Service
public class ProjectService{

	@Autowired private WeaverDao weaverDao;
	@Autowired private ProjectDao projectDao;
	@Autowired private CacheManager cacheManager;
	@Autowired private WaitJoinDao waitJoinDao;
	@Autowired private PostDao postDao;
	@Autowired private CherryPickRequestDao cherryPickRequestDao;
	@Autowired private GitUtil gitUtil;

	@Autowired @Qualifier("sessionRegistry")
	private SessionRegistry sessionRegistry;
	/** 프로젝트 추가하는 메서드
	 * @param project
	 * @param currentWeaver
	 */
	public void add(Project project,Weaver currentWeaver){
		// TODO Auto-generated method stub
		if(currentWeaver == null)
			return;
		try{
			gitUtil.Init(project);
			gitUtil.createRepository();
		} catch (Exception e) {
			return;
		}
		projectDao.insert(project);
		Pass pass = new Pass(project.getName(),2);
		currentWeaver.addPass(pass);
		weaverDao.updatePass(currentWeaver);
	}

	/** 회원 추가함.
	 * @param project
	 * @param currentWeaver
	 * @param joinWeaver
	 */
	public boolean addWeaver(Project project,Weaver joinWeaver){
		// TODO Auto-generated method stub
		if(project == null || joinWeaver == null)
			return false;
		
		Pass pass = new Pass(project.getName(), 1);
		project.addJoinWeaver(joinWeaver); //프로젝트 목록에 추가
		joinWeaver.addPass(pass);
		weaverDao.updatePass(joinWeaver);
		this.update(project);

		return true;
	}


	/** 프로젝트 이름으로 불러오기.
	 * @param projectName
	 * @return
	 */
	public Project get(String projectName) {
		// TODO Auto-generated method stub
		return projectDao.get(projectName);
	}


	public boolean delete(Weaver weaver,Project project){
		// TODO Auto-generated method stub
		if(weaver == null || project == null)
			return false;
		if(weaver.isAdmin() || weaver.equals(project.getCreator())){

			try{
				gitUtil.Init(project);
				gitUtil.deleteRepository();
			} catch (Exception e) {
				return false;
			}
			for(Weaver joinWeaver:project.getJoinWeavers()){
				joinWeaver.deletePass(project.getName());
				weaverDao.updatePass(joinWeaver);
				
				for (Object object : sessionRegistry.getAllPrincipals()) { //현재 로그인 중인 회원의 권한 삭제.
					Weaver tmpWeaver = ((Weaver) object);
					if (tmpWeaver.equals(joinWeaver))
						joinWeaver.deletePass(project.getName());
				}
			}
			for(WaitJoin waitJoin:waitJoinDao.delete(project.getName())){ // 대기 중인 초대장 삭제.
				postDao.delete(postDao.get(waitJoin.getPostID())); //처음 보냈던 메세지 삭제.
				return true;
			}
			cherryPickRequestDao.delete(project);
			
			project.getCreator().deletePass(project.getName());
			weaverDao.updatePass(project.getCreator());
			projectDao.delete(project);
			return true;
		}
		return false;

	}


	public boolean deleteWeaver(Project project, Weaver currentWeaver,Weaver deleteWeaver) {
		// 프로젝트에 동료를 탈퇴시킴
		if(project == null || deleteWeaver == null || deleteWeaver.getPass(project.getName()) == null)
			return false;

		if(project.getCreator().equals(currentWeaver) ||  //관리자가 탈퇴시키거나
				currentWeaver.equals(deleteWeaver)){ //본인이 나가거나
			deleteWeaver.deletePass(project.getName());
			project.removeJoinWeaver(deleteWeaver);
			weaverDao.updatePass(deleteWeaver);
			projectDao.update(project);

			return true;
		}

		return false;

	}

	/** 프로젝트를 추천하면 캐시에 저장하고 24시간 제한을 둠.
	 * @param project
	 * @param weaver
	 * @param ip
	 * @return
	 */
	public boolean push(Project project, Weaver weaver,String ip) {
		if(project == null || project.getCategory() > 0 || 
				(weaver == null &&  project.isProjectWeaver(weaver)))
			return false;
		
		Cache cache = cacheManager.getCache("push");
		Element element = cache.get(project.getName()+"@@"+ip);
		if (element == null || (element != null && element.getValue() == null)) {
			project.push();
			projectDao.update(project);
			Element newElement = new Element(project.getName()+"@@"+ip, ip);
			cache.put(newElement);
			return true;
		}
		return false;
	}


	public long countProjects(List<String> tags,String search,String sort){
		return projectDao.countProjects(tags, search, sort);
	}

	public List<Project> getProjects(Weaver currentWeaver,List<String> tags,
			String search,String sort, int pageNumber,int lineNumber){
		List<Project> projects=  projectDao.getProjects(tags, search, sort, pageNumber, lineNumber);
		if(currentWeaver != null)
			for(Project project:projects){
				Pass pass = currentWeaver.getPass(project.getName());
				if(pass != null)
					project.setJoin(pass.getPermission());
				else
					project.setJoin(0);
			}
		return projects;
	}


	public long countProjects(Weaver weaver,List<String> tags,String sort){
		if(sort.equals("join"))
			return projectDao.countProjects(weaver.getJoinProjects(),tags,sort);
		else if(sort.equals("admin"))
			return projectDao.countProjects(weaver.getAdminProjects(),tags,sort);
		else
			return projectDao.countProjects(weaver.getProjects(),tags,sort);
	}

	/** 회원을 기준으로 프로젝트를 검색함.
	 * @param weaver
	 * @param tags
	 * @param sort
	 * @param page
	 * @param size
	 * @return
	 */
	public List<Project> getProjects(Weaver currentWeaver,Weaver weaver,List<String> tags,String sort,int page,int size){
		List<Project> projects=  new ArrayList<Project>();

		if(sort.equals("join"))
			projects = projectDao.getProjects(weaver.getJoinProjects(),tags,sort, page, size);
		else if(sort.equals("admin"))
			projects = projectDao.getProjects(weaver.getAdminProjects(),tags,sort, page, size);
		else
			projects = projectDao.getProjects(weaver.getProjects(),tags,sort, page, size);

		if(currentWeaver != null)
			for(Project project:projects){
				Pass pass = currentWeaver.getPass(project.getName());
				if(pass != null)
					project.setJoin(pass.getPermission());
				else
					project.setJoin(0);
			}
		return projects;
	}

	/** 압축파일을 올리면 자동으로 커밋함.
	 * @param project
	 * @param weaver
	 * @param branchName
	 * @param message
	 * @param zip
	 */
	public boolean uploadZip(Project project,Weaver weaver,String branchName,String message,MultipartFile zip){
		if(message==null || message.length() == 0 || weaver.getPass(project.getName()) == null || !zip.getOriginalFilename().toUpperCase().endsWith(".ZIP"))
			return false;
		gitUtil.Init(project);
		try{
			gitUtil.uploadZip(weaver.getId(), weaver.getEmail(),branchName, message, zip.getInputStream());
		}catch(Exception e){
			System.err.print(e.getLocalizedMessage());
			return false;
		}

		return true;
	}

	public void update(Project project) {
		projectDao.update(project);
	}

	public String fork(Project originProject, Project newProject, Weaver weaver){

		if(originProject.getCategory() != 0)
			return null;

		if(!originProject.getCreator().equals(weaver)){
			if(this.get(newProject.getName())!=null){
				while(true){
					int cnt=1;
					if(this.get(newProject.getName()+'-'+cnt)==null){
						newProject.setName(newProject.getName()+'-'+cnt);
						break;
					}
					cnt++;
				}
			}

			gitUtil.Init(newProject);
			gitUtil.forkRepository(originProject.getName(), newProject.getName());
			projectDao.insert(newProject);
			projectDao.update(originProject);

			Pass pass = new Pass(newProject.getName(), 2);
			weaver.addPass(pass);

			weaverDao.updatePass(weaver);

			return newProject.getName();
		}
		return null;
	}

}
