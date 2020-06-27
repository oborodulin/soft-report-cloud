package com.oborodulin.softreport.domain.docmodel;

import java.util.HashSet;
import java.util.Set;

import lombok.Data;

@Data
public class DocumentModel implements CommonDocumentModel {

	private String header;

	private String title;

	private String version;

	private String footer;

	private Set<CommonDocModelObject> servers = new HashSet<>();

	public void addServer(CommonDocModelObject server) {
		servers.add(server);
	}

	public CommonDocModelObject getServer(String host, Integer port) {
		return servers.stream()
				.filter(server -> host.equals(server.getHost()) && (port == null || port.equals(server.getPort())))
				.findAny().orElse(null);
	}

}
