package com.forweaver.service;

import java.io.File;
import java.util.List;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.forweaver.domain.Lecture;
import com.forweaver.domain.Pass;
import com.forweaver.domain.Project;
import com.forweaver.domain.Repo;
import com.forweaver.domain.Weaver;
import com.forweaver.mongodb.dao.LectureDao;
import com.forweaver.mongodb.dao.ProjectDao;
import com.forweaver.mongodb.dao.WeaverDao;
import com.forweaver.util.GitUtil;

@Service
public class LectureService {
	@Autowired
	private LectureDao lectureDao;
	@Autowired
	private ProjectDao projectDao;
	@Autowired
	private WeaverDao weaverDao;
	@Autowired
	private CacheManager cacheManager;

	public void add(Lecture lecture,Weaver currentWeaver) {
		
		if(weaverDao.get(lecture.getName()) != null || lectureDao.get(lecture.getName()) != null)
			return; // 중복 검사
		
		Repo repo = new Repo("example", 0, "강의 자료를 올릴 수 있는 저장소입니다.",0,lecture,currentWeaver);
		lecture.addRepo(repo);
		lectureDao.add(lecture);
		
		Pass pass = new Pass(lecture.getName(), 1); // 강의의 생성자 권한을 부여
		currentWeaver.addPass(pass);
		weaverDao.update(currentWeaver);
		
		
		File file = new File(GitUtil.GitPath + lecture.getName());
		file.mkdir();
		
		try{
			GitUtil gitUtil = new GitUtil(repo);
			gitUtil.createRepository();
		} catch (Exception e) {
			System.err.println("예제 저장소 생성 불가");
		}
		
		Cache cache = cacheManager.getCache("lecture");
		Element newElement = new Element(lecture.getName(), lecture);
		cache.put(newElement);
	}
	
	public void addRepo(Lecture lecture,Repo repo){
		try{
			GitUtil gitUtil = new GitUtil(repo);
			gitUtil.createRepository();
		} catch (Exception e) {
			return;
		}
		lecture.addRepo(repo);
		lectureDao.update(lecture);
	}
	
	public void removeRepo(Lecture lecture,Repo repo){
		try{
			GitUtil gitUtil = new GitUtil(repo);
			gitUtil.deleteRepository();
		} catch (Exception e) {
			return;
		}
		lecture.removeRepo(repo);
		lectureDao.update(lecture);
	}
	

	public Lecture get(String name) {
		// TODO Auto-generated method stub
		Cache cache = cacheManager.getCache("lecture");
		Element element = cache.get(name);
		if (element == null || (element != null && element.getValue() == null)) {
			Lecture lecture = lectureDao.get(name);
			Element newElement = new Element(name, lecture);
			cache.put(newElement);
			return lecture;
		}
		return (Lecture) element.getValue();
	}

	public boolean deleteWeaver(Lecture lecture, Weaver currentWeaver,
			Weaver deleteWeaver) { // 강의중인 학생을 탈퇴시킴
		if (lecture == null || deleteWeaver == null
				|| deleteWeaver.getPass(lecture.getName()) == null) {
		}
		else if (lecture.getCreatorName().equals(currentWeaver.getId()) || // 관리자가 탈퇴시키거나
				currentWeaver.getId().equals(deleteWeaver.getId())) { // 본인이 나가거나
			
			deleteWeaver.deletePass(lecture.getName());
			lecture.removeJoinWeaver(deleteWeaver);
			
			weaverDao.update(deleteWeaver);
			lectureDao.update(lecture);
			
			return true;
		}
		return false;
	}
	
	
	public boolean addWeaver(Lecture lecture, Weaver currentWeaver,
			Weaver deleteWeaver) { // 강의중인 학생을 탈퇴시킴
		if (lecture == null || deleteWeaver == null
				|| deleteWeaver.getPass(lecture.getName()) == null) {
		}
		else if (lecture.getCreatorName().equals(currentWeaver.getId()) || // 관리자가 탈퇴시키거나
				currentWeaver.getId().equals(deleteWeaver.getId())) { // 본인이 나가거나
			
			deleteWeaver.deletePass(lecture.getName());
			lecture.removeJoinWeaver(deleteWeaver);
			
			weaverDao.update(deleteWeaver);
			lectureDao.update(lecture);
			
			return true;
		}
		return false;
	}
	
	

	public boolean delete(Weaver weaver,Lecture lecture) {
		// TODO Auto-generated method stub
		if(weaver.getId().equals(lecture.getCreatorName())){
			lectureDao.delete(lecture);
			cacheManager.getCache("lecture").remove(lecture.getName());
			return true;
		}
		return false;
	}

	public long countLectures() {
		// TODO Auto-generated method stub
		return lectureDao.countLectures(null, null, null);
	}

