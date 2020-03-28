package com.oborodulin.softreport.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import com.oborodulin.softreport.domain.model.enterprise.Enterprise;
import com.oborodulin.softreport.domain.service.EnterpriseServiceImpl;
import com.oborodulin.softreport.web.AbstractMvcTreeController;
import com.oborodulin.softreport.web.support.MessageHelper;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping(EnterpriseMvcController.BASE_URL)
public class EnterpriseMvcController extends AbstractMvcTreeController<Enterprise, EnterpriseServiceImpl, String> {

	protected static final String BASE_URL = "/enterprises";
	/** Наименование объекта контроллера (Controller Objects Name) */
	private static final String CO_NAME = "enterprise";
	/**
	 * Наименование коллекции объектов контроллера (Controller Collection Objects
	 * Name)
	 */
	private static final String CCO_NAME = "enterprises";
	/** Путь к шаблонам (каталог) */
	private static final String VN_PATH = "tpl-enterprises/";

	@Autowired
	public EnterpriseMvcController(EnterpriseServiceImpl service) {
		super(service, BASE_URL, VN_PATH, CO_NAME, CCO_NAME);
		Map<String, Object> ma = new HashMap<>();
		ma.put("enterprises", this.service.findAll());
		this.setModelAttributes(RM_CREATE, ma);
		this.setModelAttributes(RM_UPDATE, ma);
	}


	@GetMapping(URL_CREATE_CHILD)
	public String showCreateChildForm(@PathVariable(PV_PARENT_ID) Long parentId, Model model) {
		model.addAttribute("enterprises", this.service.findAll());
		model.addAttribute("enterprise", this.service.createChild(parentId));
		return this.getViewNameCreateUpdate();
	}

	@GetMapping(URL_EDIT)
	public String showUpdateForm(@PathVariable(PV_ID) Long id, Model model) {
		model.addAttribute("enterprises", this.service.findByIdIsNot(id));
		model.addAttribute("enterprise", this.service.getById(id));
		return this.getViewNameCreateUpdate();
	}
}
