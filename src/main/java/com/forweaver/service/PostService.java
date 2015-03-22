package com.forweaver.service;

import java.util.List;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.forweaver.domain.Data;
import com.forweaver.domain.Pass;
import com.forweaver.domain.Post;
import com.forweaver.domain.RePost;
import com.forweaver.domain.Reply;
import com.forweaver.domain.Weaver;
import com.forweaver.mongodb.dao.DataDao;
import com.forweaver.mongodb.dao.PostDao;
import com.forweaver.mongodb.dao.RePostDao;
import com.forweaver.mongodb.dao.WeaverDao;

@Service
public class PostService {

	@Autowired private PostDao postDao;
	@Autowired private RePostDao rePostDao;
	@Autowired private DataDao dataDao;
	@Autowired private WeaverDao weaverDao;
	@Autowired private CacheManager cacheManager;
	
	/** 글을 생성함.
	 * @param post
	 * @param datas
	 * @return
	 */
	public int add(Post post,List<Data> datas) {

		if (post.getContent().length() >= 10)
			post.setLong(true);
		else {
			post.setLong(false);
			post.setContent("");
		}
		if (post.getTitle().length() <= 1)
			return -1;
		post.setKind(1);
		for (String tag : post.getTags()) {
			if (tag.startsWith("@")) {
				post.setKind(2);
				break;
			} else if (tag.startsWith("$")) {
				post.setKind(3);
				break;
			}

		}
		//만약 자료를 올렸다면.
		if(datas != null && datas.size() >0)
			for(Data data:datas){
				dataDao.insert(data);
				post.addData(dataDao.getLast());
			}
		//사용자가 글을 추가하여 갯수를 올림.
		weaverDao.updateInfo(post.getWriter(),"weaverInfo.postCount",1);
		return postDao.insert(post);

	}

	public Post get(int postID) {
		return postDao.get(postID);
	}

	/** 글 추천함.
	 * @param post
	 * @param weaver
	 * @return
	 */
	public boolean push(Post post, Weaver weaver) {
		if (weaver == null || weaver.equals(post.getWriter()))
			return false;
		
		Cache cache = cacheManager.getCache("push"); // 중복 추천 방지!
		Element element = cache.get(post.getPostID());

		if (element == null || (element != null && element.getValue() == null)) {
			post.push();
			postDao.update(post);
			Element newElement = new Element(post.getPostID(), weaver.getId());
			cache.put(newElement);
			
			weaverDao.updateInfo(post.getWriter(),"weaverInfo.postPush",1); //추천을 하면 글쓴이의 추천수가 올라감.
			weaverDao.update(post.getWriter());
			return true;
		}
		return false;
	}

	public void update(Post post,String[] removeDataList) {
		if (post.getContent().length() >= 10)
			post.setLong(true);
		else {
			post.setLong(false);
			post.setContent("");
		}
		if (post.getTitle().length() <= 1)
			return;

		for (String tag : post.getTags()) {
			if (tag.startsWith("@"))
				post.setKind(2);
			else if (tag.startsWith("$")) {
				post.setKind(3);
			}else
				post.setKind(1);
		}
		if(removeDataList != null)
			for(String dataName: removeDataList){
				dataDao.delete(post.getData(dataName));
				post.deleteData(dataName);
			}
		postDao.update(post);
	}

	/** 실제 글을 삭제하는 메서드
	 * @param post
	 */
	private void delete(Post post){
		for(RePost rePost : rePostDao.gets(post)){ //답변들을 가져옴.
			weaverDao.updateInfo(rePost.getWriter(),"weaverInfo.myRePostCount",-1); // 답변을 단 사람들의 점수를 삭감
			weaverDao.updateInfo(rePost.getWriter(),"weaverInfo.myRePostPush",-rePost.getPush()); // 답변을 단 사람들의 추천 삭감.
			weaverDao.updateInfo(post.getWriter(),"weaverInfo.rePostCount",-1); // 글을 쓴 사람의 답변 점수 삭제
			for(Reply reply : rePost.getReplys()){ // 댓글들을 가져옴.
				weaverDao.updateInfo(reply.getWriter(),"weaverInfo.myReplysCount",-1);// 답변에 댓글을 단 사람들의 점수를 삭감
				weaverDao.updateInfo(rePost.getWriter(),"weaverInfo.replysCount",-1);// 답변에 댓글 점수를 삭감
			}
		}
		rePostDao.deleteAll(post); // 답변 전부 삭제.
		weaverDao.updateInfo(post.getWriter(),"weaverInfo.postCount",-1); // 글을 쓴 사람 갯수 삭감.
		weaverDao.updateInfo(post.getWriter(),"weaverInfo.postPush",-post.getPush()); // 글을 쓴 사람의 추천수 삭감.
		postDao.delete(post); //댓글 전부 삭제.
	}
	
