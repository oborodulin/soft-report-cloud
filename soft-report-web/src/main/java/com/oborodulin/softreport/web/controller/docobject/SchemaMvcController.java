package com.oborodulin.softreport.web.controller.docobject;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.oborodulin.softreport.domain.model.docobject.DocObject;
import com.oborodulin.softreport.domain.service.DocObjectServiceImpl;
import com.oborodulin.softreport.web.AbstractTreeChildrenMvcController;

@Controller
@RequestMapping(SchemaMvcController.BASE_URL)
public class SchemaMvcController extends AbstractTreeChildrenMvcController<DocObject, DocObjectServiceImpl, String> {

	/** Базовый URL контроллера */
	protected static final String BASE_URL = "/schemas";
	/** Наименование объекта контроллера (Controller Objects Name) */
	private static final String CO_NAME = "schema";
	/**
	 * Наименование коллекции объектов контроллера (Controller Objects Collection
	 * Name)
	 */
	private static final String COC_NAME = "schemas";
	/** Путь к шаблонам (каталог) */
	public static final String VN_PATH = DataBaseMvcController.VN_PATH.concat(COC_NAME.toLowerCase()).concat("/");

	@Autowired
	public SchemaMvcController(DocObjectServiceImpl masterService, DocObjectServiceImpl service) {
		super(service, BASE_URL, VN_PATH, CO_NAME, COC_NAME);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<DocObject> getShowListSlavesEntities(Long parentId) {
		return this.service.findSchemasByDataBaseId(parentId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DocObject createSlaveEntity(Long parentId) {
		return this.service.createSchema(parentId);
	}

}
