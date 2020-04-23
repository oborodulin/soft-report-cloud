package com.oborodulin.softreport.domain.service;

import com.oborodulin.softreport.domain.common.service.AbstractJpaTreeService;
import com.oborodulin.softreport.domain.model.project.*;
import com.oborodulin.softreport.domain.model.software.Software;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

@Service("jpaProjectService")
@Transactional
public class ProjectServiceImpl extends AbstractJpaTreeService<Project, ProjectRepository, String>
		implements ProjectService {
	@Autowired
	private SoftwareService softwareService;

	@Autowired
	public ProjectServiceImpl(ProjectRepository repository) {
		super(repository, Project.class);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<Boolean, List<Software>> getSelectSoftwares() {
		Map<Boolean, List<Software>> softwares = new HashMap<>();
		softwares.put(false, this.softwareService.findAll());
		return softwares;
	};

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<Boolean, List<Software>> getSelectSoftwares(Long id) {
		Map<Boolean, List<Software>> softwares = new HashMap<>();
		Project project = this.repository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid Id:" + id));
		List<Software> prjSoftwares = project.getSoftwares();
		if (prjSoftwares.isEmpty()) {
			return this.getSelectSoftwares();
		}
		for (Software software : this.softwareService.findAll()) {
			if (prjSoftwares.contains(software)) {
				if (softwares.get(true) == null) {
					softwares.put(true, new ArrayList<>());
				}
				softwares.get(true).add(software);
			} else {
				if (softwares.get(false) == null) {
					softwares.put(false, new ArrayList<>());
				}
				softwares.get(false).add(software);
			}
		}
		return softwares;
	};

	private Project setSelectedEntities(Project entity, @SuppressWarnings("unchecked") List<String>... reqParams) {
		List<String> softwares = reqParams[0];
		List<Software> projectSoftwares = entity.getSoftwares();
		for (Software software : projectSoftwares) {
			if (!softwares.contains(software.getId().toString())) {
				entity.removeSoftware(software);
			}
		}
		for (String softwareId : softwares) {
			Software software = this.softwareService.findById(Long.parseLong(softwareId))
					.orElseThrow(() -> new IllegalArgumentException("Invalid software Id:" + softwareId));
			if (!projectSoftwares.contains(software)) {
				entity.addSoftware(software);
			}
		}
		return entity;
	};

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public Optional<Project> save(Project entity, @SuppressWarnings("unchecked") List<String>... reqParams) {
		return this.save(this.setSelectedEntities(entity, reqParams));
	};

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public Optional<Project> save(Long parentId, Project entity, @SuppressWarnings("unchecked") List<String>... reqParams) {
		entity.setParent(this.repository.findById(parentId)
				.orElseThrow(() -> new IllegalArgumentException("Invalid software parent Id:" + parentId)));
		return this.save(this.setSelectedEntities(entity, reqParams));
	};

}
