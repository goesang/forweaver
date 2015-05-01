package com.forweaver.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.forweaver.domain.CherryPickRequest;
import com.forweaver.domain.Project;
import com.forweaver.domain.Weaver;
import com.forweaver.mongodb.dao.CherryPickRequestDao;
import com.forweaver.mongodb.dao.PostDao;
import com.forweaver.util.GitUtil;

/** 체리픽 관리 서비스
 *
 */
@Service
public class CherryPickRequestService {
	
	@Autowired 
	private CherryPickRequestDao cherryPickRequestDao;
	@Autowired 
	private PostDao postDao;
	@Autowired 
	private GitUtil gitUtil;
	
	/** 프로젝트를 통해서 체리픽 가져오기
	 * @param orginalProject
	 * @return
	 */
	public List<CherryPickRequest> get(Project orginalProject){
		return cherryPickRequestDao.get(orginalProject);
	}

	/** 아이디를 통해서 체리픽 가져오기
	 * @param orginalProject
	 * @return
	 */
	public CherryPickRequest get(String id){
		return cherryPickRequestDao.get(id);
	}

	/** 체리픽 요청 추가.
	 * @param orginalProject
	 * @param cherryPickProject
	 * @param weaver
	 * @param commitID
	 * @return
	 */
	public boolean add(Project orginalProject,
			Project cherryPickProject,
			Weaver weaver,
			String commitID){
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

	/** 채리픽 요청을 수락함.
	 * @param cherryPickRequest
	 * @param originalRepoBranch
	 * @param weaver
	 * @return
	 */
	public boolean accept(CherryPickRequest cherryPickRequest,String originalRepoBranch,Weaver weaver){
		gitUtil.Init(cherryPickRequest.getOrginalProject());

		if(!weaver.isAdminWeaver(cherryPickRequest.getOrginalProject().getName())) // 원본 프로젝트의 운영자만 수락 가능.
			return false;

		cherryPickRequest.setState(gitUtil.cherryPick(cherryPickRequest.getCherryPickProject().getName(), 
				cherryPickRequest.getCommitID(), 
				originalRepoBranch));

		cherryPickRequestDao.update(cherryPickRequest);
		return true;
	}

	/** 채리픽 요청 삭제.
	 * @param cherryPickRequest
	 * @param weaver
	 * @return
	 */
	public boolean delete(CherryPickRequest cherryPickRequest,Weaver weaver){
		
		if(cherryPickRequest == null || weaver == null)
			return false;
		
		if(cherryPickRequest.getRequestWeaver().getId().equals(weaver.getId()) 
				|| weaver.isAdminWeaver(cherryPickRequest.getOrginalProject().getName())){
			// 요청한 본인이거나 원본 프로젝트 운영자의 경우 삭제 가능.
			cherryPickRequestDao.delete(cherryPickRequest);
			return true;
		}
		return false;
	}
	
	



}
