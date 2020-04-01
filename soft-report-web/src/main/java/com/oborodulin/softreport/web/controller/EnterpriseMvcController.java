package com.oborodulin.softreport.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.oborodulin.softreport.domain.model.enterprise.Enterprise;
import com.oborodulin.softreport.domain.service.EnterpriseServiceImpl;
import com.oborodulin.softreport.web.AbstractTreeMvcController;

@Controller
@RequestMapping(EnterpriseMvcController.BASE_URL)
public class EnterpriseMvcController extends AbstractTreeMvcController<Enterprise, EnterpriseServiceImpl, String> {

	/** Базовый URL контроллера */
	protected static final String BASE_URL = "/enterprises";
	/** Наименование объекта контроллера (Controller Objects Name) */
	private static final String CO_NAME = "enterprise";
	/**
	 * Наименование коллекции объектов контроллера (Controller Objects Collection
	 * Name)
	 */
	private static final String COC_NAME = "enterprises";
	/** Путь к шаблонам (каталог) */
	private static final String VN_PATH = "tpl-enterprises/";

	@Autowired
	public EnterpriseMvcController(EnterpriseServiceImpl service) {
		super(service, BASE_URL, VN_PATH, CO_NAME, COC_NAME);
	}

}
