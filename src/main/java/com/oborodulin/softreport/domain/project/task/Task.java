package com.oborodulin.softreport.domain.project.task;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
@Entity
public class Task {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private final Long id;
	@NotBlank(message = "Name is required")
	private final String name;
	private final String descr;

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
