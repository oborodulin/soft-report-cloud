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
@RequestMapping(DataTableMvcController.BASE_URL)
public class DataTableMvcController extends AbstractTreeChildrenMvcController<DocObject, DocObjectServiceImpl, String> {

	/** Базовый URL контроллера */
	protected static final String BASE_URL = "/datatables";
	/** Наименование объекта контроллера (Controller Objects Name) */
	private static final String CO_NAME = "dataTable";
	/**
	 * Наименование коллекции объектов контроллера (Controller Objects Collection
	 * Name)
	 */
	private static final String COC_NAME = "dataTables";
	/** Путь к шаблонам (каталог) */
	public static final String VN_PATH = DataBaseMvcController.VN_PATH.concat(COC_NAME.toLowerCase()).concat("/");

	@Autowired
	public DataTableMvcController(DocObjectServiceImpl masterService, DocObjectServiceImpl service) {
		super(service, BASE_URL, VN_PATH, CO_NAME, COC_NAME);
		Map<String, Object> ma = new HashMap<>();
		ma.put("dtTypes", this.service.getDtTypes());

		this.setModelAttributes(RM_CREATE, ma);
		this.setModelAttributes(RM_UPDATE, ma);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getRedirectToRead(Long parentId) {
		return this.getRedirectToRead();
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * Возвращает список таблиц данных.
	 */
	@Override
	public List<DocObject> getShowListEntities() {
		return this.service.findDataTables();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, Object> getShowCreateModelAttributes() {
		Map<String, Object> ma = new HashMap<>();
		ma.put("dataBases", this.service.findDataBases());
		ma.put("schemas", this.service.getSchemas());
		ma.put("softwares", this.service.getSoftwares());
		ma.put("businessObjects", this.service.getBusinessObjects());
		return ma;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DocObject createEntity() {
		return this.service.createDataTable(null);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DocObject createChildEntity(Long parentId) {
		return this.service.createDataTable(parentId);
	}

}
