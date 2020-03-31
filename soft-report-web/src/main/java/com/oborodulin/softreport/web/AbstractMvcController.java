package com.oborodulin.softreport.web;

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

import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
public abstract class AbstractMvcController<E extends AuditableEntity<U>, S extends CommonJpaService<E, U>, U>
		implements CommonMvcController<E, U> {
	private static final String VN_READ_DELETE = "read-delete";
	private static final String VN_CREATE_UPDATE = "create-update";

	private static final String MA_READ_DELETE = "viewReadDelete";
	private static final String MA_CREATE_UPDATE = "viewCreateUpdate";

	public static final String RV_CHK_TABLE_RECORDS = "table_records";
	public static final String PV_PARENT_ID = "parentId";
	public static final String PV_ID = "id";
	public static final String PV_IS_CONTINUE = "isContinue";

	public static final String URL_READ = "/read";
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

	protected static final String RM_CREATE = "create";
	protected static final String RM_READ = "read";
	protected static final String RM_UPDATE = "update";

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
	protected String objCollectName;

	/** Префикс строковых ресурсов источника сообщений */
	protected String msPrefix;

	/** Префикс строковых ресурсов источника сообщений */
	private Map<String, Map<String, Object>> modelAttributes = new HashMap<>();

	/**
	 * Конструктор. Инстанцирует объект
	 * 
	 * @param service        сервис доменной модели
	 * @param baseUrl        базовый URL контроллера
	 * @param viewPath       путь к CRUD-шаблонам контроллера (каталог)
	 * @param objName        наименование объекта контроллера
	 * @param objCollectName наименование коллекции объектов контроллера
	 */
	@Autowired
	protected AbstractMvcController(S service, String baseUrl, String viewPath, String objName, String objCollectName) {
		this.service = service;
		this.baseUrl = baseUrl;
		this.viewPath = viewPath;
		this.objName = objName;
		this.objCollectName = objCollectName;
		this.msPrefix = objCollectName.toLowerCase();
	}

	/**
	 * Предустанавливает атрибуты модели.
	 * <p>
	 * Используется в конструкторе
	 * 
	 * @param requestMethName идентификатор Request-метода, в котором
	 * @param modelAttributes ассоциативный список атрибутов
	 * @see #RM_SHOW_LIST
	 * @see #RM_SHOW_UPDATE_FORM
	 */
	protected void setModelAttributes(String requestMethName, Map<String, Object> modelAttributes) {
		this.modelAttributes.put(requestMethName, modelAttributes);
	}

	/**
	 * Возвращает асоциативный список атрибутов модели по зданному идентификатору
	 * Request-метода
	 * 
	 * @param requestMethName идентификатор Request-метода
	 * @return асоциативный список атрибутов модели
	 * @see #RM_SHOW_LIST
	 * @see #RM_SHOW_UPDATE_FORM
	 */
	protected Map<String, Object> getModelAttributes(String requestMethName) {
		Map<String, Object> ma = modelAttributes.get(requestMethName);
		if (ma != null) {
			ma.forEach((key, value) -> log.info(key + ":" + value));
		}
		return ma;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getViewNameReadDelete() {
		String viewName = this.viewPath.concat(VN_READ_DELETE);
		log.info("Go to view: " + viewName);
		return viewName;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getViewNameCreateUpdate() {
		String viewName = this.viewPath.concat(VN_CREATE_UPDATE);
		log.info("Go to view: " + viewName);
		return viewName;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getRedirectToRead() {
		String redirect = "redirect:".concat(this.baseUrl);
		log.info("Redirect to: " + redirect);
		return redirect;
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
	 * {@inheritDoc}
	 */
	@Override
	public String getRedirectToCreate() {
		String redirect = this.getRedirectToRead().concat(URL_CREATE);
		log.info("Redirect to: " + redirect);
		return redirect;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@GetMapping
	public String showList(Locale locale, Model model) {
		List<E> entities = this.service.findAll();
		if (entities.isEmpty()) {
			MessageHelper.addInfoAttribute(model, this.msPrefix.concat(".info.empty"));
		}
		log.info(this.objCollectName + " [/]: " + entities.size() + " counts");
		model.mergeAttributes(this.getModelAttributes(RM_READ));
		model.addAttribute(MA_TITLE_READ, this.ms.getMessage(this.msPrefix.concat(".title.read"), null, locale));
		model.addAttribute(this.objCollectName, entities);
		return this.getViewNameReadDelete();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@GetMapping(URL_CREATE)
	public String showCreateForm(Locale locale, Model model) {
		model.mergeAttributes(this.getModelAttributes(RM_CREATE));
		model.addAttribute(MA_TITLE_CREATE, this.ms.getMessage(this.msPrefix.concat(".title.create"), null, locale));
		model.addAttribute(this.objName, this.service.create());
		log.info(this.objName + " [" + URL_CREATE + "]");
		return this.getViewNameCreateUpdate();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@GetMapping(URL_EDIT)
	public String showUpdateForm(@PathVariable(PV_ID) Long id, Locale locale, Model model) {
		model.mergeAttributes(this.getModelAttributes(RM_UPDATE));
		model.addAttribute(MA_TITLE_UPDATE, this.ms.getMessage(this.msPrefix.concat(".title.update"), null, locale));
		model.addAttribute(this.objName, this.service.getById(id));
		log.info(this.objName + " [" + URL_EDIT + "]: id = " + id);
		return this.getViewNameCreateUpdate();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@PostMapping(URL_CREATE_CONTINUE)
	public String create(@PathVariable(PV_IS_CONTINUE) boolean isContinue, @Valid E entity, Errors errors,
			Model model) {
		// @ModelAttribute("software") @Valid Software software
		if (errors.hasErrors()) {
			return this.getViewNameCreateUpdate();
		}
		this.service.save(entity);
		log.info(this.objName + " [" + URL_CREATE_CONTINUE + "]: entity = " + entity + "; isContinue = " + isContinue);
		if (isContinue) {
			return this.getRedirectToCreate();
		}
		return this.getRedirectToRead();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@PostMapping(URL_UPDATE)
	public String update(@PathVariable(PV_ID) Long id, @Valid E entity, Errors errors, Model model) {
		log.info(this.objName + " [" + URL_UPDATE + "]: id = " + id + "; entity = " + entity);
		if (errors.hasErrors()) {
			entity.setId(id);
			return this.getViewNameCreateUpdate();
		}
		this.service.save(entity);
		return this.getRedirectToRead();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@PostMapping(URL_DELETE)
	public String delete(@RequestParam(RV_CHK_TABLE_RECORDS) List<String> ids) {
		if (ids != null) {
			for (String idsStr : ids) {
				this.service.deleteById(Long.parseLong(idsStr));
			}
		}
		log.info(this.objCollectName + " [" + URL_DELETE + "]: ids = " + ids);
		return this.getRedirectToRead();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@GetMapping(URL_DELETE_BY_ID)
	public String deleteById(@PathVariable(PV_ID) Long id) {
		this.service.deleteById(id);
		log.info(this.objName + " [" + URL_DELETE_BY_ID + "]: id = " + id);
		return this.getRedirectToRead();
	}

}
