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
import java.util.Collections;
import java.util.Comparator;
import java.util.NoSuchElementException;
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
		// устанавливаем последнюю версию документа
		try {
			document.setLastVersion(document.getVersions().stream().max(Comparator.comparing(Version::getVersionNumber))
					.orElseThrow(NoSuchElementException::new));
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return document;
	}

	@Transactional(readOnly = true)
	private void setDocumentDataModel(CommonDocModelObject server, CommonDocModelObject docModelObject,
			DocObject dataBase, DocObject docObject) {
		String categ = docObject.getType().getCode();
		switch (categ) {
		case Value.VC_DOT_SCHEMA:
			CommonDocModelObject docSchema = DocModelObject.builder().name(docObject.getName())
					.descr(docObject.getDescr()).build();
			break;
		case Value.VC_DOT_DT:
			CommonDocModelObject docTable = DocModelObject.builder().name(dataBase.getName()).descr(dataBase.getDescr())
					.build();
			break;
		case Value.VC_DOT_VIEW:
			dbViews.put(docObject, new HashSet<DocObject>(doChildren));
			break;
		case Value.VC_DOT_PROC:
			dbProcs.put(docObject, new HashSet<DocObject>(doChildren));
			break;
		case Value.VC_DOT_FUNC:
			dbFuncs.put(docObject, new HashSet<DocObject>(doChildren));
			break;
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
		Map<DocObject, Set<DocObject>> uiModel = new HashMap<>();
		Comparator<DocObject> compareByPos = (DocObject o1, DocObject o2) -> o1.getPos().compareTo(o2.getPos());
		Project project = document.getMaster();
		for (Software software : project.getSoftwares()) {
			for (BusinessObject businessObject : software.getBusinessObjects()) {
				for (DocObject docObject : businessObject.getDocObjects()) {
					DocObject dataBase = this.docObjectService.getDataObjectDb(docObject);
					CommonDocModelObject docDataBase = server.getComponent(dataBase.getName());
					if (docDataBase == null) {
						docDataBase = DocModelObject.builder().categ(dataBase.getType().getCode())
								.name(dataBase.getName()).descr(dataBase.getDescr()).build();
						server.addComponent(docDataBase);
					}
					this.setDocumentDataModel(server, docDataBase, dataBase, docObject);
					// формирование структуры модели данных (БД->таблицы)

					docDataBase = DocModelObject.builder().name(dataBase.getName()).descr(dataBase.getDescr()).build();
					// формирование структуры модели данных (таблица->поля)
					List<DocObject> doChildren = docObject.getChildren();
					// формирование структуры ui (формы/веб-формы->компоненты)
					for (DocObject uiForm : this.docObjectService.getDataObjectUiForms(docObject)) {
						if (uiModel.get(uiForm) == null) {
							uiModel.put(uiForm, new HashSet<>());
						}
						uiModel.get(uiForm).addAll(new HashSet<DocObject>(uiForm.getChildren()));
					}
				}
			}
		}
		docModel.put(MA_DOC_DATAMODEL, dataModel);
		docModel.put(Value.VC_DTT_DIC, dtDic);
		docModel.put(Value.VC_DTT_OPER, dtOper);
		docModel.put(Value.VC_DTT_IFACE, dtIface);
		docModel.put(Value.VC_DTT_LOG, dtLog);
		docModel.put(Value.VC_DTT_TMP, dtTmp);
		docModel.put(Value.VC_DOT_VIEW, dbViews);
		docModel.put(Value.VC_DOT_PROC, dbProcs);
		docModel.put(Value.VC_DOT_FUNC, dbFuncs);
		docModel.put(MA_DOC_UIMODEL, uiModel);
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
