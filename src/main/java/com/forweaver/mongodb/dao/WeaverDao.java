package com.forweaver.mongodb.dao;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.group;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.match;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.forweaver.domain.Weaver;
import com.mongodb.DBObject;

@Repository
public class WeaverDao {

	@Autowired
	private MongoTemplate mongoTemplate;


	public void insert(Weaver weaver) { // 회원 추가하기
		if (!mongoTemplate.collectionExists(Weaver.class)) {
			mongoTemplate.createCollection(Weaver.class);
		}       
		mongoTemplate.insert(weaver);
	}

	public Weaver get(String id) { // 회원 정보 갖고오기
		Query query = new Query(new Criteria()	.orOperator(Criteria.where("_id").is(id),
				Criteria.where("email").is(id)));
		return mongoTemplate.findOne(query,Weaver.class);
	}


	public List<Weaver> list() {
		return mongoTemplate.findAll(Weaver.class);
	}

	public void delete(Weaver weaver) {
		mongoTemplate.remove(weaver);
	}

	public List<Weaver> searchPassName(String passName) { //특정 패스의 회원들을 검색
		Query query = new Query(Criteria.where("passes").in(passName));
		return mongoTemplate.find(query, Weaver.class);
	}

	public void update(Weaver weaver) {
		Query query = new Query(Criteria.where("_id").is(weaver.getId()));
		Update update = new Update();
		update.set("password", weaver.getPassword());
		update.set("passes", weaver.getPasses());
		update.set("image", weaver.getImage());
		update.set("imgSrc", weaver.getImgSrc());
		update.set("say", weaver.getSay());
		mongoTemplate.updateFirst(query, update, Weaver.class);     
	}

	public List<DBObject> getWeaverInfosInPost(List<String> tags){
		Criteria criteria = new Criteria();
		
		if(tags!=null && tags.size()>0)
			criteria.and("tags").all(tags);
		
		AggregationOperation match = Aggregation.match(criteria);
		
		AggregationOperation group = Aggregation. group("writer").count().as("postCount").sum("push").as("push").sum("rePostCount").as("rePostCount");
		Aggregation agg = newAggregation(match, group);

		return mongoTemplate.aggregate(agg, "post", DBObject.class).getMappedResults();
	}
	
	public List<DBObject> getWeaverInfosInRePost(List<String> tags){
		Criteria criteria = new Criteria();
		
		if(tags!=null && tags.size()>0)
			criteria.and("tags").all(tags);
		
		AggregationOperation match = Aggregation.match(criteria);
		
		AggregationOperation group = Aggregation. group("writer").count().as("myRePostCount").sum("push").as("rePostPush");
		Aggregation agg = newAggregation(match, group);

		return mongoTemplate.aggregate(agg, "rePost", DBObject.class).getMappedResults();
	}
	
	public List<DBObject> getWeaverInfosInProject(List<String> tags){
		Criteria criteria = new Criteria();
		
		if(tags!=null && tags.size()>0)
			criteria.and("tags").all(tags);
		
		AggregationOperation match = Aggregation.match(criteria);
		
		AggregationOperation group = Aggregation. group("creator").count().as("projectCount").push("childProjects").as("childProjects");
		Aggregation agg = newAggregation(match, group);

		return mongoTemplate.aggregate(agg, "project", DBObject.class).getMappedResults();
	}
	
	public List<DBObject> getWeaverInfosInLecture(List<String> tags){
		Criteria criteria = new Criteria();
		
		if(tags!=null && tags.size()>0)
			criteria.and("tags").all(tags);
		
		AggregationOperation match = Aggregation.match(criteria);
		
		AggregationOperation group = Aggregation. group("creator").count().as("lectureCount").push("joinWeavers").as("joinWeavers");
		Aggregation agg = newAggregation(match, group);

		return mongoTemplate.aggregate(agg, "lecture", DBObject.class).getMappedResults();
	}
	
	public List<DBObject> getWeaverInfosInCode(List<String> tags){
		Criteria criteria = new Criteria();
		
		if(tags!=null && tags.size()>0)
			criteria.and("tags").all(tags);
	
		
		AggregationOperation match = Aggregation.match(criteria);
		
		AggregationOperation group = Aggregation. group("writer").count().as("codeCount").sum("downCount").as("downCount");
		Aggregation agg = newAggregation(match, group);

		return mongoTemplate.aggregate(agg, "code", DBObject.class).getMappedResults();
	}
	
}
