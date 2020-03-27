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
import com.oborodulin.softreport.domain.service.SoftwareServiceImpl;
import com.oborodulin.softreport.web.AbstractMvcTreeController;
import com.oborodulin.softreport.web.support.MessageHelper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping(SoftwareMvcController.BASE_URL)
public class SoftwareMvcController extends AbstractMvcTreeController<Software, SoftwareServiceImpl, String> {

	protected static final String BASE_URL = "/softwares";
	public static final String VN_PATH = "tpl-softwares/";

	@Autowired
	public SoftwareMvcController(SoftwareServiceImpl service) {
		super(service, BASE_URL, VN_PATH);
	}

	@ModelAttribute(name = MA_TITLE_PARENT)
	public String titleParent(Locale locale) {
		return this.ms.getMessage("softwares.title.parent", null, locale);
	}

	@GetMapping
	public String showRootList(Locale locale, Model model) {
		List<Software> softwares = this.service.findByParentIsNull();
		if (softwares.isEmpty()) {
			MessageHelper.addInfoAttribute(model, "softwares.info.empty");
		}
		model.addAttribute(MA_TITLE_READ, this.ms.getMessage("softwares.title.read", null, locale));
		model.addAttribute("softwares", softwares);
		return this.getViewNameReadDelete();
	}

	@GetMapping(URL_CREATE)
	public String showCreateForm(Locale locale, Model model) {
		log.info("Отображение формы создания системы");
		model.addAttribute(MA_TITLE_CREATE, this.ms.getMessage("softwares.title.create", null, locale));
		model.addAttribute("softwares", this.service.findAll());
		model.addAttribute("types", this.service.getTypes());
		model.addAttribute("software", new Software());
		return this.getViewNameCreateUpdate();
	}

	@GetMapping(URL_CREATE_CHILD)
	public String showCreateChildForm(@PathVariable(PV_PARENT_ID) Long parentId, Locale locale, Model model) {
		model.addAttribute(MA_TITLE_CREATE, this.ms.getMessage("softwares.title.create", null, locale));
		model.addAttribute("softwares", this.service.findAll());
		model.addAttribute("types", this.service.getTypes());
		model.addAttribute("software", this.service.createChild(parentId));
		// model.addAttribute("software", new Software());
		return this.getViewNameCreateUpdate();
	}

	@GetMapping(URL_EDIT)
	public String showUpdateForm(@PathVariable(PV_ID) Long id, Locale locale, Model model) {
		model.addAttribute(MA_TITLE_UPDATE, this.ms.getMessage("softwares.title.update", null, locale));
		model.addAttribute("softwares", this.service.findByIdIsNot(id));
		model.addAttribute("types", this.service.getTypes());
		model.addAttribute("software", this.service.getById(id));
		return this.getViewNameCreateUpdate();
	}

}
