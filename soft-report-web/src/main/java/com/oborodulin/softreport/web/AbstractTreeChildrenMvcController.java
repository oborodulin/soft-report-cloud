package com.oborodulin.softreport.web;

import java.util.List;
import java.util.Locale;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.data.domain.Sort;

import com.oborodulin.softreport.domain.common.entity.TreeEntity;
import com.oborodulin.softreport.domain.common.service.CommonJpaTreeService;
import com.oborodulin.softreport.web.support.MessageHelper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractTreeChildrenMvcController<T extends TreeEntity<T, U>, S extends CommonJpaTreeService<T, U>, U>
		extends AbstractMvcController<T, S, U> implements CommonTreeChildrenMvcController<T, U> {

	public static final String URL_CHLD_READ = "/{parentId}";
	public static final String URL_CHLD_CREATE = "/{parentId}/create";
	public static final String URL_CHLD_CREATE_CONTINUE = "/{parentId}/create/{isContinue}";
	public static final String URL_CHLD_CREATE_CHILD = "/{parentId}/{parentId}/create";
	public static final String URL_CHLD_CREATE_CHILD_CONTINUE = "/{parentId}/{parentId}/create/{isContinue}";
	public static final String URL_CHLD_EDIT = "/{parentId}/edit/{id}";
	public static final String URL_CHLD_UPDATE = "/{parentId}/update/{id}";
	public static final String URL_CHLD_DELETE = "/{parentId}/delete";
	public static final String URL_CHLD_DELETE_BY_ID = "/{parentId}/delete/{id}";

	public static final String MA_PARENT = "parent";
	public static final String MA_TITLE_MASTER = "titleMaster";

	protected static final String RM_CHLD_READ = "children-read";

	private String dtlSortPropName = "name";

	/**
	 * Конструктор. Инстанцирует объект.
	 * 
	 * @param parentService сервис главного объекта доменной модели
	 * @param service       сервис подчинённого объекта доменной модели
	 * @param baseUrl       базовый URL контроллера
	 * @param viewPath      путь к CRUD-шаблонам контроллера (каталог)
	 */
	@Autowired
	protected AbstractTreeChildrenMvcController(S service, String baseUrl, String viewPath, String objName,
			String collectObjName) {
		super(service, baseUrl, viewPath, objName, collectObjName);
	}


	public String getDtlSortPropName() {
		return dtlSortPropName;
	}


	protected void setDtlSortPropName(String dtlSortPropName) {
		this.dtlSortPropName = dtlSortPropName;
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getRedirectToRead(Long parentId) {
		String redirect = "redirect:".concat(this.baseUrl).concat("/").concat(Long.toString(parentId));
		log.info("Redirect to: " + redirect);
		return redirect;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getRedirectToCreate(Long parentId) {
		String redirect = this.getRedirectToRead(parentId).concat(URL_CREATE);
		log.info("Redirect to: " + redirect);
		return redirect;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public T createChildEntity(Long parentId) {
		return this.service.getById(parentId);
	}
	/**
	 * Возвращает строковый идентификатор главного объекта.
	 * 
	 * @param parent главный объект
	 * @param isCode признак возврата значения кода {@code getCode()} или
	 *               наименование {@code getName()}
	 * @return строковый идентификатор главного объекта (код или наименование)
	 */
	/*
	 * private String getMasterIdentifier(E parent) { Object result = null; try {
	 * Class<?> clazz = parent.getClass(); if (clazz != null) { Method method =
	 * null;
	 * 
	 * switch (this.titleMaster) { case CODE: method =
	 * clazz.getDeclaredMethod("getCode", parent.getClass()); break; case NAME:
	 * method = clazz.getDeclaredMethod("getName", parent.getClass()); }
	 * 
	 * result = method.invoke(null); } } catch (Exception e) { e.printStackTrace();
	 * } return (String) result; }
	 */

	/**
	 * {@inheritDoc}
	 */
	@Override
	@GetMapping(URL_CHLD_READ)
	public String showList(@PathVariable(PV_PARENT_ID) Long parentId, Locale locale, Model model) {
		T parent = this.service.getById(parentId);;
		List<T> children = this.service.findByParentId(parentId, Sort.by(Sort.Direction.ASC, this.dtlSortPropName));
		if (children.isEmpty()) {
			MessageHelper.addInfoAttribute(model, this.msPrefix.concat(".parent.info.empty"), parent.getCodeId());
		}
		model.mergeAttributes(this.getModelAttributes(RM_CHLD_READ));
		model.addAttribute(MA_TITLE_MASTER, parent.getCodeId());
		model.addAttribute(MA_TITLE_READ, this.ms.getMessage(this.msPrefix.concat(".title.read"), null, locale));
		model.addAttribute(MA_PARENT, parent);
		model.addAttribute(this.objCollectName, children);
		// model.addAttribute(MA_TITLE_READ, this.ms.getMessage("tasks.title.read",
		// new Object[] {project.getCode()}, locale));
		log.info(this.objCollectName + " [" + URL_CHLD_READ + "]: " + children.size() + " counts");
		return this.getViewNameReadDelete();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@GetMapping(URL_CHLD_CREATE)
	public String showCreateForm(@PathVariable(PV_PARENT_ID) Long parentId, Locale locale, Model model) {
		T child = this.createChildEntity(parentId);
		model.addAttribute(MA_TITLE_MASTER, child.getParent().getCodeId());
		model.addAttribute(MA_TITLE_CREATE, this.ms.getMessage(this.msPrefix.concat(".title.create"), null, locale));
		model.addAttribute(this.objName, child);
		log.info(this.objName + " [" + URL_CHLD_CREATE + "]: parentId = " + parentId + "; child = " + child);
		return this.getViewNameCreateUpdate();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@GetMapping(URL_CHLD_EDIT)
	public String showUpdateForm(@PathVariable(PV_PARENT_ID) Long parentId, @PathVariable(PV_ID) Long id, Locale locale,
			Model model) {
		T child = this.service.getById(id);
		model.addAttribute(MA_TITLE_MASTER, child.getParent().getCodeId());
		model.addAttribute(MA_TITLE_UPDATE, this.ms.getMessage(this.msPrefix.concat(".title.update"), null, locale));
		model.addAttribute(this.objName, child);
		log.info(this.objName + " [" + URL_CHLD_EDIT + "]: parentId = " + parentId + "; child = " + child);
		return this.getViewNameCreateUpdate();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@PostMapping(URL_CHLD_CREATE_CONTINUE)
	public String create(@PathVariable(PV_PARENT_ID) Long parentId, @PathVariable(PV_IS_CONTINUE) boolean isContinue,
			@Valid T entity, Errors errors, Model model
	// , RedirectAttributes redirectAttributes
	) {
		log.info(this.objName + " [" + URL_CHLD_EDIT + "]: parentId = " + parentId + "; isContinue = " + isContinue
				+ "; entity = " + entity);
		if (errors.hasErrors()) {
			return this.getViewNameCreateUpdate();
		}
		this.service.save(parentId, entity);
		if (isContinue) {
			return getRedirectToCreate(parentId);
		}
		// redirectAttributes.addFlashAttribute("valuesSet", );
		return this.getRedirectToRead(parentId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@PostMapping(URL_CHLD_UPDATE)
	public String update(@PathVariable(PV_PARENT_ID) Long parentId, @PathVariable(PV_ID) Long id, @Valid T entity,
			Errors errors, Model model) {
		if (errors.hasErrors()) {
			entity.setId(id);
			return this.getViewNameCreateUpdate();
		}
		this.service.save(parentId, entity);
		return this.getRedirectToRead(parentId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@PostMapping(URL_CHLD_DELETE)
	public String delete(@PathVariable(PV_PARENT_ID) Long parentId,
			@RequestParam(RV_CHK_TABLE_RECORDS) List<String> ids) {
		if (ids != null) {
			for (String idsStr : ids) {
				this.service.deleteById(Long.parseLong(idsStr));
			}
		}
		return this.getRedirectToRead(parentId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@GetMapping(URL_CHLD_DELETE_BY_ID)
	public String deleteById(@PathVariable(PV_PARENT_ID) Long parentId, @PathVariable(PV_ID) Long id) {
		this.service.deleteById(id);
		return this.getRedirectToRead(parentId);
	}

}
