package com.forweaver.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
	@Autowired private GitUtil gitUtil;
	
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
		weaverDao.updatePass(currentWeaver);


		File file = new File(gitUtil.getGitPath() + lecture.getName());
		file.mkdir();

		try{
			gitUtil.Init(repo);
			gitUtil.createRepository();
		} catch (Exception e) {
			System.err.println("예제 저장소 생성 불가");
		}
	}
	/** 회원 추가함.
	 * @param lecture
	 * @param currentWeaver
	 * @param joinWeaver
	 */
	public boolean addWeaver(Lecture lecture,Weaver joinWeaver){
		// TODO Auto-generated method stub
		if(lecture == null || joinWeaver == null)
			return false;
		Pass pass = new Pass(lecture.getName(), 1);
		lecture.addJoinWeaver(joinWeaver); //프로젝트 목록에 추가
		joinWeaver.addPass(pass);
		weaverDao.updatePass(joinWeaver);
		this.update(lecture);
		return true;
	}
	public void addRepo(Lecture lecture,Repo repo){
		try{
			gitUtil.Init(repo);
			gitUtil.createRepository();
		} catch (Exception e) {
			return;
		}
		lecture.addRepo(repo);
		lectureDao.update(lecture);
	}

	public void removeRepo(Lecture lecture,Repo repo){
		try{
			gitUtil.Init(repo);
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

			weaverDao.updatePass(deleteWeaver);
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

			weaverDao.updatePass(deleteWeaver);
			lectureDao.update(lecture);

			return true;
		}
		return false;
	}



	/** 강의 삭제.
	 * @param weaver
	 * @param lecture
	 * @return
	 */
	public boolean delete(Weaver weaver,Lecture lecture) {
		// TODO Auto-generated method stub

		if(weaver == null || lecture == null)
			return false;

		if(weaver.isAdmin() || 
				weaver.getId().equals(lecture.getCreatorName())){
			for(Weaver adminWeaver:lecture.getAdminWeavers()){
				adminWeaver.deletePass(lecture.getName());
				weaverDao.updatePass(adminWeaver);
			}
			for(Weaver joinWeaver:lecture.getJoinWeavers()){
				joinWeaver.deletePass(lecture.getName());
				weaverDao.updatePass(joinWeaver);
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

	/** 강의를 검색하고 숫자를 셈.
	 * @param tags
	 * @param search
	 * @return
	 */
	public long countLectures(List<String> tags,String search) {
		// TODO Auto-generated method stub
		return lectureDao.countLectures(tags, search);
	}

	/** 강의를 검색함.
	 * @param currentWeaver
	 * @param tags
	 * @param search
	 * @param pageNumber
	 * @param lineNumber
	 * @return
	 */
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

	/** 회원이 가입한 강의들을 갯수를 파악함.
	 * @param weaver
	 * @param tags
	 * @param sort
	 * @return
	 */
	public long countLecturesWithWeaver(Weaver weaver,List<String> tags,String sort) {
		// TODO Auto-generated method stub
		if(sort.equals("join"))
			return lectureDao.countLectures(weaver.getJoinLectures(), tags);
		else if(sort.equals("admin"))
			return lectureDao.countLectures(weaver.getAdminLectures(),tags);
		else
			return lectureDao.countLectures(weaver.getLectures(),tags);
	}
	
	/** 회원이 가입한 강의들을 가지고 옴.
	 * @param weaver
	 * @param sort
	 * @param page
	 * @param size
	 * @return
	 */
	public List<Lecture> getLecturesWithWeaver(Weaver currentWeaver,Weaver weaver,List<String> tags,String sort,int page,int size){
		List<Lecture> lectures = new ArrayList<Lecture>();
		
		if(sort.equals("teach"))		
			lectures = lectureDao.getLectures(weaver.getAdminLectures(),tags, page, size);
		else if(sort.equals("join"))	
			lectures = lectureDao.getLectures(weaver.getJoinLectures(),tags, page, size);
		else
			lectures = lectureDao.getLectures(weaver.getLectures(),tags, page, size);

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

	public void update(Lecture lecture) {
		lectureDao.update(lecture);
	}

	public void uploadZip(Lecture lecture,Repo repo,Weaver weaver,String branchName,String message,MultipartFile zip){
		if(message==null || !zip.getOriginalFilename().toUpperCase().endsWith(".ZIP"))
			return;
		gitUtil.Init(repo);
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
