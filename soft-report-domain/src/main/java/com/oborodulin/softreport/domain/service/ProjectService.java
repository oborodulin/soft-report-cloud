package com.oborodulin.softreport.domain.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.oborodulin.softreport.domain.common.service.CommonJpaTreeService;
import com.oborodulin.softreport.domain.model.project.Project;
import com.oborodulin.softreport.domain.model.software.Software;

public interface ProjectService extends CommonJpaTreeService<Project, String> {

	public Map<Boolean, List<Software>> getSelectSoftwares();

	public Map<Boolean, List<Software>> getSelectSoftwares(Long id);

	public Optional<Project> save(List<String> softwares, Project entity);

	public Optional<Project> save(List<String> softwares, Long parentId, Project entity);

}
