package com.oborodulin.softreport.domain.valuesset;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.oborodulin.softreport.domain.valuesset.value.Value;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Entity
@RequiredArgsConstructor
@Table(name = "ValsSets")
public class ValuesSet {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@NotBlank
	@Column(unique = true)
	private String code;
	@NotBlank
	private String name;
	private String descr;
	@NotNull
	private Boolean isUpdatable = false;

	@OneToMany(mappedBy = "valuesSet", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Value> values;

	public void addValue(Value value) {
		this.values.add(value);
	}
}
