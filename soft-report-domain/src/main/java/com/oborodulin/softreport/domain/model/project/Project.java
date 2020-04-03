package com.oborodulin.softreport.domain.model.project;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import com.oborodulin.softreport.domain.common.entity.DetailEntity;
import com.oborodulin.softreport.domain.common.entity.TreeEntity;
import com.oborodulin.softreport.domain.model.project.task.Task;
import com.oborodulin.softreport.domain.model.software.Software;
import com.oborodulin.softreport.domain.model.software.document.Document;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = Project.TABLE_NAME)
public class Project extends TreeEntity<Project, String> {
	private static final long serialVersionUID = -3514627948973849043L;

	/** Наименование таблицы данных доменного объекта (сущности) */
	protected static final String TABLE_NAME = "PROJECTS";

	@NotBlank
	@Size(min = 5, message = "Наименование проекта должно состоять как минимум из 5 символов")
	private String name;

	private String descr;

	@OneToMany(mappedBy = DetailEntity.CLM_MASTER, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@EqualsAndHashCode.Exclude
	private List<Task> tasks = new ArrayList<>();

	@ManyToMany(mappedBy = "projects", fetch = FetchType.EAGER)
	// @ManyToMany(targetEntity = Software.class)
	@Size(min = 1, message = "Вы должны выбрать хотя бы одно ПО")
	private List<Software> softwares = new ArrayList<>();

	/**
	 * Добавляет к проекту задачу
	 * 
	 * @param task задача
	 * @see com.oborodulin.softreport.domain.model.project.task.Task
	 */
	public void addTask(Task task) {
		this.tasks.add(task);
	}

	/**
	 * Добавляет к проекту ПО
	 * 
	 * @param software ПО
	 * @see com.oborodulin.softreport.domain.model.software.Software
	 */
	public void addSoftware(Software software) {
		this.softwares.add(software);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getCodeId() {
		return this.name;
	}
	
}
