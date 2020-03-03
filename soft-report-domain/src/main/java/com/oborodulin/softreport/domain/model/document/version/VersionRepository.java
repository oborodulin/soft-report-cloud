package com.oborodulin.softreport.domain.model.document.version;

import java.util.List;

import org.springframework.data.domain.Sort;

import com.oborodulin.softreport.domain.common.repository.CommonRepository;

public interface VersionRepository extends CommonRepository<Version, String> {
	public List<Version> findByMasterId(Long id, Sort sort);

}
