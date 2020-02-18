package com.oborodulin.softreport.domain.valuesset.value;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.springframework.format.annotation.DateTimeFormat;

import com.oborodulin.softreport.domain.valuesset.ValuesSet;

import lombok.Data;

@Data
@Entity
@Table(name = "Vals")
public class Value {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "vals_sets_id", nullable = false)
	private ValuesSet valuesSet;
	@NotBlank
	@Column(unique = true)
	private String code;
	@NotBlank(message = "Value is required")
	private String val;
	private String descr;
	@DateTimeFormat(pattern="dd.MM.yyyy")
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
}
