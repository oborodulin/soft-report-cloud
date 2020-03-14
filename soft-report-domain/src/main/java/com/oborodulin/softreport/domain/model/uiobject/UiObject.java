package com.oborodulin.softreport.domain.model.uiobject;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.oborodulin.softreport.domain.common.entity.TreeEntity;
import com.oborodulin.softreport.domain.model.dbobject.DbObject;
import com.oborodulin.softreport.domain.model.dic.proglang.datatype.dataformat.DataFormat;
import com.oborodulin.softreport.domain.model.dic.valuesset.value.Value;
import com.oborodulin.softreport.domain.model.software.businessobject.BusinessObject;

import lombok.Data;
import lombok.ToString;

/**
 * Класс описывает дерево объектов пользовательского интерфейса (UI).
 * 
 * Позволяет работать со всеми возможными объектами UI: формы, панели, поля,
 * таблицы, колонки, кнопки и т.д.
 * 
 * @author acs-i
 *
 */
@Data
@Entity
@Table(name = UiObject.TABLE_NAME)
public class UiObject extends TreeEntity<UiObject, String> {

	private static final long serialVersionUID = 873176357176317561L;

	protected static final String TABLE_NAME = "UI_OBJECTS";

	/** Позиция */
	@NotBlank
	private Integer pos = 1;

	/** Метка */
	@NotBlank
	private String label;

	/** Описание */
	@Column(length = 1000)
	private String descr;

	/** Тип объекта UI */
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "type_code", nullable = false)
	@ToString.Exclude
	private Value type;

	/** Бизнес-объект */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "business_objects_id")
	@ToString.Exclude
	private BusinessObject businessObject;

	/** Форма/поле/колонка: Объект БД */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "db_objects_id")
	@ToString.Exclude
	private DbObject dbObject;

	/** Поле/колонка: Формат данных, основанный на типе объекта БД */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "data_formats_id")
	@ToString.Exclude
	private DataFormat format;
	
	/** Поле/колонка: Признак обязательного значения */
	@NotNull
	private Boolean isRequired = false;

	/** Поле/колонка: Признак обновляемого/редактируемого значения */
	@NotNull
	private Boolean isUpdatable = false;

	/** Значение списка: Признак отображаемого в списке значения */
	@NotNull
	private Boolean isDisplayAttribute = false;

	/** Поле/колонка: Выражение */
	@Column(length = 500)
	private String expression;

	/** Поле-чекбокс/поле радио-кнопка: Значение по умолчанию "Отмечено"/"Не отмечено" */
	@NotNull
	private Boolean isDefaultChecked = false;

	/** Поле-чекбокс: Значение "Отмечено" */
	@NotNull
	private String checkedValue = "1";

	/** Поле-чекбокс: Значение "Не отмечено" */
	@NotNull
	private String notCheckedValue = "0";

	/** Поле радио-кнопка: Значение */
	@NotNull
	private String radioValue;
	
}
