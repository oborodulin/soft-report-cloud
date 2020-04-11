package com.oborodulin.softreport.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.oborodulin.softreport.domain.service.ValueServiceImpl;
import com.oborodulin.softreport.domain.service.ValuesSetServiceImpl;
import com.oborodulin.softreport.domain.model.dic.valuesset.ValuesSet;
import com.oborodulin.softreport.domain.model.dic.valuesset.value.Value;
import com.oborodulin.softreport.web.AbstractMasterDetailMvcController;

@Controller
@RequestMapping(ValueMvcController.BASE_URL)
public class ValueMvcController
		extends AbstractMasterDetailMvcController<ValuesSet, Value, ValuesSetServiceImpl, ValueServiceImpl, String> {

	/** Базовый URL контроллера */
	protected static final String BASE_URL = "/values";
	/** Наименование объекта контроллера (Controller Objects Name) */
	private static final String CO_NAME = "value";
	/**
	 * Наименование коллекции объектов контроллера (Controller Objects Collection
	 * Name)
	 */
	private static final String COC_NAME = "values";
	/** Путь к шаблонам (каталог) */
	public static final String VN_PATH = ValuesSetMvcController.VN_PATH.concat(COC_NAME.toLowerCase()).concat("/");

	@Autowired
	public ValueMvcController(ValuesSetServiceImpl masterService, ValueServiceImpl service) {
		super(masterService, service, BASE_URL, VN_PATH, CO_NAME, COC_NAME);
		this.setSortPropName("code");
	}

}
