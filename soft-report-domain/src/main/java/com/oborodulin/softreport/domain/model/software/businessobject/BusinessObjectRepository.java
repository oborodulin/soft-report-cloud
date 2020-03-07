package com.oborodulin.softreport.domain.model.software.businessobject;

import org.springframework.stereotype.Repository;

import com.oborodulin.softreport.domain.common.repository.CommonDetailRepository;
import com.oborodulin.softreport.domain.model.software.Software;

@Repository
public interface BusinessObjectRepository extends CommonDetailRepository<Software, BusinessObject, String> {
}