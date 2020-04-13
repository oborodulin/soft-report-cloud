package com.oborodulin.softreport.domain.model.dic.doctype;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.oborodulin.softreport.domain.common.repository.CommonTreeRepository;

@Repository
public interface DocTypeRepository extends CommonTreeRepository<DocType, String> {

	public List<DocType> findAllByOrderByCategCode();

	public List<DocType> findByCategCode(String categCode);

	public List<DocType> findByTypeCode(String typeCode);

}
