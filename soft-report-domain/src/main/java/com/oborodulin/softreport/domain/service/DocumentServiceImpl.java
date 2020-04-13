package com.oborodulin.softreport.domain.service;

import com.oborodulin.softreport.domain.common.service.AbstractJpaDetailTreeService;
import com.oborodulin.softreport.domain.model.dic.doctype.DocType;
import com.oborodulin.softreport.domain.model.dic.valuesset.value.Value;
import com.oborodulin.softreport.domain.model.project.Project;
import com.oborodulin.softreport.domain.model.project.ProjectRepository;
import com.oborodulin.softreport.domain.model.project.document.Document;
import com.oborodulin.softreport.domain.model.project.document.DocumentRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

@Service("jpaDocumentService")
@Transactional
public class DocumentServiceImpl
		extends AbstractJpaDetailTreeService<Project, Document, ProjectRepository, DocumentRepository, String>
		implements DocumentService {
	@Autowired
	private DocTypeService docTypeService;

	@Autowired
	public DocumentServiceImpl(ProjectRepository masterRepository, DocumentRepository repository) {
		super(masterRepository, repository, Document.class);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, List<DocType>> getTypes() {
		Map<String, List<DocType>> types = new HashMap<>();
		Value prevCateg = null;
		List<DocType> categTypes = new ArrayList<>();
		for (DocType docType : this.docTypeService.findAllOrderByCateg()) {
			if (prevCateg == null || prevCateg.equals(docType.getCateg())) {
				categTypes.add(docType);
			} else {
				types.put(prevCateg.getVal(), new ArrayList<>(categTypes));
				categTypes.clear();
			}
			prevCateg = docType.getCateg();
		}
		return types;
	};

}
