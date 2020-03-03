package com.oborodulin.softreport.domain.model.dic.doctype;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.oborodulin.softreport.domain.common.repository.CommonRepository;

@Repository
public interface DocTypeRepository extends CommonRepository<DocType, String> {

	public List<DocType> findByCategCode(String categCode);
	public List<DocType> findByTypeCode(String typeCode);

}
