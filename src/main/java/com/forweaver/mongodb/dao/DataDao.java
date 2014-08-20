package com.forweaver.mongodb.dao;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.forweaver.domain.Data;
import com.forweaver.domain.Weaver;

@Repository
public class DataDao {
	@Autowired private MongoTemplate mongoTemplate;

	public void insert(Data data) { // 글 추가하기
		if (!mongoTemplate.collectionExists(Data.class)) {
			mongoTemplate.createCollection(Data.class);
		}
		mongoTemplate.insert(data);
		return;
	}

	public Data get(String dataID) { // 자료 가져오기
		Query query = new Query(Criteria.where("_id").is(dataID));
		return mongoTemplate.findOne(query, Data.class);
	}
	

	public void delete(Data data) { // 자료 삭제하기
		mongoTemplate.remove(data);
	}
	
	public Data getLast() {
		Query query = new Query().with(new Sort(Sort.Direction.DESC, "_id"));
		return mongoTemplate.findOne(query, Data.class);
	}
}

