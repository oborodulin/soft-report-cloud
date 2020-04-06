package com.oborodulin.softreport.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;

import com.oborodulin.softreport.domain.common.entity.TreeEntity;
import com.oborodulin.softreport.domain.common.service.CommonJpaTreeService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
public abstract class AbstractTreeMvcController<E extends TreeEntity<E, U>, S extends CommonJpaTreeService<E, U>, U>
		extends AbstractMvcController<E, S, U> implements CommonTreeMvcController<E, U> {

	public static final String PV_PARENT_ID = "parentId";

	protected static final String RM_CREATE_CHILD = "create-child";

	/**
	 * Конструктор. Инстанцирует объект.
	 * 
	 * @param service  сервис объекта доменной модели
	 * @param baseUrl  базовый URL контроллера
	 * @param viewPath путь к CRUD-шаблонам контроллера (каталог)
	 */
	@Autowired
	protected AbstractTreeMvcController(S service, String baseUrl, String viewPath, String objName,
			String objCollectName) {
		super(service, baseUrl, viewPath, objName, objCollectName);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getRedirectToCreateChild(Long parentId) {
		String redirect = super.getRedirectToRead().concat("/").concat(Long.toString(parentId)).concat(URL_CREATE);
		log.info("Redirect to: " + redirect);
		return redirect;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, Object> getShowCreateModelAttributes() {
		Map<String, Object> ma = new HashMap<>();
		ma.put(this.objCollectName, this.service.findAll());
		ma.forEach((key, value) -> log.info(key + ":" + value));
		return ma;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, Object> getCreateChildModelAttributes(Long parentId) {
		return this.getShowCreateModelAttributes();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, Object> getShowUpdateModelAttributes(Long id) {
		Map<String, Object> ma = new HashMap<>();
		ma.put(this.objCollectName, this.service.findByIdIsNot(id));
		ma.forEach((key, value) -> log.info(key + ":" + value));
		return ma;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<E> getShowListEntities() {
		return this.service.findByParentIsNull();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@GetMapping(URL_CREATE_CHILD)
	public String showCreateChildForm(@PathVariable(PV_PARENT_ID) Long parentId, Model model) {
		model.mergeAttributes(this.getCreateChildModelAttributes(parentId));
		model.mergeAttributes(this.getModelAttributes(RM_CREATE_CHILD));
		model.addAttribute(this.objName, this.service.create(parentId));
		log.info(this.objName + " [" + URL_CREATE_CHILD + "]: parentId = " + parentId);
		return this.getViewNameCreateUpdate();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@PostMapping(URL_CREATE_CHILD_CONTINUE)
	public String create(@PathVariable(PV_PARENT_ID) Long parentId, @PathVariable(PV_IS_CONTINUE) boolean isContinue,
			@Valid E entity, Errors errors, Model model) {
		if (errors.hasErrors()) {
			return this.getViewNameCreateUpdate();
		}
		this.service.save(parentId, entity);
		if (isContinue) {
			log.info(this.objName + " [" + URL_CREATE_CHILD_CONTINUE + "]: entity = " + entity);
			return this.getRedirectToCreateChild(parentId);
		}
		return this.getRedirectToRead();
	}

}
