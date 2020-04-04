package com.oborodulin.softreport.domain.service;

import com.oborodulin.softreport.domain.common.service.CommonJpaDetailTreeService;
import com.oborodulin.softreport.domain.model.project.Project;
import com.oborodulin.softreport.domain.model.project.document.Document;

public interface DocumentService extends CommonJpaDetailTreeService<Project, Document, String> {


}