	/** 글을 삭제함.
	 * @param post
	 * @param weaver
	 * @return
	 */
	public boolean delete(Post post, Weaver weaver) {
		if(post == null || weaver == null)
			return false;

		if (weaver.isAdmin() || 	post.getWriter().getId().equals(weaver.getId())) { // 글쓴이 또는 관리자의 경우
			this.delete(post);
			return true;
		} else if (post.getKind() == 2) { // 글쓴이가 아니고 프로젝트 태그의 경우
			String projectString = "";
			for (String tag : post.getTags()) {
				if (tag.startsWith("@"))
					projectString = tag;
			}

			for (Pass pass : weaver.getPasses()) {
				if (projectString.equals("@" + pass.getJoinName())
						&& pass.getPermission() == 1) {
					this.delete(post);
					return true;
				}
			}
			return false;
		} else if (post.getKind() == 3) { // 메세지 태그의 경우

			if (post.getTags().size() == 1
					&& post.getTags().get(0).equals("@" + weaver.getId())) {
				this.delete(post);
				return true;
			} else {
				for (String tag : post.getTags()) {
					if (tag.equals("@" + weaver.getId())) {
						post.deleteTag(tag);
						postDao.update(post);
						return true;
					}
				}
				return false;
			}
		} else
			return false;
	}

	public long countPosts(Weaver weaver,String sort) {
		if(weaver == null) //로그인하지 않은 회원의 경우
			return postDao.countPostsWhenNotLogin(null, null, null, sort);

		return postDao.countPostsWhenLogin(null,weaver.getPrivateAndMassageTags(),null,null, sort);

	}

	public List<Post> getPosts(Weaver weaver,String sort, int page, int size) {

		if(weaver == null) //로그인하지 않은 회원의 경우
			return postDao.getPostsWhenNotLogin(null, null, null, sort, page, size);

		return postDao.getPostsWhenLogin(null,weaver.getPrivateAndMassageTags(),null,null, sort, page, size);
	}

	public long countPosts(Weaver weaver,List<String> tags,String sort) {

		if(weaver == null) //로그인하지 않은 회원의 경우
			return postDao.countPostsWhenNotLogin(tags, null, null, sort);

		if(this.isPublicTags(tags)) // 태그가 공개 태그일 경우.
			return postDao.countPostsWhenLogin(tags,weaver.getPrivateAndMassageTags(),null,null, sort);
		if(this.isPrivateTags(tags)) // 태그가 프로젝트 태그일 경우.
			return postDao.countPostsWithPrivateTags(tags, null, null, sort);
		if(this.isMassageTags(tags)) // 태그가 메세지 태그의 경우.
			return postDao.countPostsWithMassageTag(tags, null, weaver, this.isMassageTagsWithWriterTag(weaver.getId(),tags), sort);

		return 0;
	}

	public List<Post> getPosts(Weaver weaver,List<String> tags,String sort, int page, int size) {

		if(weaver == null) //로그인하지 않은 회원의 경우
			return postDao.getPostsWhenNotLogin(tags, null, null, sort, page, size);
		if(this.isPublicTags(tags)) // 태그가 공개 태그일 경우.
			return postDao.getPostsWhenLogin(tags,weaver.getPrivateAndMassageTags(),null,null, sort, page, size);
		if(this.isPrivateTags(tags)) // 태그가 프로젝트 태그일 경우.
			return postDao.getPostsWithPrivateTags(tags, null, null, sort, page, size);
		if(this.isMassageTags(tags))// 태그가 메세지 태그의 경우.
			return postDao.getPostsWithMassageTag(tags, null, weaver, this.isMassageTagsWithWriterTag(weaver.getId(),tags), sort, page, size);

		return null;
	}

	public long countPosts(Weaver weaver,
			List<String> tags,String search,String sort) {

		if(weaver == null) //로그인하지 않은 회원의 경우
			return postDao.countPostsWhenNotLogin(tags, search, null, sort);
		if(this.isPublicTags(tags)) // 태그가 공개 태그일 경우.
			return postDao.countPostsWhenLogin(tags,weaver.getPrivateAndMassageTags(),null,search, sort);
		if(this.isPrivateTags(tags)) // 태그가 프로젝트 태그일 경우.
			return postDao.countPostsWithPrivateTags(tags, search, null, sort);
		if(this.isMassageTags(tags)) // 태그가 메세지 태그의 경우.
			return postDao.countPostsWithMassageTag(tags, search, weaver, this.isMassageTagsWithWriterTag(weaver.getId(),tags), sort);

		return 0;
	}

