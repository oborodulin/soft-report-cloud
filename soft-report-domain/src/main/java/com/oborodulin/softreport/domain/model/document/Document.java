package com.oborodulin.softreport.domain.model.document;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;

import org.springframework.format.annotation.DateTimeFormat;

import com.oborodulin.softreport.domain.common.entity.DetailEntity;
import com.oborodulin.softreport.domain.model.dic.doctype.DocType;
import com.oborodulin.softreport.domain.model.project.Project;

import lombok.Data;
import lombok.ToString;

@Data
@Entity
@Table(name = Document.TABLE_NAME)
public class Document extends DetailEntity<Project, String> {
	private static final long serialVersionUID = -2841744486896577266L;
	public static final String TABLE_NAME = "DOCUMENTS";

	@NotBlank(message = "Name is required")
	private String name;

	@Column(length = 2000)
	private String purpose;
	
	@Column(length = 2000)
	private String objectives;

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd.MM.yyyy")
	private Date closeDate;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "doc_types_id", nullable = false)
	@ToString.Exclude
	private DocType type;

}
