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
import com.oborodulin.softreport.domain.model.software.document.Document;
import com.oborodulin.softreport.domain.service.DocumentServiceImpl;
import com.oborodulin.softreport.domain.service.SoftwareServiceImpl;
import com.oborodulin.softreport.web.AbstractMvcDetailController;
import com.oborodulin.softreport.web.support.MessageHelper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping(DocumentMvcController.BASE_URL)
public class DocumentMvcController
		extends AbstractMvcDetailController<Software, Document, SoftwareServiceImpl, DocumentServiceImpl, String> {

	protected static final String BASE_URL = "/documents";
	private static final String VN_PATH = SoftwareMvcController.VN_PATH.concat("documents/");

	@Autowired
	public DocumentMvcController(SoftwareServiceImpl masterService, DocumentServiceImpl service) {
		super(masterService, service, BASE_URL, VN_PATH);
	}

	@ModelAttribute(name = "titleParent")
	public String titleParent(Locale locale) {
		return this.ms.getMessage("softwares.title.parent", null, locale);
	}

	@GetMapping
	public String showList(Locale locale, Model model) {
		List<Document> documents = this.service.findAll();
		if (documents.isEmpty()) {
			MessageHelper.addInfoAttribute(model, "documents.info.empty");
		}
		model.addAttribute("titleRead", this.ms.getMessage("documents.title.read", null, locale));
		model.addAttribute("documents", documents);
		return this.getViewNameReadDelete();
	}

	@GetMapping(URL_CREATE)
	public String showCreateForm(Locale locale, Model model) {
		model.addAttribute("titleCreate", this.ms.getMessage("documents.title.create", null, locale));
		model.addAttribute("businessObject", new Document());
		return this.getViewNameCreateUpdate();
	}

	@GetMapping(URL_EDIT)
	public String showUpdateForm(@PathVariable(PV_ID) Long id, Locale locale, Model model) {
		model.addAttribute("titleUpdate", this.ms.getMessage("documents.title.update", null, locale));
		model.addAttribute("businessObject", this.service.getById(id));
		return this.getViewNameCreateUpdate();
	}

	@GetMapping(URL_DTL_READ)
	public String showChildrenList(@PathVariable(PV_MASTER_ID) Long masterId, Locale locale, Model model) {
		Software software = this.masterService.getById(masterId);
		if (software.getDocuments().isEmpty()) {
			MessageHelper.addInfoAttribute(model, "documents.info.empty", software.getName());
		}
		model.addAttribute("titleMaster", software.getCode());
		model.addAttribute("titleRead", this.ms.getMessage("documents.title.read", null, locale));
		model.addAttribute("software", software);
		model.addAttribute("documentss", software.getDocuments());
		return this.getViewNameReadDelete();
	}

	@GetMapping(URL_DTL_CREATE)
	public String showCreateForm(@PathVariable(PV_MASTER_ID) Long masterId, Locale locale, Model model) {
		Document documents = this.service.create(masterId);
		model.addAttribute("titleMaster", documents.getMaster().getCode());
		model.addAttribute("titleCreate", this.ms.getMessage("documents.title.create", null, locale));
		model.addAttribute("documents", documents);
		return this.getViewNameCreateUpdate();
	}

	@GetMapping(URL_DTL_EDIT)
	public String showUpdateForm(@PathVariable(PV_MASTER_ID) Long masterId, @PathVariable(PV_ID) Long id, Locale locale,
			Model model) {
		Document documents = this.service.getById(id);
		model.addAttribute("titleMaster", documents.getMaster().getCode());
		model.addAttribute("titleUpdate", this.ms.getMessage("documents.title.update", null, locale));
		model.addAttribute("documents", documents);
		return this.getViewNameCreateUpdate();
	}

}
