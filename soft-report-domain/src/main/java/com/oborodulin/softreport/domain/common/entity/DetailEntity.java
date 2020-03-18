package com.oborodulin.softreport.domain.common.entity;

import java.io.Serializable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import lombok.Data;
import lombok.ToString;

@Data
@MappedSuperclass
public abstract class DetailEntity<E extends AuditableEntity<U>, U> extends AuditableEntity<U> implements Serializable {

	private static final long serialVersionUID = 912543733349823018L;

	public static final String CLM_MASTER = "master";

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "master_id", nullable = false)
	@ToString.Exclude
	private E master;
	
}
