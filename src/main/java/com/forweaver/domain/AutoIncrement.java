package com.forweaver.domain;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class AutoIncrement implements Serializable  {
	static final long serialVersionUID = 3845364L;
	@Id
	private String countName;
	private int count;
		
	public AutoIncrement() {}
	public AutoIncrement(String countName) {
		super();
		this.countName = countName;
		this.count = 1;
	}
	public String getCountName() {
		return countName;
	}
	public void setCountName(String countName) {
		this.countName = countName;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public int increament(){
		return ++count;
	}
}
