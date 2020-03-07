package com.oborodulin.softreport.domain.model.databaseobject;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.oborodulin.softreport.domain.common.repository.CommonTreeRepository;

@Repository
public interface DataBaseObjectRepository extends CommonTreeRepository<DataBaseObject, String> {

	public List<DataBaseObject> findByTypeCode(String typeCode);

}
