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
public abstract class DetailTreeEntity<E extends AuditableEntity<U>, T extends TreeEntity<T, U>, U>
		extends TreeEntity<T, U> implements Detailable<E, U>, Treelike<T, U>, Serializable {

	private static final long serialVersionUID = 630272255210589996L;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "master_id", nullable = false)
	@ToString.Exclude
	private E master;

}
