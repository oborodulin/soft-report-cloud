package com.oborodulin.softreport.domain.model.valuesset;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.oborodulin.softreport.domain.model.AuditableEntity;
import com.oborodulin.softreport.domain.model.valuesset.value.Value;

import lombok.Data;

@Data
@Entity
@Table(name = ValuesSet.TABLE_NAME)
public class ValuesSet extends AuditableEntity<String> {
	private static final long serialVersionUID = 951472411985160909L;
	public static final String TABLE_NAME= "VALS_SETS";

	public static final String VS_SOFTWARE_TYPES= "SOFTWARE_TYPES";

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
