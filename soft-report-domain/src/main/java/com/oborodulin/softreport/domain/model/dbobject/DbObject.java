package com.oborodulin.softreport.domain.model.dbobject;

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
import com.oborodulin.softreport.domain.model.dic.proglang.uiobjecttype.UiObjectType;
import com.oborodulin.softreport.domain.model.dic.server.Server;
import com.oborodulin.softreport.domain.model.dic.valuesset.value.Value;
import com.oborodulin.softreport.domain.model.software.businessobject.BusinessObject;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Класс описывает объект базы данных (БД).
 * 
 * Позволяет работать со всеми возможными объектами БД: схемы, таблицы (поля),
 * представления, процедуры и т.д.
 * 
 * @author acs-i
 *
 */
@Data
@Entity
@Table(name = DbObject.TABLE_NAME)
public class DbObject extends TreeEntity<DbObject, String> {
	private static final long serialVersionUID = -5847640757970947607L;

	protected static final String TABLE_NAME = "DB_OBJECTS";

	/** Позиция */
	@NotBlank
	private Integer pos = 1;

	/** Наименование */
	@NotBlank
	private String name;

	/** Описание */
	@Column(length = 1000)
	private String descr;

	/** Тип объекта БД */
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
	
	/** Поле ТД: Признак необязательного значения */
	@NotNull
	private Boolean isNullable = false;

	/** 
	 * Признак предустанавливаемого объекта (объект, 
	 * который обязательно создаётся под своего главного объекта при его создании, 
	 * например, поля ТД: первичный ключ и исторические поля) 
	 */
	@NotNull
	private Boolean isPreset = false;
	
	/** Поле ТД: Значение по умолчанию */
	@Column(length = 500)
	private String defaultValue;

	/** Поле ТД: Выражение */
	@Column(length = 500)
	private String expression;

	/** Представление/процедура/функция: SQL-запрос */
	@Column(length = 2000)
	private String sqlQuery;
	
	/** Поле ТД: Объект БД ссылки внешнего ключа */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "foreign_key_id")
	@ToString.Exclude
	private DbObject foreignKey;

	/** Поле ТД: Тип данных */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "data_types_id")
	@ToString.Exclude
	private DataType dataType;

	/** Поле ТД: Длина (точность) */
	private Integer precision;

	/** Поле ТД: Точность (масштаб) */
	private Integer scale;

	/** Поле ТД: Список UI объектов, связанных с текущим объектом БД */
	@OneToMany(mappedBy = "dbObject", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@EqualsAndHashCode.Exclude
	private List<UiObjectType> uiObjects = new ArrayList<>();
	
}
