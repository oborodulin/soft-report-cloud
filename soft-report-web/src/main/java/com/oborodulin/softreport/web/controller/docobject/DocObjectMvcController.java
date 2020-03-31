package com.oborodulin.softreport.web.controller.docobject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.oborodulin.softreport.domain.model.docobject.DocObject;
import com.oborodulin.softreport.domain.service.DocObjectServiceImpl;
import com.oborodulin.softreport.web.AbstractMvcTreeController;

@Controller
@RequestMapping(DocObjectMvcController.BASE_URL)
public class DocObjectMvcController
		extends AbstractMvcTreeController<DocObject, DocObjectServiceImpl, String> {

	/** Базовый URL контроллера */
	protected static final String BASE_URL = "/docobjects";
	/** Наименование объекта контроллера (Controller Objects Name) */
	private static final String CO_NAME = "docObject";
	/**
	 * Наименование коллекции объектов контроллера (Controller Objects Collection 
	 * Name)
	 */
	private static final String COC_NAME = "docObjects";
	/** Путь к шаблонам (каталог) */
	public static final String VN_PATH = "tpl-docobjects/";
	
	@Autowired
	public DocObjectMvcController(DocObjectServiceImpl service) {
		super(service, BASE_URL, VN_PATH, CO_NAME, COC_NAME);
	}

}
