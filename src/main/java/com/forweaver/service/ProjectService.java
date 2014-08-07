package com.forweaver.service;

import java.util.List;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import org.springframework.beans.factory.annotation.Autowired;
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

	@Autowired
	private WeaverDao weaverDao;
	@Autowired
	private ProjectDao projectDao;
	@Autowired
	private CacheManager cacheManager;


	public void add(Project project,Weaver currentWeaver){
		// TODO Auto-generated method stub

		try{
			GitUtil gitUtil = new GitUtil(project);
			gitUtil.createRepository();
		} catch (Exception e) {
			return;
		}
		Cache cache = cacheManager.getCache("project");
		Element newElement = new Element(project.getName(), project);
		cache.put(newElement);
		projectDao.insert(project);
		Pass pass = new Pass(project.getName(),1);
		currentWeaver.addPass(pass);
		weaverDao.update(currentWeaver);
	}


	public Project get(String projectName) {
		// TODO Auto-generated method stub
		Cache cache = cacheManager.getCache("project");
		Element element = cache.get(projectName);
		if(element == null || (element != null && element.getValue() == null)){
			Project project = projectDao.get(projectName);
			Element newElement = new Element(projectName, project);
			cache.put(newElement);
			return project;
		}
		return (Project)element.getValue();
	}


	public boolean delete(Weaver weaver,Project project){
		// TODO Auto-generated method stub
		if(weaver.getId().equals(project.getCreatorName())){
			try{
				GitUtil gitUtil = new GitUtil(project);
				gitUtil.deleteRepository();
			} catch (Exception e) {
				return false;
			}
			projectDao.delete(project);
			cacheManager.getCache("project").remove(project.getName());
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
		project.push();
		cacheManager.getCache("project").remove(project.getName());
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


	public long countProjects(String sort){
		return projectDao.countProjects(null, null, null, sort);
	}

	public List<Project> getProjects(Weaver currentWeaver,String sort,int pageNumber,int lineNumber){
		List<Project> projects= projectDao.getProjects(null, null, null, sort,pageNumber, lineNumber);
		if(currentWeaver != null)
			for(Project project:projects){
				if(currentWeaver.getPass(project.getName()) != null)
					project.setJoin(true);
			}
		return projects;
	}

	public long countProjectsWithTags(List<String> tags,String sort){
		return projectDao.countProjects(tags, null, null, sort);
	}

	public List<Project> getProjectsWithTags(Weaver currentWeaver,List<String> tags,String sort, int pageNumber,
			int lineNumber){
		List<Project> projects=  projectDao.getProjects(tags, null, null, sort, pageNumber, lineNumber);
		if(currentWeaver != null)
			for(Project project:projects){
				if(currentWeaver.getPass(project.getName()) != null)
					project.setJoin(true);
			}
		return projects;
	}

	public long countProjectsWithTagsAndSearch(List<String> tags,String search,String sort){
		return projectDao.countProjects(tags, search, null, sort);
	}

	public List<Project> getProjectsWithTagsAndSearch(Weaver currentWeaver,List<String> tags,String search,String sort, int pageNumber,
			int lineNumber){
		List<Project> projects=  projectDao.getProjects(tags, search, null, sort, pageNumber, lineNumber);
		if(currentWeaver != null)
			for(Project project:projects){
				if(currentWeaver.getPass(project.getName()) != null)
					project.setJoin(true);
			}
		return projects;
	}

	public void uploadZip(Project project,Weaver weaver,String message,MultipartFile zip){
		if(message==null || weaver.getPass(project.getName()) == null || !zip.getOriginalFilename().toUpperCase().endsWith(".ZIP"))
			return;
		GitUtil gitUtil = new GitUtil(project);
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
			
			GitUtil gitUtil = new GitUtil(newProject);
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
