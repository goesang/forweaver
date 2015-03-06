package com.forweaver.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;

import com.mongodb.DBObject;

@Document
public class Weaver implements UserDetails,Serializable {
	
	static final long serialVersionUID = 19900317L;
	
	@Id
	private String id;
	@Length(min=4,max=20)
	private String password;
	private String email;
	private String say;
	private String imgSrc;
	private Data image;
	private Date joinDate;
	private List<Pass> passes = new ArrayList<Pass>();
	
	@Transient
	private DBObject weaverInfo;
	
	public Weaver(){}
	
	
	public Weaver(String id,String email){
		this.id = id;
		this.email = email;
	}
	
	public Weaver(String id,String email,Data image){
		this.id = id;
		this.email = email;
		this.image = image;
	}
	

	
	public Weaver(String id,String password,String email,String say,Data image){
		this.id = id;
		this.password = password;
		this.email = email;
		this.say = say;
		this.image = image;
		this.joinDate = new Date();
	}
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public List<Pass> getPasses() {
		return passes;
	}
	public void setPasses(List<Pass> passes) {
		this.passes = passes;
	}
	
		
	public String getSay() {
		if(this.say != null)
			return say;
		else
			return "Hello World!";
	}


	public void setSay(String say) {
		this.say = say;
	}


	public Pass getPass(String joinName){
		for(Pass pass : this.passes){
			if(pass.getJoinName().equals(joinName))
				return pass;
		}
		return null;
	}
	
	public void addPass(Pass pass){
		this.passes.add(pass);
	}
	
	
	public boolean deletePass(String joinName){
		for(int i = 0 ; i<this.passes.size();i++){
			if(this.passes.get(i).getJoinName().equals(joinName)){
				this.passes.remove(i);
				return true;
			}
		}
		return false;
	}
	
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return this.passes;
	}
	public String getUsername() {
		// TODO Auto-generated method stub
		return this.id;
	}
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}


	public Data getImage() {
		return image;
	}
	

	public void setImage(Data image) {
		this.image = image;
	}
	
	public void setImgSrc(String imgSrc) {
		this.imgSrc = imgSrc;
	}

	public String getRealImgSrc() {
		return this.imgSrc;
	}
	
	public String getImgSrc() {
		return "/"+this.id+"/img/";
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}

	
	public List<String> getPassJoinNames(){
		List<String> passNames = new ArrayList<String>();
		for(Pass pass : this.passes){
			passNames.add(pass.getJoinName());
		}
		return passNames;
	}
	
	public List<String> getPrivateTags(){
		List<String> passNames = new ArrayList<String>();
		for(Pass pass : this.passes){
			if(!pass.getJoinName().startsWith("ROLE"))
			passNames.add("@"+pass.getJoinName());
		}
		passNames.add("$"+this.id);
		return passNames;
	}


	public DBObject getWeaverInfo() {
		return weaverInfo;
	}
	
	public String getInfo(String field) {
		Object value = weaverInfo.get(field);
		if(value == null)
			value = "0";
		return value.toString();
	}

	public String getInfo(String field,String search) {
		Object value = weaverInfo.get(field);
		if(value == null)
			value = "0";
		return ""+StringUtils.countOccurrencesOf(value.toString(),search);
	}

	public void setWeaverInfo(DBObject weaverInfo) {
		this.weaverInfo = weaverInfo;
	}
	
	public boolean isAdminWeaver(String joinName){
		for(Pass pass : this.passes){
			if(pass.getJoinName().equals(joinName) && pass.getPermission() > 0)
				return true;
		}
		return false;
	}
	
	public boolean isJoinWeaver(String joinName){
		for(Pass pass : this.passes){
			if(pass.getJoinName().equals(joinName) && pass.getPermission() == 0)
				return true;
		}
		return false;
	}
	
	public List<String> getPassNames(){
		List<String> passNames = new ArrayList<String>();
		for(Pass pass:this.passes)
			passNames.add(pass.getJoinName());
		return passNames;
	}
	
	public boolean isAdmin(){
		return this.getPass("ROLE_ADMIN") != null;
	}


	public Date getJoinDate() {
		return joinDate;
	}


	public void setJoinDate(Date joinDate) {
		this.joinDate = joinDate;
	}
	
	
}
