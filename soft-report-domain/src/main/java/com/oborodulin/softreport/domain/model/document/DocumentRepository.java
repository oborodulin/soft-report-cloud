package com.oborodulin.softreport.domain.model.document;

import java.util.List;

import org.springframework.data.domain.Sort;

import com.oborodulin.softreport.domain.common.repository.CommonRepository;
import com.oborodulin.softreport.domain.model.document.version.Version;

public interface DocumentRepository extends CommonRepository<Version, String> {
	public List<Version> findByMasterId(Long id, Sort sort);

}
