package com.oborodulin.softreport.domain.service;

import java.util.List;

import com.oborodulin.softreport.domain.model.valuesset.ValuesSet;
import com.oborodulin.softreport.domain.model.valuesset.ValuesSetService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.PersistenceContext;
import javax.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.core.EntityInformation;

@Service("jpaValuesSetService")
@Transactional
public class ValuesSetServiceImpl implements ValuesSetService {
	@Autowired
	private EntityManager em;

	@Transactional(readOnly = true)
	@Override
	public List<ValuesSet> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ValuesSet findById(Long id) {
		return this.em.find(ValuesSet.class, id);
	}

	@Override
	@Transactional
	public ValuesSet save(ValuesSet valuesSet) {
		return null;
	}

	@Override
	public void delete(ValuesSet valuesSet) {
		// TODO Auto-generated method stub

	}

}
