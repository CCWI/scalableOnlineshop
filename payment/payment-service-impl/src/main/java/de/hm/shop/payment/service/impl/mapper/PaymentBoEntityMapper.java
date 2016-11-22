package de.hm.shop.payment.service.impl.mapper;

import org.mapstruct.Mapper;

import de.hm.shop.payment.dao.entity.PaymentEntity;
import de.hm.shop.payment.service.api.bo.PaymentBo;


/**
 * 
 * @author Maximilian.Auch
 */
@Mapper
public interface PaymentBoEntityMapper {

	public PaymentBo mapEntityToBo(PaymentEntity paymentEntity);



	public PaymentEntity mapBoToEntity(PaymentBo paymentBo);
}
