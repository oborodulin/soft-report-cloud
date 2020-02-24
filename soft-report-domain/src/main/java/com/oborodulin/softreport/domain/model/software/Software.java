package com.oborodulin.softreport.domain.model.software;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import com.oborodulin.softreport.domain.model.AuditableEntity;
import com.oborodulin.softreport.domain.model.project.Project;
import com.oborodulin.softreport.domain.model.valuesset.value.Value;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = Software.TABLE_NAME)
public class Software extends AuditableEntity<String> {
	private static final long serialVersionUID = 3241164622564083658L;
	public static final String TABLE_NAME = "SOFTWARES";

	@NotBlank
	@Column(unique = true)
	private String code;
	
	@NotBlank
	private String name;
	
	private String descr;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "type_code", nullable = false)
	private Value type;

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "parent_id")
	private Software parent;

	@OneToMany(mappedBy = "parent", cascade = CascadeType.REMOVE, orphanRemoval = true)
	@EqualsAndHashCode.Exclude
	private List<Software> softwares = new ArrayList<Software>();

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@EqualsAndHashCode.Exclude
	private List<Project> projects = new ArrayList<>();
}
