package com.oborodulin.softreport.web.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.oborodulin.softreport.domain.service.SectionServiceImpl;
import com.oborodulin.softreport.domain.model.dic.doctype.DocType;
import com.oborodulin.softreport.domain.model.dic.doctype.section.Section;
import com.oborodulin.softreport.domain.service.DocTypeServiceImpl;
import com.oborodulin.softreport.web.AbstractMasterDetailMvcController;

@Controller
@RequestMapping(SectionMvcController.BASE_URL)
public class SectionMvcController
		extends AbstractMasterDetailMvcController<DocType, Section, DocTypeServiceImpl, SectionServiceImpl, String> {

	/** Базовый URL контроллера */
	protected static final String BASE_URL = "/sections";
	/** Наименование объекта контроллера (Controller Objects Name) */
	private static final String CO_NAME = "section";
	/**
	 * Наименование коллекции объектов контроллера (Controller Objects Collection
	 * Name)
	 */
	public static final String COC_NAME = "sections";
	/** Путь к шаблонам (каталог) */
	public static final String VN_PATH = DocTypeMvcController.VN_PATH.concat(COC_NAME.toLowerCase()).concat("/");

	@Autowired
	public SectionMvcController(DocTypeServiceImpl masterService, SectionServiceImpl service) {
		super(masterService, DocTypeMvcController.COC_NAME, service, BASE_URL, VN_PATH, CO_NAME, COC_NAME);
		Map<String, Object> ma = new HashMap<>();
		// ma.put("sqlTypes", this.service.getSqlTypes());

		this.setModelAttributes(RM_CREATE, ma);
		this.setModelAttributes(RM_UPDATE, ma);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, Object> getShowCreateModelAttributes() {
		Map<String, Object> ma = new HashMap<>();
		ma.put(this.masterObjCollectName, masterService.findAll());
		// ma.put("backendTypes", this.service.getBackendTypes());
		// ma.put("frontendTypes", this.service.getFrontendTypes());
		return ma;
	}

}
