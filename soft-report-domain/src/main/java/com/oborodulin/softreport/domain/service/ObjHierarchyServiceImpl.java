package com.oborodulin.softreport.domain.service;

import com.oborodulin.softreport.domain.common.service.JpaTreeAbstractService;
import com.oborodulin.softreport.domain.model.dic.objhierarchy.ObjHierarchy;
import com.oborodulin.softreport.domain.model.dic.objhierarchy.ObjHierarchyRepository;
import com.oborodulin.softreport.domain.model.dic.valuesset.value.Value;
import java.util.ArrayList;
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
	// @Autowired
	// private ValueRepository valueRepository;

	@Autowired
	public ObjHierarchyServiceImpl(ObjHierarchyRepository repository) {
		super(repository, ObjHierarchy.class);
	}

	@Override
	public List<ObjHierarchy> findAllСontainerObjects() {
		// return
		// this.valueRepository.findByMaster_CodeAndAttr2OrderByCodeAsc(ValuesSet.VS_DOC_OBJ_TYPES,
		// Value.AV_YES);
		return this.repository.findByType_Attr2(Value.AV_YES);
	}

	@Override
	public List<ObjHierarchy> findAllParentСontainerObjects(ObjHierarchy childObj) {
		// return
		// valuesSetService.getParentObjHierarchyСontainerTypes(childObj.getParent().getArch().getAttr1());

		// return
		// this.valueRepository.findByMaster_CodeAndAttr1AndAttr2OrderByCodeAsc(ValuesSet.VS_DOC_OBJ_TYPES,
		// childObj.getParent().getArch().getAttr1(), Value.AV_YES);
		return this.repository.findByType_Attr1AndType_Attr2(childObj.getParent().getArch().getCode(), Value.AV_YES);
	};

	@Override
	public List<Value> getParentArchs(ObjHierarchy childObj) {
		List<Value> parentArchs = new ArrayList<>();
		if (childObj.getParent() != null) {
			parentArchs.add(childObj.getParent().getArch());
		}
		return parentArchs;
	};

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

	@Override
	public List<Value> getСontainerTypes() {
		return valuesSetService.getDocObjСontainerTypes();
	};

	@Override
	public List<Value> getComponentTypes() {
		return valuesSetService.getDocObjComponentTypes();
	};

}
