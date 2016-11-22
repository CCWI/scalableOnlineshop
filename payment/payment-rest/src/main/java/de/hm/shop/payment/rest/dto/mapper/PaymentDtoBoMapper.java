package de.hm.shop.payment.rest.dto.mapper;

import org.mapstruct.Mapper;

import de.hm.shop.payment.rest.dto.PaymentDto;
import de.hm.shop.payment.service.api.bo.PaymentBo;


/**
 * Mapper zwischen Dto und Bo (MapStruct)
 * @author Maximilian.Auch
 */
@Mapper
public interface PaymentDtoBoMapper {

	public PaymentDto mapBoToDto(PaymentBo paymentBo);



	public PaymentBo mapDtoToBo(PaymentDto paymentDto);
}
