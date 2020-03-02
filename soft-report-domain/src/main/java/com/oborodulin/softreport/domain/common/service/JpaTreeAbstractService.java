package com.oborodulin.softreport.domain.common.service;

import java.util.List;
import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

import com.oborodulin.softreport.domain.common.entity.TreeEntity;
import com.oborodulin.softreport.domain.common.repository.CommonTreeRepository;

import org.springframework.beans.factory.annotation.Autowired;

public abstract class JpaTreeAbstractService<E extends TreeEntity<E, U>, R extends CommonTreeRepository<E, U>, U>
		extends JpaAbstractService<E, R, U> implements CommonJpaTreeService<E, U> {
	private Class<E> clazz;

	@Autowired
	public JpaTreeAbstractService(R repository, Class<E> clazz) {
		super(repository);
		this.clazz = clazz;
	}

	@Override
	public List<E> findByParentIsNull() {
		return this.repository.findByParentIsNull();
	};

	@Override
	public List<E> findByIdIsNot(Long id) {
		return this.repository.findByIdIsNot(id);
	};

	@Override
	@Transactional
	public E createChild(Long parentId) {
		E entity = null;
		try {
			entity = this.clazz.getDeclaredConstructor().newInstance();
			entity.setParent(this.repository.findById(parentId)
					.orElseThrow(() -> new IllegalArgumentException("Invalid parent Id:" + parentId)));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return entity;
	}

	@Override
	@Transactional
	public Optional<E> saveChild(Long parentId, E entity) {
		entity.setParent(this.repository.findById(parentId)
				.orElseThrow(() -> new IllegalArgumentException("Invalid software parent Id:" + parentId)));
		return Optional.of(this.repository.save(entity));
	};

}