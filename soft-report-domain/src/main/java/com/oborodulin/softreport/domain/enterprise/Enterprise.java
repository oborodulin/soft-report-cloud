package com.oborodulin.softreport.domain.enterprise;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Entity
@RequiredArgsConstructor
@Table(name = "Enterprises")
public class Enterprise {

	public Enterprise(Long id, String code, String name) {
		this.id = id;
		this.code = code;
		this.name = name;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@NotBlank
	@Column(unique = true)
	private String code;
	@NotBlank
	private String name;
	private String descr;

	@ManyToOne
	@JoinColumn(name = "parent_id")
	private Enterprise parent;

	@OneToMany(mappedBy = "parent", cascade = CascadeType.REMOVE, orphanRemoval = true)
	private List<Enterprise> enterprises = new ArrayList<Enterprise>();

	private Date createdAt;
	private String createdBy;
	private Date modifiedAt;
	private String modifiedBy;
}
