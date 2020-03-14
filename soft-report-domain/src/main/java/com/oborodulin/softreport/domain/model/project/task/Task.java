package com.oborodulin.softreport.domain.model.project.task;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import com.oborodulin.softreport.domain.common.entity.DetailEntity;
import com.oborodulin.softreport.domain.model.project.Project;
import lombok.Data;

@Data
@Entity
@Table(name = Task.TABLE_NAME)
public class Task extends DetailEntity<Project, String> {
	private static final long serialVersionUID = 5701376057566461892L;
	protected static final String TABLE_NAME = "TASKS";

	@NotBlank(message = "Name is required")
	private String name;

	private String descr;

	@NotNull
	private Status status;

	@NotNull
	private Priority priority;

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd.MM.yyyy")
	private Date startDate;

	public static enum Status {
		NEW, IN_PROGRESS, CLOSED
	}

	public static enum Priority {
		LOW, NORMAL, HIGH, URGENT, IMMEDIATE
	}
}
