package com.oborodulin.softreport.web;

import java.util.Locale;
import java.util.Map;

import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import com.oborodulin.softreport.domain.common.entity.TreeEntity;

public interface CommonTreeMvcController<E extends TreeEntity<E, U>, U> extends CommonMvcController<E, U> {
	
	/**
	 * Возвращает перенаправление на функцию "СОЗДАНИЕ" потомка
	 * 
	 * @return строка перенаправления на функцию "СОЗДАНИЕ" потомка
	 */
	public String getRedirectToCreateChild(Long parentId);

	public Map<String, Object> getCreateChildModelAttributes(Long parentId);

	public Map<String, Object> getShowUpdateModelAttributes(Long id);

	public String showTree(Locale locale, Model model);

	public String showCreateChildForm(Long parentId, Model model);

	public String showUpdateForm(Long id, Model model);

	public String create(Long parentId, boolean isContinue, E entity, Errors errors, Model model);

}
