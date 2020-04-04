package com.oborodulin.softreport.domain.model.docobject;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import com.oborodulin.softreport.domain.common.entity.TreeEntity;
import com.oborodulin.softreport.domain.model.dic.proglang.datatype.DataType;
import com.oborodulin.softreport.domain.model.dic.proglang.datatype.dataformat.DataFormat;
import com.oborodulin.softreport.domain.model.dic.proglang.uiobjecttype.UiObjectType;
import com.oborodulin.softreport.domain.model.dic.proglang.uiobjecttype.uieventtype.UiEventType;
import com.oborodulin.softreport.domain.model.dic.server.Server;
import com.oborodulin.softreport.domain.model.dic.valuesset.value.Value;
import com.oborodulin.softreport.domain.model.project.document.version.Version;
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
 * @author Oleg Borodulin
 *
 */
@Data
@Entity
@Table(name = DocObject.TABLE_NAME)
@Audited
public class DocObject extends TreeEntity<DocObject, String> {
	private static final long serialVersionUID = -5847640757970947607L;

	/** Наименование таблицы данных доменного объекта (сущности) */
	protected static final String TABLE_NAME = "DOC_OBJECTS";

	/** Поле ТД/параметр поиска/поле формы ввода/: Позиция */
	@NotNull
	private Integer pos = 0;

	/** Наименование объекта БД/Значение поля БД (колонка наименование)/Метка UI объекта */
	@NotBlank
	@Column(length = 1000)
	private String name;

	/** Описание */
	@Column(length = 1000)
	private String descr;

	/** Версии */
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@EqualsAndHashCode.Exclude
	@NotAudited
	private List<Version> versions = new ArrayList<>();

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
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@EqualsAndHashCode.Exclude
//	@JoinColumn(name = "business_objects_id")
	@NotAudited
	private List<BusinessObject> businessObjects;

	/** Поле ТД: Признак первичного ключа */
	@NotNull
	private Boolean isPrimaryKey = false;

	/** Поле ТД: Признак уникального ключа */
	@NotNull
	private Boolean isUniqueKey = false;

	/** Поле ТД (NULLable)/Поле формы/колонка грида: Признак обязательного значения */
	@NotNull
	private Boolean isRequired = false;

	/** Поле ТД: Признак поля логического (мягкого) удаления */
	@NotNull
	private Boolean isLogicDelete = false;
	
	/**
	 * Признак предустанавливаемого объекта (объект, который обязательно создаётся
	 * под своего главного объекта при его создании, например, поля ТД: первичный
	 * ключ и исторические поля)
	 */
	@NotNull
	private Boolean isPreset = false;

	/** Поле ТД: Тип служебного поля (исторические/версия/логическое (мягкое) удаление) */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "column_type_code")
	@ToString.Exclude
	private Value columnType;

	/** Поле ТД/Поле формы: Значение по умолчанию/Выражение */
	@Column(length = 2000)
	private String defaultValue;

	/** Представление/процедура/функция/Бизнес-правило: SQL-запрос */
	@Column(length = 2000)
	private String sqlQuery;

	/** Поле ТД: Объект БД ссылки внешнего ключа (ТД)*/
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

	/** Значение поля БД: поле ТД кода значения */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "code_objects_id")
	@ToString.Exclude
	private DocObject codeColumn;

	/** Значение поля БД: код значения */
	private String code;

	/** Значение поля БД: поле ТД наименование значения */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "name_objects_id")
	@ToString.Exclude
	private DocObject nameColumn;

	/** UI: тип UI объекта */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ui_object_types_id")
	@ToString.Exclude
	private UiObjectType uiObjectType;

	/** Форма/поле(список)/колонка: Объект БД (если поле ТД для списка, то из этого поля берутся значения для списка при их наличие)*/
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "db_objects_id")
	@ToString.Exclude
	private DocObject dbObject;

	/** Поле ТД: Список UI объектов, связанных с текущим объектом БД */
	@OneToMany(mappedBy = "dbObject", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@EqualsAndHashCode.Exclude
	@NotAudited
	private List<DocObject> uiObjects = new ArrayList<>();

	/** Все объекты (БД/UI): аналогичный объект (те же самые параметры) */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "similar_objects_id")
	@ToString.Exclude
	private DocObject similarObject;
	
	/** UI объекты: Признак активности элемента по умолчнаию */
	@NotNull
	private Boolean isDefaultActive = true;

	/** Форма: Признак немедленной валидации (после ввода значения в каждое поле, или после отправки данных формы на сервер) */
	@NotNull
	private Boolean isImmediateValidation = false;
	
	/** Поле/колонка: Формат данных, основанный на типе объекта БД */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "data_formats_id")
	@ToString.Exclude
	private DataFormat format;

	/** Поле/колонка: тип элемента управления */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "control_type_code")
	@ToString.Exclude
	private Value controlType;

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

	/** Поле-чекбокс: Значение "Отмечено", в т.ч. SQL-запрос */
	@Column(length = 2000)
	private String checkedValue = "1";

	/** Поле-чекбокс: Значение "Не отмечено", в т.ч. SQL-запрос */
	@Column(length = 2000)
	private String notCheckedValue = "0";

	/** Поле радио-кнопка: Значение */
	private String radioValue;

	/** Кнопка: Признак массовой операции */
	@NotNull
	private Boolean isMassOperation = false;

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

	/** Поле формы/Колонка: Признак вмещаемого текста */
	@NotNull
	private Boolean isInnerText = false;

	/** Бизнес-правило: тип */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "rule_type_code")
	@ToString.Exclude
	private Value ruleType;

	/** Бизнес-правило: оператор */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "operator_code")
	@ToString.Exclude
	private Value ruleOperator;

	/** Бизнес-правило: операнд */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "operand_code")
	@ToString.Exclude
	private Value ruleOperand;

	/** Бизнес-правило: значение операнда */
	@Column(length = 500)
	private String ruleOperandValue;

	/** Бизнес-правило: условие выполнения правила */
	@Column(length = 1000)
	private String ruleCondValid;

	/** Бизнес-правило/Событие (объект, над которым выполняется действие): UI объект */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ui_objects_id")
	@ToString.Exclude
	private DocObject uiObject;
	
	/** Бизнес-правило: текст ошибки */
	@Column(length = 500)
	private String ruleErrMessage;

	/** Бизнес-правило: текст информационный */
	@Column(length = 500)
	private String ruleInfoMessage;

	/** Событие: описание результата события */
	@Column(length = 2000)
	private String eventResultDescr;

	/** Событие: тип события UI объекта */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ui_event_types_id")
	@ToString.Exclude
	private UiEventType uiEventType;
	
	/** Событие: действие */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "event_action_code")
	@ToString.Exclude
	private Value eventAction;

	/** Событие: UI объект (значение поля ТД, списка и пр.), который присваивается объекту, над которым выполняется действие*/
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "event_val_doc_objects_id")
	@ToString.Exclude
	private DocObject eventValDocObject;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getCodeId() {
		return this.name;
	}
	
}
