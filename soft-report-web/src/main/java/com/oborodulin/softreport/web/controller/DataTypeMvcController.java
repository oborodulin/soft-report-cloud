package com.oborodulin.softreport.web.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.oborodulin.softreport.domain.service.DataTypeServiceImpl;
import com.oborodulin.softreport.domain.service.ProgLangServiceImpl;
import com.oborodulin.softreport.domain.model.dic.proglang.ProgLang;
import com.oborodulin.softreport.domain.model.dic.proglang.datatype.DataType;
import com.oborodulin.softreport.web.AbstractMvcDetailController;

@Controller
@RequestMapping(DataTypeMvcController.BASE_URL)
public class DataTypeMvcController
		extends AbstractMvcDetailController<ProgLang, DataType, ProgLangServiceImpl, DataTypeServiceImpl, String> {

	protected static final String BASE_URL = "/datatypes";
	/** Наименование объекта контроллера (Controller Objects Name) */
	private static final String CO_NAME = "dataType";
	/**
	 * Наименование коллекции объектов контроллера (Controller Collection Objects
	 * Name)
	 */
	private static final String CCO_NAME = "dataTypes";
	/** Путь к шаблонам (каталог) */
	public static final String VN_PATH = ProgLangMvcController.VN_PATH.concat(CCO_NAME.toLowerCase()).concat("/");

	@Autowired
	public DataTypeMvcController(ProgLangServiceImpl masterService, DataTypeServiceImpl service) {
		super(masterService, service, BASE_URL, VN_PATH, CO_NAME, CCO_NAME);
		Map<String, Object> ma = new HashMap<>();
		ma.put("progLangs", masterService.findAll());
		ma.put("sqlTypes", this.service.getSqlTypes());
		ma.put("backendTypes", this.service.getBackendTypes());
		ma.put("frontendTypes", this.service.getFrontendTypes());
		this.setModelAttributes(RM_CREATE, ma);
		this.setModelAttributes(RM_UPDATE, ma);
	}

}
