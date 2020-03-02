package com.oborodulin.softreport.domain.model.project.task;

import java.util.List;

import org.springframework.data.domain.Sort;

import com.oborodulin.softreport.domain.common.repository.CommonRepository;

public interface TaskRepository extends CommonRepository<Task, String> {
	public List<Task> findByMasterId(Long id, Sort sort);

}
