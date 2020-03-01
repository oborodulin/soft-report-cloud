package com.oborodulin.softreport.web;

import java.util.List;

import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import com.oborodulin.softreport.domain.common.entity.AuditableEntity;
import com.oborodulin.softreport.domain.common.entity.DetailEntity;

public interface CommonMvcDetailController<E extends AuditableEntity<U>, D extends DetailEntity<E, U>, U>
		extends CommonMvcController<D, U> {

	/**
	 * Возвращает перенаправление на функцию "ЧТЕНИЕ" (базовый URL) подчинённых
	 * записей
	 * 
	 * @param masterId идентификатор главной записи
	 * @return строка перенаправления на функцию "ЧТЕНИЕ" (базовый URL)
	 */
	public String getRedirectToRead(Long masterId);

	/**
	 * Возвращает перенаправление на функцию "СОЗДАНИЕ" подчинённой записи
	 * 
	 * @param masterId идентификатор главной записи
	 * @return строка перенаправления на функцию "СОЗДАНИЕ"
	 */
	public String getRedirectToCreate(Long masterId);

	public String create(Long masterId, boolean isContinue, D entity, Errors errors, Model model);

	public String update(Long masterId, Long id, D entity, Errors errors, Model model);

	public String delete(Long masterId, List<String> ids);

	public String deleteById(Long masterId, Long id);

}
