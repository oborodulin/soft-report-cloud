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

	protected static final String RV_CHK_TABLE_RECORDS = "table_records";
	protected static final String PV_MAIN_ID = "mainId";
	protected static final String PV_ID = "id";
	protected static final String PV_IS_CONTINUE = "isContinue";

	protected static final String URL_READ = "/read";
	protected static final String URL_CREATE = "/create";
	protected static final String URL_CREATE_CONTINUE = "/create/{isContinue}";
	protected static final String URL_VIEW = "/view/{id}";
	protected static final String URL_EDIT = "/edit/{id}";
	protected static final String URL_UPDATE = "/update/{id}";
	protected static final String URL_DELETE = "/delete";
	protected static final String URL_DELETE_BY_ID = "/delete/{id}";

	protected static final String URL_SLV_READ = "/{mainId}";
	protected static final String URL_SLV_CREATE = "/{mainId}/create";
	protected static final String URL_SLV_CREATE_CONTINUE = "/{mainId}/create/{isContinue}";
	protected static final String URL_SLV_VIEW = "/{mainId}/view/{id}";
	protected static final String URL_SLV_EDIT = "/{mainId}/edit/{id}";
	protected static final String URL_SLV_UPDATE = "/{mainId}/update/{id}";
	protected static final String URL_SLV_DELETE = "/{mainId}/delete";
	protected static final String URL_SLV_DELETE_BY_ID = "/{mainId}/delete/{id}";

	protected static final String URL_MVC_CREATE_CONTINUE = "/mvc/create/{isContinue}";
	protected static final String URL_MVC_UPDATE = "/mvc/update/{id}";
	protected static final String URL_MVC_SLV_CREATE_CONTINUE = "/mvc/{mainId}/create/{isContinue}";
	
	protected static final String MA_MAIN_ENTITY = "mainEntity";
	protected static final String MA_TITLE_MASTER = "titleMaster";
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

	/**
	 * Наименование свойства Entity-объектов, по которому выполняется сортировка в
	 * процессе их получения.
	 * <p>
	 * По умолчанию: {@code "name"}
	 */
	private String sortPropName = "name";

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
	 * {@inheritDoc}
	 */
	@Override
	public String getBaseUrl() {
		return baseUrl;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getViewPath() {
		return viewPath;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getObjName() {
		return objName;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getObjCollectName() {
		return objCollectName;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getMsPrefix() {
		return msPrefix;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getSortPropName() {
		return sortPropName;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setSortPropName(String sortPropName) {
		this.sortPropName = sortPropName;
	}

	/**
	 * Предустанавливает атрибуты модели.
	 * <p>
	 * Используется в конструкторе для однократной инициализации данных,
	 * используемых контроллером.
	 * 
	 * @param requestMethName идентификатор Request-метода, в котором будут
	 *                        использоваться атрибуты
	 * @param modelAttributes ассоциативный список атрибутов
	 * @see #RM_SHOW_LIST
	 * @see #RM_SHOW_UPDATE_FORM
	 */
	protected void setModelAttributes(String requestMethName, Map<String, Object> modelAttributes) {
		log.info("setModelAttributes: requestMethName = " + requestMethName);
		this.modelAttributes.put(requestMethName, modelAttributes);
	}

	/**
	 * Возвращает асоциативный список атрибутов модели по зданному идентификатору
	 * Request-метода
	 * 
	 * @param requestMethName идентификатор Request-метода, в котором будут
	 *                        использоваться атрибуты
	 * @return асоциативный список атрибутов модели
	 * @see #RM_SHOW_LIST
	 * @see #RM_SHOW_UPDATE_FORM
	 */
	protected Map<String, Object> getModelAttributes(String requestMethName) {
		log.info("getModelAttributes: requestMethName = " + requestMethName);
		Map<String, Object> ma = modelAttributes.get(requestMethName);
		if (ma != null) {
			ma.forEach((key, value) -> log.debug(key + ":" + value));
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
	public String getRedirectToRead(Long mainId) {
		String redirect = this.getRedirectToRead().concat("/").concat(Long.toString(mainId));
		log.info("Redirect to: " + redirect);
		return redirect;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getRedirectToCreate(Long mainId) {
		String redirect = this.getRedirectToRead(mainId).concat(URL_CREATE);
		log.info("Redirect to: " + redirect);
		return redirect;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getRedirectFromSlaveCreate(Long mainId) {
		return this.getRedirectToRead(mainId);
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
	public List<E> getShowListEntities() {
		log.info("getShowListEntities:");
		return this.service.init(this.service.findAll());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public AuditableEntity<U> getShowListMainEntity(Long mainId) {
		log.info("getShowListMainEntity: mainId = " + mainId);
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<E> getShowListSlavesEntities(Long mainId) {
		log.info("getShowListSlavesEntities: mainId = " + mainId);
		return new ArrayList<>();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public E createEntity() {
		log.info("createEntity:");
		return this.service.create();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public E createSlaveEntity(Long mainId) {
		log.info("createSlaveEntity: mainId = " + mainId);
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public AuditableEntity<U> getMainEntity(E slaveEntity) {
		log.info("getMainEntity: slaveEntityId = " + slaveEntity.getId());
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void saveSlaveEntity(Long mainId, E slaveEntity) {
		log.info("saveSlaveEntity: mainId = " + mainId + "; slaveEntityId = " + slaveEntity.getId());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, Object> getShowCreateModelAttributes() {
		log.info("getShowCreateModelAttributes:");
		return new HashMap<>();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, Object> getShowUpdateModelAttributes(Long id) {
		log.info("getShowUpdateModelAttributes: id = " + id);
		return this.getShowCreateModelAttributes();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, Object> getShowCreateModelAttributes(Long mainId) {
		log.info("getShowCreateModelAttributes: mainId = " + mainId);
		return this.getShowCreateModelAttributes();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, Object> getShowUpdateModelAttributes(Long mainId, Long id) {
		log.info("getShowUpdateModelAttributes: mainId = " + mainId + "; id = " + id);
		return this.getShowCreateModelAttributes(mainId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@GetMapping
	public String showList(Locale locale, Model model) {
		List<E> entities = this.getShowListEntities();
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
	@GetMapping(URL_SLV_READ)
	public String showList(@PathVariable(PV_MAIN_ID) Long mainId, Locale locale, Model model) {
		AuditableEntity<U> mainEntity = this.getShowListMainEntity(mainId);
		List<E> slaves = this.getShowListSlavesEntities(mainId);
		if (slaves.isEmpty()) {
			MessageHelper.addInfoAttribute(model, this.msPrefix.concat(".main.info.empty"), mainEntity.getCodeId());
		}
		model.mergeAttributes(this.getModelAttributes(RM_READ));
		model.addAttribute(MA_TITLE_MASTER, mainEntity.getCodeId());
		model.addAttribute(MA_TITLE_READ, this.ms.getMessage(this.msPrefix.concat(".title.read"), null, locale));
		model.addAttribute(MA_MAIN_ENTITY, mainEntity);
		model.addAttribute(this.objCollectName, slaves);
		// model.addAttribute(MA_TITLE_READ, this.ms.getMessage("tasks.title.read",
		// new Object[] {project.getCode()}, locale));
		log.info(this.objCollectName + " [" + URL_SLV_READ + "]: " + slaves.size() + " counts");
		return this.getViewNameReadDelete();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@GetMapping(URL_CREATE)
	public String showCreateForm(Locale locale, Model model) {
		model.mergeAttributes(this.getShowCreateModelAttributes());
		model.mergeAttributes(this.getModelAttributes(RM_CREATE));
		model.addAttribute(MA_TITLE_CREATE, this.ms.getMessage(this.msPrefix.concat(".title.create"), null, locale));
		model.addAttribute(this.objName, this.createEntity());
		log.info(this.objName + " [" + URL_CREATE + "]");
		return this.getViewNameCreateUpdate();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@GetMapping(URL_SLV_CREATE)
	public String showCreateForm(@PathVariable(PV_MAIN_ID) Long mainId, Locale locale, Model model) {
		E slave = this.createSlaveEntity(mainId);
		model.mergeAttributes(this.getShowCreateModelAttributes(mainId));
		model.mergeAttributes(this.getModelAttributes(RM_CREATE));
		AuditableEntity<U> mainEntity = this.getMainEntity(slave);
		if (mainEntity != null) {
			model.addAttribute(MA_TITLE_MASTER, mainEntity.getCodeId());
		}
		model.addAttribute(MA_TITLE_CREATE, this.ms.getMessage(this.msPrefix.concat(".title.create"), null, locale));
		model.addAttribute(this.objName, slave);
		log.info(this.objName + " [" + URL_SLV_CREATE + "]: mainId = " + mainId + "; slave = " + slave);
		return this.getViewNameCreateUpdate();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@GetMapping(URL_EDIT)
	public String showUpdateForm(@PathVariable(PV_ID) Long id, Locale locale, Model model) {
		model.mergeAttributes(this.getShowUpdateModelAttributes(id));
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
	@GetMapping(URL_SLV_EDIT)
	public String showUpdateForm(@PathVariable(PV_MAIN_ID) Long mainId, @PathVariable(PV_ID) Long id, Locale locale,
			Model model) {
		E slave = this.service.getById(id);
		model.mergeAttributes(this.getShowUpdateModelAttributes(mainId, id));
		model.mergeAttributes(this.getModelAttributes(RM_UPDATE));
		model.addAttribute(MA_TITLE_MASTER, this.getMainEntity(slave).getCodeId());
		model.addAttribute(MA_TITLE_UPDATE, this.ms.getMessage(this.msPrefix.concat(".title.update"), null, locale));
		model.addAttribute(this.objName, slave);
		log.info(this.objName + " [" + URL_SLV_EDIT + "]: mainId = " + mainId + "; slave = " + slave);
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
			errors.getAllErrors().stream().forEach(err -> log.error(err.toString()));
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
	@PostMapping(URL_SLV_CREATE_CONTINUE)
	public String create(@PathVariable(PV_MAIN_ID) Long mainId, @PathVariable(PV_IS_CONTINUE) boolean isContinue,
			@Valid E entity, Errors errors, Model model
	// , RedirectAttributes redirectAttributes
	) {
		log.info(this.objName + " [" + URL_SLV_CREATE_CONTINUE + "]: mainId = " + mainId + "; isContinue = "
				+ isContinue + "; entity = " + entity);
		if (errors.hasErrors()) {
			errors.getAllErrors().stream().forEach(err -> log.error(err.toString()));
			return this.getViewNameCreateUpdate();
		}
		this.saveSlaveEntity(mainId, entity);
		if (isContinue) {
			return getRedirectToCreate(mainId);
		}
		// redirectAttributes.addFlashAttribute("valuesSet", );
		return this.getRedirectFromSlaveCreate(mainId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@PostMapping(URL_UPDATE)
	public String update(@PathVariable(PV_ID) Long id, @Valid E entity, Errors errors, Model model) {
		log.info(this.objName + " [" + URL_UPDATE + "]: id = " + id + "; entity = " + entity);
		if (errors.hasErrors()) {
			errors.getAllErrors().stream().forEach(err -> log.error(err.toString()));
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
	@PostMapping(URL_SLV_UPDATE)
	public String update(@PathVariable(PV_MAIN_ID) Long mainId, @PathVariable(PV_ID) Long id, @Valid E entity,
			Errors errors, Model model) {
		log.info(this.objName + " [" + URL_SLV_UPDATE + "]: id = " + id + "; entity = " + entity);
		if (errors.hasErrors()) {
			errors.getAllErrors().stream().forEach(err -> log.error(err.toString()));
			entity.setId(id);
			return this.getViewNameCreateUpdate();
		}
		this.saveSlaveEntity(mainId, entity);
		return this.getRedirectToRead(mainId);
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
	@PostMapping(URL_SLV_DELETE)
	public String delete(@PathVariable(PV_MAIN_ID) Long mainId, @RequestParam(RV_CHK_TABLE_RECORDS) List<String> ids) {
		if (ids != null) {
			for (String idsStr : ids) {
				this.service.deleteById(Long.parseLong(idsStr));
			}
		}
		log.info(this.objCollectName + " [" + URL_SLV_DELETE + "]: ids = " + ids);
		return this.getRedirectToRead(mainId);
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

	/**
	 * {@inheritDoc}
	 */
	@Override
	@GetMapping(URL_SLV_DELETE_BY_ID)
	public String deleteById(@PathVariable(PV_MAIN_ID) Long mainId, @PathVariable(PV_ID) Long id) {
		this.service.deleteById(id);
		log.info(this.objName + " [" + URL_SLV_DELETE_BY_ID + "]: id = " + id);
		return this.getRedirectToRead(mainId);
	}

}
