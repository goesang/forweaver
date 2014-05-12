package com.forweaver.mongodb.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.forweaver.domain.Post;

@Repository
public class PostDao {
	@Autowired
	private MongoTemplate mongoTemplate;

	public void insert(Post post) { // 글 추가하기
		if (!mongoTemplate.collectionExists(Post.class)) {
			mongoTemplate.createCollection(Post.class);
			post.setPostID(1);
			mongoTemplate.insert(post);
			return;
		}
		Post lastPost = getLast();
		post.setPostID(lastPost.getPostID() + 1);
		mongoTemplate.insert(post);
	}

	public Post get(int postID) { // 글 가져오기
		Query query = new Query(Criteria.where("_id").is(postID));
		return mongoTemplate.findOne(query, Post.class);
	}

	public long countPostsWhenNotLogin( // 로그인하지 않은 회원이 글을 셈.
			List<String> tags, String search, String writerName, String sort) {
		Criteria criteria = new Criteria();

		if (search != null)
			criteria.orOperator(new Criteria("title").regex(search),
					new Criteria("content").regex(search));

		criteria.and("kind").is(1);

		if (tags != null)
			criteria.and("tags").all(tags);
		if (writerName != null)
			criteria.and("writerName").is(writerName);

		this.filter(criteria, sort);

		return mongoTemplate.count(new Query(criteria), Post.class);
	}

	public List<Post> getPostsWhenNotLogin(
			// 로그인하지 않은 회원이 글을 검색
			List<String> tags, String search, String writerName, String sort,
			int page, int size) {
		Criteria criteria = new Criteria();

		if (search != null)
			criteria.orOperator(new Criteria("title").regex(search),
					new Criteria("content").regex(search));

		criteria.and("kind").is(1);

		if (tags != null)
			criteria.and("tags").all(tags);
		if (writerName != null)
			criteria.and("writerName").is(writerName);

		this.filter(criteria, sort);

		Query query = new Query(criteria);
		query.with(new PageRequest(page - 1, size));

		this.sorting(query, sort);
		return mongoTemplate.find(query, Post.class);
	}

	public long countPostsWithProjectTags(
			// 프로젝트 태그를 이용하여 글을 파악하고 셈
			List<String> projectTags, String search, String writerName,
			String sort) {
		Criteria criteria = new Criteria().and("kind").is(2).and("tags")
				.all(projectTags); // 일반 공개글을 불러옴;

		if (writerName != null)
			criteria.and("writerName").is(writerName);

		if (search != null)
			criteria.and("search").is(search);

		this.filter(criteria, sort);

		return mongoTemplate.count(new Query(criteria), Post.class);
	}

	public List<Post> getPostsWithProjectTags(
			// 프로젝트 태그를 이용하여 글을 검색함
			List<String> projectTags, String search, String writerName,
			String sort, int page, int size) {

		Criteria criteria = new Criteria().and("kind").is(2).and("tags")
				.all(projectTags); // 일반 공개글을 불러옴;

		if (writerName != null)
			criteria.and("writerName").is(writerName);

		if (search != null)
			criteria.and("search").is(search);

		this.filter(criteria, sort);

		Query query = new Query(criteria);
		query.with(new PageRequest(page - 1, size));

		this.sorting(query, sort);
		return mongoTemplate.find(query, Post.class);
	}

	public long countPostsWithMassageTag(
			// 메세지 태그를 이용하여 글을 검색하고 셈
			List<String> massageTags, String search, String writerName,
			boolean my, String sort) {
		Criteria criteria;

		if (my)
			criteria = new Criteria()
					.orOperator(
							Criteria.where("kind")
									.is(3)
									.andOperator(
											Criteria.where("tags").in(
													massageTags)),
							Criteria.where("kind").is(3).and("writerName")
									.is(writerName));
		else
			criteria = Criteria.where("kind").is(3)
					.andOperator(Criteria.where("tags").in(massageTags));

		this.filter(criteria, sort);
		return mongoTemplate.count(new Query(criteria), Post.class);
	}

