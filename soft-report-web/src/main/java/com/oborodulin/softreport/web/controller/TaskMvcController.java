package com.oborodulin.softreport.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.oborodulin.softreport.domain.model.project.Project;
import com.oborodulin.softreport.domain.model.project.task.Task;
import com.oborodulin.softreport.domain.service.ProjectServiceImpl;
import com.oborodulin.softreport.domain.service.TaskServiceImpl;
import com.oborodulin.softreport.web.AbstractMasterDetailMvcController;

@Controller
@RequestMapping(TaskMvcController.BASE_URL)
public class TaskMvcController
		extends AbstractMasterDetailMvcController<Project, Task, ProjectServiceImpl, TaskServiceImpl, String> {

	/** Базовый URL контроллера */
	protected static final String BASE_URL = "/tasks";
	/** Наименование объекта контроллера (Controller Objects Name) */
	private static final String CO_NAME = "task";
	/**
	 * Наименование коллекции объектов контроллера (Controller Objects Collection
	 * Name)
	 */
	private static final String COC_NAME = "tasks";
	/** Путь к шаблонам (каталог) */
	public static final String VN_PATH = ProjectMvcController.VN_PATH.concat(COC_NAME.toLowerCase()).concat("/");

	@Autowired
	public TaskMvcController(ProjectServiceImpl masterService, TaskServiceImpl service) {
		super(masterService, service, BASE_URL, VN_PATH, CO_NAME, COC_NAME);
	}

}
