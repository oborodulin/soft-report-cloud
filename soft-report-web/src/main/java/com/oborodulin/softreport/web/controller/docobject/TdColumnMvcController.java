package com.oborodulin.softreport.web.controller.docobject;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.oborodulin.softreport.domain.model.docobject.DocObject;
import com.oborodulin.softreport.domain.service.DocObjectServiceImpl;
import com.oborodulin.softreport.web.AbstractTreeChildrenMvcController;

@Controller
@RequestMapping(TdColumnMvcController.BASE_URL)
public class TdColumnMvcController extends AbstractTreeChildrenMvcController<DocObject, DocObjectServiceImpl, String> {

	/** Базовый URL контроллера */
	protected static final String BASE_URL = "/tdcolumns";
	/** Наименование объекта контроллера (Controller Objects Name) */
	private static final String CO_NAME = "tdColumn";
	/**
	 * Наименование коллекции объектов контроллера (Controller Objects Collection
	 * Name)
	 */
	private static final String COC_NAME = "tdColumns";
	/** Путь к шаблонам (каталог) */
	public static final String VN_PATH = DataTableMvcController.VN_PATH.concat(COC_NAME.toLowerCase()).concat("/");

	@Autowired
	public TdColumnMvcController(DocObjectServiceImpl masterService, DocObjectServiceImpl service) {
		super(service, BASE_URL, VN_PATH, CO_NAME, COC_NAME);
		//columnTypes, dataTablesByDataBaseId()
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<DocObject> getShowListChildren(Long parentId) {
		return this.service.findDtColumnsByDataTableId(parentId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DocObject createChildEntity(Long parentId) {
		return this.service.createDtColumn(parentId);
	}

}
