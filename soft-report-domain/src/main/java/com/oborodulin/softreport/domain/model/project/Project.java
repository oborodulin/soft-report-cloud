package com.oborodulin.softreport.domain.model.project;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.oborodulin.softreport.domain.common.entity.DetailEntity;
import com.oborodulin.softreport.domain.common.entity.TreeEntity;
import com.oborodulin.softreport.domain.model.project.document.Document;
import com.oborodulin.softreport.domain.model.project.task.Task;
import com.oborodulin.softreport.domain.model.software.Software;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

/**
 * Класс описывает проект.
 * 
 * @author Oleg Borodulin
 */
@Slf4j
@Data
@Entity
@Table(name = Project.TABLE_NAME)
public class Project extends TreeEntity<Project, String> {
	private static final long serialVersionUID = -3514627948973849043L;

	/** Наименование таблицы данных доменного объекта (сущности) */
	protected static final String TABLE_NAME = "PROJECTS";

	/** Наименование проекта */
	@NotBlank
	@Size(min = 5, message = "Наименование проекта должно состоять как минимум из 5 символов")
	private String name;

	/** Описание проекта */
	private String descr;

	/** Задачи */
	@OneToMany(mappedBy = DetailEntity.CLM_MASTER, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	private List<Task> tasks = new ArrayList<>();

	/** Документы */
	@OneToMany(mappedBy = DetailEntity.CLM_MASTER, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	private List<Document> documents = new ArrayList<>();

	/** ПО */
	// @ManyToMany(mappedBy = "projects", fetch = FetchType.EAGER)
	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "PROJECTS_SOFTWARES", joinColumns = @JoinColumn(name = "PROJECTS_ID"), inverseJoinColumns = @JoinColumn(name = "SOFTWARES_ID"))
	// @ManyToMany(targetEntity = Software.class)
	// @Size(min = 1, message = "Вы должны выбрать хотя бы одно ПО")
	private List<Software> softwares = new ArrayList<>();

	/**
	 * Добавляет к проекту задачу
	 * 
	 * @param task задача
	 * @see com.oborodulin.softreport.domain.model.project.task.Task
	 */
	public void addTask(Task task) {
		this.tasks.add(task);
		task.setMaster(this);
	}

	/**
	 * Добавляет к проекту документ
	 * 
	 * @param document документ
	 * @see com.oborodulin.softreport.domain.model.project.document.Document
	 */
	public void addDocument(Document document) {
		this.documents.add(document);
		document.setMaster(this);
	}

	/**
	 * Добавляет к проекту ПО
	 * 
	 * @param software ПО
	 * @see com.oborodulin.softreport.domain.model.software.Software
	 */
	public void addSoftware(Software software) {
		this.softwares.add(software);
		software.getProjects().add(this);
		log.info("Project: add software " + software.getCodeId());
	}

	/**
	 * Удаляет заданное ПО из проекта
	 * 
	 * @param software ПО
	 * @see com.oborodulin.softreport.domain.model.software.Software
	 */
	public void removeSoftware(Software software) {
		this.softwares.remove(software);
		software.getProjects().remove(this);
		log.info("Project: remove software " + software.getCodeId());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getCodeId() {
		return this.name;
	}

}
