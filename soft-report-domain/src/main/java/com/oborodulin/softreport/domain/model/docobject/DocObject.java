package com.oborodulin.softreport.domain.model.docobject;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.oborodulin.softreport.domain.common.entity.TreeEntity;
import com.oborodulin.softreport.domain.model.dic.proglang.datatype.DataType;
import com.oborodulin.softreport.domain.model.dic.proglang.datatype.dataformat.DataFormat;
import com.oborodulin.softreport.domain.model.dic.server.Server;
import com.oborodulin.softreport.domain.model.dic.valuesset.value.Value;
import com.oborodulin.softreport.domain.model.software.businessobject.BusinessObject;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Класс описывает объект базы данных (БД) и объект пользовательского интерфейса (UI).
 * 
 * Позволяет работать со всеми возможными объектами БД/UI: схемы, таблицы (поля),
 * представления, процедуры, формы, панели, поля,
 * таблицы, колонки, кнопки и т.д.
 * 
 * @author acs-i
 *
 */
@Data
@Entity
@Table(name = DocObject.TABLE_NAME)
public class DocObject extends TreeEntity<DocObject, String> {
	private static final long serialVersionUID = -5847640757970947607L;

	protected static final String TABLE_NAME = "DOC_OBJECTS";

	/** Позиция */
	@NotBlank
	private Integer pos = 1;

	/** Наименование объекта БД/Метка UI объекта */
	@NotBlank
	private String name;

	/** Описание */
	@Column(length = 1000)
	private String descr;

	/** Тип объекта */
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "type_code", nullable = false)
	@ToString.Exclude
	private Value type;

	/** БД: Тип базы данных */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "db_type_code")
	@ToString.Exclude
	private Value dbType;

	/** БД: Сервер */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "servers_id")
	@ToString.Exclude
	private Server server;

	/** ТД: Тип таблицы данных */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "dt_type_code")
	@ToString.Exclude
	private Value dtType;

	/** ТД: Бизнес-объект */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "business_objects_id")
	@ToString.Exclude
	private BusinessObject businessObject;

	/** Поле ТД: Признак первичного ключа */
	@NotNull
	private Boolean isPrimaryKey = false;

	/** Поле ТД: Признак уникального ключа */
	@NotNull
	private Boolean isUniqueKey = false;

	/**
	 * Поле ТД/Поле формы/колонка грида: Признак обязательного значения
	 */
	@NotNull
	private Boolean isRequired = false;

	/**
	 * Признак предустанавливаемого объекта (объект, который обязательно создаётся
	 * под своего главного объекта при его создании, например, поля ТД: первичный
	 * ключ и исторические поля)
	 */
	@NotNull
	private Boolean isPreset = false;

	/** Поле ТД/Поле формы: Значение по умолчанию */
	@Column(length = 500)
	private String defaultValue;

	/** Поле ТД/Поле формы/колонка грида: Выражение */
	@Column(length = 500)
	private String expression;

	/** Представление/процедура/функция: SQL-запрос */
	@Column(length = 2000)
	private String sqlQuery;

	/** Поле ТД: Объект БД ссылки внешнего ключа */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "foreign_key_id")
	@ToString.Exclude
	private DocObject foreignKey;

	/** Поле ТД: Тип данных */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "data_types_id")
	@ToString.Exclude
	private DataType dataType;

	/** Поле ТД: Длина (точность) */
	private Integer precision;

	/** Поле ТД: Точность (масштаб) */
	private Integer scale;

	/** Форма/поле/колонка: Объект БД */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "db_objects_id")
	@ToString.Exclude
	private DocObject dbObject;

	/** Поле ТД: Список UI объектов, связанных с текущим объектом БД */
	@OneToMany(mappedBy = "dbObject", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@EqualsAndHashCode.Exclude
	private List<DocObject> uiObjects = new ArrayList<>();

	/** Поле/колонка: Формат данных, основанный на типе объекта БД */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "data_formats_id")
	@ToString.Exclude
	private DataFormat format;

	/** Поле/колонка: Признак обновляемого/редактируемого значения */
	@NotNull
	private Boolean isUpdatable = false;

	/** Список: Признак отображаемого по умолчанию в списке пустого значения */
	@NotNull
	private Boolean isDefaultEmptyValue = true;

	/** Список: значение поля ТД по умолчанию */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "list_objects_id")
	@ToString.Exclude
	private DocObject listDefaultValueObject;

	/** Атрибут мягкого поиска списка: Признак отображаемого в списке значения */
	@NotNull
	private Boolean isDisplayAttribute = false;

	/** Поле-чекбокс/поле радио-кнопка: Значение по умолчанию "Отмечено"/"Не отмечено" */
	@NotNull
	private Boolean isDefaultChecked = false;

	/** Поле-чекбокс: Значение "Отмечено" */
	@NotNull
	private String checkedValue = "1";

	/** Поле-чекбокс: Значение "Не отмечено" */
	@NotNull
	private String notCheckedValue = "0";

	/** Поле радио-кнопка: Значение */
	@NotNull
	private String radioValue;

	/** Кнопка: Признак массовой операции */
	@NotNull
	private Boolean isMassOperation = false;

	/** Кнопка: Признак опреации над выделеной/выбранной строкой грида */
	@NotNull
	private Boolean isGridRowOperation = false;

	/** Колонка: Признак возможности сортировки */
	@NotNull
	private Boolean isSortable = false;

	/** Колонка: Признак возможности сортировки */
	@NotNull
	private Boolean isDefaultSort = false;

	/** Колонка: Направление сортировки по умолчанию*/
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "sort_direction_code")
	@ToString.Exclude
	private Value defaultSortDirection;
	
}