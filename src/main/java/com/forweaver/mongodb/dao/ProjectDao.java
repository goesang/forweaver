package com.forweaver.mongodb.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.forweaver.domain.Project;
import com.forweaver.domain.Weaver;

@Repository
public class ProjectDao {
	@Autowired
	private MongoTemplate mongoTemplate;
	
	public void insert(Project project) { // 프로젝트를 생성함.
		
		if (!mongoTemplate.collectionExists(Project.class)) {
			mongoTemplate.createCollection(Project.class);
		}
		mongoTemplate.insert(project);
	}
	
	public Project get(String projectName) { // 프로젝트명으로 강의를 가져옴
		Query query = new Query(Criteria.where("_id").is(projectName));
		return mongoTemplate.findOne(query, Project.class);
	}
	
	public void delete(Project project) { // 프로젝트 삭제
		mongoTemplate.remove(project);
	}
	
	public void update(Project project) { // 프로젝트 수정하기
		Query query = new Query(Criteria.where("_id").is(project.getName()));
		Update update = new Update();
		update.set("category", project.getCategory());
		update.set("description", project.getDescription());
		update.set("tags", project.getTags());
		update.set("push", project.getPush());
		mongoTemplate.updateFirst(query, update, Project.class);
	}
	public long countProjects( // 로그인하지 않은 회원이 프로젝트를 셈.
			List<String> tags,
			String search,
			Weaver creator,
			String sort) {
		Criteria criteria = new Criteria();
		
		if(search != null)
			criteria.orOperator(new Criteria("name").regex(search),
					new Criteria("description").regex(search));
		
		if(tags != null)
			criteria.and("tags").all(tags);
		if(creator != null)
			criteria.and("creator").is(creator);
			
		this.filter(criteria, sort);

		return mongoTemplate.count(new Query(criteria), Project.class);
	}
	
	public List<Project> getProjects( // 로그인하지 않은 회원이 프로젝트를 검색
			List<String> tags,
			String search,
			Weaver creator,
			String sort,
			int page, 
			int size) {
		Criteria criteria = new Criteria();
		
		if(search != null)
			criteria.orOperator(new Criteria("name").regex(search),
					new Criteria("description").regex(search));
		
		if(tags != null)
			criteria.and("tags").all(tags);
		if(creator != null)
			criteria.and("creator").is(creator);
		
		this.filter(criteria, sort);
		
		Query query = new Query(criteria);
		query.with(new PageRequest(page-1, size));

		this.sorting(query, sort);
		return mongoTemplate.find(query, Project.class);
	}
	
	public void filter(Criteria criteria,String sort){
		if (sort.equals("push-many")) {
			criteria.and("push").gt(0);
		} else if (sort.equals("push-null")) {
			criteria.and("push").is(0);
		} else if (sort.equals("solo")) {
			criteria.where("adminWeavers").size(1).and("joinWeavers").size(0);
		}
	}
	
	public void sorting(Query query,String sort){
		if (sort.equals("opendate-asc")) {
			query.with(new Sort(Sort.Direction.ASC, "openingDate"));
		} else if (sort.equals("push-null")) {
			query.with(new Sort(Sort.Direction.ASC, "push"));
		} else if (sort.equals("push-many")) {
			query.with(new Sort(Sort.Direction.DESC, "push"));
		} else
			query.with(new Sort(Sort.Direction.DESC, "openingDate"));
	}

}
