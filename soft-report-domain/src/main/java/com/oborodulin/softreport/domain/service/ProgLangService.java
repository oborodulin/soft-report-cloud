package com.oborodulin.softreport.domain.service;

import java.util.List;

import com.oborodulin.softreport.domain.common.service.CommonJpaService;
import com.oborodulin.softreport.domain.model.dic.proglang.ProgLang;
import com.oborodulin.softreport.domain.model.dic.valuesset.value.Value;

public interface ProgLangService extends CommonJpaService<ProgLang, String> {

	public List<Value> getLangs();

	public List<Value> getArchs();

	public List<Value> getDbTypes();

}
