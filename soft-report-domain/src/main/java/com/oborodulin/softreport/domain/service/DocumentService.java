package com.oborodulin.softreport.domain.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.oborodulin.softreport.domain.common.service.CommonJpaDetailTreeService;
import com.oborodulin.softreport.domain.model.dic.doctype.DocType;
import com.oborodulin.softreport.domain.model.docobject.DocObject;
import com.oborodulin.softreport.domain.model.project.Project;
import com.oborodulin.softreport.domain.model.project.document.Document;

public interface DocumentService extends CommonJpaDetailTreeService<Project, Document, String> {

	public Map<String, List<DocType>> getTypes();

	public String getView(Document document);

	public Map<String, Map<DocObject, Set<DocObject>>> getDocModel(Document document);

}
