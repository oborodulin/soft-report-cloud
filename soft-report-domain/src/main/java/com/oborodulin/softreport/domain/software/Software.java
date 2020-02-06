package com.oborodulin.softreport.domain.software;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Entity
@RequiredArgsConstructor
@Table(name = "Softwares")
public class Software {
	
	public Software(Long id, String code, String name) {
		this.id = id;
		this.code = code;
		this.name = name;
	}
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@NotNull
	@Column(unique=true)
	private String code;
	@NotNull
	private String name;
	private String descr;

	private Date createdAt;
	private String createdBy;
	private Date modifiedAt;
	private String modifiedBy;
}
