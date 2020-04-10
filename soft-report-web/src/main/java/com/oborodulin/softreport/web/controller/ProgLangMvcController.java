package com.oborodulin.softreport.web.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.oborodulin.softreport.domain.model.dic.proglang.ProgLang;
import com.oborodulin.softreport.domain.service.ProgLangServiceImpl;
import com.oborodulin.softreport.web.AbstractMvcController;

@Controller
@RequestMapping(ProgLangMvcController.BASE_URL)
public class ProgLangMvcController extends AbstractMvcController<ProgLang, ProgLangServiceImpl, String> {

	/** Базовый URL контроллера */
	protected static final String BASE_URL = "/proglangs";
	/** Наименование объекта контроллера (Controller Objects Name) */
	private static final String CO_NAME = "progLang";
	/**
	 * Наименование коллекции объектов контроллера (Controller Objects Collection
	 * Name)
	 */
	private static final String COC_NAME = "progLangs";
	/** Путь к шаблонам (каталог) */
	public static final String VN_PATH = "tpl-proglangs/";

	@Autowired
	public ProgLangMvcController(ProgLangServiceImpl service) {
		super(service, BASE_URL, VN_PATH, CO_NAME, COC_NAME);
		Map<String, Object> ma = new HashMap<>();

		ma.put("langs", this.service.getLangs());
		ma.put("archs", this.service.getArchs());
		ma.put("dbTypes", this.service.getDbTypes());
		
		this.setModelAttributes(RM_CREATE, ma);
		this.setModelAttributes(RM_UPDATE, ma);
	}

}
