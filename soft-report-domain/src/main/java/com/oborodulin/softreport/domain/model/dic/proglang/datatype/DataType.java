package com.oborodulin.softreport.domain.model.dic.proglang.datatype;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.oborodulin.softreport.domain.common.entity.DetailEntity;
import com.oborodulin.softreport.domain.model.dic.proglang.ProgLang;
import com.oborodulin.softreport.domain.model.dic.valuesset.value.Value;
import com.oborodulin.softreport.domain.model.docobject.DocObject;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Entity
@Table(name = DataType.TABLE_NAME)
public class DataType extends DetailEntity<ProgLang, String> {

	private static final long serialVersionUID = -2045805271638510988L;

	protected static final String TABLE_NAME = "DATA_TYPES";

	/** Наименование */
	private String name;

	/** Описание */
	private String descr;

	/** Тип данных SQL */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "type_code")
	@ToString.Exclude
	private Value type;

	/** Описание */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "backend_id")
	@ToString.Exclude
	private DataType backend;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "frontend_id")
	@ToString.Exclude
	private DataType frontend;

	/** Список объектов БД/UI, текущего типа данных */
	@OneToMany(mappedBy = "dataType", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@EqualsAndHashCode.Exclude
	private List<DocObject> docObjects = new ArrayList<>();

}
