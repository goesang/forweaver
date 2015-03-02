package com.forweaver.service;

import java.util.List;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.forweaver.domain.Pass;
import com.forweaver.domain.Project;
import com.forweaver.domain.Weaver;
import com.forweaver.mongodb.dao.ProjectDao;
import com.forweaver.mongodb.dao.WeaverDao;
import com.forweaver.util.GitUtil;

@Service
public class ProjectService{

	@Autowired private WeaverDao weaverDao;
	@Autowired private ProjectDao projectDao;
	@Autowired private CacheManager cacheManager;
	
	@Value("${gitpath}")
	private String gitpath;

	public void add(Project project,Weaver currentWeaver){
		// TODO Auto-generated method stub

		try{
			GitUtil gitUtil = new GitUtil(gitpath,project);
			gitUtil.createRepository();
		} catch (Exception e) {
			return;
		}
		projectDao.insert(project);
		Pass pass = new Pass(project.getName(),2);
		currentWeaver.addPass(pass);
		weaverDao.update(currentWeaver);
	}


	public Project get(String projectName) {
		// TODO Auto-generated method stub
		return projectDao.get(projectName);
	}


	public boolean delete(Weaver weaver,Project project){
		// TODO Auto-generated method stub
		if(weaver.getId().equals(project.getCreatorName())){
			try{
				GitUtil gitUtil = new GitUtil(gitpath,project);
				gitUtil.deleteRepository();
			} catch (Exception e) {
				return false;
			}
			for(Weaver adminWeaver:project.getAdminWeavers()){
				adminWeaver.deletePass(project.getName());
				weaverDao.update(adminWeaver);
			}
			for(Weaver joinWeaver:project.getJoinWeavers()){
				joinWeaver.deletePass(project.getName());
				weaverDao.update(joinWeaver);
			}
			projectDao.delete(project);
			return true;
		}
		return false;

	}


	public boolean deleteWeaver(Project project, Weaver currentWeaver,Weaver deleteWeaver) {
		// 프로젝트에 동료를 탈퇴시킴
		if(project == null || deleteWeaver == null || deleteWeaver.getPass(project.getName()) == null){
		}

		else if(project.getCreatorName().equals(currentWeaver.getId()) ||  //관리자가 탈퇴시키거나
				currentWeaver.getId().equals(deleteWeaver.getId())){ //본인이 나가거나
			deleteWeaver.deletePass(project.getName());
			project.removeJoinWeaver(deleteWeaver);

			weaverDao.update(deleteWeaver);
			projectDao.update(project);

			return true;
		}

		return false;

	}

	public boolean push(Project project, Weaver weaver) {
		if(project == null || weaver == null)
			return false;
		Cache cache = cacheManager.getCache("push");
		Element element = cache.get(project.getName());
		if (element == null || (element != null && element.getValue() == null)) {
			project.push();
			projectDao.update(project);
			Element newElement = new Element(project.getName(), weaver.getId());
			cache.put(newElement);
			return true;
		}
		return false;
	}


	public long countProjects(List<String> tags,String search,String sort){
		return projectDao.countProjects(tags, search, sort);
	}

	public List<Project> getProjects(Weaver currentWeaver,List<String> tags,String search,String sort, int pageNumber,
			int lineNumber){
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
	
	public List<Project> getProjects(Weaver currentWeaver,int page,int size){
		List<Project> projects=  projectDao.getProjects(currentWeaver.getPassJoinNames(), page, size);
		for(Project project:projects){
			Pass pass = currentWeaver.getPass(project.getName());
			project.setJoin(pass.getPermission());
		}
		return projects;
	}

	public void uploadZip(Project project,Weaver weaver,String message,MultipartFile zip){
		if(message==null || weaver.getPass(project.getName()) == null || !zip.getOriginalFilename().toUpperCase().endsWith(".ZIP"))
			return;
		GitUtil gitUtil = new GitUtil(gitpath,project);
		try{
			gitUtil.uploadZip(weaver.getId(), weaver.getEmail(), message, zip.getInputStream());
		}catch(Exception e){
			System.err.println(e.getLocalizedMessage());
		}
	}

	public void update(Project project) {
		projectDao.update(project);
	}

	public String fork(Project originProject, Project newProject, Weaver weaver){

		if(!originProject.getCreator().getId().equals(weaver.getId())){
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
			
			GitUtil gitUtil = new GitUtil(gitpath,newProject);
			gitUtil.forkRepository(originProject.getName(), newProject.getName());
			projectDao.insert(newProject);
			projectDao.update(originProject);
			
			Pass pass = new Pass(newProject.getName(), 1);
			weaver.addPass(pass);

			weaverDao.update(weaver);

			return newProject.getName();
		}
		return null;
	}
	
}
