package com.forweaver.service;

import org.springframework.stereotype.Service;

import com.forweaver.domain.Project;
import com.forweaver.domain.Weaver;

@Service
public class PermissionService {

	public boolean projectPermission(Project project, Weaver weaver){
		if(project == null)
			return false;
		if(project.getCategory() == 0)
			return true;
		
		if(weaver == null || weaver.getPass(project.getName()) == null)
			return false;
		return true;
	}
	
}
