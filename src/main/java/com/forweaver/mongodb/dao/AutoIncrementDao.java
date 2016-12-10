package com.forweaver.mongodb.dao;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.forweaver.domain.AutoIncrement;

/** 넘버링 관리를 위한 DAO
 *
 */
@Repository
public class AutoIncrementDao {

	@Autowired private MongoTemplate mongoTemplate;

	/** 넘버링을 하나 올리고 추가함.
	 * @param autoIncrement
	 */
	public int insert(AutoIncrement autoIncrement) {
		mongoTemplate.save(autoIncrement);
		return autoIncrement.getCount();
	}

	/** 넘버링 가져오기
	 * @param countName
	 * @return
	 */
	public AutoIncrement get(String countName) {
		Query query = new Query(Criteria.where("_id").is(countName));
		return mongoTemplate.findOne(query, AutoIncrement.class);
	}
	
	/** 자료 삭제하기
	 * @param autoIncrement
	 */
	public void delete(AutoIncrement autoIncrement) {
		if(autoIncrement == null)
			return;
		mongoTemplate.remove(autoIncrement);
	}

}

