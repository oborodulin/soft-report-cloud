package com.oborodulin.softreport.web.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.oborodulin.softreport.domain.model.dic.objhierarchy.ObjHierarchy;
import com.oborodulin.softreport.domain.service.ObjHierarchyServiceImpl;
import com.oborodulin.softreport.web.AbstractTreeMvcController;

@Controller
@RequestMapping(ObjHierarchyMvcController.BASE_URL)
public class ObjHierarchyMvcController
		extends AbstractTreeMvcController<ObjHierarchy, ObjHierarchyServiceImpl, String> {

	/** Базовый URL контроллера */
	protected static final String BASE_URL = "/objhierarches";
	/** Наименование объекта контроллера (Controller Objects Name) */
	private static final String CO_NAME = "objHierarchy";
	/**
	 * Наименование коллекции объектов контроллера (Controller Objects Collection 
	 * Name)
	 */
	private static final String COC_NAME = "objHierarches";
	/** Путь к шаблонам (каталог) */
	private static final String VN_PATH = "tpl-objhierarches/";

	@Autowired
	public ObjHierarchyMvcController(ObjHierarchyServiceImpl service) {
		super(service, BASE_URL, VN_PATH, CO_NAME, COC_NAME);
		Map<String, Object> ma = new HashMap<>();
		ma.put("archs", this.service.getArchs());
		ma.put("types", this.service.getTypes());
		this.setModelAttributes(RM_UPDATE, ma);

		Map<String, Object> maCreate = new HashMap<>();
		maCreate.put("objHierarches", this.service.findAllСontainerObjects());
		maCreate.putAll(ma);
		this.setModelAttributes(RM_CREATE, maCreate);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, Object> getShowCreateModelAttributes(Long parentId) {
		ObjHierarchy objHierarchy = this.service.create(parentId);
		Map<String, Object> ma = new HashMap<>();
		ma.put("objHierarches", this.service.findAllParentСontainerObjects(objHierarchy));
		ma.put("archs", this.service.getParentArchs(objHierarchy));
		ma.put("types", this.service.getComponentTypes());
		return ma;
	}

}
