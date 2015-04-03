package com.forweaver.mongodb.dao;

import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.forweaver.domain.Lecture;

/** 강의 관리를 위한 DAO
 *
 */
@Repository
public class LectureDao {
	
	@Autowired private MongoTemplate mongoTemplate;

	/** 강의 생성함.
	 * @param lecture
	 */
	public void add(Lecture lecture) {

		if (!mongoTemplate.collectionExists(Lecture.class)) {
			mongoTemplate.createCollection(Lecture.class);
		}
		mongoTemplate.insert(lecture);
	}

	/** 강의명으로 강의를 가져옴
	 * @param lectureName
	 * @return
	 */
	public Lecture get(String lectureName) {
		Query query = new Query(Criteria.where("_id").is(lectureName));
		return mongoTemplate.findOne(query, Lecture.class);
	}

	/** 강의 삭제
	 * @param lecture
	 */
	public void delete(Lecture lecture) {
		mongoTemplate.remove(lecture);
	}

	/** 강의 수정하기
	 * @param lecture
	 */
	public void update(Lecture lecture) {
		Query query = new Query(Criteria.where("_id").is(lecture.getName()));
		Update update = new Update();
		update.set("description", lecture.getDescription());
		update.set("tags", lecture.getTags());
		update.set("joinWeavers", lecture.getJoinWeavers());
		update.set("repos", lecture.getRepos());
		update.set("adminWeavers", lecture.getAdminWeavers());
		mongoTemplate.updateFirst(query, update, Lecture.class);
	}

	/** 강의 검색하고 숫자를 셈
	 * @param tags
	 * @param search
	 * @param creator
	 * @return
	 */
	public long countLectures(
			List<String> tags,
			String search) {
		Criteria criteria = new Criteria();

		if(search != null && search.length()>0)
			criteria.orOperator(new Criteria("name").regex(search),
					new Criteria("description").regex(search));

		if(tags != null)
			criteria.and("tags").all(tags);

		return mongoTemplate.count(new Query(criteria), Lecture.class);
	}

	/** 강의를 검색
	 * @param tags
	 * @param search
	 * @param creator
	 * @param page
	 * @param size
	 * @return
	 */
	public List<Lecture> getLectures(
			List<String> tags,
			String search,
			int page, 
			int size) {
		Criteria criteria = new Criteria();

		if(search != null && search.length()>0)
			criteria.orOperator(new Criteria("name").regex(search),
					new Criteria("description").regex(search));

		if(tags != null)
			criteria.and("tags").all(tags);

		Query query = new Query(criteria);
		query.with(new PageRequest(page-1, size));

		return mongoTemplate.find(query, Lecture.class);
	}
	
	/** 회원이 가입한 강의를 검색하고 숫자를 셈
	 * @param tags
	 * @param search
	 * @param creator
	 * @return
	 */
	public long countLectures(
			List<String> lecturNames,
			List<String> tags) {
		Criteria criteria = new Criteria("name").in(lecturNames);

		if(tags != null)
			criteria.and("tags").all(tags);

		return mongoTemplate.count(new Query(criteria), Lecture.class);
	}

	
	/** 회원이 가입한 강의를 가져올 때 활용.
	 * @param lecturNames
	 * @param page
	 * @param size
	 * @return
	 */
	public List<Lecture> getLectures(
			List<String> lecturNames,
			List<String> tags,
			int page, 
			int size) {
		Criteria criteria = new Criteria("name").in(lecturNames);
		
		if(tags != null)
			criteria.and("tags").all(tags);
		
		Query query = new Query(criteria);
		query.with(new PageRequest(page-1, size));

		return mongoTemplate.find(query, Lecture.class);
	}


}
