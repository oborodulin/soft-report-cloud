package com.oborodulin.softreport.web.controller;

import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.oborodulin.softreport.domain.service.ValueServiceImpl;
import com.oborodulin.softreport.domain.service.ValuesSetServiceImpl;
import com.oborodulin.softreport.domain.model.dic.valuesset.ValuesSet;
import com.oborodulin.softreport.domain.model.dic.valuesset.value.Value;
import com.oborodulin.softreport.web.AbstractMvcDetailController;
import com.oborodulin.softreport.web.support.MessageHelper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping(ValueMvcController.BASE_URL)
public class ValueMvcController
		extends AbstractMvcDetailController<ValuesSet, Value, ValuesSetServiceImpl, ValueServiceImpl, String> {

	protected static final String BASE_URL = "/values";
	private static final String VN_PATH = ValuesSetMvcController.VN_PATH.concat("values/");

	@Autowired
	public ValueMvcController(ValuesSetServiceImpl masterService, ValueServiceImpl service) {
		super(masterService, service, BASE_URL, VN_PATH);
	}

	@ModelAttribute(name = MA_TITLE_PARENT)
	public String titleParent(Locale locale) {
		return this.ms.getMessage("valuessets.title.parent", null, locale);
	}

	@GetMapping(URL_DTL_READ)
	public String showChildrenList(@PathVariable(PV_MASTER_ID) Long masterId, Locale locale, Model model) {
		ValuesSet valuesSet = this.masterService.getById(masterId);
		// List<Value> values = valueRepository.findByValuesSet(valuesSet,
		// Sort.by("code"));
		if (valuesSet.getValues().isEmpty()) {
			MessageHelper.addInfoAttribute(model, "values.info.empty", valuesSet.getName());
		}
		model.addAttribute(MA_TITLE_MASTER, valuesSet.getNameAndCode());
		// model.addAttribute(MA_TITLE_READ, this.ms.getMessage("values.title.read",
		// new Object[] {valuesSet.getCode()}, locale));
		model.addAttribute(MA_TITLE_READ, this.ms.getMessage("values.title.read", null, locale));
		model.addAttribute("valuesSet", valuesSet);
		model.addAttribute("values", valuesSet.getValues());
		return this.getViewNameReadDelete();
	}

	@GetMapping(URL_DTL_CREATE)
	public String showCreateForm(@PathVariable(PV_MASTER_ID) Long masterId, Locale locale, Model model) {
		Value value = this.service.create(masterId);
		model.addAttribute(MA_TITLE_MASTER, value.getSetNameAndCode());
		model.addAttribute(MA_TITLE_CREATE, this.ms.getMessage("values.title.create", null, locale));
		model.addAttribute("value", value);
		return this.getViewNameCreateUpdate();
	}

	@GetMapping(URL_DTL_EDIT)
	public String showUpdateForm(@PathVariable(PV_MASTER_ID) Long masterId, @PathVariable(PV_ID) Long id, Locale locale,
			Model model) {
		Value value = this.service.getById(id);
		model.addAttribute(MA_TITLE_MASTER, value.getSetNameAndCode());
		model.addAttribute(MA_TITLE_UPDATE, this.ms.getMessage("values.title.update", null, locale));
		model.addAttribute("value", value);
		return this.getViewNameCreateUpdate();
	}

}
