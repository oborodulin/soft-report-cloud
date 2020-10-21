package com.oborodulin.softreport.domain.model.term;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import com.oborodulin.softreport.domain.common.entity.AuditableEntity;
import com.oborodulin.softreport.domain.model.project.document.Document;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = Term.TABLE_NAME)
@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
public class Term extends AuditableEntity<String> {
	private static final long serialVersionUID = 8683776013314541231L;

	/** Наименование таблицы данных доменного объекта (сущности) */
	protected static final String TABLE_NAME = "TERMS";

	/** Наименование*/
	@NotBlank
	@Column(unique = true, length = 500)
	private String name;

	/** Описание*/
	@NotBlank
	@Column(length = 2000)
	private String descr;

	@ManyToMany(mappedBy = "terms", fetch = FetchType.LAZY)
	@EqualsAndHashCode.Exclude
	private List<Document> documents = new ArrayList<>();

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String codeId() {
		return this.name;
	}
	
	public void addDocument(Document document) {
		this.documents.add(document);
	}
	
}
