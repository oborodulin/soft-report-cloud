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
import com.oborodulin.softreport.domain.service.ValuesSetServiceImpl;
import com.oborodulin.softreport.domain.model.valuesset.value.Value;
import com.oborodulin.softreport.web.AbstractMvcController;
import com.oborodulin.softreport.web.support.MessageHelper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping(ValuesSetMvcController.BASE_URL)
public class ValuesSetMvcController extends AbstractMvcController<ValuesSet, ValuesSetServiceImpl, String>{

	public static final String BASE_URL = "/valuessets/";
	private static final String VN_PATH = "tpl-valuessets/";
	private static final String VN_READ_DELETE = VN_PATH.concat("read-delete");
	private static final String VN_CREATE_UPDATE = VN_PATH.concat("create-update");

	private static final String BASE_DTL_URL = "/values";
	private static final String VN_DTL_PATH = "tpl-valuessets/values/";
	private static final String VN_DTL_READ_DELETE = VN_DTL_PATH.concat("read-delete");
	private static final String VN_DTL_CREATE_UPDATE = VN_DTL_PATH.concat("create-update");

	@Autowired
	public ValuesSetMvcController(ValuesSetServiceImpl service) {
		super(service, BASE_URL, VN_PATH);
	}

	@ModelAttribute(name = "titleParent")
	public String titleParent(Locale locale) {
		return this.ms.getMessage("valuessets.title.parent", null, locale);
	}

	@GetMapping
	public String showValuesSetsList(Locale locale, Model model) {
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
		model.addAttribute("viewReadDelete", VN_READ_DELETE);
		model.addAttribute("titleRead", this.ms.getMessage("valuessets.title.read", null, locale));
		model.addAttribute("valuesSets", valuesSets);
		return VN_READ_DELETE;
	}

	@GetMapping("create")
	public String showValuesSetCreateForm(Locale locale, Model model) {
		model.addAttribute("viewCreateUpdate", VN_CREATE_UPDATE);
		model.addAttribute("titleCreate", this.ms.getMessage("valuessets.title.create", null, locale));
		model.addAttribute("valuesSet", new ValuesSet());
		return VN_CREATE_UPDATE;
	}

	@PostMapping("create/{isContinue}")
	public String createValuesSet(@PathVariable("isContinue") boolean isContinue,
			@Valid @ModelAttribute("valuesSet") ValuesSet valuesSet, Errors errors, Model model) {
		if (errors.hasErrors()) {
			errors.getAllErrors().stream().forEach(System.out::println);
			return VN_CREATE_UPDATE;
		}
		this.service.save(valuesSet);
		if (isContinue) {
			return "redirect:".concat(BASE_URL).concat("create");
		}
		return "redirect:".concat(BASE_URL);
	}

	@GetMapping("edit/{id}")
	public String showValuesSetUpdateForm(@PathVariable("id") long id, Locale locale, Model model) {
		model.addAttribute("viewCreateUpdate", VN_CREATE_UPDATE);
		model.addAttribute("titleUpdate", this.ms.getMessage("valuessets.title.update", null, locale));
		model.addAttribute("valuesSet", this.service.getById(id));
		return VN_CREATE_UPDATE;
	}

	@PostMapping("update/{id}")
	public String updateValuesSet(@PathVariable("id") long id, @Valid ValuesSet valuesSet, Errors errors, Model model) {
		if (errors.hasErrors()) {
			valuesSet.setId(id);
			return VN_CREATE_UPDATE;
		}
		this.service.save(valuesSet);
		return "redirect:".concat(BASE_URL);
	}

	@PostMapping("delete")
	public String deleteValuesSets(@RequestParam("table_records") List<String> ids) {
		if (ids != null) {
			for (String idsStr : ids) {
				this.service.deleteById(Long.parseLong(idsStr));
			}
		}
		return "redirect:".concat(BASE_URL);
	}

	@GetMapping("delete/{id}")
	public String deleteValuesSet(@PathVariable("id") long id) {
		this.service.deleteById(id);
		return "redirect:".concat(BASE_URL);
	}

