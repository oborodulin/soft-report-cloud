package com.oborodulin.softreport.domain.docmodel;

import java.util.Set;

public interface CommonDocModelObject {

	public Integer getPos();

	public void setPos(Integer pos);

	public String getCateg();

	public void setCateg(String categ);

	public String getType();

	public void setType(String type);

	public String getName();

	public void setName(String name);

	public String getDescr();

	public void setDescr(String descr);

	public String getHost();

	public Integer getPort();

	/**
	 * Добавляет компонент к текущему объекту модели документа.
	 *
	 * @param component объект-компонент модели документа
	 */
	public void addComponent(CommonDocModelObject component);

	/**
	 * Возвращает компонент текущего объекта модели документа по заданному
	 * наименованию.
	 * <p>
	 * Подразумевается список компонентов текущего объекта модели документа.
	 * 
	 * @param name наименованию объекта-компонента модели документа
	 * @return если найден, то объект-компонент модели документа, иначе -
	 *         {@code NULL}
	 */
	public CommonDocModelObject getComponent(String name);

	/**
	 * Возвращает перечень объектов-компонентов текущего объекта модели документа
	 * через запятую.
	 *
	 * @return перечень объектов-компонентов
	 */
	public String getComponentsString();

	/**
	 * Возвращает множество всех объектов-компонентов текущего объекта модели
	 * документа.
	 *
	 * @return множество всех объектов-компонентов
	 */
	public Set<CommonDocModelObject> getComponents();

	/**
	 * Возвращает множество объектов-компонентов с заданной категорией или типом.
	 *
	 * @param categ     категория
	 * @param type      тип
	 * @param byAllTree признак поиска по всему дереву модели документа
	 *                  ({@code true} - поиск выполняется по всему дереву,
	 *                  {@code false} - поиск выполняется по списку компонентов
	 *                  текущего объекта модели документа)
	 * @return множество объектов-компонентов
	 */
	public Set<CommonDocModelObject> getComponents(String categ, String type, Boolean byAllTree);

	/**
	 * Возвращает множество объектов-компонентов с заданной категорией.
	 *
	 * @param categ     категория
	 * @param byAllTree признак поиска по всему дереву модели документа
	 *                  ({@code true} - поиск выполняется по всему дереву,
	 *                  {@code false} - поиск выполняется по списку компонентов
	 *                  текущего объекта модели документа)
	 * @return множество объектов-компонентов
	 */
	public Set<CommonDocModelObject> getComponents(String categ, Boolean byAllTree);

	/**
	 * Возвращает множество объектов-компонентов с заданной категорией.
	 * <p>
	 * Поиск выполняется только по списку компонентов текущего объекта модели
	 * документа.
	 * 
	 * @param categ категория
	 * @return множество объектов-компонентов
	 */
	public Set<CommonDocModelObject> getComponents(String categ);

	/**
	 * Добавляет бизнес-объект к текущему объекту модели документа.
	 *
	 * @param name наименование бизнес-объекта
	 */
	public void addBusinessObject(String name);

	/**
	 * Возвращает наименование бизнес-объекта текущего объекта модели документа.
	 *
	 * @param name наименованию объекта-компонента модели документа
	 * @return если найдено, то наименованию объекта-компонента модели документа,
	 *         иначе - {@code NULL}
	 */
	public String getBusinessObject(String name);

	/**
	 * Возвращает перечень бизнес-объектов через запятую.
	 *
	 * @return перечень бизнес-объектов
	 */
	public String getBusinessObjectsString();

	/**
	 * Возвращает признак наличия объекта модели документа с заданной категорией или
	 * типом.
	 *
	 * @param categ     категория
	 * @param type      тип
	 * @param byAllTree признак поиска по всему дереву модели документа
	 *                  ({@code true} - поиск выполняется по всему дереву,
	 *                  {@code false} - поиск выполняется по списку компонентов
	 *                  текущего объекта модели документа)
	 * @return если найден объект модели документа, то {@code true}, иначе -
	 *         {@code false}
	 */
	public Boolean isComponentPresent(String categ, String type, Boolean byAllTree);

	/**
	 * Возвращает признак наличия объекта модели документа с заданной категорией.
	 *
	 * @param categ     категория
	 * @param byAllTree признак поиска по всему дереву модели документа
	 *                  ({@code true} - поиск выполняется по всему дереву,
	 *                  {@code false} - поиск выполняется по списку компонентов
	 *                  текущего объекта модели документа)
	 * @return если найден объект модели документа, то {@code true}, иначе -
	 *         {@code false}
	 */
	public Boolean isComponentPresent(String categ, Boolean byAllTree);

	/**
	 * Возвращает признак наличия объекта модели документа с заданной категорией.
	 * <p>
	 * Поиск выполняется только по списку компонентов текущего объекта модели
	 * документа.
	 * 
	 * @param categ категория
	 * @return если найден объект модели документа, то {@code true}, иначе -
	 *         {@code false}
	 */
	public Boolean isComponentPresent(String categ);

	public void setIsRequired(Boolean isRequired);

	public Boolean getIsRequired();

	public void setIsUniqueKey(Boolean isUniqueKey);

	public Boolean getIsUniqueKey();

	public void setIsCompositeKey(Boolean isCompositeKey);

	public Boolean getIsCompositeKey();

	public void setDefaultValue(String defaultValue);

	public String getDefaultValue();

	public void setForeignKey(String foreignKey);

	public String getForeignKey();
}
