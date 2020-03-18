package com.oborodulin.softreport.domain.model.software.configbundle.configparameter;

import org.springframework.stereotype.Repository;

import com.oborodulin.softreport.domain.common.repository.CommonDetailRepository;
import com.oborodulin.softreport.domain.model.software.configbundle.ConfigBundle;

@Repository
public interface ConfigParameterRepository extends CommonDetailRepository<ConfigBundle, ConfigParameter, String> {
}