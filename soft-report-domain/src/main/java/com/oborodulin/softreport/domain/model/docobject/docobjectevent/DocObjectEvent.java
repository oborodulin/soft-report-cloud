package com.oborodulin.softreport.domain.model.docobject.docobjectevent;

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
import com.oborodulin.softreport.domain.common.entity.DetailEntity;
import com.oborodulin.softreport.domain.model.dic.proglang.uiobjecttype.uieventtype.UiEventType;
import com.oborodulin.softreport.domain.model.dic.valuesset.value.Value;
import com.oborodulin.softreport.domain.model.docobject.DocObject;
import com.oborodulin.softreport.domain.model.project.document.version.Version;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Entity
@Table(name = DocObjectEvent.TABLE_NAME)
public class DocObjectEvent extends DetailEntity<DocObject, String> {

	private static final long serialVersionUID = -9132886481585010836L;

	protected static final String TABLE_NAME = "DOC_OBJECT_EVENTS";

	/** Описание результата события */
	@Column(length = 2000)
	private String resultDescr;
	
	/** Версии */
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@EqualsAndHashCode.Exclude
	private List<Version> versions = new ArrayList<>();

	/** Тип события */
	/*@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "type_code", nullable = false)
	@ToString.Exclude
	private Value type;
	*/

	/** UI: тип события UI объекта */
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "ui_event_types_id", nullable = false)
	@ToString.Exclude
	private UiEventType uiEventType;
	
	/** Действие */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "action_code")
	@ToString.Exclude
	private Value action;

	/** UI/БД объект, над которым выполняется действие*/
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "doc_objects_id")
	@ToString.Exclude
	private DocObject docObject;

	/** UI объект (значение поля ТД, списка и пр.), который присваивается объекту, над которым выполняется действие*/
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "val_doc_objects_id")
	@ToString.Exclude
	private DocObject valDocObject;

}
