package com.oborodulin.softreport.rest.project;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.EntityLinks;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.oborodulin.softreport.domain.project.Project;
import com.oborodulin.softreport.domain.project.ProjectRepository;

@RestController
@RequestMapping(path = "/projects", produces = "application/json")
@CrossOrigin(origins = "*")
public class ProjectRestController {
	private ProjectRepository projectRepo;

	@Autowired
	EntityLinks entityLinks;

	public ProjectRestController(ProjectRepository projectRepo) {
		this.projectRepo = projectRepo;
	}

	@GetMapping("/recent")
	public CollectionModel<ProjectModel> recentProjects() {
		PageRequest page = PageRequest.of(0, 12, Sort	.by("createdAt")
														.descending());
		List<Project> projects = projectRepo.findAll(page)
											.getContent();
		CollectionModel<ProjectModel> recentCollectionModel = new ProjectModelAssembler().toCollectionModel(projects);
		recentCollectionModel.add(WebMvcLinkBuilder	.linkTo(WebMvcLinkBuilder.methodOn(ProjectRestController.class)
																			.recentProjects())
													.withRel("projects.recent"));
		return recentCollectionModel;
	}

	@PostMapping(consumes = "application/json")
	@ResponseStatus(HttpStatus.CREATED)
	public Project postProject(@RequestBody Project project) {
		return projectRepo.save(project);
	}

	@GetMapping("/{id}")
	public Project projectById(@PathVariable("id") Long id) {
		Optional<Project> optProject = projectRepo.findById(id);
		if (optProject.isPresent()) {
			return optProject.get();
		}
		return null;
	}
}
