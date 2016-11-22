package de.hm.shop.template.rest.dto.mapper;

import org.mapstruct.Mapper;

import de.hm.shop.template.rest.dto.TemplateDto;
import de.hm.shop.template.service.api.bo.TemplateBo;


/**
 * Mapper zwischen Dto und Bo (MapStruct)
 * @author Maximilian.Auch
 */
@Mapper
public interface TemplateDtoBoMapper {

	public TemplateDto mapBoToDto(TemplateBo templateBo);



	public TemplateBo mapDtoToBo(TemplateDto templateDto);
}
