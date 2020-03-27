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

import com.oborodulin.softreport.domain.model.dic.doctype.DocType;
import com.oborodulin.softreport.domain.service.DocTypeServiceImpl;
import com.oborodulin.softreport.web.AbstractMvcController;
import com.oborodulin.softreport.web.support.MessageHelper;

@Controller
@RequestMapping(DocTypeMvcController.BASE_URL)
public class DocTypeMvcController extends AbstractMvcController<DocType, DocTypeServiceImpl, String>{

	protected static final String BASE_URL = "/doctypes";
	private static final String VN_PATH = "tpl-doctypes/";

	@Autowired
	public DocTypeMvcController(DocTypeServiceImpl service) {
		super(service, BASE_URL, VN_PATH);
	}

	@ModelAttribute(name = MA_TITLE_PARENT)
	public String titleParent(Locale locale) {
		return this.ms.getMessage("doctypes.title.parent", null, locale);
	}

	@GetMapping
	public String showList(Locale locale, Model model) {
		List<DocType> docTypes = this.service.findAll();
		if (docTypes.isEmpty()) {
			MessageHelper.addInfoAttribute(model, "doctypes.info.empty");
		}
		model.addAttribute(MA_TITLE_READ, this.ms.getMessage("doctypes.title.read", null, locale));
		model.addAttribute("docTypes", docTypes);
		return this.getViewNameReadDelete();
	}

	@GetMapping(URL_CREATE)
	public String showCreateForm(Locale locale, Model model) {
		model.addAttribute(MA_TITLE_CREATE, this.ms.getMessage("doctypes.title.create", null, locale));
		model.addAttribute("categs", this.service.getCategs());
		model.addAttribute("types", this.service.getTypes());
		model.addAttribute("docType", new DocType());
		return this.getViewNameCreateUpdate();
	}

	@GetMapping(URL_EDIT)
	public String showUpdateForm(@PathVariable(PV_ID) Long id, Locale locale, Model model) {
		model.addAttribute(MA_TITLE_UPDATE, this.ms.getMessage("doctypes.title.update", null, locale));
		model.addAttribute("categs", this.service.getCategs());
		model.addAttribute("types", this.service.getTypes());
		model.addAttribute("docType", this.service.getById(id));
		return this.getViewNameCreateUpdate();
	}
}
