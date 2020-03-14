package com.oborodulin.softreport.domain.model.dbobject;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.oborodulin.softreport.domain.common.repository.CommonTreeRepository;

@Repository
public interface DbObjectRepository extends CommonTreeRepository<DbObject, String> {

	public List<DbObject> findByTypeCode(String typeCode);

}
