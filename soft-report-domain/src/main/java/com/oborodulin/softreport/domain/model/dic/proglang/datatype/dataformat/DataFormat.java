package com.oborodulin.softreport.domain.model.dic.proglang.datatype.dataformat;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import com.oborodulin.softreport.domain.common.entity.DetailEntity;
import com.oborodulin.softreport.domain.model.dic.proglang.datatype.DataType;

import lombok.Data;

@Data
@Entity
@Table(name = DataFormat.TABLE_NAME)
public class DataFormat extends DetailEntity<DataType, String> {

	private static final long serialVersionUID = 6898903692392787105L;

	public static final String TABLE_NAME = "DATA_FORMATS";

	/** Наименование языка программирования или технологии */
	@NotBlank
	private String format;

	/** Описание */
	private String descr;

}