	public List<Post> getPosts(Weaver weaver,
			List<String> tags,String search,String sort, int page, int size) {

		if(weaver == null) //로그인하지 않은 회원의 경우
			return postDao.getPostsWhenNotLogin(tags, search, null, sort, page, size);
		if(this.isPublicTags(tags)) // 태그가 공개 태그일 경우.
			return postDao.getPostsWhenLogin(tags,weaver.getPrivateAndMassageTags(),null,search, sort, page, size);
		if(this.isPrivateTags(tags)) // 태그가 프로젝트 태그일 경우.
			return postDao.getPostsWithPrivateTags(tags, search, null, sort, page, size);
		if(this.isMassageTags(tags)) // 태그가 메세지 태그의 경우.
			return postDao.getPostsWithMassageTag(tags, search, weaver, this.isMassageTagsWithWriterTag(weaver.getId(),tags), sort, page, size);
		return null;

	}

	public long countPosts(Weaver loginWeaver,Weaver writer,String sort) {

		if(loginWeaver == null) //로그인하지 않은 회원의 경우
			return postDao.countPostsWhenNotLogin(null, null, writer, sort);

		if(loginWeaver.equals(writer))
			return postDao.countMyPosts(null,loginWeaver.getPrivateAndMassageTags(), writer, null, sort);
		else
			return postDao.countPostsWithWriter(null, 
					loginWeaver.getPrivateAndMassageTags(),
					writer, loginWeaver, null, sort);
	}

	public List<Post> getPosts(Weaver loginWeaver,
			Weaver writer,String sort, int page, int size) {

		if(loginWeaver == null) //로그인하지 않은 회원의 경우
			return postDao.getPostsWhenNotLogin(null, null, writer, sort,page,size);

		if(loginWeaver.equals(writer))
			return postDao.getMyPosts(null,loginWeaver.getPrivateAndMassageTags(), writer, null, sort,page,size);
		else
			return postDao.getPostsWithWriter(null, 
					loginWeaver.getPrivateAndMassageTags(), 
					writer, loginWeaver, null, sort, page, size);
	}


	public long countPosts(Weaver loginWeaver,List<String> tags,Weaver writer,String sort) {

		if(loginWeaver == null) //로그인하지 않은 회원의 경우
			return postDao.countPostsWhenNotLogin(tags, null, writer, sort);

		if(loginWeaver.equals(writer))
			return postDao.countMyPosts(tags,loginWeaver.getPrivateAndMassageTags(), writer, null, sort);
		else
			return postDao.countPostsWithWriter(tags, 
					loginWeaver.getPrivateAndMassageTags(),
					writer, loginWeaver, null, sort);
	}


	public List<Post> getPosts(Weaver loginWeaver,List<String> tags,
			Weaver writer,String sort, int page, int size) {

		if(loginWeaver == null) //로그인하지 않은 회원의 경우
			return postDao.getPostsWhenNotLogin(tags, null, writer, sort,page,size);

		if(loginWeaver.equals(writer))
			return postDao.getMyPosts(tags,loginWeaver.getPrivateAndMassageTags(), writer, null, sort,page,size);
		else
			return postDao.getPostsWithWriter(tags, 
					loginWeaver.getPrivateAndMassageTags(), 
					writer, loginWeaver, null, sort, page, size);
	}

	public long countPosts(Weaver loginWeaver,List<String> tags,Weaver writer,String search,String sort) {

		if(loginWeaver == null) //로그인하지 않은 회원의 경우
			return postDao.countPostsWhenNotLogin(tags, search, writer, sort);

		if(loginWeaver.equals(writer))
			return postDao.countMyPosts(tags,loginWeaver.getPrivateAndMassageTags(), writer, search, sort);
		else
			return postDao.countPostsWithWriter(tags, 
					loginWeaver.getPrivateAndMassageTags(),
					writer, loginWeaver, search, sort);
	}


	public List<Post> getPosts(Weaver loginWeaver,List<String> tags,
			Weaver writer,String search,String sort, int page, int size) {

		if(loginWeaver == null) //로그인하지 않은 회원의 경우
			return postDao.getPostsWhenNotLogin(tags, search, writer, sort,page,size);

		if(loginWeaver.equals(writer))
			return postDao.getMyPosts(tags,loginWeaver.getPrivateAndMassageTags(), writer, search, sort,page,size);
		else
			return postDao.getPostsWithWriter(tags, 
					loginWeaver.getPrivateAndMassageTags(), 
					writer, loginWeaver, search, sort, page, size);
	}


	public boolean isMassageTags(List<String> tags) {
		return tags.get(0).startsWith("$") && !tags.get(0).contains("/");
	}

	public boolean isPrivateTags(List<String> tags) {
		for (String tag : tags) 
			if (tag.startsWith("@"))
				return true;
		return false;
	}

	public boolean isPublicTags(List<String> tags) {

		for (String tag : tags)
			if (tag.startsWith("@") || tag.startsWith("$"))
				return false;

		return true;
	}

	public boolean isMassageTagsWithWriterTag(String nickName, List<String> tags) {
		for (String tag : tags)
			if (tag.equals("$" + nickName)) 
				return true;

		return false;
	}


}
