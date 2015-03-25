package com.forweaver.domain.git;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jgit.revwalk.RevCommit;

/** 파일 정보를 담기 위한 클래스
 *
 */
public class GitFileInfo implements Serializable {

	static final long serialVersionUID = 39311473L;
	
	private String name;
	private String content;
	private List<RevCommit> gitLogList = new ArrayList<RevCommit>();
	private int selectCommitIndex;
	private boolean isDirectory;
	private List<GitBlame> gitBlames = new ArrayList<GitBlame>();

	
	public GitFileInfo(){
	}
	
	public GitFileInfo(String name, String content,
			List<RevCommit> gitLogList,int selectCommitIndex,boolean isDirectory) {
		this.name = name;
		this.content = content;
		this.gitLogList = gitLogList;
		this.selectCommitIndex = selectCommitIndex;
		this.isDirectory = isDirectory;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public List<RevCommit> getGitLogList() {
		return gitLogList;
	}
	public void setGitLogList(List<RevCommit> gitLogList) {
		this.gitLogList = gitLogList;
	}
	public int getSelectCommitIndex() {
		return selectCommitIndex;
	}
	public void setSelectCommitIndex(int selectCommitIndex) {
		this.selectCommitIndex = selectCommitIndex;
	}
	
	public RevCommit getSelectCommitLog() {
		if(this.gitLogList.size() <= 0){
			return null;
		}else if(this.gitLogList.size() == 1){
			return this.gitLogList.get(0);
		}else{
			if(this.getSelectCommitIndex() >= this.gitLogList.size())
				return this.gitLogList.get(this.gitLogList.size()-1);
			else
				return this.gitLogList.get(this.getSelectCommitIndex());
		}
	}

	public List<GitBlame> getGitBlames() {
		return gitBlames;
	}

	public void setGitBlames(List<GitBlame> gitBlames) {
		this.gitBlames = gitBlames;
	}

	public boolean isDirectory() {
		return isDirectory;
	}

	public void setDirectory(boolean isDirectory) {
		this.isDirectory = isDirectory;
	}

	
	
	
}
