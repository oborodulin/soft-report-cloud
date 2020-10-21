package com.oborodulin.softreport.domain.model.project.document;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;
import org.springframework.format.annotation.DateTimeFormat;

import com.oborodulin.softreport.domain.common.entity.DetailTreeEntity;
import com.oborodulin.softreport.domain.model.dic.doctype.DocType;
import com.oborodulin.softreport.domain.model.project.Project;
import com.oborodulin.softreport.domain.model.project.document.chapter.Chapter;
import com.oborodulin.softreport.domain.model.project.document.version.Version;
import com.oborodulin.softreport.domain.model.term.Term;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Entity
@Table(name = Document.TABLE_NAME)
@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
public class Document extends DetailTreeEntity<Project, Document, String> {

	private static final long serialVersionUID = -1155211422959024301L;

	/** Наименование таблицы данных доменного объекта (сущности) */
	protected static final String TABLE_NAME = "DOCUMENTS";

	/** Наименование (не обязательное поле) */
	@Column(length = 1000)
	private String name;

	/** Назначение */
	@Column(length = 2000)
	private String purpose;

	/** Цели */
	@Column(length = 2000)
	private String objectives;

	/** Дата */
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd.MM.yyyy")
	private Date docDate;

	/** Дата утверждения */
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd.MM.yyyy")
	private Date approveDate;

	/** Тип */
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "doc_types_id", nullable = false)
	@ToString.Exclude
	private DocType type;

	/** Термины и определения */
	@ManyToMany(fetch = FetchType.LAZY)
	@EqualsAndHashCode.Exclude
	private Set<Term> terms = new HashSet<>();

	/** Предопределённые главы */
	@OneToMany(mappedBy = CLM_MASTER, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@EqualsAndHashCode.Exclude
	private List<Chapter> chapters = new ArrayList<>();

	/** Версии */
	@OneToMany(mappedBy = CLM_MASTER, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@EqualsAndHashCode.Exclude
	private Set<Version> versions = new HashSet<>();

	/** Родительский заголовок (проект или ПО при наличие родителей) */
	@Transient
	private String parentTitle;

	/**
	 * Заголовок.
	 * <p>
	 * По умолчанию используется {@code #name}, но, если отсутствует, то
	 * определяется по проекту или его ПО.
	 */
	@Transient
	private String title;

	/** Последняя версия */
	@Transient
	private Version lastVersion;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String codeId() {
		return this.name != null && !this.name.isEmpty() ? this.name
				: this.type.getType().getVal() + "." + this.getMaster().getName();
	}

	public void addTerm(Term term) {
		this.terms.add(term);
		term.addDocument(this);
	}

	public void addVersion(Version version) {
		this.versions.add(version);
		version.setMaster(this);
	}

	public String getLastVersionString() {
		return this.getLastVersion() != null ? this.getLastVersion().getSemVersionString() : null;
	}

}
