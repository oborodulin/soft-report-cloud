package com.oborodulin.softreport.domain.docmodel;

import java.util.Set;

public interface CommonDocModelObject {

	public Integer getPos();

	public void setPos(Integer pos);

	public String getCateg();

	public void setCateg(String categ);

	public String getType();

	public void setType(String type);

	public String getName();

	public void setName(String name);

	public String getDescr();

	public void setDescr(String descr);

	public String getHost();

	public Integer getPort();

	public void addComponent(CommonDocModelObject component);

	public CommonDocModelObject getComponent(String name);

	public Set<CommonDocModelObject> getComponents();

	public boolean isComponentPresent(String categ, boolean byAllTree);

	public boolean isComponentPresent(String categ, String type, boolean byAllTree);

}
