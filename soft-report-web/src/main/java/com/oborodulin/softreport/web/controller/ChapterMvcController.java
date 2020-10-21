package com.oborodulin.softreport.web.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.oborodulin.softreport.domain.model.project.document.Document;
import com.oborodulin.softreport.domain.model.project.document.chapter.Chapter;
import com.oborodulin.softreport.domain.service.ChapterServiceImpl;
import com.oborodulin.softreport.domain.service.DocumentServiceImpl;
import com.oborodulin.softreport.web.AbstractMasterDetailMvcController;

@Controller
@RequestMapping(ChapterMvcController.BASE_URL)
public class ChapterMvcController
		extends AbstractMasterDetailMvcController<Document, Chapter, DocumentServiceImpl, ChapterServiceImpl, String> {

	/** Базовый URL контроллера */
	protected static final String BASE_URL = "/chapters";
	/** Наименование объекта контроллера (Controller Objects Name) */
	private static final String CO_NAME = "chapter";
	/**
	 * Наименование коллекции объектов контроллера (Controller Objects Collection
	 * Name)
	 */
	public static final String COC_NAME = "chapters";
	/** Путь к шаблонам (каталог) */
	public static final String VN_PATH = DocumentMvcController.VN_PATH.concat(COC_NAME.toLowerCase()).concat("/");

	@Autowired
	public ChapterMvcController(DocumentServiceImpl masterService, ChapterServiceImpl service) {
		super(masterService, DocumentMvcController.COC_NAME, service, BASE_URL, VN_PATH, CO_NAME, COC_NAME);
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
		ma.put(this.masterObjCollectName, masterService.entities());
		// ma.put("backendTypes", this.service.getBackendTypes());
		// ma.put("frontendTypes", this.service.getFrontendTypes());
		return ma;
	}

}
