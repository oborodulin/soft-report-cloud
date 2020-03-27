package com.oborodulin.softreport.web.controller;

import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.oborodulin.softreport.domain.model.dic.proglang.datatype.DataType;
import com.oborodulin.softreport.domain.model.dic.proglang.datatype.dataformat.DataFormat;
import com.oborodulin.softreport.domain.service.DataFormatServiceImpl;
import com.oborodulin.softreport.domain.service.DataTypeServiceImpl;
import com.oborodulin.softreport.web.AbstractMvcDetailController;
import com.oborodulin.softreport.web.support.MessageHelper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping(DataFormatMvcController.BASE_URL)
public class DataFormatMvcController
		extends AbstractMvcDetailController<DataType, DataFormat, DataTypeServiceImpl, DataFormatServiceImpl, String> {

	protected static final String BASE_URL = "/dataformats";
	private static final String VN_PATH = DataTypeMvcController.VN_PATH.concat("dataformats/");

	@Autowired
	public DataFormatMvcController(DataTypeServiceImpl masterService, DataFormatServiceImpl service) {
		super(masterService, service, BASE_URL, VN_PATH);
	}

	@ModelAttribute(name = MA_TITLE_PARENT)
	public String titleParent(Locale locale) {
		return this.ms.getMessage("datatypes.title.parent", null, locale);
	}

	@GetMapping(URL_DTL_READ)
	public String showChildrenList(@PathVariable(PV_MASTER_ID) Long masterId, Locale locale, Model model) {
		DataType dataType = this.masterService.getById(masterId);
		if (dataType.getFormats().isEmpty()) {
			MessageHelper.addInfoAttribute(model, "dataformats.info.empty", dataType.getName());
		}
		model.addAttribute(MA_TITLE_MASTER, dataType.getName());
		model.addAttribute(MA_TITLE_READ, this.ms.getMessage("dataformats.title.read", null, locale));
		model.addAttribute("dataType", dataType);
		model.addAttribute("dataFormats", dataType.getFormats());
		return this.getViewNameReadDelete();
	}

	@GetMapping(URL_DTL_CREATE)
	public String showCreateForm(@PathVariable(PV_MASTER_ID) Long masterId, Locale locale, Model model) {
		DataFormat dataFormat = this.service.create(masterId);
		model.addAttribute(MA_TITLE_MASTER, dataFormat.getMaster().getName());
		model.addAttribute(MA_TITLE_CREATE, this.ms.getMessage("dataformats.title.create", null, locale));
		model.addAttribute("dataFormat", dataFormat);
		return this.getViewNameCreateUpdate();
	}

	@GetMapping(URL_DTL_EDIT)
	public String showUpdateForm(@PathVariable(PV_MASTER_ID) Long masterId, @PathVariable(PV_ID) Long id, Locale locale,
			Model model) {
		DataFormat dataFormat = this.service.getById(id);
		model.addAttribute(MA_TITLE_MASTER, dataFormat.getMaster().getName());
		model.addAttribute(MA_TITLE_UPDATE, this.ms.getMessage("dataformats.title.update", null, locale));
		model.addAttribute("dataFormat", dataFormat);
		return this.getViewNameCreateUpdate();
	}

}
