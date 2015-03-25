package com.forweaver.mongodb.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.forweaver.domain.CherryPickRequest;
import com.forweaver.domain.Project;

/** 채리픽 관리를 위한 DAO
 *
 */
@Repository
public class CherryPickRequestDao {

	@Autowired private MongoTemplate mongoTemplate;
	
	/** 체리픽 요청 추가
	 * @param cherryPickRequest
	 */
	public void add(CherryPickRequest cherryPickRequest) {
		if (!mongoTemplate.collectionExists(CherryPickRequest.class)) {
			mongoTemplate.createCollection(CherryPickRequest.class);
		}
		mongoTemplate.insert(cherryPickRequest);
	}

	/** 프로젝트의 체리픽 요청 가져오기
	 * @param orginalProject
	 * @return
	 */
	public List<CherryPickRequest> get(Project orginalProject) {
		Query query = new Query(Criteria.where("orginalProject").is(orginalProject));
		return mongoTemplate.find(query, CherryPickRequest.class);
	}
	
	/** 체리픽 요청 가져오기
	 * @param id
	 * @return
	 */
	public CherryPickRequest get(String id) {
		Query query = new Query(	Criteria.where("_id").is(id));
		return mongoTemplate.findOne(query, CherryPickRequest.class);
	}
	
	/** 체리픽 요청 수정
	 * @param cherryPickRequest
	 */
	public void update(CherryPickRequest cherryPickRequest) {
		Query query = new Query(	Criteria.where("_id").is(cherryPickRequest.getId()));
		Update update = new Update();
		update.set("state", cherryPickRequest.getState());
		mongoTemplate.updateFirst(query, update, CherryPickRequest.class);
	}
	
	/** 체리픽 요청 삭제
	 * @param cherryPickRequest
	 */
	public void delete(CherryPickRequest cherryPickRequest) {
		mongoTemplate.remove(cherryPickRequest);
	}
	
	/** 프로젝트의 모든 체리픽 요청 삭제
	 * @param project
	 */
	public void delete(Project project) {
		Criteria criteria = new Criteria();
		Query query = new Query(	criteria.orOperator(Criteria.where("cherryPickProject").is(project),
				Criteria.where("orginalProject").is(project)));
		mongoTemplate.remove(query,CherryPickRequest.class);
	}
}
