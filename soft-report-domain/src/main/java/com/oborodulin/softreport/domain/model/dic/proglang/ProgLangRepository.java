package com.oborodulin.softreport.domain.model.dic.proglang;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.oborodulin.softreport.domain.common.repository.CommonRepository;

@Repository
public interface ProgLangRepository extends CommonRepository<ProgLang, String> {

	public List<ProgLang> findByArch_CodeOrderByArch_ValAsc(String archCode);

}
