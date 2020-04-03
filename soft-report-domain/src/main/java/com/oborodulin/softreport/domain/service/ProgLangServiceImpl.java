package com.oborodulin.softreport.domain.service;

import com.oborodulin.softreport.domain.common.service.JpaAbstractService;
import com.oborodulin.softreport.domain.model.dic.proglang.ProgLang;
import com.oborodulin.softreport.domain.model.dic.proglang.ProgLangRepository;
import com.oborodulin.softreport.domain.model.dic.valuesset.value.Value;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

@Service("jpaProgLangService")
@Transactional
public class ProgLangServiceImpl extends JpaAbstractService<ProgLang, ProgLangRepository, String>
		implements ProgLangService {
	@Autowired
	private ValuesSetService valuesSetService;

	@Autowired
	public ProgLangServiceImpl(ProgLangRepository repository) {
		super(repository, ProgLang.class);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Value> getLangs() {
		return valuesSetService.getProgramLangs();
	};

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Value> getArchs() {
		return valuesSetService.getSoftwareArchs();
	};

}
