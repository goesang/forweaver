package com.forweaver.domain;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.web.multipart.MultipartFile;

@Document
public class Lecture implements Serializable {

	static final long serialVersionUID = 42313124234L;
	@Id
	private String name; //강의 이름 이게 기본 키
	private int category; // 강의 종류 값이 1이면 공개 강의 2이면 비공개 강의.
	private String description; // 강의 소개
	private Date openingDate; // 강의 시작일
	private String creatorName; // 강의 개설자 이름
	private String creatorEmail; // 강의 개설자 이메일
	private Data image; // 강의 이미지
	private int push; // 강의 추천수
	
	private List<String> tags = new ArrayList<String>(); // 강의의 태그 모음
	
	private List<String> adminWeavers = new ArrayList<String>(); // 관리자들
	private List<String> joinWeavers = new ArrayList<String>(); // 비 관리자 회원들
	
	public Lecture() {
		
	}
	
	public Lecture(String name, int category, String description,
			Weaver weaver,List<String> tagList) {
		super();
		this.name = weaver.getId()+"/"+name;
		this.category = category;
		this.description = description;
		this.openingDate = new Date();
		this.creatorName = weaver.getId();
		this.creatorEmail = weaver.getEmail();
		this.image = weaver.getImage();
		this.adminWeavers.add(creatorName);
		this.tags = tagList;
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

	public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	
	public String getOpeningDateFormat() {
		SimpleDateFormat df = new SimpleDateFormat("yy/MM/dd");
		return df.format(this.openingDate); 
	}

	public String getCreatorEmail() {
		return creatorEmail;
	}

	public void setCreatorEmail(String creatorEmail) {
		this.creatorEmail = creatorEmail;
	}
	public void addAdminWeaver(Weaver weaver){
		this.adminWeavers.add(weaver.getId());
	}
	
	public void addJoinWeaver(Weaver weaver){
		this.joinWeavers.add(weaver.getId());
	}
	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}


	public Data getImage() {
		return image;
	}

	public void setImage(Data image) {
		this.image = image;
	}

	public int getPush() {
		return push;
	}

	public void setPush(int push) {
		this.push = push;
	}
	
	public void push(){
		this.push +=1;
	}

	public List<String> getAdminWeavers() {
		return adminWeavers;
	}

	public void setAdminWeavers(List<String> adminWeavers) {
		this.adminWeavers = adminWeavers;
	}

	public List<String> getJoinWeavers() {
		return joinWeavers;
	}

	public void setJoinWeavers(List<String> joinWeavers) {
		this.joinWeavers = joinWeavers;
	}
	
	
}
