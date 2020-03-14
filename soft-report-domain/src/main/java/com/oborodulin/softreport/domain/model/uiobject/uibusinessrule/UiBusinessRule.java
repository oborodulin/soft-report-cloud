package com.oborodulin.softreport.domain.model.uiobject.uibusinessrule;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import com.oborodulin.softreport.domain.common.entity.DetailEntity;
import com.oborodulin.softreport.domain.model.dbobject.DbObject;
import com.oborodulin.softreport.domain.model.dic.valuesset.value.Value;
import com.oborodulin.softreport.domain.model.uiobject.UiObject;

import lombok.Data;
import lombok.ToString;

@Data
@Entity
@Table(name = UiBusinessRule.TABLE_NAME)
public class UiBusinessRule extends DetailEntity<UiObject, String> {

	private static final long serialVersionUID = -6053643593129815794L;

	protected static final String TABLE_NAME = "UI_BUSINESS_RULES";

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

	/** UI объект */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ui_objects_id")
	@ToString.Exclude
	private UiObject uiObject;
	
	/** Объект БД */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "db_objects_id")
	@ToString.Exclude
	private DbObject dbObject;
	
	
}
