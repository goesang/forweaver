package com.forweaver.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.forweaver.domain.Pass;
import com.forweaver.domain.Weaver;

@Service
public class TagService {


	public boolean validateTag(List<String> tagList,Weaver weaver) {

		List<String> publicTags = new ArrayList<String>();
		List<String> privateTags = new ArrayList<String>();
		List<String> massageTags = new ArrayList<String>();

		if (tagList.size() == 0)
			return false;
		for (String tag : tagList) {
			if (tag.startsWith("@")){
				if (tag.contains("/"))
					privateTags.add(tag);
				else
					massageTags.add(tag);
			}
			else
				publicTags.add(tag);
		}

		if (privateTags.size() == 0) // 권한을 가진 태그가 없고
			if ((massageTags.size() == 0 && publicTags.size() > 0) || 
					// 개인 태그와 일반 태그 둘중 하나가 없을때
					(massageTags.size() > 0 && publicTags.size() == 0 && weaver != null)) {
				return true;
			}
		if (privateTags.size() >= 2) // 권한을 가진 태그가 2개일때
			return false;

		if (massageTags.size() >= 1) // 권한을 가진 태그가 있고 메세지 태그가 있을때
			return false;

		if (weaver == null) // 권한을 가진 태그가 있는데 로그인 안한 사람일때
			return false;

		for (Pass pass : weaver.getPasses()) { // 권한 검증
			if (privateTags.get(0).
					equals("@" + pass.getJoinName())) {
				return true;
			}
		}

		return false;
	}
	
	public List<String> stringToTagList(String tagNames) {
		List<String> tags = new ArrayList<String>();
		
		if(tagNames == null)
			return tags;	
		
		if(tagNames.startsWith("[")){
			tagNames = tagNames.substring(1, tagNames.length()-1);
		}
			
		for (String tagName : tagNames.split(",")) {
			
			if(tagName.startsWith("\""))
				tagName = tagName.substring(1, tagName.length()-1);
			
			if(tagName.equals(" ") || tagName.length() <= 0)
				break;
			
			tagName = tagName.replace(">", "/");
			tags.add(tagName);
		}
		return tags;
	}
	


}
