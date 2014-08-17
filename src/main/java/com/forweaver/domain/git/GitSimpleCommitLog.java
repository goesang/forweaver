package com.forweaver.domain.git;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.eclipse.jgit.revwalk.RevCommit;

import com.forweaver.util.WebUtil;

public class GitSimpleCommitLog implements Serializable {

	static final long serialVersionUID = 23434L;
	private String commitLogID;
	private String shortMassage;
	private String commiterName;
	private String commiterEmail;
	private Date commitDate;
	private int commitDateInt;
	
	public GitSimpleCommitLog(String commitLogID, String shortMassage,
			String commiterName, String commiterEmail,
			int commitDate) {
		this.commitLogID = commitLogID;
		this.shortMassage = shortMassage;
		this.commiterName = commiterName;
		this.commiterEmail = commiterEmail;
		this.commitDate = new Date(commitDate*1000L);
	}
	
	public GitSimpleCommitLog(RevCommit revCommit){
		this.commitLogID = revCommit.getName();
		this.shortMassage = revCommit.getShortMessage();
		this.commiterName = revCommit.getAuthorIdent().getName();
		this.commiterEmail =  revCommit.getAuthorIdent().getEmailAddress();
		this.commitDate = new Date(revCommit.getCommitTime()*1000L);
	}
	
	public String getCommitLogID() {
		return commitLogID;
	}
	public void setCommitLogID(String commitLogID) {
		this.commitLogID = commitLogID;
	}
	public String getShortMassage() {
		return shortMassage;
	}
	public void setShortMassage(String shortMassage) {
		this.shortMassage = shortMassage;
	}
	public String getCommiterName() {
		return commiterName;
	}
	public void setCommiterName(String commiterName) {
		this.commiterName = commiterName;
	}
	public String getCommiterEmail() {
		return commiterEmail;
	}
	public void setCommiterEmail(String commiterEmail) {
		this.commiterEmail = commiterEmail;
	}
	public String getCommitDate() {
	    SimpleDateFormat sdf = new SimpleDateFormat("yy/MM/dd HH:mm");
	    return sdf.format(this.commitDate);
	}
	public void setCommitDate(Date commitDate) {
		this.commitDate = commitDate;
	}

	public String getImgSrc(){
		return "/"+this.commiterEmail.replace(".", ",")+"/img";
	}


	public int getCommitDateInt() {
		return commitDateInt;
	}

	public void setCommitDateInt(int commitDateInt) {
		this.commitDateInt = commitDateInt;
	}
	
}
