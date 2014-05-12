package com.forweaver.domain.git;

public class UserCommitCount{
	private String userName;
	private String email;
	private int insertCount;
	private int deleteCount;
	private int commitCount;
	private int fileChangeCount;
	public UserCommitCount(String userName,String email,int insertCount,
			int deleteCount,int fileChangeCount){
		this.userName = userName;
		this.insertCount = insertCount;
		this.deleteCount = deleteCount;
		this.commitCount = 1;
		this.fileChangeCount = fileChangeCount;
		this.email = email;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public int getInsertCount() {
		return insertCount;
	}
	public void setInsertCount(int insertCount) {
		this.insertCount = insertCount;
	}
	
	public void plusInsertCount(int insertCount) {
		this.insertCount += insertCount;
	}
	
	public int getDeleteCount() {
		return deleteCount;
	}
	public void setDeleteCount(int deleteCount) {
		this.deleteCount = deleteCount;
	}
	
	public void plusDeleteCount(int deleteCount) {
		this.deleteCount += deleteCount;
	}
	
	public int getCommitCount() {
		return commitCount;
	}
	public void setCommitCount(int commitCount) {
		this.commitCount = commitCount;
	}
	
	public void plusCommitCount() {
		this.commitCount++;
	}
	public int getFileChangeCount() {
		return fileChangeCount;
	}
	public void setFileChangeCount(int fileChangeCount) {
		this.fileChangeCount = fileChangeCount;
	}
	
	public void plusFileChangeCount(int fileChangeCount) {
		this.fileChangeCount += fileChangeCount;
	}
		
}
