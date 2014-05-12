package com.forweaver.mongodb.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.forweaver.domain.Weaver;

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
    
    public Weaver getWithTwitterID(long twitterID) { // 회원의 이름으로 정보 갖고오기
    	Query query = new Query(Criteria.where("twitterID").is(twitterID));
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
        mongoTemplate.insert(weaver);      
    }
}
