package com.forweaver.domain;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.data.mongodb.core.mapping.DBRef;

public class Repo implements Serializable {

	static final long serialVersionUID = 77777773L;

	private int repoID;
	private String name;
	private int category;
	private String description;
	private Date openingDate;
	private Date deadLine;
	private String LectureName;
	@DBRef
	private Weaver creator;
	
	public Repo() {
		
	}
	
	public Repo(String name, int category, String description,int deadLine,Lecture lecture,Weaver creator) {
		this.name = name;
		this.category = category;
		this.description = description;
		this.openingDate = new Date();
		int day = (1000*60*60*24);
		if(deadLine == 0)
			this.deadLine = new Date(this.openingDate.getTime()+day*7);
		else if(deadLine == 1)
			this.deadLine = new Date(this.openingDate.getTime()+day*30);
		else if(deadLine == 2)
			this.deadLine = new Date(this.openingDate.getTime()+day*30*3);
		else if(deadLine == 3)
			this.deadLine = null;
		this.creator = creator;
		this.LectureName = lecture.getName();
	}

	public int getRepoID() {
		return repoID;
	}

	public void setRepoID(int repoID) {
		this.repoID = repoID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCategory() {
		return category;
	}

	public void setCategory(int category) {
		this.category = category;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getOpeningDate() {
		return openingDate;
	}

	public void setOpeningDate(Date openingDate) {
		this.openingDate = openingDate;
	}

	public Date getDeadLine() {
		return deadLine;
	}

	public void setDeadLine(Date deadLine) {
		this.deadLine = deadLine;
	}

	

	public String getLectureName() {
		return LectureName;
	}

	public void setLectureName(String lectureName) {
		LectureName = lectureName;
	}

	public Weaver getCreator() {
		return creator;
	}

	public void setCreator(Weaver creator) {
		this.creator = creator;
	}

	public String getOpeningDateFormat() {
		SimpleDateFormat df = new SimpleDateFormat("yy/MM/dd");
		return df.format(this.openingDate); 
	}

	public String getDeadLineFormat() {
		if(this.deadLine == null)
			return "기한 없음";
		
		SimpleDateFormat df = new SimpleDateFormat("yy/MM/dd");
		return df.format(this.deadLine); 
	}
	
	public int getDDay() {
		if(this.deadLine == null)
			return -2;
		 long left =  deadLine.getTime() - System.currentTimeMillis();
		 int leftDay = (int)Math.floor(left/(1000*60*60*24)+1);
		 if(leftDay < 0)
			 return -1;
		 
		 return leftDay;
	}
		
}
