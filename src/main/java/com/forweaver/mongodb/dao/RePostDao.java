package com.forweaver.mongodb.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.forweaver.domain.RePost;

@Repository
public class RePostDao {
	@Autowired
	private MongoTemplate mongoTemplate;

	public void insert(RePost rePost) { // 글 추가하기
		if (!mongoTemplate.collectionExists(RePost.class)) {
			mongoTemplate.createCollection(RePost.class);
			rePost.setRePostID(1);
			mongoTemplate.insert(rePost);
			return;
		}
		RePost lastRePost = getLast();
		rePost.setRePostID(lastRePost.getRePostID() + 1);
		mongoTemplate.insert(rePost);
	}

	public List<RePost> get(int ID,int kind, String sort) { // 글 가져오기

		Criteria criteria = 
				new Criteria().and("originalPostID").is(ID).and("kind").is(kind);

		this.filter(criteria, sort);

		Query query = new Query(criteria);
		this.sorting(query, sort);
		return mongoTemplate.find(query, RePost.class);
	}

	public RePost get(int rePostID) { // 글 가져오기
		Query query = new Query(Criteria.where("_id").is(rePostID));
		return mongoTemplate.findOne(query, RePost.class);
	}

	public void delete(RePost rePost) {
		mongoTemplate.remove(rePost);
	}

	public void deleteAll(int originalPostID) {
		Query query = new Query(Criteria.where("originalPostID").is(
				originalPostID));
		mongoTemplate.remove(query, RePost.class);
	}

	public void update(RePost rePost) {
		Query query = new Query(Criteria.where("rePostID").is(
				rePost.getRePostID()));
		Update update = new Update();
		update.set("content", rePost.getContent());
		update.set("push", rePost.getPush());
		update.set("replys", rePost.getReplys());
		update.set("replysCount", rePost.getReplysCount());
		update.set("recentReplyDate", rePost.getRecentReplyDate());
		mongoTemplate.updateFirst(query, update, RePost.class);
	}

	public RePost getLast() {
		Query query = new Query().with(new Sort(Sort.Direction.DESC, "_id"));
		return mongoTemplate.findOne(query, RePost.class);
	}

	public void filter(Criteria criteria, String sort) {
		if (sort.equals("push-desc")) {
			criteria.and("push").gt(0);
		} else if (sort.equals("reply-many")) {
			criteria.and("replysCount").gt(0);
		} else if (sort.equals("reply-desc")) {
			criteria.and("replysCount").gt(0);
		}
	}

	public void sorting(Query query, String sort) {
		if (sort.equals("age-asc")) {
			query.with(new Sort(Sort.Direction.ASC, "_id"));
		} else if (sort.equals("push-desc")) {
			query.with(new Sort(Sort.Direction.DESC, "push"));
		} else if (sort.equals("reply-many")) {
			query.with(new Sort(Sort.Direction.DESC, "replysCount"));
		} else if (sort.equals("reply-desc")) {
			query.with(new Sort(Sort.Direction.DESC, "recentReplyDate"));
		} else
			query.with(new Sort(Sort.Direction.DESC, "_id"));
	}
}
