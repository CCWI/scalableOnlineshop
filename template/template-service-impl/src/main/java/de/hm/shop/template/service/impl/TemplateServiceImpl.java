package de.hm.shop.template.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.hm.shop.template.dao.entity.TemplateEntity;
import de.hm.shop.template.dao.repo.TemplateRepository;
import de.hm.shop.template.service.api.TemplateService;
import de.hm.shop.template.service.api.bo.TemplateBo;
import de.hm.shop.template.service.api.exception.TemplateException;
import de.hm.shop.template.service.impl.mapper.TemplateBoEntityMapper;


/**
 * Implementierung von {@link TemplateService}.
 * @author Maximilian.Auch
 */
@Service
@Transactional
public class TemplateServiceImpl implements TemplateService {

	private static final Logger LOG = LoggerFactory.getLogger(TemplateServiceImpl.class);

	@Inject
	private TemplateRepository templateRepository;

	@Inject
	private TemplateBoEntityMapper templateMapper;



	@Transactional(readOnly = true)
	public List<TemplateBo> getAll() {
		final List<TemplateBo> templateBos = new ArrayList<TemplateBo>();

		final Iterable<TemplateEntity> templateEntities = templateRepository.findAll();
		if (templateEntities != null) {
			for (final TemplateEntity templateEntity : templateEntities) {
				templateBos.add(templateMapper.mapEntityToBo(templateEntity));
			}
		}

		return templateBos;
	}



	@Transactional(readOnly = true)
	public TemplateBo getById(final long id) {
		final TemplateEntity templateEntity = templateRepository.findOne(id);
		return templateEntity != null ? templateMapper.mapEntityToBo(templateEntity) : null;
	}



	public TemplateBo save(final TemplateBo templateBo) throws TemplateException {
		Validate.notNull(templateBo);
		LOG.debug("Speichere Example mit Namen {}", templateBo.getName());

		final TemplateEntity templateEntity = templateMapper.mapBoToEntity(templateBo);
		final TemplateEntity templateEntitySaved = templateRepository.save(templateEntity);

		return templateMapper.mapEntityToBo(templateEntitySaved);
	}



	public void delete(final Long id) {
		Validate.notNull(id);
		LOG.debug("Lösche Example mit Id {}", id);

		if (templateRepository.exists(id)) {
			templateRepository.delete(id);
		}
	}
}
