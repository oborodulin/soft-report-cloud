package com.oborodulin.softreport.web;

import java.util.List;
import java.util.Locale;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;

import com.oborodulin.softreport.domain.common.entity.TreeEntity;
import com.oborodulin.softreport.domain.common.service.CommonJpaTreeService;
import com.oborodulin.softreport.web.support.MessageHelper;

import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
public abstract class AbstractMvcTreeController<E extends TreeEntity<E, U>, S extends CommonJpaTreeService<E, U>, U>
		extends AbstractMvcController<E, S, U> implements CommonMvcTreeController<E, U> {
	public static final String PV_PARENT_ID = "parentId";

	@Autowired
	protected AbstractMvcTreeController(S service, String baseUrl, String viewPath) {
		super(service, baseUrl, viewPath);
	}

	/**
	 * Конструктор. Инстанцирует объект.
	 * 
	 * @param service       сервис объекта доменной модели
	 * @param baseUrl       базовый URL контроллера
	 * @param viewPath      путь к CRUD-шаблонам контроллера (каталог)
	 */
	@Autowired
	protected AbstractMvcTreeController(S service, String baseUrl, String viewPath, String objName,
			String collectObjName) {
		super(service, baseUrl, viewPath, objName, collectObjName);
	}

	/**
	 * Возвращает перенаправление на функцию "СОЗДАНИЕ" потомка
	 * 
	 * @return строка перенаправления на функцию "СОЗДАНИЕ" потомка
	 */
	@Override
	public String getRedirectToCreateChild(Long parentId) {
		return this.getRedirectToRead().concat("/").concat(Long.toString(parentId)).concat(URL_CREATE);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@GetMapping
	public String showRootList(Locale locale, Model model) {
		List<E> entities = this.service.findByParentIsNull();
		if (entities.isEmpty()) {
			MessageHelper.addInfoAttribute(model, this.msPrefix.concat(".info.empty"));
		}
		model.mergeAttributes(this.getModelAttributes(RM_READ));
		model.addAttribute(MA_TITLE_READ, this.ms.getMessage(this.msPrefix.concat(".title.read"), null, locale));
		model.addAttribute(this.collectObjName, entities);
		return this.getViewNameReadDelete();
	}

	@Override
	@PostMapping(URL_CREATE_CHILD_CONTINUE)
	public String createChild(@PathVariable(PV_PARENT_ID) Long parentId,
			@PathVariable(PV_IS_CONTINUE) boolean isContinue, @Valid E entity, Errors errors, Model model) {
		if (errors.hasErrors()) {
			return this.getViewNameCreateUpdate();
		}
		this.service.saveChild(parentId, entity);
		if (isContinue) {
			return this.getRedirectToCreateChild(parentId);
		}
		return this.getRedirectToRead();
	}

}
