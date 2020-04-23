package com.oborodulin.softreport.domain.service;

import com.oborodulin.softreport.domain.common.service.AbstractJpaDetailTreeService;
import com.oborodulin.softreport.domain.model.dic.doctype.DocType;
import com.oborodulin.softreport.domain.model.docobject.DocObject;
import com.oborodulin.softreport.domain.model.project.Project;
import com.oborodulin.softreport.domain.model.project.ProjectRepository;
import com.oborodulin.softreport.domain.model.project.document.Document;
import com.oborodulin.softreport.domain.model.project.document.DocumentRepository;
import com.oborodulin.softreport.domain.model.software.Software;
import com.oborodulin.softreport.domain.model.software.businessobject.BusinessObject;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service("jpaDocumentService")
@Transactional
public class DocumentServiceImpl
		extends AbstractJpaDetailTreeService<Project, Document, ProjectRepository, DocumentRepository, String>
		implements DocumentService {
	protected static final String MA_DOC_SIGNATORIES = "signatories";
	protected static final String MA_DOC_TERMS = "terms";
	protected static final String MA_DOC_DATAMODEL = "dataModel";
	protected static final String MA_DOC_UIMODEL = "uiModel";
	@Autowired
	private DocTypeService docTypeService;
	@Autowired
	private VersionService versionService;

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
		for (DocType docType : this.docTypeService.findAllOrderByCateg()) {
			log.debug("DocType = " + docType);
			if (types.get(docType.getCateg().getVal()) == null) {
				types.put(docType.getCateg().getVal(), new ArrayList<>());
			}
			types.get(docType.getCateg().getVal()).add(docType);
		}
		return types;
	};

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getView(Document document) {
		return document.getType().getTemplate();
	};

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional(readOnly = true)
	public Map<String, Set<DocObject>> getDocModel(Document document) {
		Map<String, Set<DocObject>> docModel = new HashMap<>();
		docModel.put(MA_DOC_DATAMODEL, new HashSet<>());
		Project project = document.getMaster();
		for (Software software : project.getSoftwares()) {
			for (BusinessObject businessObject : software.getBusinessObjects()) {
				for (DocObject docObject : businessObject.getDocObjects()) {
					docModel.get(MA_DOC_DATAMODEL).add(docObject);
				}
			}
		}
		return docModel;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public Optional<Document> save(Long masterId, Document entity) {
		Optional<Document> document = super.save(masterId, entity);
		if (document.isPresent()) {
			this.versionService.getNextVersion(document.get());
			return this.save(document.get());
		}
		return document;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public Optional<Document> save(Long masterId, Long parentId, Document entity) {
		return super.save(masterId, parentId, entity);
	}

}
