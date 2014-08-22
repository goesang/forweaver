package com.forweaver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.forweaver.domain.Lecture;
import com.forweaver.domain.Project;
import com.forweaver.domain.WaitJoin;
import com.forweaver.domain.Weaver;
import com.forweaver.mongodb.dao.PostDao;
import com.forweaver.mongodb.dao.WaitJoinDao;

@Service
public class WaitJoinService {
	
	@Autowired private WaitJoinDao waitJoinDao;
	@Autowired private PostDao postDao;
	
	public boolean isCreateLectureWaitJoin(Lecture lecture,Weaver waitingWeaver,Weaver proposer) {

		if(	lecture == null || waitingWeaver == null ||
				waitJoinDao.get(lecture.getName(),waitingWeaver.getId()) != null){
		}
		else if(lecture.getCreatorName().equals(proposer.getId())|| // 강의 생성자이거나
				proposer.getId().equals(waitingWeaver.getId())) // 아니면 본인이 신청해야함
		{
			return true;
		}
		return false;
	}
	
	public boolean isCreateProjectWaitJoin(Project project,Weaver waitingWeaver,Weaver proposer) {
		if(		project == null || waitingWeaver == null ||
				waitJoinDao.get(project.getName(),waitingWeaver.getId()) != null){
			
		}
		else if(project.getCreatorName().equals(proposer.getId())|| // 강의 생성자이거나
				proposer.getId().equals(waitingWeaver.getId())) // 아니면 본인이 신청해야함
		{
			return true;
		}
		return false;
	}
	
	public void createWaitJoin(String joinTeam,String proposer,String waitingWaver,int postID){
		waitJoinDao.add(new WaitJoin(joinTeam, proposer, waitingWaver,postID));
	}
	
	
	public boolean deleteProjectWaitJoin(WaitJoin waitJoin,Project project,Weaver currentWeaver){
		if(waitJoin == null || project == null){
		}
		else if(project.getCreatorName().equals(currentWeaver.getId()) || //현재 위버가 프로젝트 관리자이거나
				waitJoin.getWaitingWeaver().equals(currentWeaver.getId())){		//현재 위버가 대기중인 위버일때
				waitJoinDao.delete(waitJoin);
				postDao.delete(postDao.get(waitJoin.getPostID())); //처음 보냈던 메세지 삭제
				return true;
		}			
		return false;
	}
	
	public boolean deleteLectureWaitJoin(WaitJoin waitJoin,Lecture lecture,Weaver currentWeaver){
		if(waitJoin == null || lecture == null){
			
		}
		else if(lecture.getCreatorName().equals(currentWeaver.getId()) || 
				waitJoin.getWaitingWeaver().equals(currentWeaver.getId())){			
				waitJoinDao.delete(waitJoin);
				postDao.delete(postDao.get(waitJoin.getPostID())); //처음 보냈던 메세지 삭제
				return true;
		}			
		return false;
	}
	
	public boolean isOkJoin(WaitJoin waitJoin,String adminWeaverName,Weaver currentWeaver){
		if(waitJoin == null)
			return false;
		if(waitJoin.getProposer().equals(waitJoin.getWaitingWeaver()) &&  //제안하는 사람과 대기중인 사람이 같고 현재 관리자가 평가할때
				!waitJoin.getWaitingWeaver().equals(currentWeaver)&&
				currentWeaver.getId().equals(adminWeaverName))
			return true; //관리자가 승인하는 경우
		else if(waitJoin.getProposer().equals(adminWeaverName) && //가입자가 승인하는 경우
				waitJoin.getWaitingWeaver().equals(currentWeaver.getId()))
			return true;
		return false;
	}
	
	public WaitJoin get(String joinTeam,String waitingWeaver){
		return waitJoinDao.get(joinTeam,waitingWeaver);
	}
}
