package com.forweaver.domain.git;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;



/** git diff 파일 정보 가져오기
 *
 */
class GitDiffSlice{
	private List<GitDiffFileInfo> fileLogList = new ArrayList<GitDiffFileInfo>();
	
	public List<GitDiffFileInfo> getFileLogList() {
		return fileLogList;
	}

	public void setFileLogList(List<GitDiffFileInfo> fileLogList) {
	this.fileLogList = fileLogList;
	}

	public GitDiffSlice(String diff){
		String[] temp = diff.split("\n");
		String slice = "";
		String Filetotal="";	
		int checkdiff = 1;
		try {        
			GitDiffFileInfo loadfile = new GitDiffFileInfo();
			int firstcheck = 1;
			while( checkdiff <= temp.length) {
				if (temp[checkdiff].indexOf("---")!=-1){
				}
				else if (temp[checkdiff].indexOf("+++")!=-1){
				}
				else if (temp[checkdiff].indexOf("index")!=-1){
				}
				else{
					if(temp[checkdiff].indexOf("--git")!=-1){
						if(firstcheck==1){
							firstcheck=0;
						}
						else{
							fileLogList.add(loadfile);
						}
						
						loadfile = new GitDiffFileInfo();
						int langs = temp[checkdiff].lastIndexOf("b/");
						int ext = temp[checkdiff].lastIndexOf(".");
						int length = temp[checkdiff].length();
						String filename = temp[checkdiff].substring(langs+1,ext);
						String extname = temp[checkdiff].substring(ext+1,length);

					}
					else{
						loadfile.setFilecontent(temp[checkdiff]+"\n");
					}
					
				}	

				checkdiff++;
				
			}
			fileLogList.add(loadfile);
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

}
