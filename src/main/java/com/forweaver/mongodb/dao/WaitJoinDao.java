package com.forweaver.mongodb.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.forweaver.domain.WaitJoin;

@Repository
public class WaitJoinDao {

	@Autowired private MongoTemplate mongoTemplate;
	
	public void add(WaitJoin waitJoin) { // 초대 추가하기
		if (!mongoTemplate.collectionExists(WaitJoin.class)) {
			mongoTemplate.createCollection(WaitJoin.class);
		}
		mongoTemplate.insert(waitJoin);
	}

	public WaitJoin get(String joinTeam,String waitingWeaver) { // 초대 가져오기
		Query query = new Query(Criteria.where("joinTeam").is(joinTeam).and("waitingWeaver").is(waitingWeaver));
		return mongoTemplate.findOne(query, WaitJoin.class);
	}
	
	public void delete(WaitJoin waitJoin) { // 초대 삭제하기
		Query query = new Query(Criteria.where("joinTeam").is(waitJoin.getJoinTeam()).and("waitingWeaver").is(waitJoin.getWaitingWeaver()));
		mongoTemplate.remove(query, WaitJoin.class);
	}
	
	public List<WaitJoin> delete(String joinTeam) { // 팀 이름으로 삭제.
		Query query = new Query(Criteria.where("joinTeam").is(joinTeam));
		List<WaitJoin> waitJoins = mongoTemplate.find(query, WaitJoin.class);
		mongoTemplate.remove(query, WaitJoin.class);
		return waitJoins;
	}
}
