package com.oborodulin.softreport.domain.service;

import com.oborodulin.softreport.domain.common.service.AbstractJpaDetailTreeService;
import com.oborodulin.softreport.domain.docmodel.CommonDocModelObject;
import com.oborodulin.softreport.domain.docmodel.DocModelObject;
import com.oborodulin.softreport.domain.docmodel.DocumentModel;
import com.oborodulin.softreport.domain.model.dic.doctype.DocType;
import com.oborodulin.softreport.domain.model.dic.valuesset.value.Value;
import com.oborodulin.softreport.domain.model.docobject.DocObject;
import com.oborodulin.softreport.domain.model.project.Project;
import com.oborodulin.softreport.domain.model.project.ProjectRepository;
import com.oborodulin.softreport.domain.model.project.document.Document;
import com.oborodulin.softreport.domain.model.project.document.DocumentRepository;
import com.oborodulin.softreport.domain.model.project.document.version.Version;
import com.oborodulin.softreport.domain.model.software.Software;
import com.oborodulin.softreport.domain.model.software.businessobject.BusinessObject;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service("jpaDocumentService")
@Transactional
public class DocumentServiceImpl
		extends AbstractJpaDetailTreeService<Project, Document, ProjectRepository, DocumentRepository, String>
		implements DocumentService {
	@PersistenceContext
	private EntityManager entityManager;
	@Autowired
	private DocTypeService docTypeService;
	@Autowired
	private VersionService versionService;
	@Autowired
	private DocObjectService docObjectService;
	@Autowired
	private ProjectService projectService;
	@Autowired
	private SoftwareService softwareService;

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
	public Document init(Document document) {
		// устанавливаем заголовок документа
		if (document.getName() != null && !document.getName().isEmpty()) {
			document.setTitle(document.getName());
		} else {
			Project project = document.getMaster();
			List<Software> softwares = project.getSoftwares();
			String parentsPath = null;
			if (softwares.size() == 1) {
				parentsPath = this.softwareService.getParentsPath(softwares.get(0));
			} else {
				parentsPath = this.projectService.getParentsPath(project);
			}
			int lastDotPos = parentsPath.lastIndexOf(".");
			if (lastDotPos == -1) {
				document.setTitle(parentsPath);
			} else {
				document.setParentTitle(parentsPath.substring(0, lastDotPos + 1));
				document.setTitle(parentsPath.substring(lastDotPos + 1));
			}
		}
		// определяем последнюю версию документа
		try {
			document.setLastVersion(document.getVersions().stream().max(Comparator.comparing(Version::getVersionNumber))
					.orElseThrow(NoSuchElementException::new));
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return document;
	}

	/**
	 * Возвращает список объектов данных документа первого-второго уровней
	 * <p>
	 * Таблицы данных, представления, процедуры и функции
	 * 
	 * @return список объектов данных документа первого-второго уровней
	 */
	private List<DocObject> getDataDocObjects(Document document) {
		List<DocObject> docObjects = new ArrayList<>();
		Project project = document.getMaster();
		for (Software software : project.getSoftwares()) {
			for (BusinessObject businessObject : software.getBusinessObjects()) {
				for (DocObject docObject : businessObject.getDocObjects()) {
					docObjects.add(docObject);
				}
			}
		}
		return docObjects;
	}

	@Transactional(readOnly = true)
	private void setDocumentDataModel(CommonDocModelObject parentModelObject, List<DocObject> docObjects) {
		for (DocObject docObject : docObjects) {
			String categ = docObject.getType().getCode();
			CommonDocModelObject modelObject = DocModelObject.builder().pos(docObject.getPos()).categ(categ)
					.name(docObject.getName()).descr(docObject.getDescr()).build();
			switch (categ) {
			case Value.VC_DOT_VWCOLUMN:
			case Value.VC_DOT_DTCOLUMN:
				modelObject.setType(docObject.getDtColumnType());
				modelObject.setIsRequired(docObject.getIsRequired());
				modelObject.setIsUniqueKey(docObject.getIsUniqueKey());
				modelObject.setIsCompositeKey(docObject.getIsCompositeKey());
				modelObject.setDefaultValue(docObject.getDefaultValue());
				modelObject.setForeignKey(docObject.getForeignKeyString());
				break;
			}
			parentModelObject.addComponent(modelObject);
			this.setDocumentDataModel(modelObject, docObject.getChildren());
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional(readOnly = true)
	public DocumentModel getDocModel(Document document) {
		DocumentModel docModel = new DocumentModel();
		CommonDocModelObject server = DocModelObject.builder().name("localhost").build();
		docModel.addServer(server);
		Project project = document.getMaster();
		for (Software software : project.getSoftwares()) {
			for (BusinessObject businessObject : software.getBusinessObjects()) {
				// получаем объекты данных первого-второго уровня (таблицы данных,
				// представления, процедуры и функции)
				for (DocObject docObject : businessObject.getDocObjects()) {
					DocObject dataBase = this.docObjectService.getDataObjectDb(docObject);
					CommonDocModelObject docDataBase = server.getComponent(dataBase.getName());
					if (docDataBase == null) {
						docDataBase = DocModelObject.builder().pos(docObject.getPos())
								.categ(dataBase.getType().getCode()).type(dataBase.getDbType().getCode())
								.name(dataBase.getName()).descr(dataBase.getDescr()).build();
						server.addComponent(docDataBase);
					}
					String categ = docObject.getType().getCode();
					CommonDocModelObject modelObject = DocModelObject.builder().pos(docObject.getPos()).categ(categ)
							.name(docObject.getFullName()).descr(docObject.getDescr()).sqlQuery(docObject.getSqlQuery())
							.build();
					modelObject.addBusinessObject(businessObject.getName());
					if (categ.equals(Value.VC_DOT_DT)) {
						modelObject.setType(docObject.getDtType().getCode());
					}
					docDataBase.addComponent(modelObject);
					this.setDocumentDataModel(modelObject, docObject.getChildren());

					// формирование структуры ui (формы/веб-формы->компоненты)
					/*
					 * for (DocObject uiForm :
					 * this.docObjectService.getDataObjectUiForms(docObject)) { if
					 * (uiModel.get(uiForm) == null) { uiModel.put(uiForm, new HashSet<>()); }
					 * uiModel.get(uiForm).addAll(new HashSet<DocObject>(uiForm.getChildren())); }
					 */
				}
			}
		}
		return docModel;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Comparator<CommonDocModelObject> getDocObjectComparator() {
		return (CommonDocModelObject o1, CommonDocModelObject o2) -> o1.getPos().compareTo(o2.getPos());
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional
	public void fix(Document document) {
		AuditReader auditReader = AuditReaderFactory.get(entityManager);
		for (DocObject docObject : this.getDataDocObjects(document)) {
			List<Number> revisionNumbers = auditReader.getRevisions(DocObject.class, docObject.getId());
			for (Number rev : revisionNumbers) {
				DocObject auditedDocObject = auditReader.find(DocObject.class, docObject.getId(), rev);
				log.info("DocObject [" + auditedDocObject + "] at revision [" + rev + "].");
			}
		}
		log.info("End revisions");
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
