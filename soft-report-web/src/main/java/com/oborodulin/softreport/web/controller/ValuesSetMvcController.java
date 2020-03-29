package com.oborodulin.softreport.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.oborodulin.softreport.domain.model.dic.valuesset.ValuesSet;
import com.oborodulin.softreport.domain.service.ValuesSetServiceImpl;
import com.oborodulin.softreport.web.AbstractMvcController;

@Controller
@RequestMapping(ValuesSetMvcController.BASE_URL)
public class ValuesSetMvcController extends AbstractMvcController<ValuesSet, ValuesSetServiceImpl, String> {

	/** Базовый URL контроллера */
	protected static final String BASE_URL = "/valuessets";
	/** Наименование объекта контроллера (Controller Objects Name) */
	private static final String CO_NAME = "valuesSet";
	/**
	 * Наименование коллекции объектов контроллера (Controller Objects Collection
	 * Name)
	 */
	private static final String COC_NAME = "valuesSets";
	/** Путь к шаблонам (каталог) */
	public static final String VN_PATH = "tpl-valuessets/";

	@Autowired
	public ValuesSetMvcController(ValuesSetServiceImpl service) {
		super(service, BASE_URL, VN_PATH, CO_NAME, COC_NAME);
	}

}
