package com.oborodulin.softreport.domain.model.dic.proglang.uiobjecttype.uieventtype;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import com.oborodulin.softreport.domain.common.entity.DetailEntity;
import com.oborodulin.softreport.domain.model.dic.proglang.uiobjecttype.UiObjectType;
import com.oborodulin.softreport.domain.model.dic.valuesset.value.Value;
import com.oborodulin.softreport.domain.model.docobject.docobjectevent.DocObjectEvent;

import lombok.Data;
import lombok.EqualsAndHashCode;
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

	/** Список событий, связанных с текущим типом */
	@OneToMany(mappedBy = "uiEventType", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@EqualsAndHashCode.Exclude
	private List<DocObjectEvent> docObjectEvents = new ArrayList<>();
	
}
