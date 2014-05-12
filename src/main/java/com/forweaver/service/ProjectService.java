package com.forweaver.service;
/*
import java.util.List;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import com.forweaver.dao.PassDao;
import com.forweaver.dao.ProjectDao;
import com.forweaver.dao.TagDao;
import com.forweaver.domain.Pass;
import com.forweaver.domain.Post;
import com.forweaver.domain.Project;
import com.forweaver.domain.Tag;
import com.forweaver.domain.Weaver;


@Service
public class ProjectService{

	@Autowired
	private ProjectDao projectDao;
	@Autowired
	private PassDao passDao;
	@Autowired
	private TagDao tagDao;
	@Autowired
	private CacheManager cacheManager;
	
	
	public void add(Project project,Weaver currentWeaver){
		// TODO Auto-generated method stub
		Cache cache = cacheManager.getCache("project");
		Element newElement = new Element(project.getName(), project);
		cache.put(newElement);
		int projectID = projectDao.add(project);
		if(project.getCategory() != 2)
			for(Tag tag : project.getTags()){
				tagDao.add(tag.getTagName());
				tagDao.insertProAT(tagDao.get(tag.getTagName()).getTagID(), projectID);
			}
		Pass pass = new Pass(project.getCreatorName(),project.getName(),1);
		currentWeaver.getPasses().add(pass);
		passDao.add(pass);
	}
	
	
	public Project get(String projectName) {
		// TODO Auto-generated method stub
		 Cache cache = cacheManager.getCache("project");
		 Element element = cache.get(projectName);
		 if(element == null){
			 Project project = projectDao.get(projectName);
			 Element newElement = new Element(projectName, project);
			 cache.put(newElement);
			 return project;
		 }
	  return (Project)element.getValue();
	}
	
	
	public void delete(Project project){
			// TODO Auto-generated method stub
			
		projectDao.delete(project);
		tagDao.deleteAllProAT(project.getProjectID());
		passDao.deleteProjectPass(project.getName());
		cacheManager.getCache("project").remove(project.getName());
	}
	
	
	public boolean deleteWeaver(Project project, Weaver currentWeaver,Weaver deleteWeaver) {
		// 프로젝트에 동료를 탈퇴시킴
		if(project == null || deleteWeaver == null || deleteWeaver.getPass(project.getName()) == null){
		}
		if(project.getCreatorName().equals(currentWeaver.getName()) ||  //관리자가 탈퇴시키거나
				currentWeaver.getName().equals(deleteWeaver.getName())){ //본인이 나가거나
			deleteWeaver.deletePass(project.getName());
			for(int i=0;i<project.getJoinWeavers().size();i++){
				if(project.getJoinWeavers().get(i).getName().equals(deleteWeaver.getName())){
					passDao.deleteWeaverOnLecture(project.getName(), deleteWeaver.getName());
					project.getJoinWeavers().remove(i);
					return true;
				}
			}
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
		if (element == null) {
			project.push();
			projectDao.update(project);
			Element newElement = new Element(project.getName(), weaver.getName());
			cache.put(newElement);
			return true;
		}
		return false;
	}
	
	public List<Project> checkJoinProject(List<Project> projects,Weaver currentWeaver){
		if(currentWeaver == null)
			return projects;
		for(Project project:projects){
			for(Pass pass:currentWeaver.getPasses()){
				if(project.getName().equals(pass.getJoinName())){
					project.setTmpPermission(pass.getPermission()+1);
				}
			}
		}
		return projects;
	}
	
	public int countProjects(){
		return projectDao.countProjects();
	}
	
	public List<Project> getProjects(int pageNumber,int lineNumber){
		return projectDao.getProjects(pageNumber, lineNumber);
	}
	
	public int countProjectsWithTags(List<Tag> tags){
		return projectDao.countProjectsWithTags(tags);
	}
	
	public List<Project> getProjectsWithTags(List<Tag> tags,int pageNumber,int lineNumber){
		return projectDao.getProjectsWithTags(tags, pageNumber, lineNumber);
	}
	
}*/
