package com.forweaver.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.forweaver.domain.CherryPickRequest;
import com.forweaver.domain.Project;
import com.forweaver.domain.Weaver;
import com.forweaver.mongodb.dao.CherryPickRequestDao;
import com.forweaver.mongodb.dao.PostDao;
import com.forweaver.util.GitUtil;

@Service
public class CherryPickRequestService {
	
	@Autowired private CherryPickRequestDao cherryPickRequestDao;
	@Autowired private PostDao postDao;

	public List<CherryPickRequest> get(Project orginalProject){
		return cherryPickRequestDao.get(orginalProject);
	}

	public CherryPickRequest get(String id){
		return cherryPickRequestDao.get(id);
	}

	public boolean add(Project orginalProject, // 체리픽 요청 추가.
			Project cherryPickProject,
			Weaver weaver,
			String commitID){
		GitUtil gitUtil = new GitUtil(cherryPickProject);

		if(orginalProject == null
				|| cherryPickProject.getCategory() != 0 
				|| orginalProject.getCategory() != 0
				|| orginalProject.getName().equals(cherryPickProject.getName()) 
				|| weaver.getPass(cherryPickProject.getName()) == null 
				/*|| gitUtil.getCommit(commitID) == null*/)
			return false;

		cherryPickRequestDao.add(new CherryPickRequest(orginalProject, cherryPickProject, commitID, weaver));
		return true;
	}

	public boolean accept(CherryPickRequest cherryPickRequest,String originalRepoBranch,Weaver weaver){ // 채리픽 요청을 수락함.
		GitUtil gitUtil = new GitUtil(cherryPickRequest.getOrginalProject());

		if(!weaver.isAdminWeaver(cherryPickRequest.getOrginalProject().getName())) // 원본 프로젝트의 운영자만 수락 가능.
			return false;

		cherryPickRequest.setState(gitUtil.cherryPick(cherryPickRequest.getCherryPickProject().getName(), 
				cherryPickRequest.getCommitID(), 
				originalRepoBranch));

		cherryPickRequestDao.update(cherryPickRequest);
		return true;
	}

	public boolean delete(CherryPickRequest cherryPickRequest,Weaver weaver){
		if(cherryPickRequest != null && (cherryPickRequest.getRequestWeaver().getId().equals(weaver.getId()) 
				|| weaver.isAdminWeaver(cherryPickRequest.getOrginalProject().getName()))){
			// 요청한 본인이거나 원본 프로젝트 운영자의 경우 삭제 가능.
			cherryPickRequestDao.delete(cherryPickRequest);
			return true;
		}
		return false;
	}



}
