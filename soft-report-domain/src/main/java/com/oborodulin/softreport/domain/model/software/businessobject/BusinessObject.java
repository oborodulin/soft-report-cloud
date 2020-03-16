package com.oborodulin.softreport.domain.model.software.businessobject;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import com.oborodulin.softreport.domain.common.entity.DetailEntity;
import com.oborodulin.softreport.domain.model.docobject.DocObject;
import com.oborodulin.softreport.domain.model.software.Software;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = BusinessObject.TABLE_NAME)
public class BusinessObject extends DetailEntity<Software, String> {
	private static final long serialVersionUID = 5906310013237888996L;
	protected static final String TABLE_NAME = "BUSINESS_OBJECTS";

	@NotBlank
	private String name;

	@Column(length = 1000)
	private String descr;

	/** Список объектов БД, связанных с текущим бизнес-объектом */
	@OneToMany(mappedBy = "businessObject", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@EqualsAndHashCode.Exclude
	private List<DocObject> docObjects = new ArrayList<>();

}
