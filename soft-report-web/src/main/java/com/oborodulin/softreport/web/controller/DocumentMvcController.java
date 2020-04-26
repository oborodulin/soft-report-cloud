package com.oborodulin.softreport.web.controller;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import com.oborodulin.softreport.domain.model.project.Project;
import com.oborodulin.softreport.domain.model.project.document.Document;
import com.oborodulin.softreport.domain.service.DocumentServiceImpl;
import com.oborodulin.softreport.domain.service.ProjectServiceImpl;
import com.oborodulin.softreport.web.AbstractMasterDetailTreeMvcController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
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
	public static final String COC_NAME = "documents";
	public static final String VN_PATH = ProjectMvcController.VN_PATH.concat(COC_NAME.toLowerCase()).concat("/");
	/** Путь к шаблонам документов */
	private static final String DT_PATH = "docs/";

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

	/**
	 * {@inheritDoc}
	 */
	@GetMapping(URL_VIEW)
	public String view(@PathVariable(PV_ID) Long id, Locale locale, Model model) {
		Document document = this.service.getById(id);
		model.addAttribute(MA_TITLE_READ, document.getCodeId());
		model.addAttribute(this.objName, this.service.init(document));
		model.addAttribute("docModel", this.service.getDocModel(document));
		/*
		 * model.addAttribute("signatories", this.service.getSignatories(document));
		 * model.addAttribute("terms", this.service.getTerms(document));
		 * model.addAttribute("dataModel", this.service.getDataModel(document));
		 * model.addAttribute("uiModel", this.service.getDataModel(document));
		 */
		log.info(this.objName + " [" + URL_VIEW + "]: id = " + id);
		return DT_PATH.concat(this.service.getView(document));
	}

}
