package com.forweaver.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.forweaver.domain.Data;
import com.forweaver.mongodb.dao.DataDao;

@Service
public class DataService {

	@Autowired
	DataDao dataDao;

	public Data get(String dataID) {
		return dataDao.get(dataID);
	}
	
	public List<Data> get(List<String> dataIDs) {
		return dataDao.get(dataIDs);
	}
	
	public void delete(String dataID) {
		dataDao.delete(dataID);
	}
	
	public void delete(List<String> dataIDs) {
		dataDao.delete(dataIDs);
	}

}