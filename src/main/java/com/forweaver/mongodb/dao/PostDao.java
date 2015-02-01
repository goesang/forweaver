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
import com.forweaver.domain.Weaver;

/** 커뮤니티 글과 관련된 DB빈
 * 
 */
@Repository
public class PostDao {
	
	@Autowired private MongoTemplate mongoTemplate;

	/** 글 추가하기
	 * @param post
	 * @return
	 */
	public int insert(Post post) {
		if (!mongoTemplate.collectionExists(Post.class)) {
			mongoTemplate.createCollection(Post.class);
			post.setPostID(1);
			mongoTemplate.insert(post);
			return 1;
		}
		Post lastPost = getLast();
		if(lastPost == null)
			post.setPostID(1);
		else
			post.setPostID(lastPost.getPostID() + 1);
		mongoTemplate.insert(post);
		return post.getPostID();
	}

	/** 글 가져오기
	 * @param postID
	 * @return
	 */
	public Post get(int postID) {
		Query query = new Query(Criteria.where("_id").is(postID));
		return mongoTemplate.findOne(query, Post.class);
	}

	/** 관리자가 글을 불러올 때 계수를 셈.
	 * @param tags
	 * @param search
	 * @param writer
	 * @param sort
	 * @return
	 */
	public long countPosts(List<String> tags, String search, Weaver writer, String sort) {
		Criteria criteria = new Criteria();

		if (search != null)
			criteria.orOperator(new Criteria("title").regex(search),
					new Criteria("content").regex(search));

		if (tags != null)
			criteria.and("tags").all(tags);
		if (writer != null)
			criteria.and("writer").is(writer);

		this.filter(criteria, sort);

		return mongoTemplate.count(new Query(criteria), Post.class);
	}
	
	/** 관리자가 글을 불러올 때
	 * @param tags
	 * @param search
	 * @param writer
	 * @param sort
	 * @param page
	 * @param size
	 * @return
	 */
	public List<Post> getPosts(List<String> tags, String search, Weaver writer, String sort,
			int page, int size) {
		Criteria criteria = new Criteria();

		if (search != null)
			criteria.orOperator(new Criteria("title").regex(search),
					new Criteria("content").regex(search));

		if (tags != null)
			criteria.and("tags").all(tags);
		if (writer != null)
			criteria.and("writer").is(writer);

		this.filter(criteria, sort);

		Query query = new Query(criteria);
		query.with(new PageRequest(page - 1, size));

		this.sorting(query, sort);
		return mongoTemplate.find(query, Post.class);
	}
	
	/** 로그인하지 않은 회원이 글을 셈.
	 * @param tags
	 * @param search
	 * @param writer
	 * @param sort
	 * @return
	 */
	public long countPostsWhenNotLogin( 
			List<String> tags, String search, Weaver writer, String sort) {
		Criteria criteria = new Criteria();

		if (search != null)
			criteria.orOperator(new Criteria("title").regex(search),
					new Criteria("content").regex(search));

		criteria.and("kind").is(1);

		if (tags != null)
			criteria.and("tags").all(tags);
		if (writer != null)
			criteria.and("writer").is(writer);

		this.filter(criteria, sort);

		return mongoTemplate.count(new Query(criteria), Post.class);
	}

	
	/**  로그인하지 않은 회원이 글을 검색
	 * @param tags
	 * @param search
	 * @param writer
	 * @param sort
	 * @param page
	 * @param size
	 * @return
	 */
	public List<Post> getPostsWhenNotLogin(List<String> tags, String search, Weaver writer, String sort,
			int page, int size) {
		Criteria criteria = new Criteria();

		if (search != null)
			criteria.orOperator(new Criteria("title").regex(search),
					new Criteria("content").regex(search));

		criteria.and("kind").is(1);

		if (tags != null)
			criteria.and("tags").all(tags);
		if (writer != null)
			criteria.and("writer").is(writer);

		this.filter(criteria, sort);

		Query query = new Query(criteria);
		query.with(new PageRequest(page - 1, size));

		this.sorting(query, sort);
		return mongoTemplate.find(query, Post.class);
	}

	
	
