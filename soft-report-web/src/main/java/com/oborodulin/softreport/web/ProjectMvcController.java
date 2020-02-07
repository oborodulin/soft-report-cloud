package com.oborodulin.softreport.web;

import java.util.Arrays;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.oborodulin.softreport.domain.project.Project;
import com.oborodulin.softreport.domain.project.ProjectRepository;
import com.oborodulin.softreport.domain.software.Software;
import com.oborodulin.softreport.domain.software.SoftwareRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/projects/")
public class ProjectMvcController {
	private final SoftwareRepository softwareRepository;
	private ProjectRepository projectRepository;

	@Autowired
	public ProjectMvcController(ProjectRepository projectRepository, SoftwareRepository softwareRepository) {
		this.projectRepository = projectRepository;
		this.softwareRepository = softwareRepository;
	}

	@ModelAttribute(name = "project")
	public Project project() {
		return new Project();
	}

	@GetMapping
	public String showProjectsList(Model model) {
		model.addAttribute("projects", projectRepository.findAll());
		return "projects";
	}

	@GetMapping("create")
	public String showCreateForm(Model model) {
		List<Software> softwares = (List<Software>) softwareRepository.findAll();
		if (softwares == null || softwares.isEmpty()) {
			softwares = Arrays.asList(
				new Software(1L, "ИДС УЖДТ",
						"Информационно-диспетчерская система управления железнодорожным транспортом"),
				new Software(2L, "SAP ERP", "Система SAP"), new Software(3L, "OEBS", "Oracle E-Business Suite"));
		}
		model.addAttribute("softwares", softwares);
		log.info("Отображение формы создания проекта");
		return "create-project";
	}

	@PostMapping("create")
	public String createProject(@Valid @ModelAttribute("project") Project project, Errors errors, Model model) {
		log.info("Создание проекта: " + project);
		if (errors.hasErrors()) {
			errors.getAllErrors().stream().forEach(System.out::println);
			log.info("Создание проекта: " + project);
			return "create-project";
		}
		log.info("Сохранение проекта: " + project);
		projectRepository.save(project);

		return "redirect:/projects/";
	}

	@GetMapping("edit/{id}")
	public String showUpdateForm(@PathVariable("id") long id, Model model) {
		new IllegalArgumentException();
		Project project = projectRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid project Id:" + id));
		model.addAttribute("project", project);
		return "update-project";
	}

	@PostMapping("update/{id}")
	public String updateStudent(@PathVariable("id") long id, @Valid Project project, Errors errors, Model model) {
		if (errors.hasErrors()) {
			project.setId(id);
			return "update-project";
		}

		projectRepository.save(project);
		model.addAttribute("projects", projectRepository.findAll());
		return "projects";
	}

	@GetMapping("delete/{id}")
	public String deleteStudent(@PathVariable("id") long id, Model model) {
		Project project = projectRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid project Id:" + id));
		projectRepository.delete(project);
		model.addAttribute("projects", projectRepository.findAll());
		return "projects";
	}
}