	public List<Lecture> getLectures(Weaver currentWeaver,int pageNumber, int lineNumber) {
		// TODO Auto-generated method stub
		List<Lecture> lectures = lectureDao.getLectures(null, null, null, pageNumber, lineNumber);
		
		if(currentWeaver != null)
			for(Lecture lecture:lectures){
				if(currentWeaver.getPass(lecture.getName()) != null)
					lecture.setJoin(true);
			}
				
		return lectures;
	}

	public long countLecturesWithTags(List<String> tags) {
		// TODO Auto-generated method stub
		return lectureDao.countLectures(tags, null, null);
	}

	public List<Lecture> getLecturesWithTags(Weaver currentWeaver,List<String> tags, int pageNumber,
			int lineNumber) {
		// TODO Auto-generated method stub	
		List<Lecture> lectures = lectureDao.getLectures(tags, null, null, pageNumber, lineNumber);
		
		if(currentWeaver != null)
			for(Lecture lecture:lectures){
				if(currentWeaver.getPass(lecture.getName()) != null)
					lecture.setJoin(true);
			}
				
		return lectures;
	}
	
	public long countLecturesWithTagsAndSearch(List<String> tags,String search) {
		// TODO Auto-generated method stub
		return lectureDao.countLectures(tags, search, null);
	}

	public List<Lecture> getLecturesWithTagsAndSearch(Weaver currentWeaver,List<String> tags ,String search, int pageNumber,
			int lineNumber) {
		// TODO Auto-generated method stub	
		List<Lecture> lectures = lectureDao.getLectures(tags, search, null, pageNumber, lineNumber);
		
		if(currentWeaver != null)
			for(Lecture lecture:lectures){
				if(currentWeaver.getPass(lecture.getName()) != null)
					lecture.setJoin(true);
			}
				
		return lectures;
	}

	
	public void update(Lecture lecture) {
		lectureDao.update(lecture);
	}
	
	public void uploadZip(Lecture lecture,Repo repo,Weaver weaver,String message,MultipartFile zip){
		if(message==null || !zip.getOriginalFilename().toUpperCase().endsWith(".ZIP"))
			return;
		GitUtil gitUtil = new GitUtil(repo);
		List<String> beforeBranchList = gitUtil.getBranchList();
		try{
			gitUtil.uploadZip(weaver.getId(), weaver.getEmail(), message, zip.getInputStream());
		if (lecture.getCreatorName().equals(weaver.getId())) { // 강의 개설자의 경우
			if (repo.getCategory() == 1) {
				gitUtil.createStudentBranch(beforeBranchList,	lecture);
			}
			return;
		} else if (weaver.getPass(lecture.getName()) != null) { 
			// 강의 수강자의 경우
			
			if (repo.getCategory() == 0) { // 예제 저장소의 경우

				gitUtil.notWriteBranches();
				gitUtil.uploadZip(weaver.getId(), weaver.getEmail(), message, zip.getInputStream());
				gitUtil.writeBranches();
			} else{ // 숙제 저장소의 경우

				if(repo.getDDay() == -1) // 마감일이 지났을 못올림.
				{
					return;
				}
				gitUtil.hideNotUserBranches(weaver.getId());
				gitUtil.checkOutBranch(weaver.getId());
				gitUtil.uploadZip(weaver.getId(), weaver.getEmail(), message, zip.getInputStream());
				gitUtil.showBranches();
				gitUtil.checkOutMasterBranch();

			}
		} else {
			return;
		}
		}catch(Exception e){
			System.err.println(e.getLocalizedMessage());
		}
	}

	public Repo getRepo(String repoName){
		return this.get(repoName.split("/")[0]).getRepo(repoName.split("/")[1]);
	}
	
	public String fork(Lecture lecture,Repo repo, Project newProject, Weaver weaver){
		
		if(!repo.getCreator().getId().equals(weaver.getId()) &&repo.getCategory() ==2 && !repo.isJoinWeaver(weaver)){
			if(this.get(newProject.getName())!=null){
				while(true){
					int cnt=1;
					if(projectDao.get(newProject.getName()+'-'+cnt)==null){
						newProject.setName(newProject.getName()+'-'+cnt);
						break;
					}
					cnt++;
				}
			}
			
			GitUtil gitUtil = new GitUtil(newProject);
			gitUtil.forkRepository(repo.getLectureName()+"/"+repo.getName(), newProject.getName());
			projectDao.insert(newProject);
			lectureDao.update(lecture);
			

			Pass pass = new Pass(newProject.getName(), 1);
			weaver.addPass(pass);

			weaverDao.update(weaver);

			return newProject.getName();
		}
		return null;
	}
}
