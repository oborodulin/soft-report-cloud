package com.oborodulin.softreport.domain.service;

import com.oborodulin.softreport.domain.common.service.JpaTreeAbstractService;
import com.oborodulin.softreport.domain.model.dic.objhierarchy.ObjHierarchy;
import com.oborodulin.softreport.domain.model.dic.objhierarchy.ObjHierarchyRepository;
import com.oborodulin.softreport.domain.model.dic.valuesset.value.Value;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

@Service("jpaObjHierarchyService")
@Transactional
public class ObjHierarchyServiceImpl extends JpaTreeAbstractService<ObjHierarchy, ObjHierarchyRepository, String>
		implements ObjHierarchyService {
	@Autowired
	private ValuesSetService valuesSetService;

	@Autowired
	public ObjHierarchyServiceImpl(ObjHierarchyRepository repository) {
		super(repository, ObjHierarchy.class);
	}

	@Override
	public ObjHierarchy createChild(Long parentId) {
		ObjHierarchy objHierarchy = super.createChild(parentId);
		objHierarchy.setArch(objHierarchy.getParent().getArch());
		return objHierarchy;
	};

	@Override
	public List<Value> getArchs() {
		return valuesSetService.getSoftwareArchs();
	};

	@Override
	public List<Value> getTypes() {
		return valuesSetService.getDocObjTypes();
	};
}
