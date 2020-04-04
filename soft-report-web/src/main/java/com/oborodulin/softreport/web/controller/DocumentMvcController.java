package com.oborodulin.softreport.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.oborodulin.softreport.domain.model.project.Project;
import com.oborodulin.softreport.domain.model.project.document.Document;
import com.oborodulin.softreport.domain.service.DocumentServiceImpl;
import com.oborodulin.softreport.web.AbstractMasterDetailTreeMvcController;

@Controller
@RequestMapping(DocumentMvcController.BASE_URL)
public class DocumentMvcController
		extends AbstractMasterDetailTreeMvcController<Project, Document, DocumentServiceImpl, String> {

	/** Базовый URL контроллера */
	protected static final String BASE_URL = "/documents";
	/** Наименование объекта контроллера (Controller Objects Name) */
	private static final String CO_NAME = "document";
	/**
	 * Наименование коллекции объектов контроллера (Controller Objects Collection
	 * Name)
	 */
	private static final String COC_NAME = "documents";
	private static final String VN_PATH = ProjectMvcController.VN_PATH.concat(COC_NAME.toLowerCase()).concat("/");

	@Autowired
	public DocumentMvcController(DocumentServiceImpl service) {
		super(service, BASE_URL, VN_PATH, CO_NAME, COC_NAME);
	}

}
