package com.forweaver.service;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import net.sf.ehcache.search.Attribute;
import net.sf.ehcache.search.Query;
import net.sf.ehcache.search.Result;
import net.sf.ehcache.search.Results;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.forweaver.domain.Data;
import com.forweaver.domain.Weaver;
import com.forweaver.mongodb.dao.DataDao;

@Service
public class DataService {

	@Autowired
	DataDao dataDao;

	@Autowired
	private CacheManager cacheManager;
	
	public Data get(String dataID) {
		Cache tmpCache = cacheManager.getCache("tmp");
		Cache dataCache = cacheManager.getCache("data");
		if(tmpCache.get(dataID) != null)
			return (Data) tmpCache.get(dataID).getValue();
		else if(dataCache.get(dataID) != null)
			return (Data) dataCache.get(dataID).getValue();
		else {
			Data data = dataDao.get(dataID);
			if (data == null)
				return null;
			Element newElement = new Element(data.getId(), data);
			dataCache.put(newElement);
			return data;
		}
			
	}
	
	
	public void delete(Data data) {
		dataDao.delete(data);
	}
	
	public void addTemp(Data data){
		Cache tmpCache = cacheManager.getCache("tmp");
		Cache tmpNameCache = cacheManager.getCache("tmpName");
		tmpCache.put(new Element(data.getId(), data));
		tmpNameCache.put(new Element(data.getWeaverID()+"/"+data.getName(), data.getId()));
	}
	
	public String getObjectID(String dataName,Weaver weaver){
		Cache tmpNameCache = cacheManager.getCache("tmpName");
		return (String)tmpNameCache.get(weaver.getId()+"/"+dataName).getValue();
	}

}