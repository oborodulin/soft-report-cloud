package com.oborodulin.softreport.web.controller;

import java.util.List;
import java.util.Locale;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.oborodulin.softreport.domain.model.enterprise.Enterprise;
import com.oborodulin.softreport.domain.model.enterprise.EnterpriseRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/enterprises/")
public class EnterpriseMvcController {

	private static final String BASE_URL = "/enterprises/";

	private static final String VN_PATH = "tpl-enterprises/";
	private static final String VN_READ_DELETE = VN_PATH.concat("read-delete");
	private static final String VN_CREATE_UPDATE = VN_PATH.concat("create-update");

	private final EnterpriseRepository enterpriseRepository;
	@Autowired
	MessageSource messageSource;

	@Autowired
	public EnterpriseMvcController(EnterpriseRepository enterpriseRepository) {
		this.enterpriseRepository = enterpriseRepository;
	}

	@ModelAttribute(name = "titleParent")
	public String titleParent(Locale locale) {
		return messageSource.getMessage("enterprises.title.parent", null, locale);
	}

	@ModelAttribute(name = "titleRead")
	public String titleRead(Locale locale) {
		return messageSource.getMessage("enterprises.title.read", null, locale);
	}

	@ModelAttribute(name = "titleCreate")
	public String titleCreate(Locale locale) {
		return messageSource.getMessage("enterprises.title.create", null, locale);
	}

	@ModelAttribute(name = "titleUpdate")
	public String titleUpdate(Locale locale) {
		return messageSource.getMessage("enterprises.title.update", null, locale);
	}

	@ModelAttribute(name = "enterprise")
	public Enterprise enterprise() {
		return new Enterprise();
	}

	@GetMapping
	public String showEnterprisesList(Model model) {
		model.addAttribute("enterprises", enterpriseRepository.findAll());
		return VN_READ_DELETE;
	}

	@GetMapping("create")
	public String showCreateForm(Model model) {
		log.info("Отображение формы создания актива");
		model.addAttribute("enterprises", enterpriseRepository.findAll());
		return VN_CREATE_UPDATE;
	}

	@PostMapping("create")
	public String createEnterprise(@Valid @ModelAttribute("enterprise") Enterprise enterprise, Errors errors,
			Model model) {
		log.info("Создание актива: " + enterprise);
		if (errors.hasErrors()) {
			errors.getAllErrors().stream().forEach(System.out::println);
			log.info("Создание актива: " + enterprise);
			return VN_CREATE_UPDATE;
		}
		log.info("Сохранение актива: " + enterprise);
		enterpriseRepository.save(enterprise);

		return "redirect:".concat(BASE_URL);
	}

	@GetMapping("edit/{id}")
	public String showUpdateForm(@PathVariable("id") long id, Model model) {
		new IllegalArgumentException();
		Enterprise enterprise = enterpriseRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid enterprise Id:" + id));
		model.addAttribute("enterprise", enterprise);
		return VN_CREATE_UPDATE;
	}

	@PostMapping("update/{id}")
	public String updateEnterprise(@PathVariable("id") long id, @Valid Enterprise enterprise, Errors errors,
			Model model) {
		if (errors.hasErrors()) {
			enterprise.setId(id);
			return VN_CREATE_UPDATE;
		}

		enterpriseRepository.save(enterprise);
		model.addAttribute("enterprises", enterpriseRepository.findAll());
		return VN_READ_DELETE;
	}

	@PostMapping("delete")
	public String deleteEnterprises(@RequestParam("table_records") List<String> ids) {
		if (ids != null) {
			for (String idsStr : ids) {
				long id = Long.parseLong(idsStr);
				log.info("Удаление актива: id=" + idsStr);
				enterpriseRepository.deleteById(id);
			}
		}
		return "redirect:".concat(BASE_URL);
	}

	@GetMapping("delete/{id}")
	public String deleteEnterprise(@PathVariable("id") long id, Model model) {
		Enterprise enterprise = enterpriseRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid enterprise Id:" + id));
		enterpriseRepository.delete(enterprise);
		model.addAttribute("enterprises", enterpriseRepository.findAll());
		return VN_READ_DELETE;
	}
}
