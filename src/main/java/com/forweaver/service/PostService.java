package com.forweaver.service;

import java.util.List;
import java.util.Map;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.forweaver.domain.Data;
import com.forweaver.domain.Pass;
import com.forweaver.domain.Post;
import com.forweaver.domain.Weaver;
import com.forweaver.mongodb.dao.DataDao;
import com.forweaver.mongodb.dao.PostDao;
import com.forweaver.mongodb.dao.RePostDao;
import com.forweaver.util.WebUtil;

@Service
public class PostService {

	@Autowired
	private PostDao postDao;
	@Autowired
	private RePostDao rePostDao;
	@Autowired
	private DataDao dataDao;
	@Autowired
	private CacheManager cacheManager;

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
		if(datas != null && datas.size() >0)
			for(Data data:datas){
				dataDao.insert(data);
				post.addData(dataDao.getLast());
			}
		

		return postDao.insert(post);

	}

	public Post get(int postID) {
		Cache cache = cacheManager.getCache("post");
		Element element = cache.get(postID);
		if (element == null || (element != null && element.getValue() == null)) {
			Post post = postDao.get(postID);
			if (post == null)
				return null;
			Element newElement = new Element(post.getPostID(), post);
			cache.put(newElement);
			return post;
		}
		return (Post) element.getValue();
	}

	public boolean push(Post post, Weaver weaver) {
		if (weaver == null || weaver.equals(post.getWriter()))
			return false;
		cacheManager.getCache("post").remove(post.getPostID());
		Cache cache = cacheManager.getCache("push");
		Element element = cache.get(post.getPostID());
		if (element == null || (element != null && element.getValue() == null)) {
			post.push();
			postDao.update(post);
			Element newElement = new Element(post.getPostID(), weaver.getId());
			cache.put(newElement);
			return true;
		}
		return false;
	}

	public void update(Post post,String[] removeDataList) {
		cacheManager.getCache("post").remove(post.getPostID());
		
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
		cacheManager.getCache("post").put(new Element(post.getPostID(), post));// 수정함
	}

	public boolean delete(Post post, Weaver weaver) {

		if (post.getWriter().equals(weaver)) { // 글쓴이의 경우
			postDao.delete(post);
			rePostDao.deleteAll(post.getPostID());
			cacheManager.getCache("post").remove(post.getPostID());
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
					postDao.delete(post);
					rePostDao.deleteAll(post.getPostID());
					cacheManager.getCache("post").remove(post.getPostID());
					return true;
				}
			}
			return false;
		} else if (post.getKind() == 3) { // 메세지 태그의 경우

			if (post.getTags().size() == 1
					&& post.getTags().get(0).equals("@" + weaver.getId())) {
				postDao.delete(post);
				rePostDao.deleteAll(post.getPostID());
				cacheManager.getCache("post").remove(post.getPostID());
				return true;
			} else {
				for (String tag : post.getTags()) {
					if (tag.equals("@" + weaver.getId())) {
						post.deleteTag(tag);
						postDao.update(post);
						cacheManager.getCache("post").remove(post.getPostID());
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
		else
			return postDao.countPostsWhenLogin(null,weaver.getPrivateTags(),null,null, sort);

	}
	
	public List<Post> getPosts(Weaver weaver,String sort, int page, int size) {

		if(weaver == null) //로그인하지 않은 회원의 경우
			return postDao.getPostsWhenNotLogin(null, null, null, sort, page, size);
		else
			return postDao.getPostsWhenLogin(null,weaver.getPrivateTags(),null,null, sort, page, size);
	}
	
	public long countPostsWithTags(Weaver weaver,List<String> tags,String sort) {
		
		if(weaver == null) //로그인하지 않은 회원의 경우
			return postDao.countPostsWhenNotLogin(tags, null, null, sort);
		else{
			if(this.isPublicTags(tags)) // 태그가 공개 태그일 경우.
				return postDao.countPostsWhenLogin(tags,weaver.getPrivateTags(),null,null, sort);
			else if(this.isPrivateTags(tags)) // 태그가 프로젝트 태그일 경우.
				return postDao.countPostsWithPrivateTags(tags, null, null, sort);
			else if(this.isMassageTags(tags)) // 태그가 메세지 태그의 경우.
				return postDao.countPostsWithMassageTag(tags, null, weaver, this.isMassageTagsWithWriterTag(weaver.getId(),tags), sort);
			else 
				return 0;
		}
			
			
	}
	
	public List<Post> getPostsWithTags(Weaver weaver,List<String> tags,String sort, int page, int size) {

		if(weaver == null) //로그인하지 않은 회원의 경우
			return postDao.getPostsWhenNotLogin(tags, null, null, sort, page, size);
		else{
			if(this.isPublicTags(tags)) // 태그가 공개 태그일 경우.
				return postDao.getPostsWhenLogin(tags,weaver.getPrivateTags(),null,null, sort, page, size);
			else if(this.isPrivateTags(tags)) // 태그가 프로젝트 태그일 경우.
				return postDao.getPostsWithPrivateTags(tags, null, null, sort, page, size);
			else if(this.isMassageTags(tags)) {// 태그가 메세지 태그의 경우.
				return postDao.getPostsWithMassageTag(tags, null, weaver, this.isMassageTagsWithWriterTag(weaver.getId(),tags), sort, page, size);
				
			}else 
				return null;
		}
			
			
	}
	
	public long countPostsWithTagsAndSearch(Weaver weaver,
			List<String> tags,String search,String sort) {
		
		if(weaver == null) //로그인하지 않은 회원의 경우
			return postDao.countPostsWhenNotLogin(tags, search, null, sort);
		else{
			if(this.isPublicTags(tags)) // 태그가 공개 태그일 경우.
				return postDao.countPostsWhenLogin(tags,weaver.getPrivateTags(),null,search, sort);
			else if(this.isPrivateTags(tags)) // 태그가 프로젝트 태그일 경우.
				return postDao.countPostsWithPrivateTags(tags, search, null, sort);
			else if(this.isMassageTags(tags)) // 태그가 메세지 태그의 경우.
				return postDao.countPostsWithMassageTag(tags, search, weaver, this.isMassageTagsWithWriterTag(weaver.getId(),tags), sort);
			else 
				return 0;
		}
			
			
	}
	
	public List<Post> getPostsWithTagsAndSearch(Weaver weaver,
			List<String> tags,String search,String sort, int page, int size) {

		if(weaver == null) //로그인하지 않은 회원의 경우
			return postDao.getPostsWhenNotLogin(tags, search, null, sort, page, size);
		else{  // 로그인한 회원의 경우
			if(this.isPublicTags(tags)) // 태그가 공개 태그일 경우.
				return postDao.getPostsWhenLogin(tags,weaver.getPrivateTags(),null,search, sort, page, size);
			else if(this.isPrivateTags(tags)) // 태그가 프로젝트 태그일 경우.
				{
				System.out.println("ssss");return postDao.getPostsWithPrivateTags(tags, search, null, sort, page, size);
				}
			else if(this.isMassageTags(tags)) // 태그가 메세지 태그의 경우.
				return postDao.getPostsWithMassageTag(tags, search, weaver, this.isMassageTagsWithWriterTag(weaver.getId(),tags), sort, page, size);
			else {
				return null;
			}
		}
			
			
	}
	
	public long countPostsWhenWeaverHome(Weaver loginWeaver,Weaver writer,String sort) {
		
		if(loginWeaver == null) //로그인하지 않은 회원의 경우
			return postDao.countPostsWhenNotLogin(null, null, writer, sort);
		else 
			if(loginWeaver.getId().equals(writer))
				return postDao.countMyPosts(null,loginWeaver.getPrivateTags(), writer, null, sort);
			else
				return postDao.countPostsWithWriterName(null, 
						loginWeaver.getPrivateTags(),
						writer, loginWeaver, null, sort);
	}
	
	public List<Post> getPostsWhenWeaverHome(Weaver loginWeaver,
			Weaver writer,String sort, int page, int size) {
		
		if(loginWeaver == null) //로그인하지 않은 회원의 경우
			return postDao.getPostsWhenNotLogin(null, null, writer, sort,page,size);
		else 
			if(loginWeaver.getId().equals(writer))
				return postDao.getMyPosts(null,loginWeaver.getPrivateTags(), writer, null, sort,page,size);
			else
				return postDao.getPostsWithWriterName(null, 
						loginWeaver.getPrivateTags(), 
						writer, loginWeaver, null, sort, page, size);
	}

	
	public long countPostsWhenWeaverHomeWithTags(Weaver loginWeaver,List<String> tags,Weaver writer,String sort) {
		
		if(loginWeaver == null) //로그인하지 않은 회원의 경우
			return postDao.countPostsWhenNotLogin(tags, null, writer, sort);
		else 
			if(loginWeaver.getId().equals(writer))
				return postDao.countMyPosts(tags,loginWeaver.getPrivateTags(), writer, null, sort);
			else
				return postDao.countPostsWithWriterName(tags, 
						loginWeaver.getPrivateTags(),
						writer, loginWeaver, null, sort);
	}
	
	
	public List<Post> getPostsWhenWeaverHomeWithTags(Weaver loginWeaver,List<String> tags,
			Weaver writer,String sort, int page, int size) {
		
		if(loginWeaver == null) //로그인하지 않은 회원의 경우
			return postDao.getPostsWhenNotLogin(tags, null, writer, sort,page,size);
		else 
			if(loginWeaver.getId().equals(writer))
				return postDao.getMyPosts(tags,loginWeaver.getPrivateTags(), writer, null, sort,page,size);
			else
				return postDao.getPostsWithWriterName(tags, 
						loginWeaver.getPrivateTags(), 
						writer, loginWeaver, null, sort, page, size);
	}
	
	public long countPostsWhenWeaverHomeWithTagsAndSearch(Weaver loginWeaver,List<String> tags,Weaver writer,String search,String sort) {
		
		if(loginWeaver == null) //로그인하지 않은 회원의 경우
			return postDao.countPostsWhenNotLogin(tags, search, writer, sort);
		else 
			if(loginWeaver.getId().equals(writer))
				return postDao.countMyPosts(tags,loginWeaver.getPrivateTags(), writer, search, sort);
			else
				return postDao.countPostsWithWriterName(tags, 
						loginWeaver.getPrivateTags(),
						writer, loginWeaver, search, sort);
	}
	
	
	public List<Post> getPostsWhenWeaverHomeWithTagsAndSearch(Weaver loginWeaver,List<String> tags,
			Weaver writer,String search,String sort, int page, int size) {
		
		if(loginWeaver == null) //로그인하지 않은 회원의 경우
			return postDao.getPostsWhenNotLogin(tags, search, writer, sort,page,size);
		else 
			if(loginWeaver.getId().equals(writer))
				return postDao.getMyPosts(tags,loginWeaver.getPrivateTags(), writer, search, sort,page,size);
			else
				return postDao.getPostsWithWriterName(tags, 
						loginWeaver.getPrivateTags(), 
						writer, loginWeaver, search, sort, page, size);
	}


	public boolean isMassageTags(List<String> tags) {
		return tags.get(0).startsWith("$") && !tags.get(0).contains("/");
	}
	
	public boolean isPrivateTags(List<String> tags) {
		for (String tag : tags) {
			if (tag.startsWith("@")) {
				return true;
			}
		}
		return false;
	}
	
	public boolean isPublicTags(List<String> tags) {
		
		for (String tag : tags) {
			if (tag.startsWith("@") || tag.startsWith("$"))
				return false;
		}
		return true;
	}

	public boolean isMassageTagsWithWriterTag(String nickName, List<String> tags) {
		for (String tag : tags) {
			if (tag.equals("$" + nickName)) {
				return true;
			}
		}
		return false;
	}

	
}
