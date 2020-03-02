package com.oborodulin.softreport.domain.service;

import java.util.List;
import org.springframework.data.domain.Sort;

import com.oborodulin.softreport.domain.common.service.CommonJpaDetailService;
import com.oborodulin.softreport.domain.model.project.Project;
import com.oborodulin.softreport.domain.model.project.task.Task;

public interface TaskService extends CommonJpaDetailService<Project, Task, String> {

	public List<Task> findByProjectId(Long id, Sort sort);

}
