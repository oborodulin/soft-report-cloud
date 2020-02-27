package com.oborodulin.softreport.domain.service;

import java.util.List;
import java.util.Optional;

import com.oborodulin.softreport.domain.model.CommonJpaTreeService;
import com.oborodulin.softreport.domain.model.software.Software;
import com.oborodulin.softreport.domain.model.valuesset.value.Value;

public interface SoftwareService extends CommonJpaTreeService<Software, String> {
	public Software getById(Long id);

	public List<Software> findByIdIsNot(Long id);

	public List<Software> findByParentIsNull();

	public List<Software> findByTypeCode(String typeCode);

	public Software getNewChild(Long parentId);

	public List<Value> getTypes();
}
