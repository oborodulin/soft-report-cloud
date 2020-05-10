package com.oborodulin.softreport.domain.docmodel;

import java.util.Set;
import java.util.HashSet;
import lombok.Builder;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DocModelObject implements CommonDocModelObject {

	@Builder.Default
	private Integer pos = 0;

	private String categ;

	private String type;

	private String name;

	private String descr;

	private String host;

	private Integer port;

	private String sqlQuery;

	@Builder.Default
	Set<CommonDocModelObject> components = new HashSet<>();

	@Builder.Default
	Set<String> businessObjects = new HashSet<>();

	private Boolean isRequired;

	private Boolean isUniqueKey;

	private Boolean isCompositeKey;

	private String defaultValue;

	private String foreignKey;

	public void addComponent(CommonDocModelObject component) {
		components.add(component);
	}

	public CommonDocModelObject getComponent(String name) {
		return this.components.stream().filter(component -> name.equals(component.getName())).findAny().orElse(null);
	}

	public void addBusinessObject(String name) {
		businessObjects.add(name);
	}

	public String getBusinessObject(String name) {
		return businessObjects.stream().filter(businessObject -> name.equals(businessObject)).findAny().orElse(null);
	}

	private CommonDocModelObject findComponentByCategAndType(Set<CommonDocModelObject> components, String categ,
			String type) {
		CommonDocModelObject docModelObject = null;
		if (!components.isEmpty()) {
			docModelObject = components.stream().filter(component -> categ.equals(component.getCateg())
					&& (type == null || type.equals(component.getType()))).findAny().orElse(null);
			if (docModelObject == null) {
				for (CommonDocModelObject component : components) {
					docModelObject = this.findComponentByCategAndType(component.getComponents(), categ, type);
					if (docModelObject != null) {
						break;
					}
				}
			}
		}
		return docModelObject;
	}

	public boolean isComponentPresent(String categ, String type, boolean byAllTree) {
		CommonDocModelObject docModelObject = null;
		if (byAllTree) {
			docModelObject = this.findComponentByCategAndType(this.components, categ, type);
		} else {
			docModelObject = this.components.stream().filter(component -> categ.equals(component.getCateg())
					&& (type == null || type.equals(component.getType()))).findAny().orElse(null);
		}
		return docModelObject != null;
	}

	public boolean isComponentPresent(String categ, boolean byAllTree) {
		return this.isComponentPresent(categ, null, byAllTree);
	}

	public boolean isComponentPresent(String categ) {
		return this.isComponentPresent(categ, null, false);
	}
}
