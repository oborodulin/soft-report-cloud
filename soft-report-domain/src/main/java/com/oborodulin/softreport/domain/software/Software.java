package com.oborodulin.softreport.domain.software;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
@Entity
@Table(name = "Softwares")
public class Software {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@NotNull
	private String name;
	private String descr;
	private Date createdAt;
	private String createdBy;
	private Date modifiedAt;
	private String modifiedBy;
}
