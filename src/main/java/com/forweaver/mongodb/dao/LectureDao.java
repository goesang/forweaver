package com.forweaver.mongodb.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.forweaver.domain.Lecture;

@Repository
public class LectureDao {
	@Autowired
	private MongoTemplate mongoTemplate;

	public void add(Lecture lecture) { // 강의 생성함.

		if (!mongoTemplate.collectionExists(Lecture.class)) {
			mongoTemplate.createCollection(Lecture.class);
		}
		mongoTemplate.insert(lecture);
	}

	public Lecture get(String lectureName) { // 강의명으로 강의를 가져옴
		Query query = new Query(Criteria.where("_id").is(lectureName));
		return mongoTemplate.findOne(query, Lecture.class);
	}

	public void delete(Lecture lecture) { // 강의 삭제
		mongoTemplate.remove(lecture);
	}

	public void update(Lecture lecture) { // 강의 수정하기
		Query query = new Query(Criteria.where("_id").is(lecture.getName()));
		Update update = new Update();
		update.set("description", lecture.getDescription());
		update.set("tags", lecture.getTags());
		update.set("joinWeavers", lecture.getJoinWeavers());
		update.set("repos", lecture.getRepos());
		update.set("adminWeavers", lecture.getAdminWeavers());
		mongoTemplate.updateFirst(query, update, Lecture.class);
	}

	public long countLectures( // 강의 검색하고 숫자를 셈
			List<String> tags,
			String search,
			String creatorName) {
		Criteria criteria = new Criteria();

		if(search != null)
			criteria.orOperator(new Criteria("name").regex(search),
					new Criteria("description").regex(search));

		if(tags != null)
			criteria.and("tags").all(tags);

		if(creatorName != null)
			criteria.and("writerName").is(creatorName);

		return mongoTemplate.count(new Query(criteria), Lecture.class);
	}

	public List<Lecture> getLectures( // 강의를 검색
			List<String> tags,
			String search,
			String creatorName,
			int page, 
			int size) {
		Criteria criteria = new Criteria();

		if(search != null)
			criteria.orOperator(new Criteria("name").regex(search),
					new Criteria("description").regex(search));

		if(tags != null)
			criteria.and("tags").all(tags);
		if(creatorName != null)
			criteria.and("creatorName").is(creatorName);

		Query query = new Query(criteria);
		query.with(new PageRequest(page-1, size));

		return mongoTemplate.find(query, Lecture.class);
	}


}
