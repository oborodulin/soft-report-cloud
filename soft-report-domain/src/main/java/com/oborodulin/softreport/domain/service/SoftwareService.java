package com.oborodulin.softreport.domain.service;

import java.util.List;

import com.oborodulin.softreport.domain.model.CommonJpaService;
import com.oborodulin.softreport.domain.model.software.Software;

public interface SoftwareService extends CommonJpaService<Software, String> {
	public Software getById(Long id);

	public List<Software> findByTypeCode(String typeCode);
}
