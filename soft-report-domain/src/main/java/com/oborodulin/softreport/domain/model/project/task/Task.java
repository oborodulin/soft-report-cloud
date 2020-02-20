package com.oborodulin.softreport.domain.model.project.task;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.oborodulin.softreport.domain.model.AuditableEntity;
import com.oborodulin.softreport.domain.model.project.Project;
import lombok.Data;

@Data
@Entity
@Table(name = Task.TABLE_NAME)
public class Task extends AuditableEntity<String> {
	private static final long serialVersionUID = 5701376057566461892L;
	public static final String TABLE_NAME= "TASKS";

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
