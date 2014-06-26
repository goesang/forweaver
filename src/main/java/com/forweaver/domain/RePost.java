package com.forweaver.domain;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class RePost implements Serializable {

	static final long serialVersionUID = 57346461551669134L;
	@Id
	private int rePostID;
	private int originalPostID;
	private String content;
	private Date created;
	private String writerName;
	private String writerEmail;
	private String imgSrc;
	private int push;
	private Date recentReplyDate;
	private int kind; // 1이 일반 공개글의 답변, 2가 비밀 글 답변 , 3이 메세지글 답변 , 4가 코드의 답변.

	private List<String> dataIDs = new ArrayList<String>();
	private List<String> dataNames = new ArrayList<String>();
	private List<Reply> replys = new ArrayList<Reply>();

	public RePost() {
	}

	public RePost(int originalPostID, Weaver weaver, String content,int kind) {
		this.writerName = weaver.getId();
		this.writerEmail = weaver.getEmail();
		this.imgSrc = weaver.getImgSrc();
		this.content = content;
		this.kind = kind;
		this.created = new Date();
		this.originalPostID = originalPostID;
	}

	public int getRePostID() {
		return rePostID;
	}

	public void setRePostID(int rePostID) {
		this.rePostID = rePostID;
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
		SimpleDateFormat df = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
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

	public int getOriginalPostID() {
		return originalPostID;
	}

	public void setOriginalPostID(int originalPostID) {
		this.originalPostID = originalPostID;
	}

	public String getWriterEmail() {
		return writerEmail;
	}

	public void setWriterEmail(String writerEmail) {
		this.writerEmail = writerEmail;
	}

	public String getImgSrc() {
		return imgSrc;
	}

	public void setImgSrc(String imgSrc) {
		this.imgSrc = imgSrc;
	}

	public void push() {
		this.push += 1;
	}

	public List<Reply> getReplys() {
		return replys;
	}

	public void setReplys(List<Reply> replys) {
		this.replys = replys;
	}

	public void addReply(Reply reply) {
		if (this.replys.size() == 0)
			reply.setNumber(1);
		else
			reply.setNumber(this.replys.get(0).getNumber() + 1);
		this.replys.add(0, reply);
	}

	public void updateReply(Reply reply, Weaver weaver, int number) {
		for (Reply tmpReply : this.replys) {
			if (tmpReply.getNumber() == number
					&& weaver.getId().equals(tmpReply.getWriterName()))
				tmpReply = reply;

		}
	}

	public boolean removeReply(Weaver weaver, int number) {
		for (int i = 0; i < this.replys.size(); i++) {
			if (this.replys.get(i).getNumber() == number
					&& weaver.getId()
							.equals(this.replys.get(i).getWriterName()))
				this.replys.remove(i);
			return true;
		}
		return false;
	}

	public Date getRecentReplyDate() {
		return recentReplyDate;
	}

	public void setRecentReplyDate(Date recentReplyDate) {
		this.recentReplyDate = recentReplyDate;
	}

	public List<String> getDataIDs() {
		return dataIDs;
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

	public void insertDataList(Map<String, String> fileMap) {
		this.dataNames.clear();
		this.dataIDs.clear();

		for (Object dataID : fileMap.values())
			this.dataIDs.add((String) dataID);

		for (Object dataName : fileMap.keySet())
			this.dataNames.add((String) dataName);

	}

	public void deleteData(String name) {
		for (int i = 0; i < dataNames.size(); i++) {
			if (dataNames.get(i).equals(name)) {
				dataNames.remove(i);
				dataIDs.remove(i);
				return;
			}
		}
	}

	public String dataNameToDataID(String dataName) {

		for (int i = 0; i < dataNames.size(); i++) {
			if (dataNames.get(i).equals(dataName)) {
				return dataIDs.get(i);
			}
		}
		return "";
	}

	public int getKind() {
		return kind;
	}

	public void setKind(int kind) {
		this.kind = kind;
	}
	

}
