package com.forweaver.domain.git;

/** git diff 파일 정보 가져오기
 *
 */
class GitDiffFileInfo  {
	private String filename;
	private String filecontent;
	GitDiffFileInfo(){
		filename=null;
		filecontent="";
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getFilecontent() {
		return filecontent;
	}

	public void setFilecontent(String filecontent) {
		filecontent += filecontent;
	}

	public void setGitDiffFileInfo(String filename, String filecontent) {
		this.filename = filename;
		this.filecontent = filecontent;
	}
}
