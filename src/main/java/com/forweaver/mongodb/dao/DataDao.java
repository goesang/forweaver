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

@Repository
public class DataDao {
	@Autowired
	private MongoTemplate mongoTemplate;

	public Map<String,String> insert(List<Data> datas) { // 글 추가하기
		if (!mongoTemplate.collectionExists(Data.class)) {
			mongoTemplate.createCollection(Data.class);
		}
		HashMap<String,String> dataIDs = new HashMap<String,String>();
		
		for(Data data: datas){
			mongoTemplate.insert(data);
			dataIDs.put(data.getName(), data.getId());
		}
		
		return dataIDs;
	}

	public Data get(String dataID) { // 자료 가져오기
		Query query = new Query(Criteria.where("_id").is(dataID));
		return mongoTemplate.findOne(query, Data.class);
	}
	
	public List<Data> get(List<String> dataIDs) { // 자료 가져오기
		List<Data> datas = new ArrayList<Data>();
		for(String dataID : dataIDs){
			Query query = new Query(Criteria.where("_id").is(dataID));
			datas.add(mongoTemplate.findOne(query, Data.class));
		}
		
		return datas;
	}

	public void delete(Data data) { // 자료 삭제하기
		mongoTemplate.remove(data);
	}

	public void delete(String dataID) { // 자료 가져오기
		Query query = new Query(Criteria.where("_id").is(dataID));
		mongoTemplate.remove(query,Data.class);
	}
	
	public void delete(List<String> dataIDs) { // 자료 가져오기
		for(String dataID : dataIDs){
			Query query = new Query(Criteria.where("_id").is(dataID));
			mongoTemplate.remove(query, Data.class);
		}
	}
	
	public Data getLast() {
		Query query = new Query().with(new Sort(Sort.Direction.DESC, "_id"));
		return mongoTemplate.findOne(query, Data.class);
	}
}

