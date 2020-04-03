package com.oborodulin.softreport.domain.common.service;

import java.util.List;
import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

import com.oborodulin.softreport.domain.common.entity.AuditableEntity;
import com.oborodulin.softreport.domain.common.entity.DetailEntity;
import com.oborodulin.softreport.domain.common.repository.CommonDetailRepository;
import com.oborodulin.softreport.domain.common.repository.CommonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;

public abstract class JpaDetailAbstractService<E extends AuditableEntity<U>, D extends DetailEntity<E, U>, M extends CommonRepository<E, U>, R extends CommonDetailRepository<E, D, U>, U>
		extends JpaAbstractService<D, R, U> implements CommonJpaDetailService<E, D, U> {
	protected final M masterRepository;
	private Class<D> clazz;

	@Autowired
	public JpaDetailAbstractService(M masterRepository, R repository, Class<D> clazz) {
		super(repository);
		this.masterRepository = masterRepository;
		this.clazz = clazz;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional(readOnly = true)
	public List<D> findByMaster(E master, Sort sort) {
		return this.repository.findByMaster(master, sort);
	};

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional(readOnly = true)
	public List<D> findByMasterId(Long id, Sort sort) {
		return this.repository.findByMaster_Id(id, sort);
	};

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public D create() {
		D entity = null;
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
	public D create(Long masterId) {
		D entity = null;
		try {
			entity = this.clazz.getDeclaredConstructor().newInstance();
			entity.setMaster(this.masterRepository.findById(masterId)
					.orElseThrow(() -> new IllegalArgumentException("Invalid master Id:" + masterId)));
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
	public Optional<D> save(Long masterId, D entity) {
		entity.setMaster(this.masterRepository.findById(masterId)
				.orElseThrow(() -> new IllegalArgumentException("Invalid master Id:" + masterId)));
		return this.save(entity);
	}

}