	public List<Post> getPostsWithMassageTag(
			// 메세지 태그를 이용하여 글을 검색
			List<String> massageTags, String writerName, String search,
			boolean my, String sort, int page, int size) {

		Criteria criteria;

		if (my)
			criteria = new Criteria().orOperator(
					Criteria.where("kind").is(3).and("writerName")
							.is(writerName).and("tags").in(massageTags),
					Criteria.where("kind").is(3).and("writerName")
							.is(writerName));
		else
			criteria = Criteria.where("kind").is(3).and("writerName")
					.is(writerName).and("tags").in(massageTags);

		if (search != null)
			criteria.andOperator(new Criteria().orOperator(
					Criteria.where("title").regex(search),
					Criteria.where("content").regex(search)));

		this.filter(criteria, sort);

		Query query = new Query(criteria);
		query.with(new PageRequest(page - 1, size));
		this.sorting(query, sort);

		return mongoTemplate.find(query, Post.class);
	}

	public long countPostsWhenLogin(
			// 로그인한 회원이 글을 검색할때 숫자를 셈.
			List<String> publicTags, List<String> privateTags,
			String writerName, String search, String sort) {
		Criteria criteria = new Criteria();

		criteria.orOperator(Criteria.where("kind").is(1),
				Criteria.where("writerName").is(writerName),
				Criteria.where("tags").in(privateTags));

		if (search != null)
			criteria.andOperator(new Criteria().orOperator(
					Criteria.where("title").regex(search),
					Criteria.where("content").regex(search)));

		if (publicTags != null)
			criteria.and("tags").all(publicTags);

		this.filter(criteria, sort);

		return mongoTemplate.count(new Query(criteria), Post.class);
	}

	public List<Post> getPostsWhenLogin(
			// 로그인한 회원이 글을 검색함.
			List<String> publicTags, List<String> privateTags,
			String writerName, String search, String sort, int page, int size) {

		Criteria criteria = new Criteria();

		criteria.orOperator(Criteria.where("kind").is(1),
				Criteria.where("writerName").is(writerName),
				Criteria.where("tags").in(privateTags));

		if (search != null)
			criteria.andOperator(new Criteria().orOperator(
					Criteria.where("title").regex(search),
					Criteria.where("content").regex(search)));

		if (publicTags != null)
			criteria.and("tags").all(publicTags);

		this.filter(criteria, sort);

		Query query = new Query(criteria);

		query.with(new PageRequest(page - 1, size));

		this.sorting(query, sort);
		return mongoTemplate.find(query, Post.class);
	}

	public long countPostsWithWriterName(
			// 로그인한 회원이 다른 사용자의 글을 검색할때 숫자를 셈.
			List<String> publicTags, List<String> loginWeaverprivateTags,
			String writerName, String loginName, String search, String sort) {
		Criteria criteria = new Criteria();

		criteria.orOperator(
				Criteria.where("kind").is(1).and("writerName").is(writerName),
				Criteria.where("tags").in(loginWeaverprivateTags)
						.and("writerName").is(writerName),
				Criteria.where("writerName").is(loginName));

		if (search != null)
			criteria.andOperator(new Criteria().orOperator(
					Criteria.where("title").regex(search),
					Criteria.where("content").regex(search)));

		if (publicTags != null)
			criteria.and("tags").all(publicTags);

		this.filter(criteria, sort);

		return mongoTemplate.count(new Query(criteria), Post.class);
	}

	public List<Post> getPostsWithWriterName(
			// 로그인한 회원이 다른 사용자의 글을 검색함.
			List<String> publicTags, List<String> loginWeaverprivateTags,
			String writerName, String loginName, String search, String sort,
			int page, int size) {

		Criteria criteria = new Criteria();

		criteria.orOperator(
				Criteria.where("kind").is(1).and("writerName").is(writerName),
				Criteria.where("tags").in(loginWeaverprivateTags)
						.and("writerName").is(writerName),
				Criteria.where("writerName").is(loginName));

		if (search != null)
			criteria.andOperator(new Criteria().orOperator(
					Criteria.where("title").regex(search),
					Criteria.where("content").regex(search)));

		if (publicTags != null)
			criteria.and("tags").all(publicTags);

		this.filter(criteria, sort);

		Query query = new Query(criteria);
		query.with(new PageRequest(page - 1, size));

		this.sorting(query, sort);
		return mongoTemplate.find(query, Post.class);
	}

