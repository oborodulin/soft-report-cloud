package com.oborodulin.softreport.domain.service;

import java.util.List;

import com.oborodulin.softreport.domain.common.service.CommonJpaTreeService;
import com.oborodulin.softreport.domain.model.software.Software;
import com.oborodulin.softreport.domain.model.valuesset.value.Value;

public interface SoftwareService extends CommonJpaTreeService<Software, String> {

	public List<Software> findByTypeCode(String typeCode);

	public List<Value> getTypes();
}
