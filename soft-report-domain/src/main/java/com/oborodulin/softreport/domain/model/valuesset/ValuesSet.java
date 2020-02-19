package com.oborodulin.softreport.domain.model.valuesset;

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

import org.hibernate.annotations.SelectBeforeUpdate;

import com.oborodulin.softreport.domain.model.AuditableEntity;
import com.oborodulin.softreport.domain.model.software.Software;
import com.oborodulin.softreport.domain.model.valuesset.value.Value;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "ValsSets")
public class ValuesSet extends AuditableEntity<Software> {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(updatable = false)
	protected Long id;
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
