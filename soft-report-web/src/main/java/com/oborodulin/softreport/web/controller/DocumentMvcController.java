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
	/** Наименование объекта контроллера (Controller Objects Name) */
	private static final String CO_NAME = "document";
	/**
	 * Наименование коллекции объектов контроллера (Controller Collection Objects
	 * Name)
	 */
	private static final String CCO_NAME = "documents";
	private static final String VN_PATH = SoftwareMvcController.VN_PATH.concat(CCO_NAME.toLowerCase()).concat("/");

	@Autowired
	public DocumentMvcController(SoftwareServiceImpl masterService, DocumentServiceImpl service) {
		super(masterService, service, BASE_URL, VN_PATH, CO_NAME, CCO_NAME);
	}

	@GetMapping(URL_CREATE)
	public String showCreateForm(Locale locale, Model model) {
		model.addAttribute(MA_TITLE_CREATE, this.ms.getMessage("documents.title.create", null, locale));
		model.addAttribute("document", new Document());
		return this.getViewNameCreateUpdate();
	}

	@GetMapping(URL_EDIT)
	public String showUpdateForm(@PathVariable(PV_ID) Long id, Locale locale, Model model) {
		model.addAttribute(MA_TITLE_UPDATE, this.ms.getMessage("documents.title.update", null, locale));
		model.addAttribute("document", this.service.getById(id));
		return this.getViewNameCreateUpdate();
	}

	@GetMapping(URL_DTL_READ)
	public String showChildrenList(@PathVariable(PV_MASTER_ID) Long masterId, Locale locale, Model model) {
		Software software = this.masterService.getById(masterId);
		if (software.getDocuments().isEmpty()) {
			MessageHelper.addInfoAttribute(model, "documents.info.empty", software.getName());
		}
		model.addAttribute(MA_TITLE_MASTER, software.getCode());
		model.addAttribute(MA_TITLE_READ, this.ms.getMessage("documents.title.read", null, locale));
		model.addAttribute("software", software);
		model.addAttribute("documents", software.getDocuments());
		return this.getViewNameReadDelete();
	}

	@GetMapping(URL_DTL_CREATE)
	public String showCreateForm(@PathVariable(PV_MASTER_ID) Long masterId, Locale locale, Model model) {
		Document documents = this.service.create(masterId);
		model.addAttribute(MA_TITLE_MASTER, documents.getMaster().getCode());
		model.addAttribute(MA_TITLE_CREATE, this.ms.getMessage("documents.title.create", null, locale));
		model.addAttribute("documents", documents);
		return this.getViewNameCreateUpdate();
	}

	@GetMapping(URL_DTL_EDIT)
	public String showUpdateForm(@PathVariable(PV_MASTER_ID) Long masterId, @PathVariable(PV_ID) Long id, Locale locale,
			Model model) {
		Document documents = this.service.getById(id);
		model.addAttribute(MA_TITLE_MASTER, documents.getMaster().getCode());
		model.addAttribute(MA_TITLE_UPDATE, this.ms.getMessage("documents.title.update", null, locale));
		model.addAttribute("documents", documents);
		return this.getViewNameCreateUpdate();
	}

}
