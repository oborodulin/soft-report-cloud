package com.oborodulin.softreport.web.controller;

import java.util.List;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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
	private static final String VN_PATH = SoftwareMvcController.VN_PATH.concat("businessobjects/");

	@Autowired
	public BusinessObjectMvcController(SoftwareServiceImpl masterService, BusinessObjectServiceImpl service) {
		super(masterService, service, BASE_URL, VN_PATH);
	}

	@ModelAttribute(name = "titleParent")
	public String titleParent(Locale locale) {
		return this.ms.getMessage("softwares.title.parent", null, locale);
	}

	@GetMapping
	public String showList(Locale locale, Model model) {
		List<BusinessObject> businessObjects = this.service.findAll();
		if (businessObjects.isEmpty()) {
			MessageHelper.addInfoAttribute(model, "businessobjects.info.empty");
		}
		model.addAttribute("titleRead", this.ms.getMessage("businessobjects.title.read", null, locale));
		model.addAttribute("businessObjects", businessObjects);
		return this.getViewNameReadDelete();
	}

	@GetMapping(URL_CREATE)
	public String showCreateForm(Locale locale, Model model) {
		model.addAttribute("titleCreate", this.ms.getMessage("businessobjects.title.create", null, locale));
		model.addAttribute("businessObject", new BusinessObject());
		return this.getViewNameCreateUpdate();
	}

	@GetMapping(URL_EDIT)
	public String showUpdateForm(@PathVariable(PV_ID) Long id, Locale locale, Model model) {
		model.addAttribute("titleUpdate", this.ms.getMessage("businessobjects.title.update", null, locale));
		model.addAttribute("businessObject", this.service.getById(id));
		return this.getViewNameCreateUpdate();
	}

	@GetMapping(URL_DTL_READ)
	public String showChildrenList(@PathVariable(PV_MASTER_ID) Long masterId, Locale locale, Model model) {
		Software software = this.masterService.getById(masterId);
		if (software.getBusinessObjects().isEmpty()) {
			MessageHelper.addInfoAttribute(model, "businessobjects.info.empty", software.getName());
		}
		model.addAttribute("titleMaster", software.getCode());
		model.addAttribute("titleRead", this.ms.getMessage("businessobjects.title.read", null, locale));
		model.addAttribute("software", software);
		model.addAttribute("businessObjectss", software.getBusinessObjects());
		return this.getViewNameReadDelete();
	}

	@GetMapping(URL_DTL_CREATE)
	public String showCreateForm(@PathVariable(PV_MASTER_ID) Long masterId, Locale locale, Model model) {
		BusinessObject businessObjects = this.service.create(masterId);
		model.addAttribute("titleMaster", businessObjects.getMaster().getCode());
		model.addAttribute("titleCreate", this.ms.getMessage("businessobjects.title.create", null, locale));
		model.addAttribute("businessObjects", businessObjects);
		return this.getViewNameCreateUpdate();
	}

	@GetMapping(URL_DTL_EDIT)
	public String showUpdateForm(@PathVariable(PV_MASTER_ID) Long masterId, @PathVariable(PV_ID) Long id, Locale locale,
			Model model) {
		BusinessObject businessObjects = this.service.getById(id);
		model.addAttribute("titleMaster", businessObjects.getMaster().getCode());
		model.addAttribute("titleUpdate", this.ms.getMessage("businessobjects.title.update", null, locale));
		model.addAttribute("businessObjects", businessObjects);
		return this.getViewNameCreateUpdate();
	}

}
