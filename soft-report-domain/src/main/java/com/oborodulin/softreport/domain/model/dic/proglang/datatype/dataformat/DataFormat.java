package com.oborodulin.softreport.domain.model.dic.proglang.datatype.dataformat;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import com.oborodulin.softreport.domain.common.entity.DetailEntity;
import com.oborodulin.softreport.domain.model.dic.proglang.datatype.DataType;
import com.oborodulin.softreport.domain.model.docobject.DocObject;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = DataFormat.TABLE_NAME)
public class DataFormat extends DetailEntity<DataType, String> {

	private static final long serialVersionUID = 6898903692392787105L;

	protected static final String TABLE_NAME = "DATA_FORMATS";

	/** Наименование языка программирования или технологии */
	@NotBlank
	private String format;

	/** Пример */
	private String sample;

	/** Описание */
	private String descr;
	
	/** Список объектов БД/UI, текущего формата данных */
	@OneToMany(mappedBy = "format", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@EqualsAndHashCode.Exclude
	private List<DocObject> docObjects = new ArrayList<>();

}
