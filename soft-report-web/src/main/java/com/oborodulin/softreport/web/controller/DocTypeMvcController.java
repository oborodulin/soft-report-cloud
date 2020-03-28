package com.oborodulin.softreport.web.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.oborodulin.softreport.domain.model.dic.doctype.DocType;
import com.oborodulin.softreport.domain.service.DocTypeServiceImpl;
import com.oborodulin.softreport.web.AbstractMvcController;

@Controller
@RequestMapping(DocTypeMvcController.BASE_URL)
public class DocTypeMvcController extends AbstractMvcController<DocType, DocTypeServiceImpl, String> {

	protected static final String BASE_URL = "/doctypes";
	/** Наименование объекта контроллера (Controller Objects Name) */
	private static final String CO_NAME = "docType";
	/**
	 * Наименование коллекции объектов контроллера (Controller Collection Objects
	 * Name)
	 */
	private static final String CCO_NAME = "docTypes";
	/** Путь к шаблонам (каталог) */
	private static final String VN_PATH = "tpl-doctypes/";

	@Autowired
	public DocTypeMvcController(DocTypeServiceImpl service) {
		super(service, BASE_URL, VN_PATH, CO_NAME, CCO_NAME);
		Map<String, Object> ma = new HashMap<>();
		ma.put("categs", this.service.getCategs());
		ma.put("types", this.service.getTypes());
		this.setModelAttributes(RM_CREATE, ma);
		this.setModelAttributes(RM_UPDATE, ma);
	}

}
