package com.oborodulin.softreport.domain.service;

import com.oborodulin.softreport.domain.common.service.JpaTreeAbstractService;
import com.oborodulin.softreport.domain.model.project.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

@Service("jpaProjectService")
@Transactional
public class ProjectServiceImpl extends JpaTreeAbstractService<Project, ProjectRepository, String>
		implements ProjectService {
	//@Autowired
	//private ValueRepository valueRepository;

	@Autowired
	public ProjectServiceImpl(ProjectRepository repository) {
		super(repository, Project.class);
	}

}
