package com.oborodulin.softreport.rest.project;

import java.util.Date;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import lombok.Getter;

import com.oborodulin.softreport.domain.model.project.Project;
import com.oborodulin.softreport.rest.software.SoftwareModel;
import com.oborodulin.softreport.rest.software.SoftwareModelAssembler;

@Relation(value = "project", collectionRelation = "projects")
public class ProjectModel extends RepresentationModel<ProjectModel> {
	private static final SoftwareModelAssembler softwareAssembler = new SoftwareModelAssembler();

	@Getter
	private final String name;

	@Getter
	private final Date createdAt;

	@Getter
	private final CollectionModel<SoftwareModel> softwares;

	public ProjectModel(Project project) {
		this.name = project.getName();
		this.createdAt = project.getCreatedAt();
		this.softwares = softwareAssembler.toCollectionModel(project.getSoftwares());
	}

}
