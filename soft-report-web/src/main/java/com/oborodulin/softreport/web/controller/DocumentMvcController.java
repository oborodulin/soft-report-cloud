package com.oborodulin.softreport.web.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.oborodulin.softreport.domain.model.project.Project;
import com.oborodulin.softreport.domain.model.project.document.Document;
import com.oborodulin.softreport.domain.service.DocumentServiceImpl;
import com.oborodulin.softreport.domain.service.ProjectServiceImpl;
import com.oborodulin.softreport.web.AbstractMasterDetailTreeMvcController;

@Controller
@RequestMapping(DocumentMvcController.BASE_URL)
public class DocumentMvcController extends
		AbstractMasterDetailTreeMvcController<Project, Document, ProjectServiceImpl, DocumentServiceImpl, String> {

	/** Базовый URL контроллера */
	protected static final String BASE_URL = "/documents";
	/** Наименование объекта контроллера (Controller Objects Name) */
	private static final String CO_NAME = "document";
	/**
	 * Наименование коллекции объектов контроллера (Controller Objects Collection
	 * Name)
	 */
	private static final String COC_NAME = "documents";
	public static final String VN_PATH = ProjectMvcController.VN_PATH.concat(COC_NAME.toLowerCase()).concat("/");

	@Autowired
	public DocumentMvcController(ProjectServiceImpl masterService, DocumentServiceImpl service) {
		super(masterService, service, BASE_URL, VN_PATH, CO_NAME, COC_NAME);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, Object> getShowCreateModelAttributes() {
		Map<String, Object> ma = new HashMap<>();
		ma.put("projects", this.masterService.findAll());
		ma.put("types", this.service.getTypes());
		return ma;
	}

}
