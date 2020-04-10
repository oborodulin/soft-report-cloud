package com.oborodulin.softreport.web.controller;



//import java.util.Arrays;
//import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.oborodulin.softreport.domain.model.project.Project;
import com.oborodulin.softreport.domain.service.ProjectServiceImpl;
import com.oborodulin.softreport.web.AbstractTreeMvcController;

@Controller
@RequestMapping(ProjectMvcController.BASE_URL)
public class ProjectMvcController extends AbstractTreeMvcController<Project, ProjectServiceImpl, String> {

	/** Базовый URL контроллера */
	protected static final String BASE_URL = "/projects";
	/** Наименование объекта контроллера (Controller Objects Name) */
	private static final String CO_NAME = "project";
	/**
	 * Наименование коллекции объектов контроллера (Controller Objects Collection
	 * Name)
	 */
	private static final String COC_NAME = "projects";
	/** Путь к шаблонам (каталог) */
	public static final String VN_PATH = "tpl-projects/";

	@Autowired
	public ProjectMvcController(ProjectServiceImpl service) {
		super(service, BASE_URL, VN_PATH, CO_NAME, COC_NAME);
	}

}
