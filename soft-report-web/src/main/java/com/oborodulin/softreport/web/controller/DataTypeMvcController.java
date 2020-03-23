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

import com.oborodulin.softreport.domain.service.DataTypeServiceImpl;
import com.oborodulin.softreport.domain.service.ProgLangServiceImpl;
import com.oborodulin.softreport.domain.model.dic.proglang.ProgLang;
import com.oborodulin.softreport.domain.model.dic.proglang.datatype.DataType;
import com.oborodulin.softreport.web.AbstractMvcDetailController;
import com.oborodulin.softreport.web.support.MessageHelper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping(DataTypeMvcController.BASE_URL)
public class DataTypeMvcController
		extends AbstractMvcDetailController<ProgLang, DataType, ProgLangServiceImpl, DataTypeServiceImpl, String> {

	protected static final String BASE_URL = "/datatypes";
	private static final String VN_PATH = ProgLangMvcController.VN_PATH.concat("datatypes/");

	@Autowired
	public DataTypeMvcController(ProgLangServiceImpl masterService, DataTypeServiceImpl service) {
		super(masterService, service, BASE_URL, VN_PATH);
	}

	@ModelAttribute(name = "titleParent")
	public String titleParent(Locale locale) {
		return this.ms.getMessage("proglangs.title.parent", null, locale);
	}

	@GetMapping
	public String showList(Locale locale, Model model) {
		List<DataType> dataTypes = this.service.findAll();
		if (dataTypes.isEmpty()) {
			MessageHelper.addInfoAttribute(model, "datatypes.info.empty");
		}
		model.addAttribute("titleRead", this.ms.getMessage("datatypes.title.read", null, locale));
		model.addAttribute("dataTypes", dataTypes);
		return this.getViewNameReadDelete();
	}

	@GetMapping(URL_CREATE)
	public String showCreateForm(Locale locale, Model model) {
		model.addAttribute("titleCreate", this.ms.getMessage("datatypes.title.create", null, locale));
		model.addAttribute("progLangs", this.masterService.findAll());
		model.addAttribute("sqlTypes", this.service.getSqlTypes());
		model.addAttribute("dataType", new DataType());
		return this.getViewNameCreateUpdate();
	}

	@GetMapping(URL_EDIT)
	public String showUpdateForm(@PathVariable(PV_ID) Long id, Locale locale, Model model) {
		model.addAttribute("titleUpdate", this.ms.getMessage("datatypes.title.update", null, locale));
		model.addAttribute("progLangs", this.masterService.findAll());
		model.addAttribute("sqlTypes", this.service.getSqlTypes());
		model.addAttribute("dataType", this.service.getById(id));
		return this.getViewNameCreateUpdate();
	}

	@GetMapping(URL_DTL_READ)
	public String showChildrenList(@PathVariable(PV_MASTER_ID) Long masterId, Locale locale, Model model) {
		ProgLang progLang = this.masterService.getById(masterId);
		if (progLang.getDataTypes().isEmpty()) {
			MessageHelper.addInfoAttribute(model, "proglang.datatypes.info.empty", progLang.getLang().getVal());
		}
		model.addAttribute("titleMaster", progLang.getLang().getVal());
		model.addAttribute("titleRead", this.ms.getMessage("datatypes.title.read", null, locale));
		model.addAttribute("progLang", progLang);
		model.addAttribute("dataTypes", progLang.getDataTypes());
		return this.getViewNameReadDelete();
	}

	@GetMapping(URL_DTL_CREATE)
	public String showCreateForm(@PathVariable(PV_MASTER_ID) Long masterId, Locale locale, Model model) {
		DataType dataType = this.service.create(masterId);
		model.addAttribute("titleMaster", dataType.getMaster().getLang().getVal());
		model.addAttribute("titleCreate", this.ms.getMessage("datatypes.title.create", null, locale));
		model.addAttribute("dataType", dataType);
		return this.getViewNameCreateUpdate();
	}

	@GetMapping(URL_DTL_EDIT)
	public String showUpdateForm(@PathVariable(PV_MASTER_ID) Long masterId, @PathVariable(PV_ID) Long id, Locale locale,
			Model model) {
		DataType dataType = this.service.getById(id);
		model.addAttribute("titleMaster", dataType.getMaster().getLang().getVal());
		model.addAttribute("titleUpdate", this.ms.getMessage("datatypes.title.update", null, locale));
		model.addAttribute("dataType", dataType);
		return this.getViewNameCreateUpdate();
	}

}
