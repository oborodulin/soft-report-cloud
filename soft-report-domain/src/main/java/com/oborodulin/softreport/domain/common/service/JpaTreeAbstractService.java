package com.oborodulin.softreport.domain.common.service;

import java.util.List;
import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

import com.oborodulin.softreport.domain.common.entity.TreeEntity;
import com.oborodulin.softreport.domain.common.repository.CommonTreeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;

public abstract class JpaTreeAbstractService<E extends TreeEntity<E, U>, R extends CommonTreeRepository<E, U>, U>
		extends JpaAbstractService<E, R, U> implements CommonJpaTreeService<E, U> {

	@Autowired
	public JpaTreeAbstractService(R repository, Class<E> clazz) {
		super(repository, clazz);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<E> findByParentIsNull() {
		return this.repository.findByParentIsNull();
	};

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<E> findByParentId(Long parentId, Sort sort) {
		return this.repository.findByParentId(parentId, sort);
	};

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<E> findByIdIsNot(Long id) {
		return this.repository.findByIdIsNot(id);
	};

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public E create() {
		E entity = null;
		try {
			entity = this.clazz.getDeclaredConstructor().newInstance();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return entity;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public E create(Long parentId) {
		E entity = null;
		try {
			entity = this.clazz.getDeclaredConstructor().newInstance();
			entity.setParent(this.repository.findById(parentId)
					.orElseThrow(() -> new IllegalArgumentException("Invalid master Id:" + parentId)));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return entity;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public Optional<E> save(Long parentId, E entity) {
		entity.setParent(this.repository.findById(parentId)
				.orElseThrow(() -> new IllegalArgumentException("Invalid parent Id:" + parentId)));
		return Optional.of(this.repository.save(entity));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public E createChild(Long parentId) {
		E entity = null;
		try {
			entity = this.create();
			entity.setParent(this.repository.findById(parentId)
					.orElseThrow(() -> new IllegalArgumentException("Invalid parent Id:" + parentId)));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return entity;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public Optional<E> saveChild(Long parentId, E entity) {
		entity.setParent(this.repository.findById(parentId)
				.orElseThrow(() -> new IllegalArgumentException("Invalid software parent Id:" + parentId)));
		return this.save(entity);
	};

}