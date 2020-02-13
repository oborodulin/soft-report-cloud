package com.oborodulin.softreport.web;

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

import com.oborodulin.softreport.domain.enterprise.EnterpriseRepository;
import com.oborodulin.softreport.domain.enterprise.Enterprise;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/enterprises/")
public class EnterpriseMvcController {

	private static final String VN_PATH = "tpl-enterprises/";
	private static final String VN_READ = VN_PATH.concat("read");
	private static final String VN_CREATE = VN_PATH.concat("create");
	private static final String VN_UPDATE = VN_PATH.concat("update");

	private final EnterpriseRepository enterpriseRepository;

	@Autowired
	public EnterpriseMvcController(EnterpriseRepository enterpriseRepository) {
		this.enterpriseRepository = enterpriseRepository;
	}

	@ModelAttribute(name = "enterprise")
	public Enterprise enterprise() {
		return new Enterprise();
	}

	@GetMapping
	public String showEnterprisesList(Model model) {
		model.addAttribute("enterprises", enterpriseRepository.findAll());
		return VN_READ;
	}

	@GetMapping("create")
	public String showCreateForm(Model model) {
		log.info("Отображение формы создания актива");
		model.addAttribute("enterprises", enterpriseRepository.findAll());
		return VN_CREATE;
	}

	@PostMapping("create")
	public String createEnterprise(@Valid @ModelAttribute("enterprise") Enterprise enterprise, Errors errors, Model model) {
		log.info("Создание актива: " + enterprise);
		if (errors.hasErrors()) {
			errors.getAllErrors().stream().forEach(System.out::println);
			log.info("Создание актива: " + enterprise);
			return VN_CREATE;
		}
		log.info("Сохранение актива: " + enterprise);
		enterpriseRepository.save(enterprise);

		return "redirect:/enterprises/";
	}

	@GetMapping("edit/{id}")
	public String showUpdateForm(@PathVariable("id") long id, Model model) {
		new IllegalArgumentException();
		Enterprise enterprise = enterpriseRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid enterprise Id:" + id));
		model.addAttribute("enterprise", enterprise);
		return VN_UPDATE;
	}

	@PostMapping("update/{id}")
	public String updateEnterprise(@PathVariable("id") long id, @Valid Enterprise enterprise, Errors errors, Model model) {
		if (errors.hasErrors()) {
			enterprise.setId(id);
			return VN_UPDATE;
		}

		enterpriseRepository.save(enterprise);
		model.addAttribute("enterprises", enterpriseRepository.findAll());
		return VN_READ;
	}

	@GetMapping("delete")
	public String deleteEnterprises(Errors errors, Model model) {
		if (errors.hasErrors()) {
			errors.getAllErrors().stream().forEach(System.out::println);
			return VN_READ;
		}
		// enterpriseRepository.save(enterprise);

		return "redirect:/enterprises/";
	}

	@GetMapping("delete/{id}")
	public String deleteEnterprise(@PathVariable("id") long id, Model model) {
		Enterprise enterprise = enterpriseRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid enterprise Id:" + id));
		enterpriseRepository.delete(enterprise);
		model.addAttribute("enterprises", enterpriseRepository.findAll());
		return VN_READ;
	}
}
