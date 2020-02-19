package com.oborodulin.softreport.domain.model.software;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import com.oborodulin.softreport.domain.model.AuditableEntity;
import com.oborodulin.softreport.domain.model.project.Project;

import lombok.Data;

@Data
@Entity
@Table(name = "Softwares")
public class Software extends AuditableEntity<Software> {
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

	@ManyToOne
	@JoinColumn(name = "parent_id")
	private Software parent;

	@OneToMany(mappedBy = "parent", cascade = CascadeType.REMOVE, orphanRemoval = true)
	private List<Software> softwares = new ArrayList<Software>();

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Project> projects = new ArrayList<>();
}
