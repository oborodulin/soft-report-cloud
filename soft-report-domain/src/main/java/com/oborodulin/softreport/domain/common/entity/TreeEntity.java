package com.oborodulin.softreport.domain.common.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@MappedSuperclass
public abstract class TreeEntity<E extends AuditableEntity<U>, U> extends AuditableEntity<U> implements Serializable {

	private static final long serialVersionUID = 1765221099472416762L;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "parent_id")
	@ToString.Exclude
	private E parent;

	@OneToMany(mappedBy = "parent", cascade = CascadeType.REMOVE, orphanRemoval = true)
	@EqualsAndHashCode.Exclude
	private List<E> children = new ArrayList<E>();
}
