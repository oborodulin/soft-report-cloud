package com.oborodulin.softreport.domain.model.valuesset;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.oborodulin.softreport.domain.common.repository.CommonRepository;

@Repository
public interface ValuesSetRepository extends CommonRepository<ValuesSet, String> {

	Optional<ValuesSet> findByCode(String code);
}