	public long countMyPosts(
			// 자신의 글 숫자를 셈.
			List<String> publicTags, String writerName, String search,
			String sort) {
		Criteria criteria = new Criteria();

		criteria.orOperator(Criteria.where("writerName").is(writerName),
				Criteria.where("tags").in("@" + writerName));

		if (search != null)
			criteria.andOperator(new Criteria().orOperator(
					Criteria.where("title").regex(search),
					Criteria.where("content").regex(search)));

		if (publicTags != null)
			criteria.and("tags").all(publicTags);

		this.filter(criteria, sort);

		return mongoTemplate.count(new Query(criteria), Post.class);
	}

	public List<Post> getMyPosts(
			// 자기글과 자기한테 온 메세지를 검색함.
			List<String> publicTags, String writerName, String search,
			String sort, int page, int size) {
		Criteria criteria = new Criteria();

		criteria.orOperator(Criteria.where("writerName").is(writerName),
				Criteria.where("tags").in("@" + writerName));

		if (search != null)
			criteria.andOperator(new Criteria().orOperator(
					Criteria.where("title").regex(search),
					Criteria.where("content").regex(search)));

		if (publicTags != null)
			criteria.and("tags").all(publicTags);

		this.filter(criteria, sort);

		Query query = new Query(criteria);
		query.with(new PageRequest(page - 1, size));

		this.sorting(query, sort);
		return mongoTemplate.find(query, Post.class);
	}

	public long countMyProjectPosts( // 자기가 진행중인 프로젝트 글 숫자를 셈.
			List<String> projectTags, String search, String sort) {
		Criteria criteria = new Criteria("tags").in(projectTags);

		if (search != null)
			criteria.andOperator(new Criteria().orOperator(
					Criteria.where("title").regex(search),
					Criteria.where("content").regex(search)));

		this.filter(criteria, sort);

		return mongoTemplate.count(new Query(criteria), Post.class);
	}

	public List<Post> getMyProjectPosts(
			// 자기가 진행중인 프로젝트의 글을 검색함.
			List<String> projectTags, String search, String sort, int page,
			int size) {
		Criteria criteria = new Criteria("tags").in(projectTags);

		if (search != null)
			criteria.andOperator(new Criteria().orOperator(
					Criteria.where("title").regex(search),
					Criteria.where("content").regex(search)));

		this.filter(criteria, sort);

		Query query = new Query(criteria);
		query.with(new PageRequest(page - 1, size));

		this.sorting(query, sort);
		return mongoTemplate.find(query, Post.class);
	}

	public void delete(Post post) { // 글 삭제하기
		mongoTemplate.remove(post);
	}

	public void update(Post post) { // 글 수정하기
		Query query = new Query(Criteria.where("_id").is(post.getPostID()));
		Update update = new Update();
		update.set("content", post.getContent());
		update.set("title", post.getTitle());
		update.set("push", post.getPush());
		update.set("rePostCount", post.getRePostCount());
		update.set("recentRePostDate", post.getRecentRePostDate());
		update.set("tags", post.getTags());
		update.set("kind", post.getKind());
		mongoTemplate.updateFirst(query, update, Post.class);
	}

	public void filter(Criteria criteria, String sort) {
		if (sort.equals("push-desc")) {
			criteria.and("push").gt(0);
		} else if (sort.equals("repost-many")) {
			criteria.and("rePostCount").gt(0);
		} else if (sort.equals("repost-desc")) {
			criteria.and("rePostCount").gt(0);
		} else if (sort.equals("repost-null")) {
			criteria.and("rePostCount").is(0);
		}
	}

	public void sorting(Query query, String sort) {
		if (sort.equals("age-asc")) {
			query.with(new Sort(Sort.Direction.ASC, "_id"));
		} else if (sort.equals("push-desc")) {
			query.with(new Sort(Sort.Direction.DESC, "push"));
		} else if (sort.equals("repost-desc")) {
			query.with(new Sort(Sort.Direction.DESC, "recentRePostDate"));
		} else if (sort.equals("repost-many")) {
			query.with(new Sort(Sort.Direction.DESC, "respostCount"));
		} else
			query.with(new Sort(Sort.Direction.DESC, "_id"));
	}

	public Post getLast() {
		Query query = new Query().with(new Sort(Sort.Direction.DESC, "_id"));
		return mongoTemplate.findOne(query, Post.class);
	}
}
