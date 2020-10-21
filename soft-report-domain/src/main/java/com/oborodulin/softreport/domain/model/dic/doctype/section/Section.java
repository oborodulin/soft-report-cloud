package com.oborodulin.softreport.domain.model.dic.doctype.section;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import com.oborodulin.softreport.domain.common.entity.DetailTreeEntity;
import com.oborodulin.softreport.domain.model.dic.doctype.DocType;
import com.oborodulin.softreport.domain.model.dic.valuesset.value.Value;
import com.oborodulin.softreport.domain.model.software.Software;

import lombok.Data;
import lombok.ToString;

/**
 * Класс описывает главу документа.
 * 
 * @author Oleg Borodulin
 */
@Data
@Entity
@Table(name = Section.TABLE_NAME)
@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
public class Section extends DetailTreeEntity<DocType, Section, String> {

	private static final long serialVersionUID = -8500159764860114950L;

	/** Наименование таблицы данных доменного объекта (сущности) */
	protected static final String TABLE_NAME = "DOC_TYPE_SECTIONS";

	/** Раздел типа документа */
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "section_code", nullable = false)
	@ToString.Exclude
	private Value section;

	/** ПО */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "softwares_id")
	@ToString.Exclude
	private Software software;

	/** Предконтент (вначале раздела) */
	@Column(length = 2000)
	private String preContent;

	/** Постконтент (перед завершением раздела) */
	@Column(length = 2000)
	private String postContent;

	/**
	 * {@inheritDoc}
	 * <p>
	 * 
	 * @see #section
	 */
	@Override
	public String codeId() {
		return this.section.getVal();
	}

}
