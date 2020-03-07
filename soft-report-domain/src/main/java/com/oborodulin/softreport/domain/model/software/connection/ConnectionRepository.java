package com.oborodulin.softreport.domain.model.software.connection;

import org.springframework.stereotype.Repository;

import com.oborodulin.softreport.domain.common.repository.CommonDetailRepository;
import com.oborodulin.softreport.domain.model.software.Software;

@Repository
public interface ConnectionRepository extends CommonDetailRepository<Software, Connection, String> {
}