	@GetMapping("{setId}/values")
	public String showValuesList(@PathVariable("setId") long setId, Locale locale, Model model) {
		ValuesSet valuesSet = this.service.getById(setId);
		// List<Value> values = valueRepository.findByValuesSet(valuesSet,
		// Sort.by("code"));
		if (valuesSet.getValues().isEmpty()) {
			MessageHelper.addInfoAttribute(model, "values.info.empty", valuesSet.getName());
		}
		model.addAttribute("titleMaster", valuesSet.getNameAndCode());
		// model.addAttribute("titleRead", this.ms.getMessage("values.title.read",
		// new Object[] {valuesSet.getCode()}, locale));
		model.addAttribute("viewReadDelete", VN_DTL_READ_DELETE);
		model.addAttribute("titleRead", this.ms.getMessage("values.title.read", null, locale));
		model.addAttribute("valuesSet", valuesSet);
		model.addAttribute("values", valuesSet.getValues());
		return VN_DTL_READ_DELETE;
	}

	@GetMapping("{setId}/values/create")
	public String showValueCreateForm(@PathVariable("setId") long setId, Locale locale, Model model) {
		Value value = this.service.getNewValue(setId);
		model.addAttribute("viewCreateUpdate", VN_DTL_CREATE_UPDATE);
		model.addAttribute("titleMaster", value.getSetNameAndCode());
		model.addAttribute("titleCreate", this.ms.getMessage("values.title.create", null, locale));
		model.addAttribute("value", value);
		return VN_DTL_CREATE_UPDATE;
	}

	@PostMapping("{setId}/values/create/{isContinue}")
	public String createValue(@PathVariable("setId") long setId, @PathVariable("isContinue") boolean isContinue,
			@Valid @ModelAttribute("value") Value value, Errors errors, Model model
	// , RedirectAttributes redirectAttributes
	) {
		if (errors.hasErrors()) {
			errors.getAllErrors().stream().forEach(System.out::println);
			return VN_DTL_CREATE_UPDATE;
		}
		this.service.saveValue(setId, value);
		if (isContinue) {
			return "redirect:".concat(BASE_URL).concat(Long.toString(setId)).concat(BASE_DTL_URL).concat("/create");
		}
		// redirectAttributes.addFlashAttribute("valuesSet", );
		return "redirect:".concat(BASE_URL).concat(Long.toString(setId)).concat(BASE_DTL_URL);
	}

	@GetMapping("{setId}/values/edit/{id}")
	public String showValueUpdateForm(@PathVariable("setId") long setId, @PathVariable("id") long id, Locale locale,
			Model model) {
		Value value = this.service.findValueById(id);
		model.addAttribute("viewCreateUpdate", VN_DTL_CREATE_UPDATE);
		model.addAttribute("titleMaster", value.getSetNameAndCode());
		model.addAttribute("titleUpdate", this.ms.getMessage("values.title.update", null, locale));
		model.addAttribute("value", value);
		return VN_DTL_CREATE_UPDATE;
	}

	@PostMapping("{setId}/values/update/{id}")
	public String updateValue(@PathVariable("setId") long setId, @PathVariable("id") long id, @Valid Value value,
			Errors errors, Model model) {
		if (errors.hasErrors()) {
			value.setId(id);
			return VN_DTL_CREATE_UPDATE;
		}
		this.service.saveValue(setId, value);
		return "redirect:".concat(BASE_URL).concat(Long.toString(setId)).concat(BASE_DTL_URL);
	}

	@PostMapping("{setId}/values/delete")
	public String deleteValues(@PathVariable("setId") long setId, @RequestParam("table_records") List<String> ids) {
		if (ids != null) {
			for (String idsStr : ids) {
				long id = Long.parseLong(idsStr);
				this.service.deleteValueById(id);
			}
		}
		return "redirect:".concat(BASE_URL).concat(Long.toString(setId)).concat(BASE_DTL_URL);
	}

	@GetMapping("{setId}/values/delete/{id}")
	public String deleteValue(@PathVariable("setId") long setId, @PathVariable("id") long id, Model model) {
		this.service.deleteValueById(id);
		return "redirect:".concat(BASE_URL).concat(Long.toString(setId)).concat(BASE_DTL_URL);
	}
}
