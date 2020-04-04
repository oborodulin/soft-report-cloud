package com.oborodulin.softreport.domain.common.entity;

public interface Detailable<E extends AuditableEntity<U>, U> {

	public static final String CLM_MASTER = "master";

	public E getMaster();

	public void setMaster(E master);

}
