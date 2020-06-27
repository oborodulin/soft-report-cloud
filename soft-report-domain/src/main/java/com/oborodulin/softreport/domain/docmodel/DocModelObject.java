package com.oborodulin.softreport.domain.docmodel;

import java.util.Set;
import java.util.HashSet;
import lombok.Builder;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * Класс объект модели документа (объекты БД и UI.
 * 
 * @author Oleg Borodulin
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DocModelObject implements CommonDocModelObject {

	/** Позиция в документе */
	@Builder.Default
	private Integer pos = 0;

	/**
	 * Категория
	 * {@link com.oborodulin.softreport.domain.model.dic.valuesset.ValuesSet#VS_DOC_OBJ_TYPES}
	 */
	private String categ;

	/** Тип */
	private String type;

	/** Наименование */
	private String name;

	/** Описание */
	private String descr;

	/** Сервер: хост */
	private String host;

	/** Сервер: порт */
	private Integer port;

	/** SQL-запрос */
	private String sqlQuery;

	/** Компоненты */
	@Builder.Default
	Set<CommonDocModelObject> components = new HashSet<>();

	/** Бизнес-объекты */
	@Builder.Default
	Set<String> businessObjects = new HashSet<>();

	/** Признак обязательности */
	private Boolean isRequired;

	/** Поле БД: признак уникального ключа */
	private Boolean isUniqueKey;

	/** Поле БД: признак составного ключа */
	private Boolean isCompositeKey;

	/** Поле БД: значение по умолчанию */
	private String defaultValue;

	/** Поле БД: внешний ключ */
	private String foreignKey;

	/**
	 * Добавляет компонент к текущему объекту модели документа.
	 *
	 * @param component объект-компонент модели документа
	 */
	public void addComponent(CommonDocModelObject component) {
		components.add(component);
	}

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
	public CommonDocModelObject getComponent(String name) {
		return this.components.stream().filter(component -> name.equals(component.getName())).findAny().orElse(null);
	}

	/**
	 * Добавляет бизнес-объект к текущему объекту модели документа.
	 *
	 * @param name наименование бизнес-объекта
	 */
	public void addBusinessObject(String name) {
		businessObjects.add(name);
	}

	/**
	 * Возвращает наименование бизнес-объекта текущего объекту модели документа.
	 *
	 * @param name наименованию объекта-компонента модели документа
	 * @return если найдено, то наименованию объекта-компонента модели документа,
	 *         иначе - {@code NULL}
	 */
	public String getBusinessObject(String name) {
		return businessObjects.stream().filter(businessObject -> name.equals(businessObject)).findAny().orElse(null);
	}

	/**
	 * Находит и возвращает объект модели документа из заданного списка по заданной
	 * категории или типу.
	 *
	 * @param components список объектов модели документа (компоненты)
	 * @param categ      категория
	 * @param type       тип
	 * @return если найден, то объект-компонент модели документа, иначе -
	 *         {@code NULL}
	 */
	private CommonDocModelObject findComponentByCategAndType(Set<CommonDocModelObject> components, String categ,
			String type) {
		CommonDocModelObject docModelObject = null;
		if (!components.isEmpty()) {
			docModelObject = components.stream().filter(component -> categ.equals(component.getCateg())
					&& (type == null || type.equals(component.getType()))).findAny().orElse(null);
			if (docModelObject == null) {
				for (CommonDocModelObject component : components) {
					docModelObject = this.findComponentByCategAndType(component.getComponents(), categ, type);
					if (docModelObject != null) {
						break;
					}
				}
			}
		}
		return docModelObject;
	}

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
	public boolean isComponentPresent(String categ, String type, boolean byAllTree) {
		CommonDocModelObject docModelObject = null;
		if (byAllTree) {
			docModelObject = this.findComponentByCategAndType(this.components, categ, type);
		} else {
			docModelObject = this.components.stream().filter(component -> categ.equals(component.getCateg())
					&& (type == null || type.equals(component.getType()))).findAny().orElse(null);
		}
		return docModelObject != null;
	}

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
	public boolean isComponentPresent(String categ, boolean byAllTree) {
		return this.isComponentPresent(categ, null, byAllTree);
	}

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
	public boolean isComponentPresent(String categ) {
		return this.isComponentPresent(categ, null, false);
	}
}
