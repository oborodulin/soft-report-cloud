package com.oborodulin.softreport.rest.project;

import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import com.oborodulin.softreport.domain.project.Project;

public class ProjectModelAssembler extends RepresentationModelAssemblerSupport<Project, ProjectModel> {

	public ProjectModelAssembler() {
		super(ProjectRestController.class, ProjectModel.class);
	}

	@Override
	protected ProjectModel instantiateModel(Project project) {
		return new ProjectModel(project);
	}

	@Override
	public ProjectModel toModel(Project project) {
		return createModelWithId(project.getId(), project);
	}

}
