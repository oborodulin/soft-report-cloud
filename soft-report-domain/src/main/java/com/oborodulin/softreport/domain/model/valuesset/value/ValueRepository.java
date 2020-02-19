package com.oborodulin.softreport.domain.model.valuesset.value;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oborodulin.softreport.domain.model.valuesset.ValuesSet;

import org.springframework.data.domain.Sort;

public interface ValueRepository extends JpaRepository<Value, Long> {
	List<Value> findByValuesSet(ValuesSet valuesSet, Sort sort);
}