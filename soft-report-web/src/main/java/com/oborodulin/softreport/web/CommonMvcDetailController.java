package com.oborodulin.softreport.web;

import java.util.List;
import java.util.Locale;

import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import com.oborodulin.softreport.domain.common.entity.AuditableEntity;
import com.oborodulin.softreport.domain.common.entity.DetailEntity;

public interface CommonMvcDetailController<E extends AuditableEntity<U>, D extends DetailEntity<E, U>, U>
		extends CommonMvcController<D, U> {

	/**
	 * Возвращает перенаправление на функцию "ЧТЕНИЕ" (базовый URL) подчинённых
	 * объектов.
	 * 
	 * @param masterId идентификатор главного объекта
	 * @return строка перенаправления на функцию "ЧТЕНИЕ" (базовый URL)
	 */
	public String getRedirectToRead(Long masterId);

	/**
	 * Возвращает перенаправление на функцию "СОЗДАНИЕ" подчинённого объекта.
	 * 
	 * @param masterId идентификатор главного объекта
	 * @return строка перенаправления на функцию "СОЗДАНИЕ"
	 */
	public String getRedirectToCreate(Long masterId);

	/**
	 * Формирует и возвращает перечень подчинённых объектов.
	 * 
	 * @param masterId идентификатор главного объекта
	 * @param locale   локаль
	 * @param model    модель
	 * @return путь к шаблону "чтение-удаление"
	 */
	public String showList(Long masterId, Locale locale, Model model);

	/**
	 * Создаёт новый и возвращает для наполнения данными подчинённый объект
	 * 
	 * @param masterId идентификатор главного объекта
	 * @param locale   локаль
	 * @param model    модель
	 * @return путь к шаблону "создание-изменение"
	 */
	public String showCreateForm(Long masterId, Locale locale, Model model);

	/**
	 * Возвращает для изменения существующий подчинённый объект
	 * 
	 * @param masterId идентификатор главного объекта
	 * @param locale   локаль
	 * @param model    модель
	 * @return путь к шаблону "создание-изменение"
	 */
	public String showUpdateForm(Long masterId, Long id, Locale locale, Model model);

	/**
	 * Создаёт дочерний доменный объект по полученным данным модели.
	 * 
	 * <p>
	 * Обеспечивает возврат на создание и ввод данных по новому объекту при наличие
	 * признака продолжения {@code isContinue = true}
	 * 
	 * @param masterId   идентификатор главного объекта
	 * @param isContinue признак продолжения создания дочернего объекта
	 * @param entity     дочерний объект, подлежащий добавлению в доменную модель
	 * @param errors     ошибки модели
	 * @param model      модель
	 * @return путь к шаблону "чтение-удаление" при {@code isContinue = false}, или
	 *         "создание-изменение" при {@code isContinue = true}
	 */
	public String create(Long masterId, boolean isContinue, D entity, Errors errors, Model model);

	/**
	 * Обновляет дочерний доменный объект по полученным данным модели.
	 * 
	 * @param masterId идентификатор главного объекта
	 * @param entity   дочерний объект, подлежащий обновлению в доменную модель
	 * @param errors   ошибки модели
	 * @param model    модель
	 * @return путь к шаблону "чтение-удаление"
	 */
	public String update(Long masterId, Long id, D entity, Errors errors, Model model);

	/**
	 * Удаляет дочерние доменные объекты по заданному списку идентификаторов.
	 * 
	 * @param masterId идентификатор главного объекта
	 * @param ids      список идентификаторов
	 * @return путь к шаблону "чтение-удаление"
	 */
	public String delete(Long masterId, List<String> ids);

	/**
	 * Удаляет дочерний доменный объект по заданному идентификатору.
	 * 
	 * @param masterId идентификатор главного объекта
	 * @param id       идентификатор дочернего объекта
	 * @return путь к шаблону "чтение-удаление"
	 */
	public String deleteById(Long masterId, Long id);

}
