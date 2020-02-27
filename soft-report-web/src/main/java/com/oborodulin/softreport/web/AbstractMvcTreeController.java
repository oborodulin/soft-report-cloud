package com.oborodulin.softreport.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;

import com.oborodulin.softreport.domain.model.CommonJpaTreeService;
import com.oborodulin.softreport.domain.model.TreeEntity;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

public abstract class AbstractMvcTreeController<E extends TreeEntity<E, U>, S extends CommonJpaTreeService<E, U>, U>
		extends AbstractMvcController<E, S, U> implements CommonMvcTreeController<E, U> {

	@Autowired
	protected AbstractMvcTreeController(S service, String baseUrl, String viewPath) {
		super(service, baseUrl, viewPath);
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
