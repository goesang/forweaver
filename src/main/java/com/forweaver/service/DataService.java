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
	
	
	public void delete(Data data) {
		dataDao.delete(data);
	}
	

}