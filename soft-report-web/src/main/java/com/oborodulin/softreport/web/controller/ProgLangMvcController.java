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

import com.oborodulin.softreport.domain.model.dic.proglang.ProgLang;
import com.oborodulin.softreport.domain.service.ProgLangServiceImpl;
import com.oborodulin.softreport.web.AbstractMvcController;
import com.oborodulin.softreport.web.support.MessageHelper;

@Controller
@RequestMapping(ProgLangMvcController.BASE_URL)
public class ProgLangMvcController extends AbstractMvcController<ProgLang, ProgLangServiceImpl, String> {

	protected static final String BASE_URL = "/proglangs";
	public static final String VN_PATH = "tpl-proglangs/";

	@Autowired
	public ProgLangMvcController(ProgLangServiceImpl service) {
		super(service, BASE_URL, VN_PATH);
	}

	@ModelAttribute(name = "titleParent")
	public String titleParent(Locale locale) {
		return this.ms.getMessage("proglangs.title.parent", null, locale);
	}

	@GetMapping
	public String showList(Locale locale, Model model) {
		List<ProgLang> progLangs = this.service.findAll();
		if (progLangs.isEmpty()) {
			MessageHelper.addInfoAttribute(model, "proglangs.info.empty");
		}
		model.addAttribute("titleRead", this.ms.getMessage("proglangs.title.read", null, locale));
		model.addAttribute("progLangs", progLangs);
		return this.getViewNameReadDelete();
	}

	@GetMapping(URL_CREATE)
	public String showCreateForm(Locale locale, Model model) {
		model.addAttribute("titleCreate", this.ms.getMessage("proglangs.title.create", null, locale));
		model.addAttribute("langs", this.service.getLangs());
		model.addAttribute("archs", this.service.getArchs());
		model.addAttribute("progLang", new ProgLang());
		return this.getViewNameCreateUpdate();
	}

	@GetMapping(URL_EDIT)
	public String showUpdateForm(@PathVariable(PV_ID) Long id, Locale locale, Model model) {
		model.addAttribute("titleUpdate", this.ms.getMessage("proglangs.title.update", null, locale));
		model.addAttribute("langs", this.service.getLangs());
		model.addAttribute("archs", this.service.getArchs());
		model.addAttribute("progLang", this.service.getById(id));
		return this.getViewNameCreateUpdate();
	}
}
