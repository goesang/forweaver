package com.forweaver.service;

import java.io.File;
import java.util.List;

import javax.servlet.jsp.tagext.Tag;

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
		
		lecture.addAdminWeaver(currentWeaver);
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

	public List<Lecture> getLectures(String sort,int pageNumber, int lineNumber) {
		// TODO Auto-generated method stub
		return lectureDao.getLectures(null, null, null, sort, pageNumber, lineNumber);
	}

	public long countLecturesWithTags(List<String> tags,String sort) {
		// TODO Auto-generated method stub
		return lectureDao.countLectures(tags, null, null, sort);
	}

	public List<Lecture> getLecturesWithTags(List<String> tags,String sort, int pageNumber,
			int lineNumber) {
		// TODO Auto-generated method stub
		return lectureDao.getLectures(tags, null, null, sort, pageNumber, lineNumber);
	}

	public List<Lecture> checkJoinLecture(List<Lecture> lectures,
			Weaver currentWeaver) {
		return null;
	}

	public boolean push(Lecture lecture, Weaver weaver) {
		if(lecture == null || weaver == null)
			return false;
		
		cacheManager.getCache("lecture").remove(lecture.getName());
		Cache cache = cacheManager.getCache("push");
		Element element = cache.get(lecture.getName());
		if (element == null) {
			lecture.push();
			lectureDao.update(lecture);
			Element newElement = new Element(lecture.getName(), weaver.getId());
			cache.put(newElement);
			return true;
		}
		return false;
	}
}
