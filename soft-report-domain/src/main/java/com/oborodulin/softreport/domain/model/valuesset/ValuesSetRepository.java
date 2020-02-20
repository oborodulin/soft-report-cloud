package com.oborodulin.softreport.domain.model.valuesset;

import org.springframework.stereotype.Repository;

import com.oborodulin.softreport.domain.model.CommonRepository;

@Repository
public interface ValuesSetRepository extends CommonRepository<ValuesSet, String> {

	ValuesSet findByCode(String code);
}
