package com.forweaver.service;

import java.util.List;
import java.util.Map;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.forweaver.domain.Code;
import com.forweaver.domain.Data;
import com.forweaver.domain.Post;
import com.forweaver.domain.RePost;
import com.forweaver.domain.Weaver;
import com.forweaver.mongodb.dao.CodeDao;
import com.forweaver.mongodb.dao.DataDao;
import com.forweaver.mongodb.dao.PostDao;
import com.forweaver.mongodb.dao.RePostDao;

@Service
public class RePostService {
	@Autowired
	private RePostDao rePostDao;
	
	@Autowired
	private PostDao postDao;
	
	@Autowired
	private DataDao dataDao;
	
	@Autowired
	private CodeDao codeDao;
	
	@Autowired
	private CacheManager cacheManager;
	
	public void add(RePost rePost,List<Data> datas) {
		if(datas.size() > 0){
			Map<String,String> fileMap = dataDao.insert(datas);
			rePost.insertDataList(fileMap);
		}
		rePostDao.insert(rePost);
		cacheManager.getCache("post").remove(rePost.getOriginalPostID());
	}
	
	public List<RePost> get(int ID,int kind,String sort) {
		return rePostDao.get(ID,kind,sort);
	}
	
	public RePost get(int rePostID) {
		return rePostDao.get(rePostID);
	}
	
	public boolean push(RePost rePost, Weaver weaver) {
		if(weaver == null)
			return false;
		cacheManager.getCache("post").remove(rePost.getOriginalPostID());
		rePost.push();
		Cache cache = cacheManager.getCache("push");
		Element element = cache.get("re"+rePost.getRePostID());
		if (element == null) {
			rePostDao.update(rePost);
			Element newElement = new Element("re"+rePost.getRePostID(), weaver.getId());
			cache.put(newElement);
			return true;
		}
		return false;
	}
	
	public void update(RePost rePost){
		rePostDao.update(rePost);
		cacheManager.getCache("post").remove(rePost.getOriginalPostID());
	}
	
	public boolean delete(Post post,RePost rePost,Weaver weaver){

		if(post == null || rePost == null || !rePost.getWriterName().equals(weaver.getId()))
			return false;
		post.rePostCountDown();
		postDao.update(post);
		rePostDao.delete(rePost);
		cacheManager.getCache("post").remove(rePost.getOriginalPostID());

		return true;
	}
	
	public boolean delete(Code code,RePost rePost,Weaver weaver){

		if(code == null || rePost == null || !rePost.getWriterName().equals(weaver.getId()))
			return false;
		code.setRePostCount(code.getRePostCount()-1);
		codeDao.update(code);
		rePostDao.delete(rePost);
		return true;
	}
}