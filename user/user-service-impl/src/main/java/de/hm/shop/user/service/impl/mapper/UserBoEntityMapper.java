package de.hm.shop.user.service.impl.mapper;

import org.mapstruct.Mapper;

import de.hm.shop.user.dao.entity.UserEntity;
import de.hm.shop.user.service.api.bo.UserBo;


/**
 * 
 * @author Maximilian.Auch
 */
@Mapper
public interface UserBoEntityMapper {

	public UserBo mapEntityToBo(UserEntity userEntity);



	public UserEntity mapBoToEntity(UserBo userBo);
}
