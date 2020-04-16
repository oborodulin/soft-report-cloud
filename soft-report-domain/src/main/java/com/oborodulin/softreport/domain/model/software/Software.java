package com.oborodulin.softreport.domain.model.software;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import com.oborodulin.softreport.domain.common.entity.DetailEntity;
import com.oborodulin.softreport.domain.common.entity.TreeEntity;
import com.oborodulin.softreport.domain.model.dic.proglang.ProgLang;
import com.oborodulin.softreport.domain.model.dic.valuesset.value.Value;
import com.oborodulin.softreport.domain.model.project.Project;
import com.oborodulin.softreport.domain.model.software.businessobject.BusinessObject;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Класс реализует программное обеспечение.
 * 
 * Наследуемые ПО получают доступ к бизнес-объектам родительского ПО.
 * 
 * @author Oleg Borodulin
 *
 */
@Data
@Entity
@Table(name = Software.TABLE_NAME)
@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
public class Software extends TreeEntity<Software, String> {
	private static final long serialVersionUID = 3241164622564083658L;

	/** Наименование таблицы данных доменного объекта (сущности) */
	protected static final String TABLE_NAME = "SOFTWARES";

	/** Уникальный код */
	@NotBlank
	@Column(unique = true)
	private String code;

	/** Наименование */
	@NotBlank
	private String name;

	/** Описание */
	private String descr;

	/** Признак сторонней разработки (подрядчиком) */
	@NotNull
	@Column(columnDefinition = "boolean not null default false")
	private Boolean isContractor = false;

	/** Тип ПО */
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "type_code", nullable = false)
	@ToString.Exclude
	private Value type;

	/** Проекты */
	@ManyToMany(mappedBy = "softwares")
	// @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	private List<Project> projects = new ArrayList<>();

	/** Бизнес-объекты */
	@OneToMany(mappedBy = DetailEntity.CLM_MASTER, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	private Set<BusinessObject> businessObjects = new HashSet<BusinessObject>();

	/** Список языков программирования */
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	private List<ProgLang> progLangs = new ArrayList<>();

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getCodeId() {
		return this.code;
	}

}
