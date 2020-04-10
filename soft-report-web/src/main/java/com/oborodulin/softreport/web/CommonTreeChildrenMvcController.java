package com.oborodulin.softreport.web;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import com.oborodulin.softreport.domain.common.entity.TreeEntity;

public interface CommonTreeChildrenMvcController<T extends TreeEntity<T, U>, U> extends CommonMvcController<T, U> {

	/**
	 * Возвращает перенаправление на функцию "ЧТЕНИЕ" (базовый URL) дочерних
	 * объектов.
	 * 
	 * @param parentId идентификатор родительского объекта
	 * @return строка перенаправления на функцию "ЧТЕНИЕ" (базовый URL)
	 */
	public String getRedirectToRead(Long parentId);

	/**
	 * Возвращает перенаправление на функцию "СОЗДАНИЕ" подчинённого объекта.
	 * 
	 * @param parentId идентификатор родительского объекта
	 * @return строка перенаправления на функцию "СОЗДАНИЕ"
	 */
	public String getRedirectToCreate(Long parentId);

	/**
	 * Возвращает список дочерних объектов по заданному идентификатору родительского
	 * объекта.
	 * 
	 * @param parentId идентификатор родительского объекта
	 * @return список дочерних объектов
	 * @see
	 */
	public List<T> getShowListChildren(Long parentId);

	/**
	 * Возвращает ассоциированный список атрибутов модели для нового дочернего доменного
	 * объекта.
	 * <p>
	 * Для последующего использования при наполнении данными нового дочернего доменного
	 * объекта в шаблоне представления "создание-обновление".
	 * 
	 * @return ассоциированный список атрибутов модели
	 * @see #showCreateForm(Long, Locale, Model)
	 */
	public Map<String, Object> getShowCreateModelAttributes(Long parentId);

	/**
	 * Возвращает ассоциированный список атрибутов модели для заданного
	 * идентификаторар доменного объекта.
	 * <p>
	 * Для последующего использования при обновлении данных доменного объекта в
	 * шаблоне представления "создание-обновление".
	 * 
	 * @param id идентификатор обновляемого доменного объекта
	 * @return ассоциированный список атрибутов модели
	 * @see #showUpdateForm(Long, Long, Locale, Model)
	 */
	public Map<String, Object> getShowUpdateModelAttributes(Long parentId, Long id);

	/**
	 * Создаёт и возвращает дочерний объект.
	 * 
	 * @param parentId идентификатор родительского объекта
	 * @return путь к шаблону "чтение-удаление"
	 */
	public T createChildEntity(Long parentId);

	/**
	 * Формирует и возвращает перечень дочерних объектов.
	 * 
	 * @param parentId идентификатор родительского объекта
	 * @param locale   локаль
	 * @param model    модель
	 * @return путь к шаблону "чтение-удаление"
	 */
	public String showList(Long parentId, Locale locale, Model model);

	/**
	 * Создаёт новый и возвращает для наполнения данными дочерний объект
	 * 
	 * @param parentId идентификатор родительского объекта
	 * @param locale   локаль
	 * @param model    модель
	 * @return путь к шаблону "создание-изменение"
	 */
	public String showCreateForm(Long parentId, Locale locale, Model model);

	/**
	 * Возвращает для изменения существующий дочерний объект
	 * 
	 * @param parentId идентификатор родительского объекта
	 * @param locale   локаль
	 * @param model    модель
	 * @return путь к шаблону "создание-изменение"
	 */
	public String showUpdateForm(Long parentId, Long id, Locale locale, Model model);

	/**
	 * Создаёт дочерний доменный объект по полученным данным модели.
	 * 
	 * <p>
	 * Обеспечивает возврат на создание и ввод данных по новому объекту при наличие
	 * признака продолжения {@code isContinue = true}
	 * 
	 * @param parentId   идентификатор родительского объекта
	 * @param isContinue признак продолжения создания дочернего объекта
	 * @param entity     дочерний объект, подлежащий добавлению в доменную модель
	 * @param errors     ошибки модели
	 * @param model      модель
	 * @return путь к шаблону "чтение-удаление" при {@code isContinue = false}, или
	 *         "создание-изменение" при {@code isContinue = true}
	 */
	public String create(Long parentId, boolean isContinue, T entity, Errors errors, Model model);

	/**
	 * Обновляет дочерний доменный объект по полученным данным модели.
	 * 
	 * @param parentId идентификатор родительского объекта
	 * @param entity   дочерний объект, подлежащий обновлению в доменную модель
	 * @param errors   ошибки модели
	 * @param model    модель
	 * @return путь к шаблону "чтение-удаление"
	 */
	public String update(Long parentId, Long id, T entity, Errors errors, Model model);

	/**
	 * Удаляет дочерние доменные объекты по заданному списку идентификаторов.
	 * 
	 * @param parentId идентификатор родительского объекта
	 * @param ids      список идентификаторов
	 * @return путь к шаблону "чтение-удаление"
	 */
	public String delete(Long parentId, List<String> ids);

	/**
	 * Удаляет дочерний доменный объект по заданному идентификатору.
	 * 
	 * @param parentId идентификатор родительского объекта
	 * @param id       идентификатор дочернего объекта
	 * @return путь к шаблону "чтение-удаление"
	 */
	public String deleteById(Long parentId, Long id);

}
