package com.forweaver.domain;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

/**<pre> 강의 정보를 담은 클래스. 
 * name 강의 이름 이게 기본 키
 * description  강의 소개
 * openingDate 강의 시작일
 * creator 강의 개설자 정보
 * isJoin 강의 가입 여부 0일떄 미가입 ,1일때 그냥 가입 ,2일때 관리자
 * tags 강의 태그 모음
 * adminWeavers 관리자들
 * joinWeavers 가입자들
 * </pre>
 */
@Document
public class Lecture implements Serializable {

	static final long serialVersionUID = 423234L;
	@Id
	private String name;
	private String description;
	private Date openingDate;
	@DBRef
	private Weaver creator;
	
	@Transient
	private int isJoin;
	
	private List<String> tags = new ArrayList<String>();
	
	@DBRef
	private List<Weaver> adminWeavers = new ArrayList<Weaver>();
	@DBRef
	private List<Weaver> joinWeavers = new ArrayList<Weaver>();
	
	private List<Repo> repos = new ArrayList<Repo>(); 
	
	public Lecture() {
		
	}
	
	public Lecture(String name, String description,
			Weaver weaver,List<String> tagList) {
		super();
		this.name = name;
		this.name = this.name.toLowerCase();
		this.description = description;
		this.openingDate = new Date();
		this.creator = weaver;
		this.adminWeavers.add(weaver);
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
		return this.creator.getId();
	}


	
	public String getOpeningDateFormat() {
		SimpleDateFormat df = new SimpleDateFormat("yy/MM/dd");
		return df.format(this.openingDate); 
	}

	public String getCreatorEmail() {
		return this.creator.getEmail();
	}

	public Weaver getCreator() {
		return creator;
	}

	public void setCreator(Weaver creator) {
		this.creator = creator;
	}

	public void addAdminWeaver(Weaver weaver){
		this.adminWeavers.add(weaver);
	}
	
	public void addJoinWeaver(Weaver weaver){
		this.joinWeavers.add(weaver);
	}
	
	public void removeJoinWeaver(Weaver weaver){
		int index = -1;
		
		for(int i = 0;i<this.joinWeavers.size();i++)
			if(joinWeavers.get(i).getId().equals(weaver.getId()))
				index = i;
		
		if(index >= 0)
			this.joinWeavers.remove(index);
	}
	
	public void removeAdminWeaver(Weaver weaver){
		int index = -1;
		
		for(int i = 0;i<this.adminWeavers.size();i++)
			if(adminWeavers.get(i).getId().equals(weaver.getId()))
				index = i;
		
		if(index >= 0)
			this.adminWeavers.remove(index);
	}
	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	public List<Weaver> getAdminWeavers() {
		return adminWeavers;
	}

	public void setAdminWeavers(List<Weaver> adminWeavers) {
		this.adminWeavers = adminWeavers;
	}

	public List<Weaver> getJoinWeavers() {
		return joinWeavers;
	}

	public void setJoinWeavers(List<Weaver> joinWeavers) {
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

	public int isJoin() {
		return isJoin;
	}

	public void setJoin(int isJoin) {
		this.isJoin = isJoin;
	}
	public String getImgSrc(){
		return creator.getImgSrc();
	}
	
	public List<Weaver> getWeavers() {
		List<Weaver> weavers = new ArrayList<Weaver>();
		weavers.addAll(this.joinWeavers);
		weavers.addAll(this.adminWeavers);
		return weavers;
	}
	
}
