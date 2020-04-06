package com.oborodulin.softreport.domain.model.docobject;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import com.oborodulin.softreport.domain.common.repository.CommonTreeRepository;
import com.oborodulin.softreport.domain.model.dic.valuesset.value.Value;

@Repository
public interface DocObjectRepository extends CommonTreeRepository<DocObject, String> {

	public List<DocObject> findByType(Value type, Sort sort);

	public List<DocObject> findByTypeCode(String typeCode);

	public List<DocObject> findByParentIdAndType(Long parentId, Value type, Sort sort);

	public DocObject findFirstByTypeAndParentIsNullOrderByPosDesc(Value type);

	public DocObject findFirstByTypeAndParentIdOrderByPosDesc(Value type, Long parentId);

	public List<DocObject> findByPosGreaterThanEqual(Integer pos);

}
