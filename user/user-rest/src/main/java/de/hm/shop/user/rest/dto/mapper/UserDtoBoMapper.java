package de.hm.shop.user.rest.dto.mapper;

import org.mapstruct.Mapper;

import de.hm.shop.user.rest.dto.UserDto;
import de.hm.shop.user.service.api.bo.UserBo;


/**
 * Mapper zwischen Dto und Bo (MapStruct)
 * @author Maximilian.Auch
 */
@Mapper
public interface UserDtoBoMapper {

	public UserDto mapBoToDto(UserBo userBo);



	public UserBo mapDtoToBo(UserDto userDto);
}
