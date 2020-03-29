package com.oborodulin.softreport.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.oborodulin.softreport.domain.model.software.Software;
import com.oborodulin.softreport.domain.model.software.document.Document;
import com.oborodulin.softreport.domain.service.DocumentServiceImpl;
import com.oborodulin.softreport.domain.service.SoftwareServiceImpl;
import com.oborodulin.softreport.web.AbstractMvcDetailController;

@Controller
@RequestMapping(DocumentMvcController.BASE_URL)
public class DocumentMvcController
		extends AbstractMvcDetailController<Software, Document, SoftwareServiceImpl, DocumentServiceImpl, String> {

	/** Базовый URL контроллера */
	protected static final String BASE_URL = "/documents";
	/** Наименование объекта контроллера (Controller Objects Name) */
	private static final String CO_NAME = "document";
	/**
	 * Наименование коллекции объектов контроллера (Controller Objects Collection
	 * Name)
	 */
	private static final String COC_NAME = "documents";
	private static final String VN_PATH = SoftwareMvcController.VN_PATH.concat(COC_NAME.toLowerCase()).concat("/");

	@Autowired
	public DocumentMvcController(SoftwareServiceImpl masterService, DocumentServiceImpl service) {
		super(masterService, service, BASE_URL, VN_PATH, CO_NAME, COC_NAME);
	}

}