	/**  프로젝트 태그를 이용하여 글을 파악하고 셈
	 * @param privateTags
	 * @param search
	 * @param writer
	 * @param sort
	 * @return
	 */
	public long countPostsWithPrivateTags(List<String> privateTags, String search, Weaver writer,String sort) {
		Criteria criteria = new Criteria().and("kind").is(2).and("tags")
				.all(privateTags); // 비밀 글 가져오기

		if (writer != null)
			criteria.and("writer").is(writer);

		if (search != null)
			criteria.andOperator(criteria.orOperator(new Criteria("title").regex(search),
					new Criteria("content").regex(search)));

		this.filter(criteria, sort);

		return mongoTemplate.count(new Query(criteria), Post.class);
	}

	/** 프로젝트 태그를 이용하여 글을 검색함
	 * @param privateTags
	 * @param search
	 * @param writer
	 * @param sort
	 * @param page
	 * @param size
	 * @return
	 */
	public List<Post> getPostsWithPrivateTags(List<String> privateTags, String search, Weaver writer,
			String sort, int page, int size) {

		Criteria criteria = new Criteria().and("kind").is(2).and("tags")
				.all(privateTags); // 일반 공개글을 불러옴;

		if (writer != null)
			criteria.and("writer").is(writer);

		if (search != null)
			criteria.andOperator(criteria.orOperator(new Criteria("title").regex(search),
					new Criteria("content").regex(search)));

		this.filter(criteria, sort);

		Query query = new Query(criteria);
		query.with(new PageRequest(page - 1, size));

		this.sorting(query, sort);
		return mongoTemplate.find(query, Post.class);
	}

