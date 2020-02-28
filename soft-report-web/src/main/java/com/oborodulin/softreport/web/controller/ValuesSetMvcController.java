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
import com.oborodulin.softreport.domain.model.valuesset.ValuesSet;
import com.oborodulin.softreport.domain.service.ValuesSetServiceImpl;
import com.oborodulin.softreport.web.AbstractMvcController;
import com.oborodulin.softreport.web.support.MessageHelper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping(ValuesSetMvcController.BASE_URL)
public class ValuesSetMvcController extends AbstractMvcController<ValuesSet, ValuesSetServiceImpl, String>{

	public static final String BASE_URL = "/valuessets";
	private static final String VN_PATH = "tpl-valuessets/";

	@Autowired
	public ValuesSetMvcController(ValuesSetServiceImpl service) {
		super(service, BASE_URL, VN_PATH);
	}

	@ModelAttribute(name = "titleParent")
	public String titleParent(Locale locale) {
		return this.ms.getMessage("valuessets.title.parent", null, locale);
	}

	@GetMapping
	public String showList(Locale locale, Model model) {
		List<ValuesSet> valuesSets = this.service.findAll();
		/*
		 * Optional<Set<Value>> values =
		 * this.service.getValuesBySetCode(ValuesSet.VS_SOFTWARE_TYPES); if
		 * (values.isPresent()) { for (Value value : values.get())
		 * log.info(value.toString()); }
		 */
		if (valuesSets.isEmpty()) {
			MessageHelper.addInfoAttribute(model, "valuessets.info.empty");
		}
		model.addAttribute("viewReadDelete", this.getViewNameReadDelete());
		model.addAttribute("titleRead", this.ms.getMessage("valuessets.title.read", null, locale));
		model.addAttribute("valuesSets", valuesSets);
		return this.getViewNameReadDelete();
	}

	@GetMapping(URL_CREATE)
	public String showCreateForm(Locale locale, Model model) {
		model.addAttribute("viewCreateUpdate", this.getViewNameCreateUpdate());
		model.addAttribute("titleCreate", this.ms.getMessage("valuessets.title.create", null, locale));
		model.addAttribute("valuesSet", new ValuesSet());
		return this.getViewNameCreateUpdate();
	}

	@GetMapping(URL_EDIT)
	public String showUpdateForm(@PathVariable(PV_ID) Long id, Locale locale, Model model) {
		model.addAttribute("viewCreateUpdate", this.getViewNameCreateUpdate());
		model.addAttribute("titleUpdate", this.ms.getMessage("valuessets.title.update", null, locale));
		model.addAttribute("valuesSet", this.service.getById(id));
		return this.getViewNameCreateUpdate();
	}
}
