package de.hm.shop.shoppingcart.rest.dto.mapper;

import org.mapstruct.Mapper;

import de.hm.shop.shoppingcart.rest.dto.ShoppingcartDto;
import de.hm.shop.shoppingcart.service.api.bo.ShoppingcartBo;



@Mapper
public interface ShoppingcartDtoBoMapper {

	public ShoppingcartDto mapBoToDto(ShoppingcartBo shoppingcartBo);



	public ShoppingcartBo mapDtoToBo(ShoppingcartDto shoppingcartDto);
}
