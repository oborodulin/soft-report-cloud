package com.oborodulin.softreport.domain.model.project.task;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.oborodulin.softreport.domain.model.AuditableEntity;
import com.oborodulin.softreport.domain.model.project.Project;
import com.oborodulin.softreport.domain.model.software.Software;

import lombok.Data;

@Data
@Entity
@Table(name = "Tasks")
public class Task extends AuditableEntity<Software> {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(updatable = false)
	protected Long id;
	@ManyToOne
	@JoinColumn(name = "projects_id", nullable = false)
	private Project project;

	@NotBlank(message = "Name is required")
	private String name;
	private String descr;

	@NotNull
	private Status status;
	@NotNull
	private Priority priority;
	private Date startDate;

	public static enum Status {
		NEW, IN_PROGRESS, CLOSED
	}

	public static enum Priority {
		LOW, NORMAL, HIGH, URGENT, IMMEDIATE
	}
}
