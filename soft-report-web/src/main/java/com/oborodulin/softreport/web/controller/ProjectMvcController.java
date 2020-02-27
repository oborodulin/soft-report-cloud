package com.oborodulin.softreport.web.controller;

//import java.util.Arrays;
//import java.util.List;

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

import com.oborodulin.softreport.domain.model.project.Project;
import com.oborodulin.softreport.domain.model.project.ProjectRepository;
import com.oborodulin.softreport.domain.model.software.SoftwareRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/projects/")
public class ProjectMvcController {

	private static final String BASE_URL = "/projects/";

	private static final String VN_PATH = "tpl-projects/";
	private static final String VN_READ = VN_PATH.concat("read");
	private static final String VN_CREATE = VN_PATH.concat("create");
	private static final String VN_UPDATE = VN_PATH.concat("update");

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
		return VN_READ;
	}

	@GetMapping("create")
	public String showCreateForm(Model model) {
		/*
		 * List<Software> softwares = (List<Software>) softwareRepository.findAll(); if
		 * (softwares == null || softwares.isEmpty()) { softwares = Arrays.asList( new
		 * Software(1L, "ИДС УЖДТ",
		 * "Информационно-диспетчерская система управления железнодорожным транспортом"
		 * ), new Software(2L, "SAP ERP", "Система SAP"), new Software(3L, "OEBS",
		 * "Oracle E-Business Suite")); }
		 */
		model.addAttribute("softwares", softwareRepository.findAll());
		log.info("Отображение формы создания проекта");
		return VN_CREATE;
	}

	@PostMapping("create")
	public String createProject(@Valid @ModelAttribute("project") Project project, Errors errors, Model model) {
		log.info("Создание проекта: " + project);
		if (errors.hasErrors()) {
			errors.getAllErrors().stream().forEach(System.out::println);
			log.info("Создание проекта: " + project);
			return VN_CREATE;
		}
		log.info("Сохранение проекта: " + project);
		projectRepository.save(project);

		return "redirect:".concat(BASE_URL);
	}

	@GetMapping("edit/{id}")
	public String showUpdateForm(@PathVariable("id") long id, Model model) {
		new IllegalArgumentException();
		Project project = projectRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid project Id:" + id));
		model.addAttribute("project", project);
		return VN_UPDATE;
	}

	@PostMapping("update/{id}")
	public String updateProject(@PathVariable("id") long id, @Valid Project project, Errors errors, Model model) {
		if (errors.hasErrors()) {
			project.setId(id);
			return VN_UPDATE;
		}

		projectRepository.save(project);
		model.addAttribute("projects", projectRepository.findAll());
		return VN_READ;
	}

	@GetMapping("delete")
	public String deleteProjects(Errors errors, Model model) {
		if (errors.hasErrors()) {
			errors.getAllErrors().stream().forEach(System.out::println);
			return VN_READ;
		}
		// projectRepository.save(project);

		return "redirect:".concat(BASE_URL);
	}

	@GetMapping("delete/{id}")
	public String deleteProject(@PathVariable("id") long id, Model model) {
		Project project = projectRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid project Id:" + id));
		projectRepository.delete(project);
		model.addAttribute("projects", projectRepository.findAll());
		return VN_READ;
	}
}
