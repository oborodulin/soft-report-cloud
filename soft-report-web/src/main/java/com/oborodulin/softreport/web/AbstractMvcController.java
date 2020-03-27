package com.oborodulin.softreport.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;

import com.oborodulin.softreport.domain.common.entity.AuditableEntity;
import com.oborodulin.softreport.domain.common.service.CommonJpaService;
import com.oborodulin.softreport.web.support.MessageHelper;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

public abstract class AbstractMvcController<E extends AuditableEntity<U>, S extends CommonJpaService<E, U>, U>
		implements CommonMvcController<E, U> {
	private static final String VN_READ_DELETE = "read-delete";
	private static final String VN_CREATE_UPDATE = "create-update";

	private static final String MA_READ_DELETE = "viewReadDelete";
	private static final String MA_CREATE_UPDATE = "viewCreateUpdate";

	public static final String RV_CHK_TABLE_RECORDS = "table_records";
	public static final String PV_ID = "id";
	public static final String PV_IS_CONTINUE = "isContinue";

	public static final String URL_CREATE = "/create";
	public static final String URL_CREATE_CONTINUE = "/create/{isContinue}";
	public static final String URL_CREATE_CHILD = "/{parentId}/create";
	public static final String URL_CREATE_CHILD_CONTINUE = "/{parentId}/create/{isContinue}";
	public static final String URL_EDIT = "/edit/{id}";
	public static final String URL_UPDATE = "/update/{id}";
	public static final String URL_DELETE = "/delete";
	public static final String URL_DELETE_BY_ID = "/delete/{id}";

	protected static final String MA_TITLE_PARENT = "titleParent";
	protected static final String MA_TITLE_READ = "titleRead";
	protected static final String MA_TITLE_CREATE = "titleCreate";
	protected static final String MA_TITLE_UPDATE = "titleUpdate";

	protected static final String RM_SHOW_LIST = "showList";
	protected static final String RM_SHOW_UPDATE_FORM = "showUpdateForm";

	/** Основной сервис контроллера */
	protected final S service;
	/** Интерфейс источника сообщений (ресурсный пакет, интернационализация) */
	@Autowired
	protected MessageSource ms;

	/** Базовый URL контроллера */
	protected String baseUrl;

	/** Путь к CRUD-шаблонам контроллера */
	protected String viewPath;

	/** Наименование объекта контроллера */
	protected String objName;

	/** Наименование коллекции объектов контроллера */
	protected String collectObjName;

	/** Префикс строковых ресурсов источника сообщений */
	protected String msPrefix;

	/** Префикс строковых ресурсов источника сообщений */
	private Map<String, Map<String, Object>> modelAttributes = new HashMap<>();

	/**
	 * Конструктор инстанцирует объект
	 * 
	 * @param service  сервис доменной модели
	 * @param baseUrl  базовый URL контроллера
	 * @param viewPath путь к CRUD-шаблонам контроллера (каталог)
	 */
	@Deprecated
	@Autowired
	protected AbstractMvcController(S service, String baseUrl, String viewPath) {
		this.service = service;
		this.baseUrl = baseUrl;
		this.viewPath = viewPath;
	}

	@Autowired
	protected AbstractMvcController(S service, String baseUrl, String viewPath, String objName, String collectObjName) {
		this(service, baseUrl, viewPath);
		this.objName = objName;
		this.collectObjName = collectObjName;
		this.msPrefix = collectObjName.toLowerCase();
	}

	protected void presetModelAttributes(String requestMethName, Map<String, Object> modelAttributes) {
		this.modelAttributes.put(requestMethName, modelAttributes);
	}

	private Map<String, Object> getModelAttributes(String requestMethName) {
		return modelAttributes.get(requestMethName);
	}

	private void setModelAttributes(String requestMethName, Model model) {
		Map<String, Object> ma = getModelAttributes(requestMethName);
		if (ma == null) {
			return;
		}
		for (Map.Entry<String, Object> entry : ma.entrySet()) {
			model.addAttribute(entry.getKey(), entry.getValue());
		}
	}

	/**
	 * Возвращает путь к шаблону "чтение-удаление"
	 * 
	 * @return путь к шаблону "чтение-удаление"
	 */
	@Override
	public String getViewNameReadDelete() {
		return this.viewPath.concat(VN_READ_DELETE);
	}

	/**
	 * Возвращает путь к шаблону "создание-обновление"
	 * 
	 * @return путь к шаблону "создание-обновление"
	 */
	@Override
	public String getViewNameCreateUpdate() {
		return this.viewPath.concat(VN_CREATE_UPDATE);
	}

	/**
	 * Возвращает перенаправление на функцию "ЧТЕНИЕ" (базовый URL)
	 * 
	 * @return строка перенаправления на функцию "ЧТЕНИЕ" (базовый URL)
	 */
	@Override
	public String getRedirectToRead() {
		return "redirect:".concat(this.baseUrl);
	}

	@ModelAttribute(name = MA_TITLE_PARENT)
	public String titleParent(Locale locale) {
		return this.ms.getMessage(this.msPrefix.concat(".title.parent"), null, locale);
	}

	@Override
	@ModelAttribute(name = MA_READ_DELETE)
	public String viewReadDelete() {
		return this.getViewNameReadDelete();
	}

	@Override
	@ModelAttribute(name = MA_CREATE_UPDATE)
	public String viewCreateUpdate() {
		return this.getViewNameCreateUpdate();
	}

	/**
	 * Возвращает перенаправление на функцию "СОЗДАНИЕ"
	 * 
	 * @return строка перенаправления на функцию "СОЗДАНИЕ"
	 */
	@Override
	public String getRedirectToCreate() {
		return this.getRedirectToRead().concat(URL_CREATE);
	}

	@Override
	@GetMapping
	public String showList(Locale locale, Model model) {
		List<E> entities = this.service.findAll();
		if (entities.isEmpty()) {
			MessageHelper.addInfoAttribute(model, this.msPrefix.concat(".info.empty"));
		}
		model.addAttribute(MA_TITLE_READ, this.ms.getMessage(this.msPrefix.concat(".title.read"), null, locale));
		this.setModelAttributes(RM_SHOW_LIST, model);
		model.addAttribute(this.collectObjName, entities);
		return this.getViewNameReadDelete();
	}

	@Override
	@GetMapping(URL_EDIT)
	public String showUpdateForm(@PathVariable(PV_ID) Long id, Locale locale, Model model) {
		model.addAttribute(MA_TITLE_UPDATE, this.ms.getMessage(this.msPrefix.concat(".title.update"), null, locale));
		this.setModelAttributes(RM_SHOW_UPDATE_FORM, model);
		model.addAttribute(this.objName, this.service.getById(id));
		return this.getViewNameCreateUpdate();
	}

	@Override
	@GetMapping(URL_CREATE)
	public String showCreateForm(Locale locale, Model model) {
		model.addAttribute(MA_TITLE_CREATE, this.ms.getMessage(this.msPrefix.concat(".title.create"), null, locale));
		model.addAttribute(this.objName, this.service.create());
		return this.getViewNameCreateUpdate();
	}

	@Override
	@PostMapping(URL_CREATE_CONTINUE)
	public String create(@PathVariable(PV_IS_CONTINUE) boolean isContinue, @Valid E entity, Errors errors,
			Model model) {
		// @ModelAttribute("software") @Valid Software software
		if (errors.hasErrors()) {
			return this.getViewNameCreateUpdate();
		}
		this.service.save(entity);
		if (isContinue) {
			return this.getRedirectToCreate();
		}
		return this.getRedirectToRead();
	}

	@Override
	@PostMapping(URL_UPDATE)
	public String update(@PathVariable(PV_ID) Long id, @Valid E entity, Errors errors, Model model) {
		if (errors.hasErrors()) {
			entity.setId(id);
			return this.getViewNameCreateUpdate();
		}
		this.service.save(entity);
		return this.getRedirectToRead();
	}

	@Override
	@PostMapping(URL_DELETE)
	public String delete(@RequestParam(RV_CHK_TABLE_RECORDS) List<String> ids) {
		if (ids != null) {
			for (String idsStr : ids) {
				this.service.deleteById(Long.parseLong(idsStr));
			}
		}
		return this.getRedirectToRead();
	}

	@Override
	@GetMapping(URL_DELETE_BY_ID)
	public String deleteById(@PathVariable(PV_ID) Long id) {
		this.service.deleteById(id);
		return this.getRedirectToRead();
	}

}
