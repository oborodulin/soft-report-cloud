package com.oborodulin.softreport.domain.model.docobject.docobjectrule;

import javax.persistence.Column;
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
@Table(name = DocObjectRule.TABLE_NAME)
public class DocObjectRule extends DetailEntity<DocObject, String> {

	private static final long serialVersionUID = -6053643593129815794L;

	protected static final String TABLE_NAME = "DOC_OBJECT_RULES";

	/** Наименование */
	private String name;

	/** Описание */
	@Column(length = 500)
	private String descr;

	/** Тип правила */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "type_code")
	@ToString.Exclude
	private Value type;

	/** Оператор */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "operator_code")
	@ToString.Exclude
	private Value operator;

	/** Операнд */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "operand_code")
	@ToString.Exclude
	private Value operand;

	/** Значение операнда */
	@Column(length = 500)
	private String operandValue;

	/** SQL-запрос */
	@Column(length = 2000)
	private String queryValue;
	
	/** Условие выполнения правила */
	@Column(length = 1000)
	private String conditionValidation;

	/** UI/БД объект */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "doc_objects_id")
	@ToString.Exclude
	private DocObject uiObject;
	
	/** Текст ошибки */
	@Column(length = 500)
	private String errMessage;

	/** Текст информационный */
	@Column(length = 500)
	private String infoMessage;
	
}
