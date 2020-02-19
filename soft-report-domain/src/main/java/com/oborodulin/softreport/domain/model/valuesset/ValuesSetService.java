package com.oborodulin.softreport.domain.model.valuesset;

import java.util.List;

public interface ValuesSetService {
	List<ValuesSet> findAll();

	ValuesSet findById(Long id);

	ValuesSet save(ValuesSet singer);

	void delete(ValuesSet singer);
}
