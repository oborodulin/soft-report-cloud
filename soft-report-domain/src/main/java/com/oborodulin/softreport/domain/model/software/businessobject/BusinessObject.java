package com.oborodulin.softreport.domain.model.software.businessobject;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import com.oborodulin.softreport.domain.common.entity.DetailEntity;
import com.oborodulin.softreport.domain.model.docobject.DocObject;
import com.oborodulin.softreport.domain.model.software.Software;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Entity
@Table(name = BusinessObject.TABLE_NAME)
@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
public class BusinessObject extends DetailEntity<Software, String> {
	private static final long serialVersionUID = 5906310013237888996L;

	/** Наименование таблицы данных доменного объекта (сущности) */
	protected static final String TABLE_NAME = "BUSINESS_OBJECTS";

	@NotBlank
	private String name;

	@Column(length = 1000)
	private String descr;

	/** Список объектов БД, связанных с текущим бизнес-объектом */
	@ManyToMany(mappedBy = "businessObjects", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	private List<DocObject> docObjects = new ArrayList<>();

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getCodeId() {
		return this.name;
	}
	
}
