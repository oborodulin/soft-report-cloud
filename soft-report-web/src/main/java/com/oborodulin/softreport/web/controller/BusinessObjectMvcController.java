package com.oborodulin.softreport.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.oborodulin.softreport.domain.model.software.Software;
import com.oborodulin.softreport.domain.model.software.businessobject.BusinessObject;
import com.oborodulin.softreport.domain.service.BusinessObjectServiceImpl;
import com.oborodulin.softreport.domain.service.SoftwareServiceImpl;
import com.oborodulin.softreport.web.AbstractMasterDetailMvcController;

/**
 * Класс MVC-контроллера взаимодействия с бизнес-объектами программного
 * обеспечения (ПО).
 * <p>
 * Управляет отображением, добавлением, изменением и удалением бизнес-объектов.
 * Бизнес-объекты играют основную роль в процессе формирования документации. Они
 * являются связующим звеном между ПО (а, значит, проектами) и объектами
 * документов, по которым формируются документы.
 * 
 * @author Oleg Borodulin
 * @see com.oborodulin.softreport.domain.model.software.businessobject.BusinessObject
 */
@Controller
@RequestMapping(BusinessObjectMvcController.BASE_URL)
public class BusinessObjectMvcController extends
		AbstractMasterDetailMvcController<Software, BusinessObject, SoftwareServiceImpl, BusinessObjectServiceImpl, String> {

	/** Базовый URL контроллера */
	protected static final String BASE_URL = "/businessobjects";
	/** Наименование объекта контроллера (Controller Objects Name) */
	private static final String CO_NAME = "businessObject";
	/**
	 * Наименование коллекции объектов контроллера (Controller Objects Collection
	 * Name)
	 */
	private static final String COC_NAME = "businessObjects";
	/** Путь к шаблонам (каталог) */
	private static final String VN_PATH = SoftwareMvcController.VN_PATH.concat(COC_NAME.toLowerCase()).concat("/");

	/**
	 * Конструктор. Инстанцирует объект.
	 * 
	 * @param masterService сервис доменной модели главного объекта
	 * @param service       сервис доменной модели подчинённого объекта
	 */
	@Autowired
	public BusinessObjectMvcController(SoftwareServiceImpl masterService, BusinessObjectServiceImpl service) {
		super(masterService, SoftwareMvcController.COC_NAME, service, BASE_URL, VN_PATH, CO_NAME, COC_NAME);
	}

}
