package com.oborodulin.softreport.web.controller;

import java.util.List;
import java.util.Locale;

//import java.util.Arrays;
//import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.oborodulin.softreport.domain.model.project.Project;
import com.oborodulin.softreport.domain.service.ProjectServiceImpl;
import com.oborodulin.softreport.web.AbstractMvcTreeController;
import com.oborodulin.softreport.web.support.MessageHelper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping(ProjectMvcController.BASE_URL)
public class ProjectMvcController extends AbstractMvcTreeController<Project, ProjectServiceImpl, String> {

	protected static final String BASE_URL = "/projects";
	private static final String VN_PATH = "tpl-projects/";

	@Autowired
	public ProjectMvcController(ProjectServiceImpl service) {
		super(service, BASE_URL, VN_PATH);
	}

	@ModelAttribute(name = MA_TITLE_PARENT)
	public String titleParent(Locale locale) {
		return this.ms.getMessage("projects.title.parent", null, locale);
	}

	@ModelAttribute(name = MA_TITLE_READ)
	public String titleRead(Locale locale) {
		return this.ms.getMessage("projects.title.read", null, locale);
	}

	@ModelAttribute(name = MA_TITLE_CREATE)
	public String titleCreate(Locale locale) {
		return this.ms.getMessage("projects.title.create", null, locale);
	}

	@ModelAttribute(name = MA_TITLE_UPDATE)
	public String titleUpdate(Locale locale) {
		return this.ms.getMessage("projects.title.update", null, locale);
	}

	@GetMapping
	public String showRootList(Model model) {
		List<Project> projects = this.service.findByParentIsNull();
		if (projects.isEmpty()) {
			MessageHelper.addInfoAttribute(model, "projects.info.empty");
		}
		model.addAttribute("projects", projects);
		return this.getViewNameReadDelete();
	}

	@GetMapping(URL_CREATE)
	public String showCreateForm(Model model) {
		model.addAttribute("projects", this.service.findAll());
		model.addAttribute("project", new Project());
		return this.getViewNameCreateUpdate();
	}

	@GetMapping(URL_CREATE_CHILD)
	public String showCreateChildForm(@PathVariable(PV_PARENT_ID) Long parentId, Model model) {
		model.addAttribute("projects", this.service.findAll());
		model.addAttribute("project", this.service.createChild(parentId));
		return this.getViewNameCreateUpdate();
	}

	@GetMapping(URL_EDIT)
	public String showUpdateForm(@PathVariable(PV_ID) Long id, Model model) {
		model.addAttribute("projects", this.service.findByIdIsNot(id));
		model.addAttribute("project", this.service.getById(id));
		return this.getViewNameCreateUpdate();
	}
}
