package de.hm.shop.shippment.service.impl.mapper;

import org.mapstruct.Mapper;

import de.hm.shop.shippment.dao.entity.ShippmentEntity;
import de.hm.shop.shippment.service.api.bo.ShippmentBo;


/**
 * 
 * @author Maximilian.Auch
 */
@Mapper
public interface ShippmentBoEntityMapper {

	public ShippmentBo mapEntityToBo(ShippmentEntity shippmentEntity);



	public ShippmentEntity mapBoToEntity(ShippmentBo shippmentBo);
}
