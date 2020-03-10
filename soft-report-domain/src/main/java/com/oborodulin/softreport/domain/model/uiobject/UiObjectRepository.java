package com.oborodulin.softreport.domain.model.uiobject;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.oborodulin.softreport.domain.common.repository.CommonTreeRepository;

@Repository
public interface UiObjectRepository extends CommonTreeRepository<UiObject, String> {

	public List<UiObject> findByTypeCode(String typeCode);

}
