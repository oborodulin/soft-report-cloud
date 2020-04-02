package com.oborodulin.softreport.domain.model.dic.server;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import com.oborodulin.softreport.domain.common.repository.CommonRepository;
import com.oborodulin.softreport.domain.model.dic.valuesset.value.Value;

@Repository
public interface ServerRepository extends CommonRepository<Server, String> {

	public List<Server> findByType(Value type, Sort sort);

}
