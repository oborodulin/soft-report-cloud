package com.oborodulin.softreport.domain.model.software.configbundle;

import org.springframework.stereotype.Repository;

import com.oborodulin.softreport.domain.common.repository.CommonDetailRepository;
import com.oborodulin.softreport.domain.model.software.Software;

@Repository
public interface ConfigBundleRepository extends CommonDetailRepository<Software, ConfigBundle, String> {
}