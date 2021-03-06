package com.oborodulin.softreport.domain.service;

import com.oborodulin.softreport.domain.common.service.AbstractJpaDetailService;
import com.oborodulin.softreport.domain.model.project.Project;
import com.oborodulin.softreport.domain.model.project.ProjectRepository;
import com.oborodulin.softreport.domain.model.project.task.Task;
import com.oborodulin.softreport.domain.model.project.task.TaskRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

@Service("jpaTaskService")
@Transactional
public class TaskServiceImpl
		extends AbstractJpaDetailService<Project, Task, ProjectRepository, TaskRepository, String>
		implements TaskService {
	@Autowired
	public TaskServiceImpl(ProjectRepository masterRepository, TaskRepository repository) {
		super(masterRepository, repository, Task.class);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Task> findByProjectId(Long id, Sort sort) {
		return this.repository.findByMaster_Id(id, sort);
	}
}
