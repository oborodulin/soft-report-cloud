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
import com.oborodulin.softreport.domain.common.entity.DetailEntity;
import com.oborodulin.softreport.domain.common.entity.TreeEntity;
import com.oborodulin.softreport.domain.model.dic.proglang.ProgLang;
import com.oborodulin.softreport.domain.model.dic.valuesset.value.Value;
import com.oborodulin.softreport.domain.model.project.Project;
import com.oborodulin.softreport.domain.model.software.businessobject.BusinessObject;
import com.oborodulin.softreport.domain.model.software.document.Document;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Класс реализует программное обеспечение.
 * 
 * Наследуемые ПО получают доступ к бизнес-объектам родительского ПО.
 * 
 * @author acs-i
 *
 */
@Data
@Entity
@Table(name = Software.TABLE_NAME)
public class Software extends TreeEntity<Software, String> {
	private static final long serialVersionUID = 3241164622564083658L;
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
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@EqualsAndHashCode.Exclude
	private List<Project> projects = new ArrayList<>();
	
	/** Бизнес-объекты */
	@OneToMany(mappedBy = DetailEntity.CLM_MASTER, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@EqualsAndHashCode.Exclude
	private Set<BusinessObject> businessObjects = new HashSet<BusinessObject>();

	@OneToMany(mappedBy = DetailEntity.CLM_MASTER, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@EqualsAndHashCode.Exclude
	private List<Document> documents = new ArrayList<>();
	
	/** Список языков программирования */
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@EqualsAndHashCode.Exclude
	private List<ProgLang> progLangs = new ArrayList<>();

	/**
	 * Добавляет к ПО документ
	 * 
	 * @param document документ
	 * @see com.oborodulin.softreport.domain.model.software.document.Document
	 */
	public void addDocument(Document document) {
		this.documents.add(document);
	}
	
}
