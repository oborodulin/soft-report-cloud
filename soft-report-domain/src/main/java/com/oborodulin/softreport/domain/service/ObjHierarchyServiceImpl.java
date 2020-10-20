package com.oborodulin.softreport.domain.service;

import com.oborodulin.softreport.domain.common.service.AbstractJpaTreeService;
import com.oborodulin.softreport.domain.model.dic.objhierarchy.ObjHierarchy;
import com.oborodulin.softreport.domain.model.dic.objhierarchy.ObjHierarchyRepository;
import com.oborodulin.softreport.domain.model.dic.valuesset.value.AttrValueYes;
import com.oborodulin.softreport.domain.model.dic.valuesset.value.Value;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

@Service("jpaObjHierarchyService")
@Transactional
public class ObjHierarchyServiceImpl extends AbstractJpaTreeService<ObjHierarchy, ObjHierarchyRepository, String>
		implements ObjHierarchyService {
	@Autowired
	private ValuesSetService valuesSetService;
	// @Autowired
	// private ValueRepository valueRepository;

	@Autowired
	public ObjHierarchyServiceImpl(ObjHierarchyRepository repository) {
		super(repository, ObjHierarchy.class);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<ObjHierarchy> findAllСontainerObjects() {
		// return
		// this.valueRepository.findByMaster_CodeAndAttr2OrderByCodeAsc(ValuesSet.VS_DOC_OBJ_TYPES,
		// Value.AV_YES);
		return this.repository.findByType_Attr2(new AttrValueYes().toString());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<ObjHierarchy> findAllParentСontainerObjects(ObjHierarchy childObj) {
		// return
		// valuesSetService.getParentObjHierarchyСontainerTypes(childObj.getParent().getArch().getAttr1());

		// return
		// this.valueRepository.findByMaster_CodeAndAttr1AndAttr2OrderByCodeAsc(ValuesSet.VS_DOC_OBJ_TYPES,
		// childObj.getParent().getArch().getAttr1(), Value.AV_YES);
		return this.repository.findByType_Attr1AndType_Attr2(childObj.getParent().getArch().getCodeId(), new AttrValueYes().toString());
	};

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Value> getParentArchs(ObjHierarchy childObj) {
		List<Value> parentArchs = new ArrayList<>();
		if (childObj.getParent() != null) {
			parentArchs.add(childObj.getParent().getArch());
		}
		return parentArchs;
	};

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ObjHierarchy create(Long parentId) {
		ObjHierarchy objHierarchy = super.create(parentId);
		objHierarchy.setArch(objHierarchy.getParent().getArch());
		return objHierarchy;
	};

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Value> getArchs() {
		return valuesSetService.getSoftwareArchs();
	};

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Value> getTypes() {
		return valuesSetService.getDocObjTypes();
	};

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Value> getСontainerTypes() {
		return valuesSetService.getDocObjСontainerTypes();
	};

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Value> getComponentTypes() {
		return valuesSetService.getDocObjComponentTypes();
	};

}
