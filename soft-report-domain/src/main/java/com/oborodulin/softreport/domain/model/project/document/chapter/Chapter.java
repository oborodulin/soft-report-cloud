package com.oborodulin.softreport.domain.model.project.document.chapter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import com.oborodulin.softreport.domain.common.entity.DetailEntity;
import com.oborodulin.softreport.domain.model.dic.valuesset.value.Value;
import com.oborodulin.softreport.domain.model.project.document.Document;

import lombok.Data;
import lombok.ToString;

/**
 * Класс описывает главу документа.
 * 
 * @author Oleg Borodulin
 */
@Data
@Entity
@Table(name = Chapter.TABLE_NAME)
@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
public class Chapter extends DetailEntity<Document, String> {

	private static final long serialVersionUID = -1093673728898034401L;

	/** Наименование таблицы данных доменного объекта (сущности) */
	protected static final String TABLE_NAME = "DOC_CHAPTERS";

	/** Наименование */
	@Column(length = 500)
	private String name;

	/** Глава документа */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "chapter_code")
	@ToString.Exclude
	private Value chapter;

	/** Предконтент (вначале главы) */
	@Column(length = 2000)
	private String preContent;

	/** Постконтент (перед завершением главы) */
	@Column(length = 2000)
	private String postContent;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String codeId() {
		return this.name != null && !this.name.isEmpty() ? this.name : this.chapter.getVal();
	}

}
