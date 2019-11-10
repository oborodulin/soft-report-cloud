package com.oborodulin.softreport.domain.project;

import java.util.List;
import javax.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.oborodulin.softreport.domain.project.Project;
import com.oborodulin.softreport.domain.software.Software;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/projects")
public class ProjectController {
	@GetMapping
	public String showProjectsForm(Model model) {
		List<Software> softwares = null;
		//Arrays.asList(new Software("IDSUGDT", "ИДС УЖДТ", ""),
		//		new Software("SCALES", "Система весоизмерения", ""));
		model.addAttribute("systems", softwares);
		model.addAttribute("project", new Project());
		return "projects";
	}

	@PostMapping
	public String processDesign(@Valid @ModelAttribute("project") Project _project, Errors errors, Model model) {
		if (errors.hasErrors()) {
			return "projects";
		}

		// Save the project...
		// We'll do this in chapter 3
		log.info("Processing project: " + _project);

		return "redirect:/orders/current";
	}

}
