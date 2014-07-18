package com.forweaver.domain;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
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
	@DBRef
	private Weaver writer;
	private int push;
	private int rePostCount;
	private List<String> tags = new ArrayList<String>();
	@DBRef
	private List<Data> datas = new ArrayList<Data>();
	
	public Post(){}
		
	public Post(Weaver weaver,
			String title, String content,List<String> tags) {
		this.writer = weaver;
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
	

	public Weaver getWriter() {
		return writer;
	}

	public void setWriter(Weaver writer) {
		this.writer = writer;
	}
	
	public String getWriterName() {
		return writer.getId();
	}
	
	public String getWriterEmail() {
		return writer.getEmail();
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
		return this.writer.getImgSrc();	
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

	public void addData(Data data){
		this.datas.add(data);
	}
	
	public void deleteData(String name){
		for(int i = 0 ; i< this.datas.size() ; i++){
			if(this.datas.get(i).getName().equals(name)){
				this.datas.remove(i);
				return;
			}
		}
		
	}
	
	public Data getData(String dataName){
		for(Data data:this.datas)
			if(data.getName().equals(dataName))
				return data;
		return null;
	}

	public List<Data> getDatas() {
		return datas;
	}

	public void setDatas(List<Data> datas) {
		this.datas = datas;
	}
	
}
