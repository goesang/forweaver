package com.forweaver.domain.git;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.eclipse.jgit.revwalk.RevCommit;

import com.forweaver.util.WebUtil;

public class GitCommitLog implements Serializable {

	static final long serialVersionUID = 23434L;
	private String commitLogID;
	private String shortMassage;
	private String fullMassage;
	private String commiterName;
	private String commiterEmail;
	private String diff;
	private String note;
	private Date commitDate;
	
	public GitCommitLog(String commitLogID, String shortMassage,String fullMassage,
			String commiterName, String commiterEmail,String diff,String note,
			int commitDate) {
		this.commitLogID = commitLogID;
		this.shortMassage = shortMassage;
		this.fullMassage = fullMassage;
		this.commiterName = commiterName;
		this.commiterEmail = commiterEmail;
		this.note = note;
		this.diff = diff;
		this.commitDate = new Date(commitDate*1000L);
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

	public String getFullMassage() {
		return fullMassage;
	}
	public void setFullMassage(String fullMassage) {
		this.fullMassage = fullMassage;
	}
	public String getDiff() {
		return diff;
	}
	public void setDiff(String diff) {
		this.diff = diff;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}	
	
}
