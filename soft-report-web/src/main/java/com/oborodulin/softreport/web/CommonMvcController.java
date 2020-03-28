package com.oborodulin.softreport.web;

import java.util.List;
import java.util.Locale;

import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import com.oborodulin.softreport.domain.common.entity.AuditableEntity;

public interface CommonMvcController<E extends AuditableEntity<U>, U> {

	/**
	 * Возвращает путь к шаблону "чтение-удаление"
	 * 
	 * @return путь к шаблону "чтение-удаление"
	 */
	public String getViewNameReadDelete();

	/**
	 * Возвращает путь к шаблону "создание-обновление"
	 * 
	 * @return путь к шаблону "создание-обновление"
	 */
	public String getViewNameCreateUpdate();

	/**
	 * Возвращает перенаправление на функцию "ЧТЕНИЕ" (базовый URL)
	 * 
	 * @return строка перенаправления на функцию "ЧТЕНИЕ" (базовый URL)
	 */
	public String getRedirectToRead();

	/**
	 * Возвращает перенаправление на функцию "СОЗДАНИЕ"
	 * 
	 * @return строка перенаправления на функцию "СОЗДАНИЕ"
	 */
	public String getRedirectToCreate();

	public String viewReadDelete();

	public String viewCreateUpdate();

	public String showList(Locale locale, Model model);

	public String showUpdateForm(Long id, Locale locale, Model model);

	public String showCreateForm(Locale locale, Model model);

	public String create(boolean isContinue, E entity, Errors errors, Model model);

	public String update(Long id, E entity, Errors errors, Model model);

	public String delete(List<String> ids);

	public String deleteById(Long id);

}
