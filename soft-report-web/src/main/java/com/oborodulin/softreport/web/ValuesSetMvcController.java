package com.oborodulin.softreport.web;

import java.util.List;
import java.util.Locale;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.data.domain.Sort;

import com.oborodulin.softreport.domain.model.valuesset.ValuesSet;
import com.oborodulin.softreport.domain.model.valuesset.ValuesSetRepository;
import com.oborodulin.softreport.domain.model.valuesset.value.Value;
import com.oborodulin.softreport.domain.model.valuesset.value.ValueRepository;
import com.oborodulin.softreport.web.support.MessageHelper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/valuessets/")
public class ValuesSetMvcController {

	private static final String BASE_MST_URL = "/valuessets/";
	private static final String VN_MST_PATH = "tpl-valuessets/";
	private static final String VN_MST_READ_DELETE = VN_MST_PATH.concat("read-delete");
	private static final String VN_MST_CREATE_UPDATE = VN_MST_PATH.concat("create-update");

	private static final String BASE_DTL_URL = "/values";
	private static final String VN_DTL_PATH = "tpl-valuessets/values/";
	private static final String VN_DTL_READ_DELETE = VN_DTL_PATH.concat("read-delete");
	private static final String VN_DTL_CREATE_UPDATE = VN_DTL_PATH.concat("create-update");

	private final ValuesSetRepository valuesSetRepository;
	private final ValueRepository valueRepository;
	@Autowired
	MessageSource messageSource;

	@Autowired
	public ValuesSetMvcController(ValuesSetRepository valuesSetRepository, ValueRepository valuesRepository) {
		this.valuesSetRepository = valuesSetRepository;
		this.valueRepository = valuesRepository;
	}

	@ModelAttribute(name = "titleParent")
	public String titleParent(Locale locale) {
		return messageSource.getMessage("valuessets.title.parent", null, locale);
	}

	@ModelAttribute(name = "valuesSet")
	public ValuesSet valuesSet() {
		return new ValuesSet();
	}

	@GetMapping
	public String showValuesSetsList(Locale locale, Model model) {
		List<ValuesSet> valuesSets = valuesSetRepository.findAll();
		if (valuesSets.isEmpty()) {
			MessageHelper.addInfoAttribute(model, "valuessets.info.empty");
		}
		model.addAttribute("titleRead", messageSource.getMessage("valuessets.title.read", null, locale));
		model.addAttribute("valuesSets", valuesSets);
		return VN_MST_READ_DELETE;
	}

	@GetMapping("create")
	public String showValuesSetCreateForm(Locale locale, Model model) {
		model.addAttribute("titleCreate", messageSource.getMessage("valuessets.title.create", null, locale));
		return VN_MST_CREATE_UPDATE;
	}

	@PostMapping("create/{isContinue}")
	public String createValuesSet(@PathVariable("isContinue") boolean isContinue,
			@Valid @ModelAttribute("valuesSet") ValuesSet valuesSet, Errors errors, Model model) {
		if (errors.hasErrors()) {
			errors.getAllErrors().stream().forEach(System.out::println);
			return VN_MST_CREATE_UPDATE;
		}
		valuesSetRepository.save(valuesSet);
		if (isContinue) {
			return "redirect:".concat(BASE_MST_URL).concat("create");
		}
		return "redirect:".concat(BASE_MST_URL);
	}

	@GetMapping("edit/{id}")
	public String showValuesSetUpdateForm(@PathVariable("id") long id, Locale locale, Model model) {
		ValuesSet valuesSet = valuesSetRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid valuesSet Id:" + id));
		model.addAttribute("titleUpdate", messageSource.getMessage("valuessets.title.update", null, locale));
		model.addAttribute("valuesSet", valuesSet);
		return VN_MST_CREATE_UPDATE;
	}

	@PostMapping("update/{id}")
	public String updateValuesSet(@PathVariable("id") long id, @Valid ValuesSet valuesSet, Locale locale, Errors errors,
			Model model) {
		// ValuesSet updValuesSet = valuesSetRepository.findById(id).get();

		log.info(valuesSet.toString());
		if (errors.hasErrors()) {
			valuesSet.setId(id);
			return VN_MST_CREATE_UPDATE;
		}

		valuesSetRepository.save(valuesSet);
		model.addAttribute("titleRead", messageSource.getMessage("valuessets.title.read", null, locale));
		model.addAttribute("valuesSets", valuesSetRepository.findAll());
		return VN_MST_READ_DELETE;
	}

	@PostMapping("delete")
	public String deleteValuesSets(@RequestParam("table_records") List<String> ids) {
		if (ids != null) {
			for (String idsStr : ids) {
				long id = Long.parseLong(idsStr);
				valuesSetRepository.deleteById(id);
			}
		}
		return "redirect:".concat(BASE_MST_URL);
	}

	@GetMapping("delete/{id}")
	public String deleteValuesSet(@PathVariable("id") long id, Model model) {
		ValuesSet valuesSet = valuesSetRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid valuesSet Id:" + id));
		valuesSetRepository.delete(valuesSet);
		return "redirect:".concat(BASE_MST_URL);
	}

