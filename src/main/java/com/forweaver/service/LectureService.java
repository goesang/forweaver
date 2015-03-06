package com.forweaver.service;

import java.io.File;
import java.util.List;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.forweaver.domain.Lecture;
import com.forweaver.domain.Pass;
import com.forweaver.domain.Repo;
import com.forweaver.domain.WaitJoin;
import com.forweaver.domain.Weaver;
import com.forweaver.mongodb.dao.LectureDao;
import com.forweaver.mongodb.dao.PostDao;
import com.forweaver.mongodb.dao.ProjectDao;
import com.forweaver.mongodb.dao.WaitJoinDao;
import com.forweaver.mongodb.dao.WeaverDao;
import com.forweaver.util.GitUtil;

@Service
public class LectureService {
	
	@Autowired private LectureDao lectureDao;
	@Autowired private ProjectDao projectDao;
	@Autowired private WeaverDao weaverDao;
	@Autowired private WaitJoinDao waitJoinDao;
	@Autowired private PostDao postDao;
	
	@Value("${gitpath}")
	private String gitpath;
	
	public void add(Lecture lecture,Weaver currentWeaver) {
		
		if(lecture.getName().toUpperCase().equals("ROLE_ADMIN")||
			lecture.getName().toUpperCase().equals("ROLE_USER")||	
				weaverDao.get(lecture.getName()) != null || 
				lectureDao.get(lecture.getName()) != null)
			return; // 중복 검사
		
		Repo repo = new Repo("example", 0, "강의 자료를 올릴 수 있는 저장소입니다.",0,lecture,currentWeaver);
		lecture.addRepo(repo);
		lectureDao.add(lecture);
		
		Pass pass = new Pass(lecture.getName(), 2); // 강의의 생성자 권한을 부여
		currentWeaver.addPass(pass);
		weaverDao.update(currentWeaver);
		
		
		File file = new File(gitpath + lecture.getName());
		file.mkdir();
		
		try{
			GitUtil gitUtil = new GitUtil(gitpath,repo);
			gitUtil.createRepository();
		} catch (Exception e) {
			System.err.println("예제 저장소 생성 불가");
		}
	}
	
	public void addRepo(Lecture lecture,Repo repo){
		try{
			GitUtil gitUtil = new GitUtil(gitpath,repo);
			gitUtil.createRepository();
		} catch (Exception e) {
			return;
		}
		lecture.addRepo(repo);
		lectureDao.update(lecture);
	}
	
	public void removeRepo(Lecture lecture,Repo repo){
		try{
			GitUtil gitUtil = new GitUtil(gitpath,repo);
			gitUtil.deleteRepository();
		} catch (Exception e) {
			return;
		}
		lecture.removeRepo(repo);
		lectureDao.update(lecture);
	}
	

	public Lecture get(String name) {
		// TODO Auto-generated method stub
		return lectureDao.get(name);
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
		
		if(weaver == null || lecture == null)
			return false;
		
		if(weaver.isAdmin() || 
				weaver.getId().equals(lecture.getCreatorName())){
			for(Weaver adminWeaver:lecture.getAdminWeavers()){
				adminWeaver.deletePass(lecture.getName());
				weaverDao.update(adminWeaver);
			}
			for(Weaver joinWeaver:lecture.getJoinWeavers()){
				joinWeaver.deletePass(lecture.getName());
				weaverDao.update(joinWeaver);
			}
			for(WaitJoin waitJoin:waitJoinDao.delete(lecture.getName())){ // 대기 중인 초대장 삭제.
				postDao.delete(postDao.get(waitJoin.getPostID())); //처음 보냈던 메세지 삭제.
				return true;
			}			
			lectureDao.delete(lecture);
			return true;
		}
		return false;
	}
	
	public long countLectures(List<String> tags,String search) {
		// TODO Auto-generated method stub
		return lectureDao.countLectures(tags, search);
	}

	public List<Lecture> getLectures(Weaver currentWeaver,List<String> tags ,String search, int pageNumber,
			int lineNumber) {
		// TODO Auto-generated method stub	
		List<Lecture> lectures = lectureDao.getLectures(tags, search, pageNumber, lineNumber);
		
		if(currentWeaver != null)
			for(Lecture lecture:lectures){
				Pass pass = currentWeaver.getPass(lecture.getName());
				if(pass != null)
					lecture.setJoin(pass.getPermission());
				else
					lecture.setJoin(0);
			}
				
		return lectures;
	}

	public List<Lecture> getLectures(Weaver currentWeaver,int page,int size){
		List<Lecture> lectures=  lectureDao.getLectures(currentWeaver.getPassJoinNames(), page, size);
		for(Lecture Lecture:lectures){
			Pass pass = currentWeaver.getPass(Lecture.getName());
			Lecture.setJoin(pass.getPermission());
		}
		return lectures;
	}
	
	public void update(Lecture lecture) {
		lectureDao.update(lecture);
	}
	
	public void uploadZip(Lecture lecture,Repo repo,Weaver weaver,String branchName,String message,MultipartFile zip){
		if(message==null || !zip.getOriginalFilename().toUpperCase().endsWith(".ZIP"))
			return;
		GitUtil gitUtil = new GitUtil(gitpath,repo);
		List<String> beforeBranchList = gitUtil.getBranchList();
		try{
			gitUtil.uploadZip(weaver.getId(), weaver.getEmail(),branchName, message, zip.getInputStream());
		if (lecture.getCreatorName().equals(weaver.getId())) { // 강의 개설자의 경우
			if (repo.getCategory() == 1) {
				gitUtil.createStudentBranch(beforeBranchList,	lecture);
			}
			return;
		} else if (weaver.getPass(lecture.getName()) != null) { 
			// 강의 수강자의 경우
			
			if (repo.getCategory() == 0) { // 예제 저장소의 경우

				gitUtil.notWriteBranches();
				gitUtil.uploadZip(weaver.getId(), weaver.getEmail(),branchName, message, zip.getInputStream());
				gitUtil.writeBranches();
			} else{ // 숙제 저장소의 경우

				if(repo.getDDay() == -1) // 마감일이 지났을 못올림.
				{
					return;
				}
				gitUtil.hideNotUserBranches(weaver.getId());
				gitUtil.checkOutBranch(weaver.getId());
				gitUtil.uploadZip(weaver.getId(), weaver.getEmail(),branchName, message, zip.getInputStream());
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

	public Repo getRepo(String lectureAndRepoName){
		return this.get(lectureAndRepoName.split("/")[0]).getRepo(lectureAndRepoName.split("/")[1]);
	}
	
}
