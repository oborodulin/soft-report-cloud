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
import com.oborodulin.softreport.domain.model.enterprise.Enterprise;
import com.oborodulin.softreport.domain.service.EnterpriseServiceImpl;
import com.oborodulin.softreport.web.AbstractMvcTreeController;
import com.oborodulin.softreport.web.support.MessageHelper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping(EnterpriseMvcController.BASE_URL)
public class EnterpriseMvcController extends AbstractMvcTreeController<Enterprise, EnterpriseServiceImpl, String> {

	protected static final String BASE_URL = "/enterprises";
	private static final String VN_PATH = "tpl-enterprises/";

	@Autowired
	public EnterpriseMvcController(EnterpriseServiceImpl service) {
		super(service, BASE_URL, VN_PATH);
	}

	@ModelAttribute(name = "titleParent")
	public String titleParent(Locale locale) {
		return this.ms.getMessage("enterprises.title.parent", null, locale);
	}

	@ModelAttribute(name = "titleRead")
	public String titleRead(Locale locale) {
		return this.ms.getMessage("enterprises.title.read", null, locale);
	}

	@ModelAttribute(name = "titleCreate")
	public String titleCreate(Locale locale) {
		return this.ms.getMessage("enterprises.title.create", null, locale);
	}

	@ModelAttribute(name = "titleUpdate")
	public String titleUpdate(Locale locale) {
		return this.ms.getMessage("enterprises.title.update", null, locale);
	}

	@GetMapping
	public String showRootList(Model model) {
		List<Enterprise> enterprises = this.service.findByParentIsNull();
		if (enterprises.isEmpty()) {
			MessageHelper.addInfoAttribute(model, "enterprises.info.empty");
		}
		model.addAttribute("enterprises", enterprises);
		return this.getViewNameReadDelete();
	}

	@GetMapping(URL_CREATE)
	public String showCreateForm(Model model) {
		log.info("Отображение формы создания актива");
		model.addAttribute("enterprises", this.service.findAll());
		model.addAttribute("enterprise", new Enterprise());
		return this.getViewNameCreateUpdate();
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
