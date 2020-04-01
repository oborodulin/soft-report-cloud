package com.oborodulin.softreport.web.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.oborodulin.softreport.domain.model.software.Software;
import com.oborodulin.softreport.domain.service.SoftwareServiceImpl;
import com.oborodulin.softreport.web.AbstractTreeMvcController;

@Controller
@RequestMapping(SoftwareMvcController.BASE_URL)
public class SoftwareMvcController extends AbstractTreeMvcController<Software, SoftwareServiceImpl, String> {

	/** Базовый URL контроллера */
	protected static final String BASE_URL = "/softwares";
	/** Наименование объекта контроллера (Controller Objects Name) */
	private static final String CO_NAME = "software";
	/**
	 * Наименование коллекции объектов контроллера (Controller Objects Collection
	 * Name)
	 */
	private static final String COC_NAME = "softwares";
	/** Путь к шаблонам (каталог) */
	public static final String VN_PATH = "tpl-softwares/";

	@Autowired
	public SoftwareMvcController(SoftwareServiceImpl service) {
		super(service, BASE_URL, VN_PATH, CO_NAME, COC_NAME);
		Map<String, Object> ma = new HashMap<>();
		ma.put("types", this.service.getTypes());
		this.setModelAttributes(RM_CREATE_CHILD, ma);
		this.setModelAttributes(RM_UPDATE, ma);

		Map<String, Object> maCreate = new HashMap<>();
		maCreate.put("softwares", this.service.findAll());
		maCreate.putAll(ma);
		this.setModelAttributes(RM_CREATE, maCreate);
	}

}
