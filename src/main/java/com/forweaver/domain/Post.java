package com.forweaver.domain;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document
public class Post implements Serializable {

	static final long serialVersionUID = 11666661134L;
	@Id
	private int postID;
	private String title;
	private String content;
	private boolean isLong;
	private int kind;
	private Date created;
	private Date recentRePostDate;
	private String writerName;
	private String writerEmail;
	private String imgSrc;
	private int push;
	private int rePostCount;
	private List<String> tags = new ArrayList<String>();
	private List<String> dataIDs = new ArrayList<String>();
	private List<String> dataNames = new ArrayList<String>();
	
	public Post(){}
		
	public Post(Weaver weaver,
			String title, String content,List<String> tags) {
		this.writerName = weaver.getId();
		this.writerEmail = weaver.getEmail();
		this.imgSrc = weaver.getImgSrc();
		this.title = title;
		this.content = content;
		this.created = new Date();
		this.tags = tags;
	}

	public int getPostID() {
		return postID;
	}
	public void setPostID(int postID) {
		this.postID = postID;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	
	public String getFormatCreated() {
		SimpleDateFormat df = new SimpleDateFormat("yy/MM/dd HH:mm:ss");
		return df.format(created); 
	}
	
	public String getWriterName() {
		return writerName;
	}
	public void setWriterName(String writerName) {
		this.writerName = writerName;
	}
	public int getPush() {
		return push;
	}
	public void setPush(int push) {
		this.push = push;
	}
	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	public boolean isLong() {
		return isLong;
	}
	public void setLong(boolean isLong) {
		this.isLong = isLong;
	}
	public String getWriterEmail() {
		return writerEmail;
	}
	public void setWriterEmail(String writerEmail) {
		this.writerEmail = writerEmail;
	}
	
	public void addTag(String tag){
		this.tags.add(tag);
	}

	public int getRePostCount() {
		return rePostCount;
	}

	public void setRePostCount(int rePostCount) {
		this.rePostCount = rePostCount;
	}
	
	public void rePostCountDown() {
		this.rePostCount -=1;
	}


	public Date getRecentRePostDate() {
		return recentRePostDate;
	}

	public void setRecentRePostDate(Date recentRePostDate) {
		this.recentRePostDate = recentRePostDate;
	}

	public String getImgSrc(){
		return imgSrc;		
	}

	public void setImgSrc(String imgSrc) {
		this.imgSrc = imgSrc;
	}

	public void push(){
		this.push +=1;
	}
	
	public void addRePostCount(){
		this.rePostCount +=1;
	}

	public int getKind() {
		return kind;
	}

	public void setKind(int kind) {
		this.kind = kind;
	}

	public void deleteTag(String tag){
		int i=0;
		for(String tmpTag : this.tags){
			if(tmpTag.equals(tag))
				this.tags.remove(i);
		}
	}

	public List<String> getDataIDs() {
		return dataIDs;
	}
	
	public String dataNameToDataID(String dataName) {
		
		for(int i = 0 ; i< dataNames.size() ; i++){
			if(dataNames.get(i).equals(dataName)){
				return dataIDs.get(i);
			}
		}
		return "";
	}

	public void setDataIDs(List<String> dataIDs) {
		this.dataIDs = dataIDs;
	}

	public List<String> getDataNames() {
		return dataNames;
	}

	public void setDataNames(List<String> dataNames) {
		this.dataNames = dataNames;
	}

	public void insertDataList(Map<String,String> fileMap){
		this.dataNames.clear(); 
		this.dataIDs.clear(); 
		
		for(Object dataID: fileMap.values())
			this.dataIDs.add((String) dataID);
		
		for(Object dataName: fileMap.keySet())
			this.dataNames.add((String) dataName);
		
	}
	
	public void deleteData(String name){
		for(int i = 0 ; i< dataNames.size() ; i++){
			if(dataNames.get(i).equals(name)){
				dataNames.remove(i);
				dataIDs.remove(i);
				return;
			}
		}
		
	}


	
	
	
}
