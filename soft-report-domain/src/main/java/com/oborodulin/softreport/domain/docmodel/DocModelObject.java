package com.oborodulin.softreport.domain.docmodel;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

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

	/** Тип (ТД, поля ТД/представления)*/
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
	 * {@inheritDoc}
	 */
	public void addComponent(CommonDocModelObject component) {
		components.add(component);
	}

	/**
	 * {@inheritDoc}
	 */
	public CommonDocModelObject getComponent(String name) {
		return this.components.stream().filter(component -> name.equals(component.getName())).findAny().orElse(null);
	}

	/**
	 * {@inheritDoc}
	 */
	public String getComponentsString() {
		return this.components.stream().map(x -> x.getName()).collect(Collectors.joining(","));
	}

	/**
	 * {@inheritDoc}
	 */
	public void addBusinessObject(String name) {
		this.businessObjects.add(name);
	}

	/**
	 * {@inheritDoc}
	 */
	public String getBusinessObject(String name) {
		return this.businessObjects.stream().filter(businessObject -> name.equals(businessObject)).findAny()
				.orElse(null);
	}

	/**
	 * {@inheritDoc}
	 */
	public String getBusinessObjectsString() {
		return this.businessObjects.stream().collect(Collectors.joining(","));
	}

	/**
	 * Находит и возвращает множество объектов модели документа из заданного
	 * множества по заданной категории или типу.
	 *
	 * @param components множество объектов модели документа (компоненты)
	 * @param categ      категория
	 * @param type       тип
	 * @return множество объектов-компонентов модели документа
	 */
	private Set<CommonDocModelObject> findComponentsByCategAndType(Set<CommonDocModelObject> components, String categ,
			String type) {
		List<CommonDocModelObject> findComponents = new ArrayList<>();
		if (!components.isEmpty()) {
			findComponents
					.addAll(components.stream()
							.filter(component -> categ.equals(component.getCateg())
									&& (type == null || type.equals(component.getType())))
							.collect(Collectors.toList()));
			for (CommonDocModelObject component : components) {
				findComponents.addAll(this.findComponentsByCategAndType(component.getComponents(), categ, type));
			}
		}
		return new HashSet<>(findComponents);
	}

	/**
	 * {@inheritDoc}
	 */
	public Set<CommonDocModelObject> getComponents(String categ, String type, boolean byAllTree) {
		List<CommonDocModelObject> components = new ArrayList<>();
		if (byAllTree) {
			components.addAll(this.findComponentsByCategAndType(this.components, categ, type));
		} else {
			components
					.addAll(this.components.stream()
							.filter(component -> categ.equals(component.getCateg())
									&& (type == null || type.equals(component.getType())))
							.collect(Collectors.toList()));
		}
		return new HashSet<>(components);
	}

	/**
	 * {@inheritDoc}
	 */
	public Set<CommonDocModelObject> getComponents(String categ, boolean byAllTree) {
		return this.getComponents(categ, null, byAllTree);
	}

	/**
	 * {@inheritDoc}
	 */
	public Set<CommonDocModelObject> getComponents(String categ) {
		return this.getComponents(categ, null, false);
	}

	/**
	 * Находит и возвращает объект модели документа из заданного множества по
	 * заданной категории или типу.
	 *
	 * @param components множество объектов модели документа (компоненты)
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
	 * {@inheritDoc}
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
	 * {@inheritDoc}
	 */
	public boolean isComponentPresent(String categ, boolean byAllTree) {
		return this.isComponentPresent(categ, null, byAllTree);
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean isComponentPresent(String categ) {
		return this.isComponentPresent(categ, null, false);
	}
}
