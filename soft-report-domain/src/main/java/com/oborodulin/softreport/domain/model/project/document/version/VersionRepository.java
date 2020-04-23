package com.oborodulin.softreport.domain.model.project.document.version;

import java.util.Optional;

import com.oborodulin.softreport.domain.common.repository.CommonDetailRepository;
import com.oborodulin.softreport.domain.model.project.document.Document;

public interface VersionRepository extends CommonDetailRepository<Document, Version, String> {

	public Optional<Version> findFirstByMasterOrderByIdDesc(Document master);

}
