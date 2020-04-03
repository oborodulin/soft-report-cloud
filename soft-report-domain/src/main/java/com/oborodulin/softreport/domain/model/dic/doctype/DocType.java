package com.oborodulin.softreport.domain.model.dic.doctype;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import com.oborodulin.softreport.domain.common.entity.AuditableEntity;
import com.oborodulin.softreport.domain.model.dic.valuesset.value.Value;
import com.oborodulin.softreport.domain.model.software.document.Document;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Класс описывает составной типы документов на основании его категории и
 * уникального типа.
 * <p>
 * Позволяет работать с несколькими документами в одной категории. Например, с
 * несколькими шаблонами ТЗ, руководств, инструкций и т.д.
 * <p>
 * Категории и уникальные типы представлены в виде наборов значений.
 * 
 * @author Oleg Borodulin
 *
 * @see com.oborodulin.softreport.domain.model.dic.valuesset.ValuesSet#VS_DOC_CATEGS
 * @see com.oborodulin.softreport.domain.model.dic.valuesset.ValuesSet#VS_DOC_TYPES
 */
@Data
@Entity
@Table(name = DocType.TABLE_NAME)
@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
public class DocType extends AuditableEntity<String> {
	private static final long serialVersionUID = 8743856478405649207L;

	/** Наименование таблицы данных доменного объекта (сущности) */
	protected static final String TABLE_NAME = "DOC_TYPES";

	/** Имя файла шаблона представления */
	@NotBlank
	private String template;

	/** Описание */
	@Column(length = 500)
	private String descr;

	/**
	 * Признак формирования документа по текущей версии (а не с первой по последнюю)
	 */
	@NotNull
	@Column(columnDefinition = "boolean not null default false")
	private Boolean isCurrentVersion = false;

	/** Категория документа */
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "categ_code", nullable = false)
	@ToString.Exclude
	private Value categ;

	/** Уникальный тип документа */
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "type_code", nullable = false)
	@ToString.Exclude
	private Value type;

	/** Список документов текущего составного типа */
	@OneToMany(mappedBy = "type", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@EqualsAndHashCode.Exclude
	private List<Document> documents = new ArrayList<>();

	/**
	 * {@inheritDoc}
	 * <p>
	 * Для составного типа документа это "категория :: уникльный тип".
	 * 
	 * @see #categ
	 * @see #type
	 */
	@Override
	public String getCodeId() {
		return this.categ.getVal().concat(" :: ").concat(this.type.getVal());
	}

}
