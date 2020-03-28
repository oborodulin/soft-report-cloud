package com.oborodulin.softreport.domain.model.software.document;

import java.util.Date;
import java.util.HashSet;
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
import org.springframework.format.annotation.DateTimeFormat;

import com.oborodulin.softreport.domain.common.entity.DetailEntity;
import com.oborodulin.softreport.domain.model.dic.doctype.DocType;
import com.oborodulin.softreport.domain.model.software.Software;
import com.oborodulin.softreport.domain.model.software.document.version.Version;
import com.oborodulin.softreport.domain.model.term.Term;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Entity
@Table(name = Document.TABLE_NAME)
public class Document extends DetailEntity<Software, String> {

	private static final long serialVersionUID = -1155211422959024301L;

	protected static final String TABLE_NAME = "DOCUMENTS";

	@Column(length = 1000)
	private String name;

	@Column(length = 2000)
	private String purpose;
	
	@Column(length = 2000)
	private String objectives;

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd.MM.yyyy")
	private Date approveDate;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "doc_types_id", nullable = false)
	@ToString.Exclude
	private DocType type;

	@ManyToMany(fetch = FetchType.LAZY)
	@EqualsAndHashCode.Exclude
	private Set<Term> terms = new HashSet<>();

	@OneToMany(mappedBy = CLM_MASTER, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@EqualsAndHashCode.Exclude
	private Set<Version> versions = new HashSet<>();
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getCodeId() {
		return this.name;
	}

	public void addTerm(Term term) {
		this.terms.add(term);
	}

	public void addVersion(Version version) {
		this.versions.add(version);
	}

}
