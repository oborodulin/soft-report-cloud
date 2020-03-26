package com.oborodulin.softreport.domain.model.dic.objhierarchy;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.oborodulin.softreport.domain.common.repository.CommonTreeRepository;

@Repository
public interface ObjHierarchyRepository extends CommonTreeRepository<ObjHierarchy, String> {

	public List<ObjHierarchy> findByType_Attr2(String containerMark);

	public List<ObjHierarchy> findByType_Attr1AndType_Attr2(String archCode, String containerMark);

}
