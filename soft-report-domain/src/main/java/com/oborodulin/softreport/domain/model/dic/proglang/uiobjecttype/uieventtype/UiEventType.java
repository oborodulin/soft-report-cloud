package com.oborodulin.softreport.domain.model.dic.proglang.uiobjecttype.uieventtype;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import com.oborodulin.softreport.domain.common.entity.DetailEntity;
import com.oborodulin.softreport.domain.model.dic.proglang.uiobjecttype.UiObjectType;
import com.oborodulin.softreport.domain.model.dic.valuesset.value.Value;

import lombok.Data;
import lombok.ToString;

@Data
@Entity
@Table(name = UiEventType.TABLE_NAME)
public class UiEventType extends DetailEntity<UiObjectType, String> {

	private static final long serialVersionUID = 4118220275040901488L;

	protected static final String TABLE_NAME = "UI_EVENT_TYPES";

	/** Наименование */
	@NotBlank
	private String name;

	/** Описание */
	private String descr;
	
	/** Тип UI события */
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "type_code", nullable = false)
	@ToString.Exclude
	private Value type;

}
