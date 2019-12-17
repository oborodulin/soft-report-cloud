package com.oborodulin.softreport.rest.software;

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

import com.oborodulin.softreport.domain.software.Software;
import com.oborodulin.softreport.domain.software.SoftwareRepository;

@RestController
@RequestMapping(path = "/softwares", produces = "application/json")
@CrossOrigin(origins = "*")
public class SoftwareController {
	private SoftwareRepository softwareRepo;

	@Autowired
	EntityLinks entityLinks;

	public SoftwareController(SoftwareRepository softwareRepo) {
		this.softwareRepo = softwareRepo;
	}

	@GetMapping("/recent")
	public CollectionModel<SoftwareModel> recentProjects() {
		PageRequest page = PageRequest.of(0, 12, Sort	.by("createdAt")
														.descending());
		List<Software> softwares = softwareRepo.findAll(page)
											.getContent();
		CollectionModel<SoftwareModel> recentCollectionModel = new SoftwareModelAssembler().toCollectionModel(softwares);
		recentCollectionModel.add(WebMvcLinkBuilder	.linkTo(WebMvcLinkBuilder.methodOn(SoftwareController.class)
																			.recentProjects())
													.withRel("projects.recent"));
		return recentCollectionModel;
	}

	@PostMapping(consumes = "application/json")
	@ResponseStatus(HttpStatus.CREATED)
	public Software postProject(@RequestBody Software software) {
		return softwareRepo.save(software);
	}

	@GetMapping("/{id}")
	public Software tacoById(@PathVariable("id") Long id) {
		Optional<Software> optProject = softwareRepo.findById(id);
		if (optProject.isPresent()) {
			return optProject.get();
		}
		return null;
	}
}
