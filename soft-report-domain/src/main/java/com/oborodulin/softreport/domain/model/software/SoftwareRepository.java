package com.oborodulin.softreport.domain.model.software;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.oborodulin.softreport.domain.model.CommonRepository;

@Repository
public interface SoftwareRepository extends CommonRepository<Software, String> {

	public List<Software> findByTypeCode(String typeCode);

	public List<Software> findByParentIsNull();

	public List<Software> findByIdIsNot(Long id);

}
