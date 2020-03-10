package com.oborodulin.softreport.domain.model.dic.valuesset.value;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;

import org.springframework.format.annotation.DateTimeFormat;

import com.oborodulin.softreport.domain.common.entity.DetailEntity;
import com.oborodulin.softreport.domain.model.databaseobject.DataBaseObject;
import com.oborodulin.softreport.domain.model.dic.doctype.DocType;
import com.oborodulin.softreport.domain.model.dic.objecthierarchy.ObjectHierarchy;
import com.oborodulin.softreport.domain.model.dic.proglang.ProgLang;
import com.oborodulin.softreport.domain.model.dic.proglang.datatype.DataType;
import com.oborodulin.softreport.domain.model.dic.proglang.uiobject.UiObject;
import com.oborodulin.softreport.domain.model.dic.server.Server;
import com.oborodulin.softreport.domain.model.dic.valuesset.ValuesSet;
import com.oborodulin.softreport.domain.model.software.Software;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = Value.TABLE_NAME)
public class Value extends DetailEntity<ValuesSet, String> {
	private static final long serialVersionUID = 639613089661707969L;
	public static final String TABLE_NAME = "VALS";

	/** Уникальный код значения */
	@NotBlank
	@Column(unique = true)
	private String code;

	/** Значение */
	@NotBlank(message = "Value is required")
	@Column(length = 2000)
	private String val;

	/** Описание значения */
	private String descr;

	/** Дата закрытия значения */
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd.MM.yyyy")
	private Date closeDate;

	/** Атрибут №1 значения*/
	@Column(length = 500)
	private String attr1;

	/** Атрибут №2 значения*/
	@Column(length = 500)
	private String attr2;

	/** Атрибут №3 значения*/
	@Column(length = 500)
	private String attr3;

	/** Атрибут №4 значения*/
	@Column(length = 500)
	private String attr4;

	/** Атрибут №5 значения*/
	@Column(length = 500)
	private String attr5;

	/** Атрибут №6 значения*/
	@Column(length = 500)
	private String attr6;

	/** Атрибут №7 значения*/
	@Column(length = 500)
	private String attr7;

	/** Атрибут №8 значения*/
	@Column(length = 500)
	private String attr8;

	/** Атрибут №9 значения*/
	@Column(length = 500)
	private String attr9;

	/** Атрибут №10 значения*/
	@Column(length = 500)
	private String attr10;

	/** Список ПО текущего типа (значения)*/
	@OneToMany(mappedBy = "type", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@EqualsAndHashCode.Exclude
	private List<Software> softwares = new ArrayList<>();

	/** Список типов документов текущей категории (значения)*/
	@OneToMany(mappedBy = "categ", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@EqualsAndHashCode.Exclude
	private List<DocType> categDocTypes = new ArrayList<>();

	/** Список типов документов текущего типа (значения)*/
	@OneToMany(mappedBy = "type", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@EqualsAndHashCode.Exclude
	private List<DocType> typeDocTypes = new ArrayList<>();

	/** Список объектов БД текущего типа (значения)*/
	@OneToMany(mappedBy = "type", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@EqualsAndHashCode.Exclude
	private List<DataBaseObject> typeDataBaseObjects = new ArrayList<>();

	/** Список объектов БД текущего типа таблиц данных (значения)*/
	@OneToMany(mappedBy = "dtType", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@EqualsAndHashCode.Exclude
	private List<DataBaseObject> dtTypeDataBaseObjects = new ArrayList<>();

	/** Список объектов БД текущего типа базы данных (значения)*/
	@OneToMany(mappedBy = "dbType", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@EqualsAndHashCode.Exclude
	private List<DataBaseObject> dbTypeDataBaseObjects = new ArrayList<>();
	
	/** Язык программирования текущей технологии (значения)*/
	@OneToOne(mappedBy = "lang")
	@EqualsAndHashCode.Exclude
	private ProgLang progLang;

	/** Список языков программирования текущей архитектуры ПО (значения)*/
	@OneToMany(mappedBy = "arch", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@EqualsAndHashCode.Exclude
	private List<ProgLang> archProgLangs = new ArrayList<>();

	/** Список типов данных текущего типа данных (значения)*/
	@OneToMany(mappedBy = "type", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@EqualsAndHashCode.Exclude
	private List<DataType> dataTypes = new ArrayList<>();

	/** Список серверов текущего типа (значения)*/
	@OneToMany(mappedBy = "type", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@EqualsAndHashCode.Exclude
	private List<Server> typeServers = new ArrayList<>();

	/** Список серверов текущего типа окружения (значения)*/
	@OneToMany(mappedBy = "env", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@EqualsAndHashCode.Exclude
	private List<Server> envServers = new ArrayList<>();

	/** Список объектов иерархии текущей архитектуры ПО (значения)*/
	@OneToMany(mappedBy = "arch", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@EqualsAndHashCode.Exclude
	private List<ObjectHierarchy> archObjectHierarches = new ArrayList<>();

	/** Список объектов БД иерархии текущего типа БД (значения)*/
	@OneToMany(mappedBy = "dbType", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@EqualsAndHashCode.Exclude
	private List<ObjectHierarchy> dbTypeObjectHierarches = new ArrayList<>();

	/** Список объектов UI иерархии текущего типа UI (значения)*/
	@OneToMany(mappedBy = "uiType", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@EqualsAndHashCode.Exclude
	private List<ObjectHierarchy> uiTypeObjectHierarches = new ArrayList<>();

	/** Список объектов UI текущего типа UI (значения)*/
	@OneToMany(mappedBy = "type", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@EqualsAndHashCode.Exclude
	private List<UiObject> uiObjectTypes = new ArrayList<>();
	
	/**
	 * Возвращает имя и код набора значений.
	 * 
	 * Необходимо для отображения с целью визуальной идентификации набора значений,
	 * которому принадлежит или будет принадлежать текущее значение.
	 * 
	 * @return имя и код набора значений
	 * @see com.oborodulin.softreport.domain.model.dic.valuesset.ValuesSet
	 * @see com.oborodulin.softreport.domain.model.dic.valuesset.ValuesSet#getNameAndCode()
	 */
	public String getSetNameAndCode() {
		return getMaster() != null ? getMaster().getNameAndCode() : null;
	};
}
