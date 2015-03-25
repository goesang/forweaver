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

	public boolean isCreateWaitJoin(Lecture lecture,Weaver waitingWeaver,Weaver proposer) {

		if(	lecture == null || waitingWeaver == null ||
				waitJoinDao.get(lecture.getName(),waitingWeaver.getId()) != null)
			return false;
		
		if(proposer.isAdmin(lecture.getName())|| // 강의 관리자이거나
				proposer.getId().equals(waitingWeaver.getId())|| // 아니면 본인이 신청해야함
				waitingWeaver.getPass(lecture.getName()) != null) // 그리고 가입자가 아니어야함.
			return true;
		
		return false;
	}

	public boolean isCreateWaitJoin(Project project,Weaver waitingWeaver,Weaver proposer) {
		if(		project == null || waitingWeaver == null ||
				waitJoinDao.get(project.getName(),waitingWeaver.getId()) != null)
			return false;
		
		if(proposer.isAdmin(project.getName())|| // 강의 관리자이거나
				proposer.getId().equals(waitingWeaver.getId())|| // 아니면 본인이 신청해야함
				waitingWeaver.getPass(project.getName()) != null) // 그리고 가입자가 아니어야함.
			return true;
		
		 return false;
	}

	public void createWaitJoin(String joinTeam,String proposer,String waitingWaver,int postID){
		waitJoinDao.add(new WaitJoin(joinTeam, proposer, waitingWaver,postID));
	}


	public boolean deleteWaitJoin(WaitJoin waitJoin,Project project,Weaver currentWeaver){
		if(waitJoin == null || project == null)
			return false;
		
		if(project.getCreatorName().equals(currentWeaver.getId()) || //현재 위버가 프로젝트 관리자이거나
				waitJoin.getWaitingWeaver().equals(currentWeaver.getId())){		//현재 위버가 대기중인 위버일때
			waitJoinDao.delete(waitJoin);			
			return true;
		}			
		
		return false;
	}

	public boolean deleteWaitJoin(WaitJoin waitJoin,Lecture lecture,Weaver currentWeaver){
		if(waitJoin == null || lecture == null)
			return false;
		
		if(currentWeaver.isAdmin(lecture.getName()) || 
				waitJoin.getWaitingWeaver().equals(currentWeaver.getId())){			
			waitJoinDao.delete(waitJoin);
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

		if(waitJoin.getProposer().equals(adminWeaverName) && //가입자가 승인하는 경우
				waitJoin.getWaitingWeaver().equals(currentWeaver.getId()))
			return true;
		
		return false;
	}


	public WaitJoin get(String joinTeam,String waitingWeaver){
		return waitJoinDao.get(joinTeam,waitingWeaver);
	}
}
