package com.oborodulin.softreport.domain.model.dic.proglang.uiobject;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import com.oborodulin.softreport.domain.common.entity.DetailEntity;
import com.oborodulin.softreport.domain.model.dic.proglang.ProgLang;
import com.oborodulin.softreport.domain.model.dic.valuesset.value.Value;

import lombok.Data;
import lombok.ToString;

@Data
@Entity
@Table(name = UiObject.TABLE_NAME)
public class UiObject extends DetailEntity<ProgLang, String> {

	private static final long serialVersionUID = -5894361677694239614L;

	public static final String TABLE_NAME = "UI_OBJECTS";

	/** Наименование */
	@NotBlank
	private String name;

	/** Описание */
	private String descr;
	
	/** Тип UI объекта */
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "type_code", nullable = false)
	@ToString.Exclude
	private Value type;

}
