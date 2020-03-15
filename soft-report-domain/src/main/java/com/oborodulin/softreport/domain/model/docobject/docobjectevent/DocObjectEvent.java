package com.oborodulin.softreport.domain.model.docobject.docobjectevent;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import com.oborodulin.softreport.domain.common.entity.DetailEntity;
import com.oborodulin.softreport.domain.model.dic.valuesset.value.Value;
import com.oborodulin.softreport.domain.model.docobject.DocObject;

import lombok.Data;
import lombok.ToString;

@Data
@Entity
@Table(name = DocObjectEvent.TABLE_NAME)
public class DocObjectEvent extends DetailEntity<DocObject, String> {

	private static final long serialVersionUID = -9132886481585010836L;

	protected static final String TABLE_NAME = "DOC_OBJECT_EVENTS";

	/** Тип правила */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "type_code")
	@ToString.Exclude
	private Value type;

	/** Действие */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "action_code")
	@ToString.Exclude
	private Value action;

	/** UI объект над которым выполняется действие*/
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ui_doc_objects_id")
	@ToString.Exclude
	private DocObject uiObject;
	
}
