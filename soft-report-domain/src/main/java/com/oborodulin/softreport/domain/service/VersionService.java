package com.oborodulin.softreport.domain.service;

import com.oborodulin.softreport.domain.common.service.CommonJpaDetailService;
import com.oborodulin.softreport.domain.model.project.document.Document;
import com.oborodulin.softreport.domain.model.project.document.version.Version;

public interface VersionService extends CommonJpaDetailService<Document, Version, String> {

	public Version getNextVersion(Document document);

}
