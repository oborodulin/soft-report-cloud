package com.oborodulin.softreport.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.data.domain.Sort;

import com.oborodulin.softreport.domain.model.software.Software;
import com.oborodulin.softreport.domain.model.software.businessobject.BusinessObject;
import com.oborodulin.softreport.domain.service.BusinessObjectServiceImpl;
import com.oborodulin.softreport.domain.service.SoftwareServiceImpl;
import com.oborodulin.softreport.web.AbstractMvcDetailController;
import com.oborodulin.softreport.web.support.MessageHelper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping(BusinessObjectMvcController.BASE_URL)
public class BusinessObjectMvcController extends
		AbstractMvcDetailController<Software, BusinessObject, SoftwareServiceImpl, BusinessObjectServiceImpl, String> {

	protected static final String BASE_URL = "/businessobjects";
	/** Наименование объекта контроллера (Controller Objects Name) */
	private static final String CO_NAME = "businessObject";
	/**
	 * Наименование коллекции объектов контроллера (Controller Collection Objects
	 * Name)
	 */
	private static final String CCO_NAME = "businessObjects";
	/** Путь к шаблонам (каталог) */
	private static final String VN_PATH = SoftwareMvcController.VN_PATH.concat(CCO_NAME.toLowerCase()).concat("/");

	@Autowired
	public BusinessObjectMvcController(SoftwareServiceImpl masterService, BusinessObjectServiceImpl service) {
		super(masterService, service, BASE_URL, VN_PATH, CO_NAME, CCO_NAME);
		Map<String, Object> ma = new HashMap<>();
		ma.put("softwares", masterService.findAll());
		this.presetModelAttributes(RM_SHOW_LIST, ma);
		this.presetModelAttributes(RM_SHOW_UPDATE_FORM, ma);
	}

	@GetMapping(URL_DTL_READ)
	public String showChildrenList(@PathVariable(PV_MASTER_ID) Long masterId, Locale locale, Model model) {
		Software software = this.masterService.getById(masterId);
		List<BusinessObject> businessObjects = this.service.findByMasterId(masterId, Sort.by(Sort.Direction.ASC, "name"));
		if (businessObjects.isEmpty()) {
			MessageHelper.addInfoAttribute(model, this.msPrefix.concat(".info.empty"), software.getName());
		}
		model.addAttribute(MA_TITLE_MASTER, software.getCode());
		model.addAttribute(MA_TITLE_READ, this.ms.getMessage(this.msPrefix.concat(".title.read"), null, locale));
		model.addAttribute("master", software);
		model.addAttribute(CCO_NAME, businessObjects);
		return this.getViewNameReadDelete();
	}

	@GetMapping(URL_DTL_CREATE)
	public String showCreateForm(@PathVariable(PV_MASTER_ID) Long masterId, Locale locale, Model model) {
		BusinessObject businessObject = this.service.create(masterId);
		model.addAttribute(MA_TITLE_MASTER, businessObject.getMaster().getCode());
		model.addAttribute(MA_TITLE_CREATE, this.ms.getMessage(this.msPrefix.concat(".title.create"), null, locale));
		model.addAttribute(CO_NAME, businessObject);
		return this.getViewNameCreateUpdate();
	}

	@GetMapping(URL_DTL_EDIT)
	public String showUpdateForm(@PathVariable(PV_MASTER_ID) Long masterId, @PathVariable(PV_ID) Long id, Locale locale,
			Model model) {
		BusinessObject businessObject = this.service.getById(id);
		model.addAttribute(MA_TITLE_MASTER, businessObject.getMaster().getCode());
		model.addAttribute(MA_TITLE_UPDATE, this.ms.getMessage("businessobjects.title.update", null, locale));
		model.addAttribute(CO_NAME, businessObject);
		return this.getViewNameCreateUpdate();
	}

}
