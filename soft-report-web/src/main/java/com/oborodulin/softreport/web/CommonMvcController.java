package com.oborodulin.softreport.web;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import com.oborodulin.softreport.domain.common.entity.AuditableEntity;

/**
 * Интерфейс определяет общие обязательные методы и данные для всех
 * MVC-контроллеров.
 * <p>
 * Интерфейс обеспечивает наличие обязательных методов для всех
 * MVC-контроллеров.
 * 
 * @author Oleg Borodulin
 *
 */
public interface CommonMvcController<E extends AuditableEntity<U>, U> {

	/**
	 * Возвращает базовый URL контроллера.
	 * 
	 * @return базовый URL контроллера
	 */
	public String getBaseUrl();

	/**
	 * Возвращает путь (каталог) к шаблонам представления.
	 * 
	 * @return путь (каталог) к шаблонам представления
	 */
	public String getViewPath();

	/**
	 * Возвращает наименование доменного объекта контроллера (Controller Object
	 * Name).
	 * <p>
	 * Наименование должно быть в единственном числе
	 * 
	 * @return наименование доменного объекта контроллера
	 */
	public String getObjName();

	/**
	 * Возвращает наименование коллекции доменных объектов контроллера (Controller
	 * Objects Collection Name).
	 * <p>
	 * Наименование должно быть во множественном числе
	 * 
	 * @return наименование коллекции доменных объектов контроллера
	 */
	public String getObjCollectName();

	/**
	 * Возвращает префикс строковых ресурсов контроллера в ресурсном пакете.
	 * <p>
	 * Соответствует наименованию коллекции доменных объектов контроллера строчными
	 * символами
	 * 
	 * @return префикс строковых ресурсов контроллера в ресурсном пакете
	 */
	public String getMsPrefix();

	/**
	 * Возвращает путь к шаблону CRUD "чтение-удаление" {@code read-delete.html}.
	 * 
	 * @return путь к шаблону "чтение-удаление"
	 */
	public String getViewNameReadDelete();

	/**
	 * Возвращает путь к шаблону CRUD "создание-обновление"
	 * {@code create-update.html}.
	 * 
	 * @return путь к шаблону "создание-обновление"
	 */
	public String getViewNameCreateUpdate();

	/**
	 * Возвращает перенаправление на функцию CRUD-Read ("ЧТЕНИЕ").
	 * <p>
	 * В общем случае возврат выполняется на базовый URL.
	 * 
	 * @return строка перенаправления на функцию "ЧТЕНИЕ" (базовый URL)
	 * @see
	 */
	public String getRedirectToRead();

	/**
	 * Возвращает перенаправление на функцию CRUD-Create ("СОЗДАНИЕ").
	 * 
	 * @return строка перенаправления на функцию "СОЗДАНИЕ"
	 */
	public String getRedirectToCreate();

	/**
	 * Возвращает путь к шаблону "чтение-удаление" {@code read-delete.html} и
	 * устанавливает соответствующий атрибут модели.
	 * <p>
	 * Для замены ThymeLeaf элементов макета на уникальные фрагменты из шаблона
	 * "чтение-удаление" (для html-страницы: css-стили в заголовке и js-скрипты в
	 * теле)
	 * 
	 * @return путь к шаблону "чтение-удаление"
	 */
	public String viewReadDelete();

	/**
	 * Возвращает путь к шаблону "создание-обновление" {@code create-update.html} и
	 * устанавливает соответствующий атрибут модели.
	 * <p>
	 * Для замены ThymeLeaf элементов макета на уникальные фрагменты из шаблона
	 * "создание-обновление" (для html-страницы: css-стили в заголовке и js-скрипты
	 * в теле)
	 * 
	 * @return путь к шаблону "создание-обновление"
	 */
	public String viewCreateUpdate();

	/**
	 * Возвращает список объектов доменной модели для последующего отображения в
	 * шаблоне представления "чтение-удаление".
	 * 
	 * @return список объектов доменной модели
	 */
	public List<E> getShowListEntities();

	/**
	 * Создаёт и возвращает новый доменный объект для последующего отображения и
	 * наполнения данными в шаблоне представления "создание-обновление".
	 * 
	 * @return новый доменный объект
	 */
	public E createEntity();

	/**
	 * Возвращает ассоциированный список атрибутов модели для нового доменного объекта.
	 * <p>
	 * Для последующего использования при наполнении данными нового доменного объекта в
	 * шаблоне представления "создание-обновление".
	 * 
	 * @return ассоциированный список атрибутов модели
	 * @see #showCreateForm(Locale, Model)
	 */
	public Map<String, Object> getShowCreateModelAttributes();
	
	/**
	 * Возвращает ассоциированный список атрибутов модели для заданного
	 * идентификаторар доменного объекта.
	 * <p>
	 * Для последующего использования при обновлении данных доменного объекта в
	 * шаблоне представления "создание-обновление".
	 * 
	 * @param id идентификатор обновляемого доменного объекта
	 * @return ассоциированный список атрибутов модели
	 * @see #showUpdateForm(Long, Locale, Model)
	 */
	public Map<String, Object> getShowUpdateModelAttributes(Long id);

	public String showList(Locale locale, Model model);

	public String showUpdateForm(Long id, Locale locale, Model model);

	public String showCreateForm(Locale locale, Model model);

	public String create(boolean isContinue, E entity, Errors errors, Model model);

	public String update(Long id, E entity, Errors errors, Model model);

	public String delete(List<String> ids);

	public String deleteById(Long id);

}
