package com.oborodulin.softreport.domain.model.dic.valuesset;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import com.oborodulin.softreport.domain.common.entity.AuditableEntity;
import com.oborodulin.softreport.domain.common.entity.DetailEntity;
import com.oborodulin.softreport.domain.model.dic.valuesset.value.Value;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = ValuesSet.TABLE_NAME)
@Audited
public class ValuesSet extends AuditableEntity<String> {
	private static final long serialVersionUID = 951472411985160909L;
	protected static final String TABLE_NAME = "VALS_SETS";

	/**
	 * Код набора значений: типы серверов (БД, Вебсервер, Сервер приложений,
	 * Отчётов)
	 */
	public static final String VS_SERVERS_TYPES = "SERVERS_TYPES";

	/**
	 * Код набора значений: типы окружений (разработки, тестовое, промышленное и
	 * пр.)
	 */
	public static final String VS_ENV_TYPES = "ENV_TYPES";

	/** Код набора значений: Направления сортировки (по возрастанию/убыванию) */
	public static final String VS_SORT_DIRECTIONS = "SORT_DIRECTIONS";

	/** !!!Код набора значений: Тип конфигурационного пакета (командная строка, окружение, файл, поле ТД) */
	public static final String VS_CFG_BUNDLE_TYPES = "CFG_BUNDLE_TYPES";
	
	/**
	 * Код набора значений: типы ПО (веб-приложение, десктопное приложение,
	 * интерфейс, служба)
	 */
	public static final String VS_SOFTWARE_TYPES = "SOFTWARE_TYPES";
	/** Код набора значений: архитектуры ПО (БД, ETL, фронтенд, бэкенд) */
	public static final String VS_SOFTWARE_ARCHS = "SOFTWARE_ARCHS";
	/**
	 * !!!Код набора значений: технологии в т.ч. языки программирования с признаком
	 * язык/технология
	 */
	public static final String VS_SOFTWARE_TECHS = "SOFTWARE_TECHS";

	/** Код набора значений: категории документов */
	public static final String VS_DOC_CATEGS = "DOC_CATEGS";
	/** Код набора значений: типы документов */
	public static final String VS_DOC_TYPES = "DOC_TYPES";

	/** Код набора значений: типы БД (MS SQL, Oracle DB, MySQL и пр.) */
	public static final String VS_DB_TYPES = "DB_TYPES";
	/** Код набора значений: типы таблиц данных (справочные, оперативные, интерфейсные, временные, логгирования)*/
	public static final String VS_DB_DT_TYPES = "DB_DT_TYPES";
	/** !!!Код набора значений: типы полей таблиц данных (исторические/версия) */
	public static final String VS_DB_COLUMN_TYPES = "DB_COLUMN_TYPES";
	/** !!!Код набора значений: типы данных SQL */
	public static final String VS_DB_SQL_DATA_TYPES = "DB_SQL_DATA_TYPES";

	/**
	 * !!!Код набора значений: типы объектов БД/UI (общие) с признаками DB/UI,
	 * контейнер/нет, компонент/нет: БД, схемы, типы данных, ТД, триггеры, поля
	 * таблиц, значения полей, представления, поля представлений, хранимые
	 * процедуры, функции, сиквенсы, меню, пункт меню, окна, панели, панели
	 * инструментов, кнопки панели инструментов, вкладки, параметры поиска
	 * (параметр-список (атрибуты мягкого поиска, значения списка),
	 * параметр-чекбокс, параметр-радио-кнопка, параметр-дата), формы ввода, группа
	 * полей, радио-группа, поля ввода (поле-метка, текстовое поле, список (атрибуты
	 * мягкого поиска, значения списка), чекбокс, радио-кнопка, поле даты, поле
	 * времени), атрибуты списков, зависимость полей, гриды, колонки (чекбокс
	 * массовых операций, чекбокс), кнопки, сообщения (информационные,
	 * подтверждающие, об ошибке), параметры сообщения и т.д.
	 */
	public static final String VS_DOC_OBJ_TYPES = "DOC_OBJ_TYPES";
	/**
	 * !!!Код набора значений: события объектов UI (общие): параметр/поле-список
	 * (после выбора, ), сообщение (при подтверждении, при отмене) получение/потеря
	 * фокуса, кнопка (при нажатии), ввод и т.д.
	 */
	public static final String VS_DOC_OBJ_EVENTS = "DOC_OBJ_EVENTS";
	/**
	 * !!!Код набора значений: действия над объектами (с признаками DB/UI, DB, UI) в
	 * результате событий: отображается, появляется, исчезает, активируется,
	 * деактивируется, сохраняется, устанавливается (например, значения у новой
	 * записи), создаётся запись, переписываются/импортируются данные (например, из
	 * временной ТД в оперативную)
	 */
	public static final String VS_DOC_OBJ_EVENT_ACTIONS = "DOC_OBJ_EVENT_ACTIONS";

	/**
	 * !!!Код набора значений: типы бизнесс-правил (активность, не активность,
	 * сравнение, наличие, отсутствие, длина, диапазон)
	 */
	public static final String VS_RULE_TYPES = "RULE_TYPES";
	/**
	 * !!!Код набора значений: типы сравнения бизнесс-правил (значение, результат
	 * запроса, строка грида, поле ТД, поле формы ввода, выражение)
	 */
	public static final String VS_RULE_COMPARE_TYPES = "RULE_COMPARE_TYPES";
	/**
	 * !!!Код набора значений: операторы сравнения бизнесс-правил (равно, не равно,
	 * больше, меньше, больше или равно, меньше или равно, входит в набор значений,
	 * не входит в набор значений, пустой, не пустой, выделено/отмечено)
	 */
	public static final String VS_RULE_OPERATORS = "RULE_OPERATORS";

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
	@NotAudited
	private List<Value> values = new ArrayList<>();

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

	/**
	 * Предобработка данных перед созданием и сохранением.
	 * 
	 * (Устанавливает значение кода набора значений прописными символами)
	 * @see #code
	 */
	@PrePersist
	public void prePersist() {
		this.setCode(code.toUpperCase());
	}

	/**
	 * Предобработка данных перед изменением.
	 * 
	 * (Устанавливает значение кода набора значений прописными символами)
	 * @see #code
	 */
	@PreUpdate
	public void preUpdate() {
		this.setCode(code.toUpperCase());
	}
}