	/** 메세지 태그를 이용하여 글을 검색하고 셈
	 * @param massageTags
	 * @param search
	 * @param writer
	 * @param my
	 * @param sort
	 * @return
	 */
	public long countPostsWithMassageTag(List<String> massageTags, String search, Weaver writer,
			boolean my, String sort) {
		Criteria criteria;
		if (my)
			criteria = new Criteria().orOperator(
					Criteria.where("kind").is(3).andOperator(
					Criteria.where("tags").in(massageTags)),
					Criteria.where("kind").is(3).and("writer")
							.is(writer));
		else
			criteria = Criteria.where("kind").is(3).and("writer")
					.is(writer).and("tags").in(massageTags);

		if (search != null)
			criteria.andOperator(new Criteria().orOperator(
					Criteria.where("title").regex(search),
					Criteria.where("content").regex(search)));

		this.filter(criteria, sort);
		return mongoTemplate.count(new Query(criteria), Post.class);
	}
	/** 메세지 태그를 이용하여 글을 검색
	 * @param massageTags
	 * @param search
	 * @param writer
	 * @param my
	 * @param sort
	 * @param page
	 * @param size
	 * @return
	 */
	public List<Post> getPostsWithMassageTag(
			List<String> massageTags, String search,Weaver writer, 
			boolean my, String sort, int page, int size) {
		Criteria criteria;

		if (my)
			criteria = new Criteria().orOperator(
					Criteria.where("kind").is(3).andOperator(
					Criteria.where("tags").in(massageTags)),
					Criteria.where("kind").is(3).and("writer")
							.is(writer));
		else
			criteria = Criteria.where("kind").is(3).and("writer")
					.is(writer).and("tags").in(massageTags);

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

	/** 로그인한 회원이 글을 검색할때 숫자를 셈.
	 * @param publicTags
	 * @param privateTags
	 * @param writer
	 * @param search
	 * @param sort
	 * @return
	 */
	public long countPostsWhenLogin(List<String> publicTags, List<String> privateTags,
			Weaver writer, String search, String sort) {
		Criteria criteria = new Criteria();

		criteria.orOperator(Criteria.where("kind").is(1),
				Criteria.where("writer").is(writer),
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

	/** 로그인한 회원이 글을 검색함.
	 * @param publicTags
	 * @param privateTags
	 * @param writer
	 * @param search
	 * @param sort
	 * @param page
	 * @param size
	 * @return
	 */
	public List<Post> getPostsWhenLogin(
			List<String> publicTags, List<String> privateTags,
			Weaver writer, String search, String sort, int page, int size) {

		Criteria criteria = new Criteria();

		criteria.orOperator(Criteria.where("kind").is(1),
				Criteria.where("writer").is(writer),
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

	/** 로그인한 회원이 다른 사용자의 글을 검색할때 숫자를 셈.
	 * @param publicTags
	 * @param loginWeaverprivateTags
	 * @param writer
	 * @param loginWeaver
	 * @param search
	 * @param sort
	 * @return
	 */
	public long countPostsWithWriterName(
			List<String> publicTags, List<String> loginWeaverprivateTags,
			Weaver writer, Weaver loginWeaver, String search, String sort) {
		Criteria criteria = new Criteria();

		criteria.orOperator(
				Criteria.where("kind").is(1).and("writer").is(writer),
				Criteria.where("tags").in(loginWeaverprivateTags)
						.and("writer").is(writer),
				Criteria.where("writer").is(loginWeaver));

		if (search != null)
			criteria.andOperator(new Criteria().orOperator(
					Criteria.where("title").regex(search),
					Criteria.where("content").regex(search)));

		if (publicTags != null)
			criteria.and("tags").all(publicTags);

		this.filter(criteria, sort);

		return mongoTemplate.count(new Query(criteria), Post.class);
	}

	/** 로그인한 회원이 다른 사용자의 글을 검색함.
	 * @param publicTags
	 * @param loginWeaverprivateTags
	 * @param writer
	 * @param loginWeaver
	 * @param search
	 * @param sort
	 * @param page
	 * @param size
	 * @return
	 */
	public List<Post> getPostsWithWriterName(
			List<String> publicTags, List<String> loginWeaverprivateTags,
			Weaver writer, Weaver loginWeaver, String search, String sort,
			int page, int size) {

		Criteria criteria = new Criteria();

		criteria.orOperator(
				Criteria.where("kind").is(1).and("writer").is(writer),
				Criteria.where("tags").in(loginWeaverprivateTags)
						.and("writer").is(writer),
				Criteria.where("writer").is(loginWeaver));

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

	/** 자신의 글 숫자를 셈.
	 * @param publicTags
	 * @param privateTags
	 * @param writer
	 * @param search
	 * @param sort
	 * @return
	 */
	public long countMyPosts(List<String> publicTags,List<String> privateTags, Weaver writer, 
			String search, String sort) {
		Criteria criteria = new Criteria();

		criteria.orOperator(Criteria.where("writer").is(writer),
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

	/** 자기글과 자기한테 온 메세지를 검색함.
	 * @param publicTags
	 * @param privateTags
	 * @param writer
	 * @param search
	 * @param sort
	 * @param page
	 * @param size
	 * @return
	 */
	public List<Post> getMyPosts(List<String> publicTags,List<String> privateTags,  Weaver writer, String search,
			String sort, int page, int size) {
		Criteria criteria = new Criteria();

		criteria.orOperator(Criteria.where("writer").is(writer),
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

	/** // 자기가 진행중인 프로젝트 글 숫자를 셈.
	 * @param privateTags
	 * @param search
	 * @param sort
	 * @return
	 */
	public long countMyProjectPosts(List<String> privateTags, String search, String sort) {
		Criteria criteria = new Criteria("tags").in(privateTags);

		if (search != null)
			criteria.andOperator(new Criteria().orOperator(
					Criteria.where("title").regex(search),
					Criteria.where("content").regex(search)));

		this.filter(criteria, sort);

		return mongoTemplate.count(new Query(criteria), Post.class);
	}

	/** 자기가 진행중인 프로젝트의 글을 검색함.
	 * @param privateTags
	 * @param search
	 * @param sort
	 * @param page
	 * @param size
	 * @return
	 */
	public List<Post> getMyProjectPosts(List<String> privateTags, String search, 
			String sort, int page,int size) {
		Criteria criteria = new Criteria("tags").in(privateTags);

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

	/** 글 삭제하기
	 * @param post
	 */
	public void delete(Post post) { 
		mongoTemplate.remove(post);
	}

	/**  글 수정하기
	 * @param post
	 */
	public void update(Post post) {
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

	/** 글 정렬에 사용되는 필터
	 * @param criteria
	 * @param sort
	 */
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

	/** 글 정렬 메서드
	 * @param query
	 * @param sort
	 */
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

	/** 마지막 글의 아이디를 가져옴
	 * @return
	 */
	public Post getLast() {
		Query query = new Query().with(new Sort(Sort.Direction.DESC, "_id"));
		return mongoTemplate.findOne(query, Post.class);
	}
}
