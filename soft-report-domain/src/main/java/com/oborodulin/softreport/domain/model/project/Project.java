package com.oborodulin.softreport.domain.model.project;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.oborodulin.softreport.domain.common.entity.AuditableEntity;
import com.oborodulin.softreport.domain.model.project.task.Task;
import com.oborodulin.softreport.domain.model.software.Software;

import lombok.Data;

@Data
@Entity
@Table(name = Project.TABLE_NAME)
public class Project extends AuditableEntity<String> {
	private static final long serialVersionUID = -3514627948973849043L;
	public static final String TABLE_NAME= "PROJECTS";

	@NotBlank
	@Size(min = 5, message = "Наименование проекта должно состоять как минимум из 5 символов")
	private String name;
	private String descr;

	@OneToMany(mappedBy = "project")
	private List<Task> tasks = new ArrayList<>();

	@ManyToMany(mappedBy = "projects", fetch = FetchType.EAGER)
	// @ManyToMany(targetEntity = Software.class)
	@Size(min = 1, message = "Вы должны выбрать хотя бы одно ПО")
	private List<Software> softwares = new ArrayList<>();

	public void addTask(Task task) {
		this.tasks.add(task);
	}

	public void addSoftware(Software software) {
		this.softwares.add(software);
	}
}
