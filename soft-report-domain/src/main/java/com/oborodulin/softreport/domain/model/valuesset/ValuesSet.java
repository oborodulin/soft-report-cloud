package com.oborodulin.softreport.domain.model.valuesset;

import java.util.HashSet;
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
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = ValuesSet.TABLE_NAME)
public class ValuesSet extends AuditableEntity<String> {
	private static final long serialVersionUID = 951472411985160909L;
	public static final String TABLE_NAME = "VALS_SETS";

	public static final String VS_SOFTWARE_TYPES = "SOFTWARE_TYPES";

	@NotBlank
	@Column(unique = true)
	private String code;
	@NotBlank
	private String name;
	private String descr;
	@NotNull
	private Boolean isUpdatable = false;

	@OneToMany(mappedBy = "valuesSet", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@EqualsAndHashCode.Exclude
	private Set<Value> values = new HashSet<Value>();

	public void addValue(Value value) {
		this.values.add(value);
	}

	/**
	 * Возвращает имя и код набора значений.
	 * 
	 * Необходимо для отображения с целью визуальной идентификации набора значений,
	 * которому принадлежит или будет принадлежать текущее значение.
	 * 
	 * @return имя и код набора значений
	 */
	public String getNameAndCode() {
		return getName().concat(" [").concat(getCode()).concat("]");
	};
}
