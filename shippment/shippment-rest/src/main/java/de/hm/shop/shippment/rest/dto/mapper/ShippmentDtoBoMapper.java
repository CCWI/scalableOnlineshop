package de.hm.shop.shippment.rest.dto.mapper;

import org.mapstruct.Mapper;

import de.hm.shop.shippment.rest.dto.ShippmentDto;
import de.hm.shop.shippment.service.api.bo.ShippmentBo;


/**
 * Mapper zwischen Dto und Bo (MapStruct)
 * @author Maximilian.Auch
 */
@Mapper
public interface ShippmentDtoBoMapper {

	public ShippmentDto mapBoToDto(ShippmentBo shippmentBo);



	public ShippmentBo mapDtoToBo(ShippmentDto shippmentDto);
}
