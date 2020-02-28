package com.oborodulin.softreport.web.controller;

import java.util.List;
import java.util.Locale;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.oborodulin.softreport.domain.model.valuesset.ValuesSet;
import com.oborodulin.softreport.domain.service.ValueServiceImpl;
import com.oborodulin.softreport.domain.service.ValuesSetServiceImpl;
import com.oborodulin.softreport.domain.model.valuesset.value.Value;
import com.oborodulin.softreport.web.AbstractMvcController;
import com.oborodulin.softreport.web.support.MessageHelper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping(ValuesMvcController.BASE_URL)
public class ValuesMvcController extends AbstractMvcController<ValuesSet, ValueServiceImpl, String> {

	protected static final String BASE_URL = "/values/{masterId}";
	private static final String VN_PATH = "tpl-valuessets/values/";

	@Autowired
	public ValuesMvcController(ValueServiceImpl service) {
		super(service, BASE_URL, VN_PATH);
	}

	@ModelAttribute(name = "titleParent")
	public String titleParent(Locale locale) {
		return this.ms.getMessage("valuessets.title.parent", null, locale);
	}

	@GetMapping
	public String showValuesList(@PathVariable(PV_MASTER_ID) Long masterId, Locale locale, Model model) {
		ValuesSet valuesSet = this.service.getById(masterId);
		// List<Value> values = valueRepository.findByValuesSet(valuesSet,
		// Sort.by("code"));
		if (valuesSet.getValues().isEmpty()) {
			MessageHelper.addInfoAttribute(model, "values.info.empty", valuesSet.getName());
		}
		model.addAttribute("titleMaster", valuesSet.getNameAndCode());
		// model.addAttribute("titleRead", this.ms.getMessage("values.title.read",
		// new Object[] {valuesSet.getCode()}, locale));
		model.addAttribute("viewReadDelete", this.getViewNameReadDelete());
		model.addAttribute("titleRead", this.ms.getMessage("values.title.read", null, locale));
		model.addAttribute("valuesSet", valuesSet);
		model.addAttribute("values", valuesSet.getValues());
		return this.getViewNameReadDelete();
	}

	@GetMapping(URL_CREATE)
	public String showValueCreateForm(@PathVariable(PV_MASTER_ID) Long masterId, Locale locale, Model model) {
		Value value = this.service.create(masterId, new Value());
		model.addAttribute("viewCreateUpdate", this.getViewNameCreateUpdate());
		model.addAttribute("titleMaster", value.getSetNameAndCode());
		model.addAttribute("titleCreate", this.ms.getMessage("values.title.create", null, locale));
		model.addAttribute("value", value);
		return this.getViewNameCreateUpdate();
	}

	@PostMapping(URL_CREATE_CONTINUE)
	public String createValue(@PathVariable(PV_MASTER_ID) Long masterId,
			@PathVariable(PV_IS_CONTINUE) boolean isContinue, @Valid @ModelAttribute("value") Value value,
			Errors errors, Model model
	// , RedirectAttributes redirectAttributes
	) {
		if (errors.hasErrors()) {
			errors.getAllErrors().stream().forEach(System.out::println);
			return this.getViewNameCreateUpdate();
		}
		this.service.saveValue(masterId, value);
		if (isContinue) {
			return "redirect:".concat(BASE_URL).replace("{".concat(PV_MASTER_ID).concat("}"), Long.toString(masterId))
					.concat(URL_CREATE);
		}
		// redirectAttributes.addFlashAttribute("valuesSet", );
		return "redirect:".concat(BASE_URL).replace("{".concat(PV_MASTER_ID).concat("}"), Long.toString(masterId));
	}

	@GetMapping(URL_EDIT)
	public String showValueUpdateForm(@PathVariable(PV_MASTER_ID) Long masterId, @PathVariable(PV_ID) Long id,
			Locale locale, Model model) {
		Value value = this.service.findValueById(id);
		model.addAttribute("viewCreateUpdate", this.getViewNameCreateUpdate());
		model.addAttribute("titleMaster", value.getSetNameAndCode());
		model.addAttribute("titleUpdate", this.ms.getMessage("values.title.update", null, locale));
		model.addAttribute("value", value);
		return this.getViewNameCreateUpdate();
	}

	@PostMapping(URL_UPDATE)
	public String updateValue(@PathVariable(PV_MASTER_ID) Long masterId, @PathVariable(PV_ID) Long id,
			@Valid Value value, Errors errors, Model model) {
		if (errors.hasErrors()) {
			value.setId(id);
			return this.getViewNameCreateUpdate();
		}
		this.service.save(masterId, value);
		return "redirect:".concat(BASE_URL).replace("{".concat(PV_MASTER_ID).concat("}"), Long.toString(masterId));
	}

	@PostMapping(URL_DELETE)
	public String deleteValues(@PathVariable(PV_MASTER_ID) Long masterId,
			@RequestParam("table_records") List<String> ids) {
		if (ids != null) {
			for (String idsStr : ids) {
				Long id = Long.parseLong(idsStr);
				this.service.deleteValueById(id);
			}
		}
		return "redirect:".concat(BASE_URL).replace("{".concat(PV_MASTER_ID).concat("}"), Long.toString(masterId));
	}

	@GetMapping(URL_DELETE_BY_ID)
	public String deleteValue(@PathVariable(PV_MASTER_ID) Long masterId, @PathVariable(PV_ID) Long id, Model model) {
		this.service.deleteValueById(id);
		return "redirect:".concat(BASE_URL).replace("{".concat(PV_MASTER_ID).concat("}"), Long.toString(masterId));
	}
}
