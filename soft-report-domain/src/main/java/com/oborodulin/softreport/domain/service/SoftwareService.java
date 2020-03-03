package com.oborodulin.softreport.domain.service;

import java.util.List;

import com.oborodulin.softreport.domain.common.service.CommonJpaTreeService;
import com.oborodulin.softreport.domain.model.dic.valuesset.value.Value;
import com.oborodulin.softreport.domain.model.software.Software;

public interface SoftwareService extends CommonJpaTreeService<Software, String> {

	public List<Software> findByTypeCode(String typeCode);

	public List<Value> getTypes();
}
