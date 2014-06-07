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
	private String description; // 강의 소개
	private Date openingDate; // 강의 시작일
	private String creatorName; // 강의 개설자 이름
	private String creatorEmail; // 강의 개설자 이메일
	private int push; // 강의 추천수
	
	private List<String> tags = new ArrayList<String>(); // 강의의 태그 모음
	
	private List<String> adminWeavers = new ArrayList<String>(); // 관리자들
	private List<String> joinWeavers = new ArrayList<String>(); // 비 관리자 회원들
	
	private List<Repo> repos = new ArrayList<Repo>(); 
	
	public Lecture() {
		
	}
	
	public Lecture(String name, String description,
			Weaver weaver,List<String> tagList) {
		super();
		this.name = weaver.getId()+"/"+name;
		this.description = description;
		this.openingDate = new Date();
		this.creatorName = weaver.getId();
		this.creatorEmail = weaver.getEmail();
		this.adminWeavers.add(creatorName);
		this.tags = tagList;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
	
	public void removeJoinWeaver(Weaver weaver){
		this.joinWeavers.remove(weaver.getId());
	}
	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
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

	public List<Repo> getRepos() {
		return repos;
	}

	public void setRepos(List<Repo> repos) {
		this.repos = repos;
	}
	
	public void addRepo(Repo repo){
		this.repos.add(repo);
	}
	
	public void removeRepo(Repo repo){
		this.repos.remove(repo);
	}
	
	public Repo getRepo(String repoName){
		for(Repo repo: this.repos)
			if(repo.getName().equals(repoName))
				return repo;
		return null;
	}
	
}
