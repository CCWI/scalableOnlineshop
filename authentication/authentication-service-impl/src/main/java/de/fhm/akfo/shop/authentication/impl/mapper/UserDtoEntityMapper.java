package de.fhm.akfo.shop.authentication.impl.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import de.fhm.akfo.shop.authentication.entity.Role;
import de.fhm.akfo.shop.authentication.entity.User;
import de.fhm.akfo.shop.authentication.service.api.dto.RoleDto;
import de.fhm.akfo.shop.authentication.service.api.dto.UserDto;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserDtoEntityMapper {

	UserDtoEntityMapper INSTANCE = Mappers.getMapper( UserDtoEntityMapper.class );
	
	@Mapping(target = "roles", ignore=true)
	UserDto entityToDto(User User);
		
	@Mapping(target = "roles", ignore=true)
    User dtoToEntity(UserDto User);
	
	Role roleDtoToEntity(RoleDto Dto);
	
	RoleDto roleEntityToDto(Role role);
}
