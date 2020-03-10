package com.oborodulin.softreport.domain.model.dic.proglang.datatype;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import com.oborodulin.softreport.domain.common.entity.DetailEntity;
import com.oborodulin.softreport.domain.model.dic.proglang.ProgLang;
import com.oborodulin.softreport.domain.model.dic.valuesset.value.Value;

import lombok.Data;
import lombok.ToString;

@Data
@Entity
@Table(name = DataType.TABLE_NAME)
public class DataType extends DetailEntity<ProgLang, String> {

	private static final long serialVersionUID = -2045805271638510988L;

	public static final String TABLE_NAME = "DATA_TYPES";

	/** Наименование */
	private String name;

	/** Тип данных */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "type_code")
	@ToString.Exclude
	private Value type;

	/** Описание */
	private String descr;

	/** Описание */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "backend_id")
	@ToString.Exclude
	private DataType backend;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "frontend_id")
	@ToString.Exclude
	private DataType frontend;

}
