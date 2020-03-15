package com.oborodulin.softreport.domain.model.docobject;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.oborodulin.softreport.domain.common.repository.CommonTreeRepository;

@Repository
public interface DocObjectRepository extends CommonTreeRepository<DocObject, String> {

	public List<DocObject> findByTypeCode(String typeCode);

}
