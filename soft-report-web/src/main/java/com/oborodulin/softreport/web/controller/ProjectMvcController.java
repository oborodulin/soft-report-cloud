package com.oborodulin.softreport.web.controller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.oborodulin.softreport.domain.model.project.Project;
import com.oborodulin.softreport.domain.service.ProjectServiceImpl;
import com.oborodulin.softreport.web.AbstractTreeMvcController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping(ProjectMvcController.BASE_URL)
public class ProjectMvcController extends AbstractTreeMvcController<Project, ProjectServiceImpl, String> {

	public static final String PV_SEL_SOFTWARES = "selectSoftwares[]";

	/** Базовый URL контроллера */
	protected static final String BASE_URL = "/projects";
	/** Наименование объекта контроллера (Controller Objects Name) */
	private static final String CO_NAME = "project";
	/**
	 * Наименование коллекции объектов контроллера (Controller Objects Collection
	 * Name)
	 */
	public static final String COC_NAME = "projects";
	/** Путь к шаблонам (каталог) */
	public static final String VN_PATH = "tpl-projects/";

	@Autowired
	public ProjectMvcController(ProjectServiceImpl service) {
		super(service, BASE_URL, VN_PATH, CO_NAME, COC_NAME);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, Object> getShowCreateModelAttributes() {
		Map<String, Object> ma = super.getShowCreateModelAttributes();
		ma.put("softwares", this.service.getSelectSoftwares());
		return ma;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, Object> getShowUpdateModelAttributes(Long id) {
		Map<String, Object> ma = super.getShowUpdateModelAttributes(id);
		ma.put("softwares", this.service.getSelectSoftwares(id));
		return ma;
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@PostMapping(URL_MVC_CREATE_CONTINUE)
	public String create(@RequestParam(PV_SEL_SOFTWARES) List<String> softwares,
			@PathVariable(PV_IS_CONTINUE) boolean isContinue, @Valid Project entity, Errors errors, Model model) {
		if (errors.hasErrors()) {
			errors.getAllErrors().stream().forEach(err -> log.error(err.toString()));
			return this.getViewNameCreateUpdate();
		}
		this.service.save(entity, softwares);
		log.info(this.objName + " [" + URL_MVC_CREATE_CONTINUE + "]: entity = " + entity + "; isContinue = "
				+ isContinue);
		if (isContinue) {
			return this.getRedirectToCreate();
		}
		return this.getRedirectToRead();
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@PostMapping(URL_MVC_SLV_CREATE_CONTINUE)
	public String create(@RequestParam(PV_SEL_SOFTWARES) List<String> softwares, @PathVariable(PV_MAIN_ID) Long mainId,
			@PathVariable(PV_IS_CONTINUE) boolean isContinue, @Valid Project entity, Errors errors, Model model) {
		log.info(this.objName + " [" + URL_MVC_SLV_CREATE_CONTINUE + "]: mainId = " + mainId + "; isContinue = "
				+ isContinue + "; entity = " + entity);
		if (errors.hasErrors()) {
			errors.getAllErrors().stream().forEach(err -> log.error(err.toString()));
			return this.getViewNameCreateUpdate();
		}
		this.service.save(mainId, entity, softwares);
		if (isContinue) {
			return getRedirectToCreate(mainId);
		}
		return this.getRedirectFromSlaveCreate(mainId);
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@PostMapping(URL_MVC_UPDATE)
	public String update(@RequestParam(PV_SEL_SOFTWARES) List<String> softwares, @PathVariable(PV_ID) Long id,
			@Valid Project entity, Errors errors, Model model) {
		log.info(this.objName + " [" + URL_MVC_UPDATE + "]: id = " + id + "; entity = " + entity);
		if (errors.hasErrors()) {
			errors.getAllErrors().stream().forEach(err -> log.error(err.toString()));
			entity.setId(id);
			return this.getViewNameCreateUpdate();
		}
		this.service.save(entity, softwares);
		return this.getRedirectToRead();
	}

}
