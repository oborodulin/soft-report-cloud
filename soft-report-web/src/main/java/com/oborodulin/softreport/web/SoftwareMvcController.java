package com.oborodulin.softreport.web;

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

import com.oborodulin.softreport.domain.model.software.Software;
import com.oborodulin.softreport.domain.service.SoftwareService;
import com.oborodulin.softreport.web.support.MessageHelper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping(SoftwareMvcController.BASE_URL)
public class SoftwareMvcController {

	public static final String BASE_URL = "/enterprises/";
	private static final String VN_PATH = "tpl-softwares/";
	private static final String VN_READ_DELETE = VN_PATH.concat("read-delete");
	private static final String VN_CREATE_UPDATE = VN_PATH.concat("create-update");

	private final SoftwareService softwareService;
	@Autowired
	MessageSource messageSource;

	@Autowired
	public SoftwareMvcController(SoftwareService softwareService) {
		this.softwareService = softwareService;
	}

	@ModelAttribute(name = "titleParent")
	public String titleParent(Locale locale) {
		return messageSource.getMessage("softwares.title.parent", null, locale);
	}

	@ModelAttribute(name = "viewReadDelete")
	public String viewReadDelete() {
		return VN_READ_DELETE;
	}

	@ModelAttribute(name = "viewCreateUpdate")
	public String viewCreateUpdate() {
		return VN_CREATE_UPDATE;
	}

	@GetMapping
	public String showSoftwaresList(Locale locale, Model model) {
		List<Software> softwares = softwareService.findAll();
		if (softwares.isEmpty()) {
			MessageHelper.addInfoAttribute(model, "softwares.info.empty");
		}
		model.addAttribute("titleRead", messageSource.getMessage("softwares.title.read", null, locale));
		model.addAttribute("softwares", softwares);
		return VN_READ_DELETE;
	}

	@GetMapping("create")
	public String showCreateForm(Locale locale, Model model) {
		log.info("Отображение формы создания системы");
		model.addAttribute("titleCreate", messageSource.getMessage("softwares.title.create", null, locale));
		model.addAttribute("software", new Software());
		return VN_CREATE_UPDATE;
	}

	@PostMapping("create/{isContinue}")
	public String createSoftware(@PathVariable("isContinue") boolean isContinue,
			@Valid @ModelAttribute("software") Software software, Errors errors, Model model) {
		if (errors.hasErrors()) {
			errors.getAllErrors().stream().forEach(System.out::println);
			return VN_CREATE_UPDATE;
		}
		softwareService.save(software);
		if (isContinue) {
			return "redirect:".concat(BASE_URL).concat("create");
		}
		return "redirect:".concat(BASE_URL);
	}

	@GetMapping("{parentId}/create")
	public String showCreateChildForm(@PathVariable("parentId") long parentId, Locale locale, Model model) {
		model.addAttribute("titleCreate", messageSource.getMessage("softwares.title.create", null, locale));
		model.addAttribute("software", softwareService.getNewChild(parentId));
		return VN_CREATE_UPDATE;
	}

	@PostMapping("{parentId}/create/{isContinue}")
	public String createChildSoftware(@PathVariable("parentId") long parentId,
			@PathVariable("isContinue") boolean isContinue, @Valid @ModelAttribute("software") Software software,
			Errors errors, Model model) {
		if (errors.hasErrors()) {
			errors.getAllErrors().stream().forEach(System.out::println);
			return VN_CREATE_UPDATE;
		}
		softwareService.save(software);
		if (isContinue) {
			return "redirect:".concat(BASE_URL).concat("create");
		}
		return "redirect:".concat(BASE_URL);
	}

	@GetMapping("edit/{id}")
	public String showUpdateForm(@PathVariable("id") long id, Locale locale, Model model) {
		model.addAttribute("titleUpdate", messageSource.getMessage("softwares.title.update", null, locale));
		model.addAttribute("software", softwareService.getById(id));
		return VN_CREATE_UPDATE;
	}

	@PostMapping("update/{id}")
	public String updateSoftware(@PathVariable("id") long id, @Valid Software software, Errors errors, Model model) {
		if (errors.hasErrors()) {
			software.setId(id);
			return VN_CREATE_UPDATE;
		}
		softwareService.save(software);
		return "redirect:".concat(BASE_URL);
	}

	@GetMapping("delete")
	public String deleteSoftwares(@RequestParam("table_records") List<String> ids) {
		if (ids != null) {
			for (String idsStr : ids) {
				softwareService.deleteById(Long.parseLong(idsStr));
			}
		}
		return "redirect:".concat(BASE_URL);
	}

	@GetMapping("delete/{id}")
	public String deleteSoftware(@PathVariable("id") long id) {
		softwareService.deleteById(id);
		return "redirect:".concat(BASE_URL);
	}
}
