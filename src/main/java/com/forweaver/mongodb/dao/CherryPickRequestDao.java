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

@Repository
public class CherryPickRequestDao {

	@Autowired private MongoTemplate mongoTemplate;
	
	public void add(CherryPickRequest cherryPickRequest) { // 체리픽 요청 추가
		if (!mongoTemplate.collectionExists(CherryPickRequest.class)) {
			mongoTemplate.createCollection(CherryPickRequest.class);
		}
		mongoTemplate.insert(cherryPickRequest);
	}

	public List<CherryPickRequest> get(Project orginalProject) { // 체리픽 요청 가져오기
		Query query = new Query(Criteria.where("orginalProject").is(orginalProject));
		return mongoTemplate.find(query, CherryPickRequest.class);
	}
	
	public CherryPickRequest get(String id) {// 체리픽 요청 가져오기
		Query query = new Query(	Criteria.where("_id").is(id));
		return mongoTemplate.findOne(query, CherryPickRequest.class);
	}
	
	public void update(CherryPickRequest cherryPickRequest) { // 체리픽 요청 수정
		Query query = new Query(	Criteria.where("_id").is(cherryPickRequest.getId()));
		Update update = new Update();
		update.set("state", cherryPickRequest.getState());
		mongoTemplate.updateFirst(query, update, CherryPickRequest.class);
	}
	
	public void delete(CherryPickRequest cherryPickRequest) { // 체리픽 요청 삭제
		mongoTemplate.remove(cherryPickRequest);
	}
}
