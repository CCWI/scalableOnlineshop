package de.hm.shop.shippment.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.hm.shop.shippment.dao.entity.ShippmentEntity;
import de.hm.shop.shippment.dao.repo.ShippmentRepository;
import de.hm.shop.shippment.service.api.ShippmentService;
import de.hm.shop.shippment.service.api.bo.ShippmentBo;
import de.hm.shop.shippment.service.api.exception.ShippmentException;
import de.hm.shop.shippment.service.impl.mapper.ShippmentBoEntityMapper;


/**
 * Implementierung von {@link ShippmentService}.
 * @author Maximilian.Auch
 */
@Service
@Transactional
public class ShippmentServiceImpl implements ShippmentService {

	private static final Logger LOG = LoggerFactory.getLogger(ShippmentServiceImpl.class);

	@Inject
	private ShippmentRepository shippmentRepository;

	@Inject
	private ShippmentBoEntityMapper shippmentMapper;



	@Transactional(readOnly = true)
	public List<ShippmentBo> getAll() {
		final List<ShippmentBo> shippmentBos = new ArrayList<ShippmentBo>();

		final Iterable<ShippmentEntity> shippmentEntities = shippmentRepository.findAll();
		if (shippmentEntities != null) {
			for (final ShippmentEntity shippmentEntity : shippmentEntities) {
				shippmentBos.add(shippmentMapper.mapEntityToBo(shippmentEntity));
			}
		}

		return shippmentBos;
	}



	@Transactional(readOnly = true)
	public ShippmentBo getById(final long id) {
		final ShippmentEntity shippmentEntity = shippmentRepository.findOne(id);
		return shippmentEntity != null ? shippmentMapper.mapEntityToBo(shippmentEntity) : null;
	}



	public ShippmentBo save(final ShippmentBo shippmentBo) throws ShippmentException {
		Validate.notNull(shippmentBo);
		LOG.debug("Speichere ShippmentEntity für den Artikel mit der Id: {}", shippmentBo.getId());

		final ShippmentEntity shippmentEntity = shippmentMapper.mapBoToEntity(shippmentBo);
		final ShippmentEntity shippmentEntitySaved = shippmentRepository.save(shippmentEntity);

		return shippmentMapper.mapEntityToBo(shippmentEntitySaved);
	}



	public void delete(final Long id) {
		Validate.notNull(id);
		LOG.debug("Lösche Example mit Id {}", id);

		if (shippmentRepository.exists(id)) {
			shippmentRepository.delete(id);
		}
	}
}
