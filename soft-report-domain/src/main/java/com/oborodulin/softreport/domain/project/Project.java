package com.oborodulin.softreport.domain.project;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.oborodulin.softreport.domain.project.task.Task;
import com.oborodulin.softreport.domain.software.Software;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Entity
@RequiredArgsConstructor
@Table(name = "Projects")
public class Project {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@NotBlank
	@Size(min = 5, message = "Наименование проекта должно состоять как минимум из 5 символов")
	private String name;
	private String descr;

	private Date createdAt;
	private String createdBy;
	private Date modifiedAt;
	private String modifiedBy;

	@OneToMany(mappedBy = "project")
	private List<Task> tasks = new ArrayList<>();

	@ManyToMany(mappedBy = "projects",fetch = FetchType.EAGER)
	//@ManyToMany(targetEntity = Software.class)
	@Size(min = 1, message = "Вы должны выбрать хотя бы одно ПО")
	private List<Software> softwares = new ArrayList<>();

	@PrePersist
	void createdAt() {
		this.createdAt = new Date();
	}

	public void addTask(Task task) {
		this.tasks.add(task);
	}

	public void addSoftware(Software software) {
		this.softwares.add(software);
	}
}
