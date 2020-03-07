package com.oborodulin.softreport.domain.model.databaseobject;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.oborodulin.softreport.domain.common.entity.TreeEntity;
import com.oborodulin.softreport.domain.model.dic.server.Server;
import com.oborodulin.softreport.domain.model.dic.valuesset.value.Value;
import com.oborodulin.softreport.domain.model.software.Software;
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
@Table(name = DataBaseObject.TABLE_NAME)
public class DataBaseObject extends TreeEntity<DataBaseObject, String> {
	private static final long serialVersionUID = -5847640757970947607L;

	public static final String TABLE_NAME = "DATABASE_OBJECTS";

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

	/** Тип базы данных */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "db_type_code")
	@ToString.Exclude
	private Value dbType;

	/** Сервер */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "servers_id")
	@ToString.Exclude
	private Server server;
	
	/** Тип таблицы данных */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "table_type_code")
	@ToString.Exclude
	private Value tableType;

	/** Бизнес-объект */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "business_objects_id")
	@ToString.Exclude
	private BusinessObject businessObject;
	
	/** Признак первичного ключа */
	@NotNull
	private Boolean isPrimaryKey = false;

	/** Признак уникального ключа */
	@NotNull
	private Boolean isUniqueKey = false;
	
	/** Признак необязательного значения */
	@NotNull
	private Boolean isNullable = false;

	/** Признак предустанавливаемого объекта */
	@NotNull
	private Boolean isPreset = false;
	
	/** Значение по умолчанию */
	@Column(length = 200)
	private String defaultValue;

	/** Объект БД ссылки внешнего ключа */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "foreign_key_id")
	@ToString.Exclude
	private DataBaseObject foreignKey;

	/** Длина (точность) */
	private Integer precision;

	/** Точность (масштаб) */
	private Integer scale;

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@EqualsAndHashCode.Exclude
	private List<Software> softwares = new ArrayList<>();
	
}
