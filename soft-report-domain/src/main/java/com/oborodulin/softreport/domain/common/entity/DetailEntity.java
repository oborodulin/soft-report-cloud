package com.oborodulin.softreport.domain.common.entity;

import java.io.Serializable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@MappedSuperclass
public abstract class DetailEntity<E extends AuditableEntity<U>, U> extends AuditableEntity<U> implements Serializable {
	
	private static final long serialVersionUID = 8924802008694600105L;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "master_id", nullable = false)
	@ToString.Exclude
	private E master;
}
