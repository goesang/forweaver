package com.forweaver.domain;

import java.io.Serializable;


/** <pre> 회원 활동 정보를 담은 클래스. 
 * postCount 글 갯수
 * rePostCount 답글 갯수
 * postPush 글 추천 수
 * myRePostCount 내가 올린 답변수
 * myRePostPush 내가 올린 답변의 추천수
 * myReplysCount 내가 올린 댓글 수
 * projectPush 프로젝트 추천수
 * lectureCount 강의 갯수
 * joinLectureCount 가입한 강의 갯수
 * projectCount 프로젝트 갯수
 * joinProjectCount 가입한 프로젝트 갯수
 * forkProjectCount 프로젝트 파생된 수
 * studentCount 가르치는 학생수
 * codeCount 올린 코드 갯수
 * codeDownloadCount 올린 코드의 다운로드 횟수
 * codeRePostCount 올린 코드의 답변수
 * replysCount 댓글을 단 갯수
 * score 총 점수
 */
public class WeaverInfo implements Serializable {
	
	static final long serialVersionUID = 1322424;
	
	private long postCount;
	private long rePostCount;
	private long postPush;
	private long myRePostCount;
	private long myRePostPush;
	private long projectPush;
	private long projectCount;
	private long joinProjectCount;
	private long lectureCount;
	private long forkProjectCount;
	private long studentCount;
	private long codeCount;
	private long codeDownloadCount;
	private long codeRePostCount;
	private long myReplysCount;
	private long replysCount;
	private long joinLectureCount;
	private long score;
		
	public WeaverInfo() {
	}
	

	
	public long getPostCount() {
		return postCount;
	}



	public void setPostCount(long postCount) {
		this.postCount = postCount;
	}



	public long getRePostCount() {
		return rePostCount;
	}



	public void setRePostCount(long rePostCount) {
		this.rePostCount = rePostCount;
	}



	public long getPostPush() {
		return postPush;
	}



	public void setPostPush(long postPush) {
		this.postPush = postPush;
	}



	public long getMyRePostCount() {
		return myRePostCount;
	}



	public void setMyRePostCount(long myRePostCount) {
		this.myRePostCount = myRePostCount;
	}



	public long getMyRePostPush() {
		return myRePostPush;
	}



	public void setMyRePostPush(long myRePostPush) {
		this.myRePostPush = myRePostPush;
	}



	public long getProjectPush() {
		return projectPush;
	}



	public void setProjectPush(long projectPush) {
		this.projectPush = projectPush;
	}



	public long getProjectCount() {
		return projectCount;
	}



	public void setProjectCount(long projectCount) {
		this.projectCount = projectCount;
	}



	public long getLectureCount() {
		return lectureCount;
	}



	public void setLectureCount(long lectureCount) {
		this.lectureCount = lectureCount;
	}



	public long getForkProjectCount() {
		return forkProjectCount;
	}



	public void setForkProjectCount(long forkProjectCount) {
		this.forkProjectCount = forkProjectCount;
	}



	public long getStudentCount() {
		return studentCount;
	}



	public void setStudentCount(long studentCount) {
		this.studentCount = studentCount;
	}



	public long getCodeCount() {
		return codeCount;
	}



	public void setCodeCount(long codeCount) {
		this.codeCount = codeCount;
	}



	public long getCodeDownloadCount() {
		return codeDownloadCount;
	}



	public void setCodeDownloadCount(long codeDownloadCount) {
		this.codeDownloadCount = codeDownloadCount;
	}



	public long getCodeRePostCount() {
		return codeRePostCount;
	}



	public void setCodeRePostCount(long codeRePostCount) {
		this.codeRePostCount = codeRePostCount;
	}



	public long getReplysCount() {
		return replysCount;
	}



	public void setReplysCount(long replysCount) {
		this.replysCount = replysCount;
	}



	public long getScore() {
		return score;
	}



	public void setScore(long score) {
		this.score = score;
	}



	public long getMyReplysCount() {
		return myReplysCount;
	}



	public void setMyReplysCount(long myReplysCount) {
		this.myReplysCount = myReplysCount;
	}

	public long getJoinProjectCount() {
		return joinProjectCount;
	}



	public void setJoinProjectCount(long joinProjectCount) {
		this.joinProjectCount = joinProjectCount;
	}



	public long getJoinLectureCount() {
		return joinLectureCount;
	}



	public void setJoinLectureCount(long joinLectureCount) {
		this.joinLectureCount = joinLectureCount;
	}



	public void updateScore(){
		this.score = 0;
		this.score +=this.postCount;
		this.score +=this.rePostCount;
		this.score +=this.postPush;
		this.score +=this.myRePostCount;
		this.score +=this.myRePostPush;
		this.score +=this.projectPush;
		this.score +=this.forkProjectCount;
		this.score +=this.studentCount;
		this.score +=this.codeCount;
		this.score +=this.codeDownloadCount;
		this.score +=this.codeRePostCount;
		this.score +=this.projectCount;
		this.score +=this.lectureCount;
		this.score +=this.replysCount;
		this.score +=this.myReplysCount;
		this.score +=this.joinProjectCount;
		this.score +=this.joinLectureCount;
	}
	
	
}

