package com.oborodulin.softreport.domain.model.dic.doctype.section;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import com.oborodulin.softreport.domain.common.entity.DetailEntity;
import com.oborodulin.softreport.domain.model.dic.doctype.DocType;
import com.oborodulin.softreport.domain.model.dic.valuesset.value.Value;
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
public class Section extends DetailEntity<DocType, String> {

	private static final long serialVersionUID = -8500159764860114950L;

	/** Наименование таблицы данных доменного объекта (сущности) */
	protected static final String TABLE_NAME = "DOC_TYPE_SECTIONS";

	/** Раздел типа документа */
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "section_code", nullable = false)
	@ToString.Exclude
	private Value section;

	/**
	 * {@inheritDoc}
	 * <p>
	 * 
	 * @see #section
	 */
	@Override
	public String getCodeId() {
		return this.section.getVal();
	}

}
