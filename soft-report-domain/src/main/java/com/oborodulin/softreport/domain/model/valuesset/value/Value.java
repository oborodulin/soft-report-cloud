package com.oborodulin.softreport.domain.model.valuesset.value;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;

import org.springframework.format.annotation.DateTimeFormat;

import com.oborodulin.softreport.domain.model.AuditableEntity;
import com.oborodulin.softreport.domain.model.software.Software;
import com.oborodulin.softreport.domain.model.valuesset.ValuesSet;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = Value.TABLE_NAME)
public class Value extends AuditableEntity<String> {
	private static final long serialVersionUID = 639613089661707969L;
	public static final String TABLE_NAME = "VALS";

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "vals_sets_id", nullable = false)
	private ValuesSet valuesSet;
	@NotBlank
	@Column(unique = true)
	private String code;
	@NotBlank(message = "Value is required")
	private String val;
	private String descr;
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd.MM.yyyy")
	private Date closeDate;
	private String attr1;
	private String attr2;
	private String attr3;
	private String attr4;
	private String attr5;
	private String attr6;
	private String attr7;
	private String attr8;
	private String attr9;
	private String attr10;

	@OneToMany(mappedBy = "typeCode", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@EqualsAndHashCode.Exclude
	private List<Software> softwares = new ArrayList<Software>();

	/**
	 * Возвращает имя и код набора значений.
	 * 
	 * Необходимо для отображения с целью визуальной идентификации набора значений,
	 * которому принадлежит или будет принадлежать текущее значение.
	 * 
	 * @return имя и код набора значений
	 */
	public String getSetNameAndCode() {
		return getValuesSet() != null ? getValuesSet().getNameAndCode() : null;
	};
}
