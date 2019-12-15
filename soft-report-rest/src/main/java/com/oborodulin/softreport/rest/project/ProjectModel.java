package com.oborodulin.softreport.rest.project;

import java.util.Date;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import lombok.Getter;
import com.oborodulin.softreport.domain.project.Project;

@Relation(value = "project", collectionRelation = "projects")
public class ProjectModel extends RepresentationModel<ProjectModel> {

	@Getter
	private final String name;

	@Getter
	private final Date createdAt;

	public ProjectModel(Project project) {
		this.name = project.getName();
		this.createdAt = project.getCreatedAt();
	}

}
