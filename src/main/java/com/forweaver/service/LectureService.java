package com.forweaver.service;

import java.io.File;
import java.util.List;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.forweaver.domain.Lecture;
import com.forweaver.domain.Pass;
import com.forweaver.domain.Repo;
import com.forweaver.domain.Weaver;
import com.forweaver.mongodb.dao.LectureDao;
import com.forweaver.mongodb.dao.WeaverDao;
import com.forweaver.util.GitUtil;

@Service
public class LectureService {
	@Autowired
	private LectureDao lectureDao;
	@Autowired
	private WeaverDao weaverDao;
	@Autowired
	private CacheManager cacheManager;

	public void add(Lecture lecture,Weaver currentWeaver) {
		
		if(weaverDao.get(lecture.getName()) != null || lectureDao.get(lecture.getName()) != null)
			return; // 중복 검사
		
		Repo repo = new Repo("example", 0, "강의 자료를 올릴 수 있는 저장소입니다.",0, lecture);
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
	

	public Lecture get(String name) {
		// TODO Auto-generated method stub
		Cache cache = cacheManager.getCache("lecture");
		Element element = cache.get(name);
		if (element == null) {
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

	public long countLectures(String sort) {
		// TODO Auto-generated method stub
		return lectureDao.countLectures(null, null, null, sort);
	}

	public List<Lecture> getLectures(Weaver currentWeaver,String sort,int pageNumber, int lineNumber) {
		// TODO Auto-generated method stub
		List<Lecture> lectures = lectureDao.getLectures(null, null, null, sort, pageNumber, lineNumber);
		
		if(currentWeaver != null)
			for(Lecture lecture:lectures){
				if(currentWeaver.getPass(lecture.getName()) != null)
					lecture.setJoin(true);
			}
				
		return lectures;
	}

	public long countLecturesWithTags(List<String> tags,String sort) {
		// TODO Auto-generated method stub
		return lectureDao.countLectures(tags, null, null, sort);
	}

	public List<Lecture> getLecturesWithTags(Weaver currentWeaver,List<String> tags,String sort, int pageNumber,
			int lineNumber) {
		// TODO Auto-generated method stub	
		List<Lecture> lectures = lectureDao.getLectures(tags, null, null, sort, pageNumber, lineNumber);
		
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
}
