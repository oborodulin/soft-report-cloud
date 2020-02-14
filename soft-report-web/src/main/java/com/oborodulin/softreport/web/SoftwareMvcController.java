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

import com.oborodulin.softreport.domain.software.Software;
import com.oborodulin.softreport.domain.software.SoftwareRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/softwares/")
public class SoftwareMvcController {

	private static final String BASE_URL = "/enterprises/";

	private static final String VN_PATH = "tpl-softwares/";
	private static final String VN_READ = VN_PATH.concat("read");
	private static final String VN_CREATE = VN_PATH.concat("create");
	private static final String VN_UPDATE = VN_PATH.concat("update");

	private final SoftwareRepository softwareRepository;

	@Autowired
	public SoftwareMvcController(SoftwareRepository softwareRepository) {
		this.softwareRepository = softwareRepository;
	}

	@ModelAttribute(name = "software")
	public Software software() {
		return new Software();
	}

	@GetMapping
	public String showSoftwaresList(Model model) {
		model.addAttribute("softwares", softwareRepository.findAll());
		return VN_READ;
	}

	@GetMapping("create")
	public String showCreateForm(Model model) {
		log.info("Отображение формы создания системы");
		model.addAttribute("softwares", softwareRepository.findAll());
		return VN_CREATE;
	}

	@PostMapping("create")
	public String createSoftware(@Valid @ModelAttribute("software") Software software, Errors errors, Model model) {
		log.info("Создание ПО: " + software);
		if (errors.hasErrors()) {
			errors.getAllErrors().stream().forEach(System.out::println);
			log.info("Создание ПО: " + software);
			return VN_CREATE;
		}
		log.info("Сохранение ПО: " + software);
		softwareRepository.save(software);

		return "redirect:".concat(BASE_URL);
	}

	@GetMapping("edit/{id}")
	public String showUpdateForm(@PathVariable("id") long id, Model model) {
		new IllegalArgumentException();
		Software software = softwareRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid software Id:" + id));
		model.addAttribute("software", software);
		return VN_UPDATE;
	}

	@PostMapping("update/{id}")
	public String updateSoftware(@PathVariable("id") long id, @Valid Software software, Errors errors, Model model) {
		if (errors.hasErrors()) {
			software.setId(id);
			return VN_UPDATE;
		}

		softwareRepository.save(software);
		model.addAttribute("softwares", softwareRepository.findAll());
		return VN_READ;
	}

	@GetMapping("delete")
	public String deleteSoftwares(Errors errors, Model model) {
		if (errors.hasErrors()) {
			errors.getAllErrors().stream().forEach(System.out::println);
			return VN_READ;
		}
		// softwareRepository.save(software);

		return "redirect:".concat(BASE_URL);
	}

	@GetMapping("delete/{id}")
	public String deleteSoftware(@PathVariable("id") long id, Model model) {
		Software software = softwareRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid software Id:" + id));
		softwareRepository.delete(software);
		model.addAttribute("softwares", softwareRepository.findAll());
		return VN_READ;
	}
}
