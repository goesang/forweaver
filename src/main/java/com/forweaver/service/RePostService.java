package com.forweaver.service;

import java.util.List;

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
	@Autowired private RePostDao rePostDao;
	@Autowired private PostDao postDao;
	@Autowired private DataDao dataDao;
	@Autowired private CodeDao codeDao;
	@Autowired private CacheManager cacheManager;

	public boolean add(RePost rePost,List<Data> datas) {
		if(rePost == null)
			return false;
		if(datas != null)
			for(Data data:datas){
				dataDao.insert(data);
				rePost.addData(dataDao.getLast());
			}
		rePostDao.insert(rePost);
		return true;
	}

	public List<RePost> get(int ID,int kind,String sort) {
		return rePostDao.get(ID,kind,sort);
	}

	public RePost get(int rePostID) {
		return rePostDao.get(rePostID);
	}

	public boolean push(RePost rePost, Weaver weaver) {
		if(rePost == null || weaver == null)
			return false;
		rePost.push();
		Cache cache = cacheManager.getCache("push");
		Element element = cache.get("re"+rePost.getRePostID());
		if (element == null || (element != null && element.getValue() == null)) {
			rePostDao.update(rePost);
			Element newElement = new Element("re"+rePost.getRePostID(), weaver.getId());
			cache.put(newElement);
			return true;
		}
		return false;
	}

	public boolean update(RePost rePost,String[] removeDataList){
		if(rePost == null)
			return false;
		if(removeDataList != null)
			for(String dataName: removeDataList){
				dataDao.delete(rePost.getData(dataName));
				rePost.deleteData(dataName);
			}
		rePostDao.update(rePost);
		return true;
	}

	public boolean delete(Post post,RePost rePost,Weaver weaver){

		if(post == null ||rePost == null || weaver == null)
			return false;
		
		if(rePost.getWriterName().equals(weaver.getId()) 
				||  weaver.isAdmin()){
			post.rePostCountDown();
			postDao.update(post);
			rePostDao.delete(rePost);
			return true;
		}
		return false;
	}

	public boolean delete(Code code,RePost rePost,Weaver weaver){

		if(code == null ||rePost == null || weaver == null)
			return false;
		
		if(rePost.getWriterName().equals(weaver.getId()) 
				||  weaver.isAdmin()){
			code.rePostCountDown();
			codeDao.update(code);
			rePostDao.delete(rePost);
			return true;
		}
		return false;
	}

	public boolean delete(RePost rePost,Weaver weaver){

		if(rePost == null || weaver == null)
			return false;
		
		if(rePost.getWriterName().equals(weaver.getId()) 
				|| weaver.isAdmin()){
			rePostDao.delete(rePost);
			return true;
		}
		return false;
	}
}