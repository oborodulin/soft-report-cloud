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

import com.oborodulin.softreport.domain.model.project.Project;
import com.oborodulin.softreport.domain.model.project.task.Task;
import com.oborodulin.softreport.domain.model.valuesset.ValuesSet;
import com.oborodulin.softreport.domain.service.ProjectServiceImpl;
import com.oborodulin.softreport.domain.service.TaskServiceImpl;
import com.oborodulin.softreport.web.AbstractMvcDetailController;
import com.oborodulin.softreport.web.support.MessageHelper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping(TaskMvcController.BASE_URL)
public class TaskMvcController
		extends AbstractMvcDetailController<Project, Task, ProjectServiceImpl, TaskServiceImpl, String> {

	protected static final String BASE_URL = "/tasks";
	private static final String VN_PATH = "tpl-projects/tasks/";

	@Autowired
	public TaskMvcController(ProjectServiceImpl masterService, TaskServiceImpl service) {
		super(masterService, service, BASE_URL, VN_PATH);
	}

	@ModelAttribute(name = "titleParent")
	public String titleParent(Locale locale) {
		return this.ms.getMessage("valuessets.title.parent", null, locale);
	}

	@GetMapping
	public String showList(Locale locale, Model model) {
		List<Task> tasks = this.service.findAll();
		if (tasks.isEmpty()) {
			MessageHelper.addInfoAttribute(model, "tasks.info.empty");
		}
		model.addAttribute("titleRead", this.ms.getMessage("tasks.title.read", null, locale));
		model.addAttribute("tasks", tasks);
		return this.getViewNameReadDelete();
	}
	
	@GetMapping(URL_DTL_READ)
	public String showChildrenList(@PathVariable(PV_MASTER_ID) Long masterId, Locale locale, Model model) {
		Project project = this.masterService.getById(masterId);
		// List<Task> tasks = valueRepository.findByValuesSet(project,
		// Sort.by("code"));
		if (project.getTasks().isEmpty()) {
			MessageHelper.addInfoAttribute(model, "tasks.info.empty", project.getName());
		}
		model.addAttribute("titleMaster", project.getName());
		// model.addAttribute("titleRead", this.ms.getMessage("tasks.title.read",
		// new Object[] {project.getCode()}, locale));
		model.addAttribute("titleRead", this.ms.getMessage("tasks.title.read", null, locale));
		model.addAttribute("project", project);
		model.addAttribute("tasks", project.getTasks());
		return this.getViewNameReadDelete();
	}

	@GetMapping(URL_DTL_CREATE)
	public String showCreateForm(@PathVariable(PV_MASTER_ID) Long masterId, Locale locale, Model model) {
		Task task = this.service.create(masterId);
		model.addAttribute("titleMaster", task.getName());
		model.addAttribute("titleCreate", this.ms.getMessage("tasks.title.create", null, locale));
		model.addAttribute("task", task);
		return this.getViewNameCreateUpdate();
	}

	@GetMapping(URL_DTL_EDIT)
	public String showUpdateForm(@PathVariable(PV_MASTER_ID) Long masterId, @PathVariable(PV_ID) Long id, Locale locale,
			Model model) {
		Task task = this.service.getById(id);
		model.addAttribute("titleMaster", task.getName());
		model.addAttribute("titleUpdate", this.ms.getMessage("tasks.title.update", null, locale));
		model.addAttribute("task", task);
		return this.getViewNameCreateUpdate();
	}

}
