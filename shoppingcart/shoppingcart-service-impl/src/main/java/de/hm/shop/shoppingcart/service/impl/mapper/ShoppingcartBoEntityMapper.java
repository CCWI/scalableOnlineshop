package de.hm.shop.shoppingcart.service.impl.mapper;

import org.mapstruct.Mapper;

import de.hm.shop.shoppingcart.dao.entity.ShoppingcartEntity;
import de.hm.shop.shoppingcart.service.api.bo.ShoppingcartBo;



@Mapper
public interface ShoppingcartBoEntityMapper {

	public ShoppingcartBo mapEntityToBo(ShoppingcartEntity shoppingcartEntity);



	public ShoppingcartEntity mapBoToEntity(ShoppingcartBo shoppingcartBo);
}
