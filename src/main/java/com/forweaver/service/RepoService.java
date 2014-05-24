/*package com.forweaver.service;

import net.sf.ehcache.CacheManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.forweaver.dao.RepoDao;
import com.forweaver.domain.Repo;

@Service
public class RepoService {
	@Autowired
	private RepoDao repoDao;

	@Autowired
	private CacheManager cacheManager;
	
	public void add(Repo repo) {
		repoDao.add(repo);
		cacheManager.getCache("lecture").remove(repo.getLectureName());
	}
	public void delete(Repo repo){
		repoDao.delete(repo);
		cacheManager.getCache("lecture").remove(repo.getLectureName());
	}
	
	public Repo get(String name,String lectureName){
		return repoDao.get(name, lectureName);
	}
	
}
*/