package com.forweaver.domain.git;

public class GitBlame {

	private String commitID;
	private String userName;
	private String userEmail;
	private String commitTime;
	
	public GitBlame(String commitID, String userName, String userEmail,
			String commitTime) {
		super();
		this.commitID = commitID;
		this.userName = userName;
		this.userEmail = userEmail;
		this.commitTime = commitTime;
	}
	public String getCommitID() {
		return commitID;
	}
	public void setCommitID(String commitID) {
		this.commitID = commitID;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public String getCommitTime() {
		return commitTime;
	}
	public void setCommitTime(String commitTime) {
		this.commitTime = commitTime;
	}
	

	
}
