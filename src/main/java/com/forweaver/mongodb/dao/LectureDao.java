package com.forweaver.mongodb.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.forweaver.domain.Lecture;

public class LectureDao {
	@Autowired
	private MongoTemplate mongoTemplate;
	
	public void insert(Lecture lecture) { // 강의 생성함.
		
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
		update.set("category", lecture.getCategory());
		update.set("description", lecture.getDescription());
		update.set("image", lecture.getImage());
		update.set("tags", lecture.getTags());
		update.set("push", lecture.getPush());
		mongoTemplate.updateFirst(query, update, Lecture.class);
	}
	
	public long countLectures( // 로그인하지 않은 회원이 글을 셈.
			List<String> tags,
			String search,
			String writerName,
			String sort) {
		Criteria criteria = new Criteria();
		
		if(search != null)
			criteria.orOperator(new Criteria("name").regex(search),
					new Criteria("description").regex(search));
		
		if(tags != null)
			criteria.and("tags").all(tags);
		if(writerName != null)
			criteria.and("writerName").is(writerName);
			
		this.filter(criteria, sort);

		return mongoTemplate.count(new Query(criteria), Lecture.class);
	}
	
	public List<Lecture> getLectures( // 로그인하지 않은 회원이 강의를 검색
			List<String> tags,
			String search,
			String creatorName,
			String sort,
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
		
		this.filter(criteria, sort);
		
		Query query = new Query(criteria);
		query.with(new PageRequest(page-1, size));

		this.sorting(query, sort);
		return mongoTemplate.find(query, Lecture.class);
	}
	
	public void filter(Criteria criteria,String sort){
		if (sort.equals("push-many")) {
			criteria.and("push").gt(0);
		} else if (sort.equals("push-null")) {
			criteria.and("push").is(0);
		} 
	}
	
	public void sorting(Query query,String sort){
		if (sort.equals("opendate-asc")) {
			query.with(new Sort(Sort.Direction.ASC, "openingDate"));
		} else if (sort.equals("push-null")) {
			query.with(new Sort(Sort.Direction.ASC, "push"));
		} else if (sort.equals("push-many")) {
			query.with(new Sort(Sort.Direction.DESC, "push"));
		} else
			query.with(new Sort(Sort.Direction.DESC, "openingDate"));
	}
	
}
