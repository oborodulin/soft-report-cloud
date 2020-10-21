package com.oborodulin.softreport.domain.common.service;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;

import com.oborodulin.softreport.domain.common.entity.AuditableEntity;
import com.oborodulin.softreport.domain.common.entity.DetailEntity;
import com.oborodulin.softreport.domain.common.repository.CommonDetailRepository;
import com.oborodulin.softreport.domain.common.repository.CommonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;

public abstract class AbstractJpaDetailService<E extends AuditableEntity<U>, D extends DetailEntity<E, U>, M extends CommonRepository<E, U>, R extends CommonDetailRepository<E, D, U>, U>
		extends AbstractJpaService<D, R, U> implements CommonJpaDetailService<E, D, U> {
	protected final M masterRepository;
	private Class<D> clazz;

	@Autowired
	public AbstractJpaDetailService(M masterRepository, R repository, Class<D> clazz) {
		super(repository);
		this.masterRepository = masterRepository;
		this.clazz = clazz;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional(readOnly = true)
	public List<D> entities(E master, Sort sort) {
		return this.repository.findByMaster(master, sort);
	};

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional(readOnly = true)
	public List<D> entities(Long masterId, Sort sort) {
		return this.repository.findByMaster_Id(masterId, sort);
	};

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public D createdEntity() {
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
	public D createdEntity(Long masterId) {
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
	public void save(Long masterId, D entity) {
		entity.setMaster(this.masterRepository.findById(masterId)
				.orElseThrow(() -> new IllegalArgumentException("Invalid master Id:" + masterId)));
		this.save(entity);
	}

}