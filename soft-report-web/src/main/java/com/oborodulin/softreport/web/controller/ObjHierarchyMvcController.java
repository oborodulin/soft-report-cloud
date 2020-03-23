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

import com.oborodulin.softreport.domain.model.dic.objhierarchy.ObjHierarchy;
import com.oborodulin.softreport.domain.service.ObjHierarchyServiceImpl;
import com.oborodulin.softreport.web.AbstractMvcTreeController;
import com.oborodulin.softreport.web.support.MessageHelper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping(ObjHierarchyMvcController.BASE_URL)
public class ObjHierarchyMvcController
		extends AbstractMvcTreeController<ObjHierarchy, ObjHierarchyServiceImpl, String> {

	protected static final String BASE_URL = "/objhierarches";
	private static final String VN_PATH = "tpl-objhierarches/";

	@Autowired
	public ObjHierarchyMvcController(ObjHierarchyServiceImpl service) {
		super(service, BASE_URL, VN_PATH);
	}

	@ModelAttribute(name = "titleParent")
	public String titleParent(Locale locale) {
		return this.ms.getMessage("objhierarches.title.parent", null, locale);
	}

	@GetMapping
	public String showRootList(Locale locale, Model model) {
		List<ObjHierarchy> objHierarches = this.service.findByParentIsNull();
		if (objHierarches.isEmpty()) {
			MessageHelper.addInfoAttribute(model, "objhierarches.info.empty");
		}
		model.addAttribute("titleRead", this.ms.getMessage("objhierarches.title.read", null, locale));
		model.addAttribute("objHierarches", objHierarches);
		return this.getViewNameReadDelete();
	}

	@GetMapping(URL_CREATE)
	public String showCreateForm(Locale locale, Model model) {
		log.info("Create objHierarchy");
		model.addAttribute("titleCreate", this.ms.getMessage("objhierarches.title.create", null, locale));
		model.addAttribute("objHierarches", this.service.findAllСontainerObjects());
		model.addAttribute("archs", this.service.getArchs());
		model.addAttribute("types", this.service.getTypes());
		model.addAttribute("objHierarchy", new ObjHierarchy());
		return this.getViewNameCreateUpdate();
	}

	@GetMapping(URL_CREATE_CHILD)
	public String showCreateChildForm(@PathVariable(PV_PARENT_ID) Long parentId, Locale locale, Model model) {
		ObjHierarchy objHierarchy = this.service.createChild(parentId);
		log.info("objHierarchy parent Id = " + objHierarchy.getParent().getId() + "; arch = "
				+ objHierarchy.getParent().getArch().getAttr1());
		model.addAttribute("titleCreate", this.ms.getMessage("objhierarches.title.create", null, locale));
		model.addAttribute("objHierarches", this.service.findAllParentСontainerObjects(objHierarchy));
		model.addAttribute("archs", this.service.getParentArchs(objHierarchy));
		model.addAttribute("types", this.service.getComponentTypes());
		model.addAttribute("objHierarchy", objHierarchy);
		return this.getViewNameCreateUpdate();
	}

	@GetMapping(URL_EDIT)
	public String showUpdateForm(@PathVariable(PV_ID) Long id, Locale locale, Model model) {
		model.addAttribute("titleUpdate", this.ms.getMessage("objhierarches.title.update", null, locale));
		model.addAttribute("objHierarches", this.service.findByIdIsNot(id));
		model.addAttribute("archs", this.service.getArchs());
		model.addAttribute("types", this.service.getTypes());
		model.addAttribute("objHierarchy", this.service.getById(id));
		return this.getViewNameCreateUpdate();
	}

}