	@GetMapping("{setId}/values")
	public String showValuesList(@PathVariable("setId") long setId, Locale locale, Model model) {
		ValuesSet valuesSet = valuesSetRepository.findById(setId)
				.orElseThrow(() -> new IllegalArgumentException("Invalid valuesSet Id:" + setId));
		List<Value> values = valueRepository.findByValuesSet(valuesSet, Sort.by("code"));
		if (values.isEmpty()) {
			MessageHelper.addInfoAttribute(model, "values.info.empty", valuesSet.getName());
		}
		model.addAttribute("titleMaster", valuesSet.getName().concat(" [").concat(valuesSet.getCode()).concat("]"));
		// model.addAttribute("titleRead", messageSource.getMessage("values.title.read",
		// new Object[] {valuesSet.getCode()}, locale));
		model.addAttribute("titleRead", messageSource.getMessage("values.title.read", null, locale));
		model.addAttribute("valuesSet", valuesSet);
		model.addAttribute("values", values);
		return VN_DTL_READ_DELETE;
	}

	@GetMapping("{setId}/values/create")
	public String showValueCreateForm(@PathVariable("setId") long setId, Locale locale, Model model) {
		Value value = new Value();
		value.setValuesSet(valuesSetRepository.findById(setId)
				.orElseThrow(() -> new IllegalArgumentException("Invalid valuesSet Id:" + setId)));
		model.addAttribute("titleMaster",
				value.getValuesSet().getName().concat(" [").concat(value.getValuesSet().getCode()).concat("]"));
		model.addAttribute("titleCreate", messageSource.getMessage("values.title.create", null, locale));
		model.addAttribute("value", value);
		return VN_DTL_CREATE_UPDATE;
	}

	@PostMapping("{setId}/values/create/{isContinue}")
	public String createValue(@PathVariable("setId") long setId, @PathVariable("isContinue") boolean isContinue,
			@Valid @ModelAttribute("value") Value value, Locale locale, Errors errors, Model model
	// , RedirectAttributes redirectAttributes
	) {
		value.setValuesSet(valuesSetRepository.findById(setId)
				.orElseThrow(() -> new IllegalArgumentException("Invalid valuesSet Id:" + setId)));
		if (errors.hasErrors()) {
			errors.getAllErrors().stream().forEach(System.out::println);
			return VN_DTL_CREATE_UPDATE;
		}
		valueRepository.save(value);
		if (isContinue) {
			return "redirect:".concat(BASE_MST_URL).concat(Long.toString(setId)).concat(BASE_DTL_URL).concat("/create");
		}
		// redirectAttributes.addFlashAttribute("valuesSet", );
		return "redirect:".concat(BASE_MST_URL).concat(Long.toString(setId)).concat(BASE_DTL_URL);
	}

	@GetMapping("{setId}/values/edit/{id}")
	public String showValueUpdateForm(@PathVariable("setId") long setId, @PathVariable("id") long id, Locale locale,
			Model model) {
		Value value = valueRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid value Id:" + id));
		model.addAttribute("titleMaster",
				value.getValuesSet().getName().concat(" [").concat(value.getValuesSet().getCode()).concat("]"));
		model.addAttribute("titleUpdate", messageSource.getMessage("values.title.update", null, locale));
		model.addAttribute("value", value);
		return VN_DTL_CREATE_UPDATE;
	}

	@PostMapping("{setId}/values/update/{id}")
	public String updateValue(@PathVariable("setId") long setId, @PathVariable("id") long id, @Valid Value value,
			Locale locale, Errors errors, Model model) {
		if (errors.hasErrors()) {
			value.setId(id);
			return VN_DTL_CREATE_UPDATE;
		}
		value.setValuesSet(valuesSetRepository.findById(setId)
				.orElseThrow(() -> new IllegalArgumentException("Invalid valuesSet Id:" + setId)));
		valueRepository.save(value);
		ValuesSet valuesSet = valuesSetRepository.findById(setId)
				.orElseThrow(() -> new IllegalArgumentException("Invalid valuesSet Id:" + setId));
		model.addAttribute("titleMaster", valuesSet.getName().concat(" [").concat(valuesSet.getCode()).concat("]"));
		model.addAttribute("titleRead", messageSource.getMessage("values.title.read", null, locale));
		model.addAttribute("valuesSet", valuesSet);
		model.addAttribute("values", valueRepository.findByValuesSet(valuesSet, Sort.by("code")));
		return VN_DTL_READ_DELETE;
	}

	@PostMapping("{setId}/values/delete")
	public String deleteValues(@PathVariable("setId") long setId, @RequestParam("table_records") List<String> ids) {
		if (ids != null) {
			for (String idsStr : ids) {
				long id = Long.parseLong(idsStr);
				valueRepository.deleteById(id);
			}
		}
		return "redirect:".concat(BASE_MST_URL).concat(Long.toString(setId)).concat(BASE_DTL_URL);
	}

	@GetMapping("{setId}/values/delete/{id}")
	public String deleteValue(@PathVariable("setId") long setId, @PathVariable("id") long id, Model model) {
		Value value = valueRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid value Id:" + id));
		valueRepository.delete(value);
		return "redirect:".concat(BASE_MST_URL).concat(Long.toString(setId)).concat(BASE_DTL_URL);
	}
}
