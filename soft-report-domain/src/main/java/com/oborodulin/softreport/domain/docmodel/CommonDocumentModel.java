package com.oborodulin.softreport.domain.docmodel;

import java.util.Set;

public interface CommonDocumentModel {

	public void setHeader(String header);

	public void setTitle(String title);

	public void setVersion(String version);

	public void setFooter(String footer);

	public Set<CommonDocModelObject> getServers();
	
}
