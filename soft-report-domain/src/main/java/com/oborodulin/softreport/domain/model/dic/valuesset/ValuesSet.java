package com.oborodulin.softreport.domain.model.dic.valuesset;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.oborodulin.softreport.domain.common.entity.AuditableEntity;
import com.oborodulin.softreport.domain.common.entity.DetailEntity;
import com.oborodulin.softreport.domain.model.dic.valuesset.value.Value;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = ValuesSet.TABLE_NAME)
public class ValuesSet extends AuditableEntity<String> {
	private static final long serialVersionUID = 951472411985160909L;
	protected static final String TABLE_NAME = "VALS_SETS";

	/**	!!!Код набора значений: типы серверов (БД, Вебсервер, Сервер приложений, Отчётов) */
	public static final String VS_SERVERS_TYPES = "SERVERS_TYPES";

	/**	!!!Код набора значений: типы окружений (разработки, тестовое, промышленное и пр.) */
	public static final String VS_ENV_TYPES = "ENV_TYPES";
	
	/**	Код набора значений: типы ПО (веб-приложение, десктопное приложение, интерфейс, служба) */
	public static final String VS_SOFTWARE_TYPES = "SOFTWARE_TYPES";
	/**	Код набора значений: архитектуры ПО (БД, ETL, фронтенд, бэкенд) */
	public static final String VS_SOFTWARE_ARCHS = "SOFTWARE_ARCHS";
	/**	!!!Код набора значений: технологии в т.ч. языки программирования с признаком язык/технология*/
	public static final String VS_SOFTWARE_TECHS = "SOFTWARE_TECHS";
	
	/**	Код набора значений: категории документов */
	public static final String VS_DOC_CATEGS = "DOC_CATEGS";
	/**	Код набора значений: типы документов */
	public static final String VS_DOC_TYPES = "DOC_TYPES";

	/**	Код набора значений: типы БД (MS SQL, Oracle DB, MySQL и пр.) */
	public static final String VS_DB_TYPES = "DB_TYPES";
	/**	!!!Код набора значений: типы таблиц данных (справочные, оперативные, интерфейсные, временные, логгирования)*/
	public static final String VS_DB_DT_TYPES = "DB_DT_TYPES";
	/**	!!!Код набора значений: типы объектов данных (БД, схемы, типы данных, ТД, триггеры, поля таблиц, значения полей, представления, поля представлений, хранимые процедуры, функции, сиквенсы) */
	public static final String VS_DB_OBJ_TYPES = "DB_OBJ_TYPES";
	/**	!!!Код набора значений: типы данных SQL*/
	public static final String VS_DB_SQL_DATA_TYPES = "DB_SQL_DATA_TYPES";

	/**	!!!Код набора значений: типы объектов UI (общие) с признаками контейнер/нет, компонент/нет: 
	 * меню, пункт меню, окна, панели, панели инструментов, кнопки панели инструментов,
	 * параметры поиска (параметр-список (атрибуты мягкого поиска), параметр-чекбокс, параметр-радио-кнопка, параметр-дата), формы ввода, 
	 * группа полей, радио-группа, поля ввода 
	 * (поле-метка, текстовое поле, список (атрибуты мягкого поиска), чекбокс, радио-кнопка, поле даты, поле времени), 
	 * атрибуты списков, зависимость полей, гриды, 
	 * колонки (чекбокс массовых операций, чекбокс), кнопки, сообщения (информационные, подтверждающие, об ошибке), 
	 * параметры сообщения и т.д. 
	 */
	public static final String VS_DOC_OBJ_TYPES = "DOC_OBJ_TYPES";
	/**	!!!Код набора значений: события объектов UI (общие): 
	 * параметр/поле-список (после выбора, ), сообщение (при подтверждении, при отмене)
	 * получение/потеря фокуса, кнопка (при нажатии), ввод и т.д. 
	 */
	public static final String VS_DOC_OBJ_EVENTS = "DOC_OBJ_EVENTS";

	/**	!!!Код набора значений: действия над объектами в результате событий: 
	 * отображается, появляется, исчезает, активируется, деактивируется, сохраняется, 
	 * устанавливается (например, значения у новой записи), создаётся запись
	 */
	public static final String VS_DOC_OBJ_EVENT_ACTIONS = "DOC_OBJ_EVENT_ACTIONS";
	
	/**	!!!Код набора значений: типы бизнесс-правил (сравнение, наличие, отсутствие) */
	public static final String VS_RULE_TYPES = "RULE_TYPES";
	/**	!!!Код набора значений: типы сравнения бизнесс-правил (значение, результат запроса, поле ТД, поле формы ввода, выражение) */
	public static final String VS_RULE_COMPARE_TYPES = "RULE_COMPARE_TYPES";
	
	@NotBlank
	@Column(unique = true)
	private String code;
	
	@NotBlank
	private String name;
	
	private String descr;
	
	@NotNull
	private Boolean isUpdatable = false;

	@OneToMany(mappedBy = DetailEntity.CLM_MASTER, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@EqualsAndHashCode.Exclude
	private Set<Value> values = new HashSet<>();

	/**
	 * Добавляет значение в набор.
	 *
	 * @param value значение
	 * @see com.oborodulin.softreport.domain.model.dic.valuesset.value.Value
	 */
	public void addValue(Value value) {
		this.values.add(value);
	}

	/**
	 * Возвращает имя и код набора значений в формате <code>Name [Code]</code>.
	 *  
	 * Необходимо для отображения с целью визуальной идентификации набора значений,
	 * которому принадлежит текущее или будет принадлежать новое значение.
	 * 
	 * @return имя и код набора значений
	 */
	public String getNameAndCode() {
		return getName().concat(" [").concat(getCode()).concat("]");
	};
}
