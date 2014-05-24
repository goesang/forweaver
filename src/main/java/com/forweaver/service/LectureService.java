/*package com.forweaver.service;

import java.util.List;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.forweaver.dao.LectureDao;
import com.forweaver.dao.PassDao;
import com.forweaver.dao.RepoDao;
import com.forweaver.dao.TagDao;
import com.forweaver.dao.WeaverDao;
import com.forweaver.domain.Lecture;
import com.forweaver.domain.Pass;
import com.forweaver.domain.Project;
import com.forweaver.domain.Repo;
import com.forweaver.domain.Tag;
import com.forweaver.domain.Weaver;

@Service
public class LectureService {
	@Autowired
	private LectureDao lectureDao;
	@Autowired
	private PassDao passDao;
	@Autowired
	private WeaverDao weaverDao;
	@Autowired
	private RepoDao repoDao;
	@Autowired
	private TagDao tagDao;
	@Autowired
	private CacheManager cacheManager;

	public void add(Lecture lecture,Weaver currentWeaver) {
		
		int lectureID = lectureDao.add(lecture);
		
		if(lecture.getCategory() != 2)
		for (Tag tag : lecture.getTags()) {
			tagDao.add(tag.getTagName());
			tagDao.insertLAT(tagDao.get(tag.getTagName()).getTagID(),lectureID);
		}
		
		Repo repo = new Repo("example", 0, "강의 자료를 올릴 수 있는 저장소입니다.",3, lecture);
		
		repoDao.add(repo);


		Pass pass = new Pass(lecture.getCreatorName(), lecture.getName(), 1, 1); 
		// 강의의 생성자 권한을 부여
		currentWeaver.getPasses().add(pass);
		passDao.add(pass);
		
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
		if (lecture.getCreatorName().equals(currentWeaver.getNickName()) || // 관리자가 탈퇴시키거나
				currentWeaver.getNickName().equals(deleteWeaver.getNickName())) { // 본인이 나가거나
			deleteWeaver.deletePass(lecture.getName());
			for (int i = 0; i < lecture.getJoinWeavers().size(); i++) {
				if (lecture.getJoinWeavers().get(i).getNickName()
						.equals(deleteWeaver.getNickName())) {
					passDao.deleteWeaverOnLecture(lecture.getName(),
							deleteWeaver.getNickName());
					lecture.getJoinWeavers().remove(i);
					return true;
				}
			}
		}
		return false;
	}

	public void delete(Lecture lecture) {
		// TODO Auto-generated method stub

		lectureDao.delete(lecture);
		tagDao.deleteAllLAT(lecture.getLectureID());
		passDao.deleteLecturePass(lecture.getName());
		cacheManager.getCache("lecture").remove(lecture.getName());
	}

	public int countLectures() {
		// TODO Auto-generated method stub
		return lectureDao.countLectures();
	}

	public List<Lecture> getLectures(int pageNumber, int lineNumber) {
		// TODO Auto-generated method stub
		return lectureDao.getLectures(pageNumber, lineNumber);
	}

	public int countLecturesWithTags(List<Tag> tags) {
		// TODO Auto-generated method stub
		return lectureDao.countLecturesWithTags(tags);
	}

	public List<Lecture> getLecturesWithTags(List<Tag> tags, int pageNumber,
			int lineNumber) {
		// TODO Auto-generated method stub
		return lectureDao.getLecturesWithTags(tags, pageNumber, lineNumber);
	}

	public List<Lecture> checkJoinLecture(List<Lecture> lectures,
			Weaver currentWeaver) {
		if (currentWeaver == null)
			return lectures;
		for (Lecture lecture : lectures) {
			for (Pass pass : currentWeaver.getPasses()) {
				if (lecture.getName().equals(pass.getJoinName())) {
					lecture.setTmpPermission(pass.getPermission() + 1);
				}
			}
		}
		return lectures;
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
			Element newElement = new Element(lecture.getName(), weaver.getNickName());
			cache.put(newElement);
			return true;
		}
		return false;
	}
}
*/