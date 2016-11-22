package de.hm.shop.template.service.impl.mapper;

import org.mapstruct.Mapper;

import de.hm.shop.template.dao.entity.TemplateEntity;
import de.hm.shop.template.service.api.bo.TemplateBo;


/**
 * 
 * @author Maximilian.Auch
 */
@Mapper
public interface TemplateBoEntityMapper {

	public TemplateBo mapEntityToBo(TemplateEntity templateEntity);



	public TemplateEntity mapBoToEntity(TemplateBo templateBo);
}
