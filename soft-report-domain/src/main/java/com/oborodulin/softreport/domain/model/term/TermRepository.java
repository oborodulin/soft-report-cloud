package com.oborodulin.softreport.domain.model.term;

import org.springframework.stereotype.Repository;

import com.oborodulin.softreport.domain.common.repository.CommonRepository;

@Repository
public interface TermRepository extends CommonRepository<Term, String> {
}
