package com.oborodulin.softreport.domain.service;

import java.util.List;
import java.util.Map;
import com.oborodulin.softreport.domain.common.service.CommonJpaTreeService;
import com.oborodulin.softreport.domain.model.project.Project;
import com.oborodulin.softreport.domain.model.software.Software;

public interface ProjectService extends CommonJpaTreeService<Project, String> {

	public Map<Boolean, List<Software>> getSelectSoftwares();

	public Map<Boolean, List<Software>> getSelectSoftwares(Long id);

	public void save(Project entity, @SuppressWarnings("unchecked") List<String> ... reqParams);

	public void save(Long parentId, Project entity, @SuppressWarnings("unchecked") List<String> ... reqParams);

}
