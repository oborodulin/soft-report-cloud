package com.oborodulin.softreport.web.controller.docobject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.oborodulin.softreport.domain.model.docobject.DocObject;
import com.oborodulin.softreport.domain.service.DocObjectServiceImpl;
import com.oborodulin.softreport.web.AbstractTreeChildrenMvcController;

@Controller
@RequestMapping(DataTableColumnMvcController.BASE_URL)
public class DataTableColumnMvcController extends AbstractTreeChildrenMvcController<DocObject, DocObjectServiceImpl, String> {

	/** Базовый URL контроллера */
	protected static final String BASE_URL = "/dtcolumns";
	/** Наименование объекта контроллера (Controller Objects Name) */
	private static final String CO_NAME = "dtColumn";
	/**
	 * Наименование коллекции объектов контроллера (Controller Objects Collection
	 * Name)
	 */
	private static final String COC_NAME = "dtColumns";
	/** Путь к шаблонам (каталог) */
	public static final String VN_PATH = DataTableMvcController.VN_PATH.concat(COC_NAME.toLowerCase()).concat("/");

	@Autowired
	public DataTableColumnMvcController(DocObjectServiceImpl masterService, DocObjectServiceImpl service) {
		super(service, BASE_URL, VN_PATH, CO_NAME, COC_NAME);
		Map<String, Object> ma = new HashMap<>();
		ma.put("dtColumnTypes", this.service.getDtColumnTypes());
		
		this.setModelAttributes(RM_CREATE, ma);
		this.setModelAttributes(RM_UPDATE, ma);
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
	public Map<String, Object> getShowCreateModelAttributes(Long parentId) {
		Map<String, Object> ma = new HashMap<>();
		ma.put("dataTypes", this.service.getDbDataTypes(parentId));
		ma.put("primaryKeys", this.service.getDbDataTablesPrimaryKeys(parentId));
		return ma;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DocObject createChildEntity(Long parentId) {
		return this.service.createDtColumn(parentId);
	}

}
