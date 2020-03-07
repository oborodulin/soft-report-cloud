package com.oborodulin.softreport.domain.model.dic.doctype;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import com.oborodulin.softreport.domain.common.entity.AuditableEntity;
import com.oborodulin.softreport.domain.model.dic.valuesset.value.Value;
import com.oborodulin.softreport.domain.model.project.document.Document;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Entity
@Table(name = DocType.TABLE_NAME)
public class DocType extends AuditableEntity<String> {
	private static final long serialVersionUID = 8743856478405649207L;

	public static final String TABLE_NAME = "DOC_TYPES";

	/** Имя файла шаблона представления */
	@NotBlank
	private String template;
	
	/** Категория документа */
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "categ_code", nullable = false)
	@ToString.Exclude
	private Value categ;

	/** Тип документа */
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "type_code", nullable = false)
	@ToString.Exclude
	private Value type;

	/** Список документов текущего типа */
	@OneToMany(mappedBy = "type", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@EqualsAndHashCode.Exclude
	private List<Document> documents = new ArrayList<>();
}
