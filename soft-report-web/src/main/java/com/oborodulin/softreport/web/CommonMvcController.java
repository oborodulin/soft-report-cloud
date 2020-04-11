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
	 * Возвращает наименование свойства Entity-объектов, по которому выполняется
	 * сортировка в процессе их получения.
	 * <p>
	 * Направление сортировка по умолчанию: по возрастанию
	 * 
	 * @return наименование свойства Entity-объектов
	 */
	public String getSortPropName();

	/**
	 * Устанавливает наименование свойства Entity-объектов, по которому выполняется
	 * сортировка в процессе их получения.
	 * <p>
	 * Направление сортировка по умолчанию: по возрастанию
	 * 
	 * @param sortPropName наименование свойства Entity-объектов
	 */
	public void setSortPropName(String sortPropName);

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
	 * @return строка перенаправления на функцию CRUD-Read ("ЧТЕНИЕ") (базовый URL)
	 * @see
	 */
	public String getRedirectToRead();

	/**
	 * Возвращает перенаправление на функцию CRUD-Create ("СОЗДАНИЕ").
	 * 
	 * @return строка перенаправления на функцию CRUD-Create ("СОЗДАНИЕ")
	 */
	public String getRedirectToCreate();

	/**
	 * Возвращает перенаправление на функцию CRUD-Read ("ЧТЕНИЕ") подчинённых
	 * объектов.
	 * <p>
	 * В общем случае возврат выполняется на базовый URL.
	 * 
	 * @param mainId идентификатор главного объекта
	 * @return строка перенаправления на функцию CRUD-Read ("ЧТЕНИЕ")
	 */
	public String getRedirectToRead(Long mainId);

	/**
	 * Возвращает перенаправление на функцию CRUD-Create ("СОЗДАНИЕ") подчинённого
	 * объекта.
	 * 
	 * @param mainId идентификатор главного объекта
	 * @return строка перенаправления на функцию CRUD-Create ("СОЗДАНИЕ")
	 */
	public String getRedirectToCreate(Long mainId);

	/**
	 * Возвращает перенаправление из функции CRUD-Create ("СОЗДАНИЕ") подчинённого
	 * объекта.
	 * 
	 * @param mainId идентификатор главного объекта
	 * @return строка перенаправление из функции CRUD-Create ("СОЗДАНИЕ")
	 */
	public String getRedirectFromSlaveCreate(Long mainId);

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
	 * Возвращает главный объект доменной модели по заданному идентификатору.
	 * 
	 * @param mainId идентификатор главного объекта
	 * @return главный объект доменной модели
	 */
	public AuditableEntity<U> getShowListMainEntity(Long mainId);

	/**
	 * Возвращает список подчинённых объектов доменной модели по заданному
	 * идентификатору.
	 * 
	 * @param mainId идентификатор главного объекта
	 * @return список подчинённых объектов доменной модели
	 */
	public List<E> getShowListSlavesEntities(Long mainId);

	/**
	 * Создаёт и возвращает новый доменный объект.
	 * <p>
	 * Для последующего отображения и наполнения данными в шаблоне представления
	 * "создание-обновление".
	 * 
	 * @return новый доменный объект
	 */
	public E createEntity();

	/**
	 * Создаёт и возвращает новый подчинённый доменный объект по идентификатор
	 * главного объекта.
	 * <p>
	 * Для последующего отображения и наполнения данными в шаблоне представления
	 * "создание-обновление".
	 * 
	 * @param mainId идентификатор главного объекта
	 * @return новый подчинённый доменный объект
	 */
	public E createSlaveEntity(Long mainId);

	/**
	 * Возвращает главный доменный объект заданного подчинённого объекта.
	 * 
	 * @param slaveEntity подчинённый объект
	 * @return главный доменный объект
	 */
	public AuditableEntity<U> getMainEntity(E slaveEntity);

	/**
	 * Сохраняет подчинённый объект по заданному идентификатору главного доменного
	 * объекта.
	 * 
	 * @param mainId      идентификатор главного объекта
	 * @param slaveEntity подчинённый объект
	 */
	public void saveSlaveEntity(Long mainId, E slaveEntity);

	/**
	 * Возвращает ассоциированный список атрибутов модели для нового доменного
	 * объекта.
	 * <p>
	 * Для последующего использования при наполнении данными нового доменного
	 * объекта в шаблоне представления "создание-обновление".
	 * 
	 * @return ассоциированный список атрибутов модели
	 * @see #showCreateForm(Locale, Model)
	 */
	public Map<String, Object> getShowCreateModelAttributes();

	/**
	 * Возвращает ассоциированный список атрибутов модели для нового дочернего
	 * доменного объекта.
	 * <p>
	 * Для последующего использования при наполнении данными нового дочернего
	 * доменного объекта в шаблоне представления "создание-обновление".
	 * 
	 * @return ассоциированный список атрибутов модели
	 * @see #showCreateForm(Long, Locale, Model)
	 */
	public Map<String, Object> getShowCreateModelAttributes(Long mainId);

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
	public Map<String, Object> getShowUpdateModelAttributes(Long mainId, Long id);

	/**
	 * Формирует и возвращает перечень доменных объектов.
	 * 
	 * @param locale локаль
	 * @param model  модель
	 * @return путь к шаблону "чтение-удаление"
	 */
	public String showList(Locale locale, Model model);

	/**
	 * Формирует и возвращает перечень подчинённых объектов.
	 * 
	 * @param mainId идентификатор главного объекта
	 * @param locale локаль
	 * @param model  модель
	 * @return путь к шаблону "чтение-удаление"
	 */
	public String showList(Long mainId, Locale locale, Model model);

	/**
	 * Обеспечивает отображение веб-формы создания и наполнения данными нового
	 * доменного объекта по идентификатору главного объекта.
	 * 
	 * @param locale локаль
	 * @param model  модель
	 * @return путь к шаблону "создание-изменение"
	 */
	public String showCreateForm(Locale locale, Model model);

	/**
	 * Обеспечивает отображение веб-формы создания и наполнения данными нового
	 * подчинённого объекта по идентификатору главного объекта.
	 * 
	 * @param mainId идентификатор главного объекта
	 * @param locale локаль
	 * @param model  модель
	 * @return путь к шаблону "создание-изменение"
	 */
	public String showCreateForm(Long mainId, Locale locale, Model model);

	/**
	 * Обеспечивает отображение веб-формы для изменения существующего объекта.
	 * 
	 * @param id     идентификатор объекта
	 * @param locale локаль
	 * @param model  модель
	 * @return путь к шаблону "создание-изменение"
	 */
	public String showUpdateForm(Long id, Locale locale, Model model);

	/**
	 * Обеспечивает отображение веб-формы для изменения существующего подчинённого
	 * объекта.
	 * 
	 * @param mainId идентификатор главного объекта
	 * @param id     идентификатор подчинённого объекта
	 * @param locale локаль
	 * @param model  модель
	 * @return путь к шаблону "создание-изменение"
	 */
	public String showUpdateForm(Long mainId, Long id, Locale locale, Model model);

	/**
	 * Создаёт доменный объект по полученным данным модели.
	 * 
	 * <p>
	 * Обеспечивает возврат на создание и ввод данных по новому объекту при наличие
	 * признака продолжения {@code isContinue = true}
	 * 
	 * @param isContinue признак продолжения создания объекта
	 * @param entity     объект, подлежащий добавлению в доменную модель
	 * @param errors     ошибки модели
	 * @param model      модель
	 * @return путь к шаблону "чтение-удаление" при {@code isContinue = false}, или
	 *         "создание-изменение" при {@code isContinue = true}
	 */
	public String create(boolean isContinue, E entity, Errors errors, Model model);

	/**
	 * Создаёт подчинённый доменный объект по полученным данным модели.
	 * 
	 * <p>
	 * Обеспечивает возврат на создание и ввод данных по новому объекту при наличие
	 * признака продолжения {@code isContinue = true}
	 * 
	 * @param mainId     идентификатор главного объекта
	 * @param isContinue признак продолжения создания подчинённого объекта
	 * @param entity     подчинённый объект, подлежащий добавлению в доменную модель
	 * @param errors     ошибки модели
	 * @param model      модель
	 * @return путь к шаблону "чтение-удаление" при {@code isContinue = false}, или
	 *         "создание-изменение" при {@code isContinue = true}
	 */
	public String create(Long mainId, boolean isContinue, E entity, Errors errors, Model model);

	/**
	 * Обновляет доменный объект по полученным данным модели.
	 * 
	 * @param id     идентификатор объекта
	 * @param entity объект, подлежащий обновлению в доменной модели
	 * @param errors ошибки модели
	 * @param model  модель
	 * @return путь к шаблону "чтение-удаление"
	 */
	public String update(Long id, E entity, Errors errors, Model model);

	/**
	 * Обновляет подчинённый доменный объект по полученным данным модели.
	 * 
	 * @param mainId идентификатор главного объекта
	 * @param id     идентификатор подчинённого объекта
	 * @param entity подчинённый объект, подлежащий обновлению в доменной модели
	 * @param errors ошибки модели
	 * @param model  модель
	 * @return путь к шаблону "чтение-удаление"
	 */
	public String update(Long mainId, Long id, E entity, Errors errors, Model model);

	/**
	 * Удаляет доменные объекты по заданному списку идентификаторов.
	 * 
	 * @param ids список идентификаторов
	 * @return путь к шаблону "чтение-удаление"
	 */
	public String delete(List<String> ids);

	/**
	 * Удаляет подчинённые доменные объекты по заданному списку идентификаторов.
	 * 
	 * @param mainId идентификатор главного объекта
	 * @param ids    список идентификаторов
	 * @return путь к шаблону "чтение-удаление"
	 */
	public String delete(Long mainId, List<String> ids);

	/**
	 * Удаляет доменный объект по заданному идентификатору.
	 * 
	 * @param id идентификатор объекта
	 * @return путь к шаблону "чтение-удаление"
	 */
	public String deleteById(Long id);

	/**
	 * Удаляет подчинённый доменный объект по заданному идентификатору.
	 * 
	 * @param mainId идентификатор главного объекта
	 * @param id     идентификатор подчинённого объекта
	 * @return путь к шаблону "чтение-удаление"
	 */
	public String deleteById(Long mainId, Long id);

}
