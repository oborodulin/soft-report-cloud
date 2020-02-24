package com.oborodulin.softreport.domain.model.software;

import java.util.List;

import com.oborodulin.softreport.domain.model.CommonRepository;

public interface SoftwareRepository extends CommonRepository<Software, String> {

	public List<Software> findByTypeCode(String typeCode);
}
